package com.ascendio.store_backend.service;

import com.ascendio.store_backend.dto.*;
import com.ascendio.store_backend.model.ChatGPTHistory;
import com.ascendio.store_backend.model.StoryBook;
import com.ascendio.store_backend.model.StoryUser;
import com.ascendio.store_backend.repository.StoryHistoryRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChatGPTService {
    final String chatCompletionURL;
    final String chatCompletionModel;
    final String initialPrompt;
    final String secondPrompt;
    final String apikey;
    final StoryHistoryRepository storyHistoryRepository;
    final StoryBookService storyBookService;
    final StoryService storyService;
    final DalleImageGeneratorService dalleImageGeneratorService;
    final RestTemplate restTemplate;
    String apiEndpoint = "/v1/chat/completions";

    public ChatGPTService(
            @Value("${openai.api-url}")
            String chatCompletionURL,
            @Value("${openai.model}")
            String chatCompletionModel,
            @Value("${openai.api-initialprompt}")
            String initialPrompt,
            @Value("${openai.api-secondprompt}")
            String secondPrompt,
            @Value("${openai.api-key}")
            String apikey,
            StoryHistoryRepository storyHistoryRepository,
            StoryBookService storyBookService,
            StoryService storyService,
            DalleImageGeneratorService dalleImageGeneratorService,
            RestTemplate restTemplate
    ) {
        this.chatCompletionURL = chatCompletionURL;
        this.chatCompletionModel = chatCompletionModel;
        this.initialPrompt = initialPrompt;
        this.secondPrompt = secondPrompt;
        this.apikey = apikey;
        this.storyHistoryRepository = storyHistoryRepository;
        this.storyBookService = storyBookService;
        this.dalleImageGeneratorService = dalleImageGeneratorService;
        this.storyService=storyService;
        this.restTemplate = restTemplate;
    }

    public StoryStartResponseDto startStoryBook() {
        long requestTime = System.currentTimeMillis();

        ChatGPTResponse response = sendChatGPTRequest(
                List.of(
                        new ChatGPTMessage("user", initialPrompt)
                )
        );

        String content = response.choices().get(0).message().content();

        ChatGPTHistory chatGPTResponseHistory = new ChatGPTHistory(UUID.randomUUID(), response.id(), content, "assistant", System.currentTimeMillis());
        ChatGPTHistory chatGPTRequestHistory = new ChatGPTHistory(UUID.randomUUID(), response.id(), initialPrompt, "user", requestTime);
        storyHistoryRepository.saveStory(chatGPTRequestHistory);
        storyHistoryRepository.saveStory(chatGPTResponseHistory);

        List<String> options = getOptions(content.split("\n"));

//        Create story book here
        StoryBook storyBook = storyBookService.saveStoryBook();


        return new StoryStartResponseDto(options, response.id(), storyBook.getId());
    }

    public StoryContinueResponseDto continueStoryBook(int optionChoice, String conversationId, UUID storyBookId, int pageNumber) {
        List<ChatGPTHistory> previousMessages = storyHistoryRepository.findPreviousMessages(conversationId);

        String prompt;
        if (previousMessages.size() == 2) {
            prompt = "I chose Option " + optionChoice + ". " + secondPrompt;
        } else if (previousMessages.size() == 10) {
            prompt = "I chose Option " + optionChoice + ". This the last part the story should end with maximum 100 word.";
        } else {
            prompt = "I chose Option " + optionChoice;
        }

        ChatGPTHistory continueStory = new ChatGPTHistory(
                UUID.randomUUID(),
                conversationId,
                prompt,
                "user",
                System.currentTimeMillis());
        previousMessages.add(continueStory);

        ChatGPTResponse response = sendChatGPTRequest(
                previousMessages
                        .stream()
                        .map(storyhistory ->
                                new ChatGPTMessage(storyhistory.getRole(), storyhistory.getContent())
                        )
                        .toList()
        );

        String content = response.choices().get(0).message().content();

        ChatGPTHistory chatGPTResponseHistory = new ChatGPTHistory(UUID.randomUUID(), conversationId, content, "assistant", System.currentTimeMillis());
        storyHistoryRepository.saveStory(chatGPTResponseHistory);
        storyHistoryRepository.saveStory(continueStory);

        String[] lines = content.split("\n");

        String part = lines[0];

        String story = Arrays.stream(lines)
                .filter(line -> !line.startsWith("Part ") && !line.isEmpty())
                .findFirst()
                .orElseThrow();

        List<String> options = getOptions(lines);


        String imageUrl = dalleImageGeneratorService.generateImage(story);

        Optional<StoryBook> storyBook = storyBookService.findStoryBookById(storyBookId);
        // save story here
        storyService.saveStory(story, pageNumber,imageUrl, storyBook.get());

        return new StoryContinueResponseDto(part, story, options);
    }

    public ChatGPTResponse sendChatGPTRequest(List<ChatGPTMessage> messages) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + apikey);

        HttpEntity<ChatGPTRequest> entity = new HttpEntity<>(
                new ChatGPTRequest(
                        chatCompletionModel,
                        messages,
                        1,
                        1.0
                ),
                headers
        );

        return restTemplate.postForObject(chatCompletionURL + apiEndpoint, entity, ChatGPTResponse.class);
    }

    public List<String> getOptions(String[] lines) {
        return Arrays.stream(lines)
                .filter(line -> line.startsWith("Option "))
                .map(optionLine -> optionLine.split(":"))
                .map(option -> {
                    String s = option[1].trim();
                    if (s.startsWith("\"")) {
                        s = s.substring(1);
                    }
                    if (s.endsWith("\"")) {
                        s = s.substring(0, s.length() - 2);
                    }
                    return s;
                })
                .toList();
    }
}
