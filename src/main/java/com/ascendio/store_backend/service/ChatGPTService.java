package com.ascendio.store_backend.service;

import com.ascendio.store_backend.dto.*;
import com.ascendio.store_backend.model.ChatGPTHistory;
import com.ascendio.store_backend.model.StoryBook;
import com.ascendio.store_backend.repository.StoryHistoryRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
    final String randomPrompt;
    final String apikey;
    final StoryHistoryRepository storyHistoryRepository;
    final StoryBookService storyBookService;
    final StoryService storyService;
    final DalleImageGeneratorService dalleImageGeneratorService;
    final ImageBlobService imageBlobService;
    final RestTemplate restTemplate;
    final String apiEndpoint = "/v1/chat/completions";

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
            @Value("${openai.api-randomprompt}")
            String randomPrompt,
            StoryHistoryRepository storyHistoryRepository,
            StoryBookService storyBookService,
            StoryService storyService,
            DalleImageGeneratorService dalleImageGeneratorService,
            ImageBlobService imageBlobService,
            RestTemplate restTemplate
    ) {
        this.chatCompletionURL = chatCompletionURL;
        this.chatCompletionModel = chatCompletionModel;
        this.initialPrompt = initialPrompt;
        this.secondPrompt = secondPrompt;
        this.apikey = apikey;
        this.storyHistoryRepository = storyHistoryRepository;
        this.storyBookService = storyBookService;
        this.storyService = storyService;
        this.dalleImageGeneratorService = dalleImageGeneratorService;
        this.imageBlobService = imageBlobService;
        this.restTemplate = restTemplate;
        this.randomPrompt = randomPrompt;
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

        String storyText = Arrays.stream(lines)
                .filter(line -> !line.startsWith("Part ") && !line.isEmpty())
                .findFirst()
                .orElseThrow();

        List<String> options = getOptions(lines);


        String imageUrl = dalleImageGeneratorService.generateImage(storyText);

        Optional<StoryBook> storyBook = storyBookService.getStoryBookById(storyBookId);
        //add image to blob storage

        String imageName = imageBlobService.addToBlobStorage(imageUrl, storyBookId, pageNumber);

        // save story here
        storyService.saveStory(storyText, pageNumber, imageName, storyBook.get());

        //set first photo as cover image of storyBook
        if (storyBook.isPresent() && pageNumber == 1) {
            storyBook.get().setCoverImage(imageName);
            storyBookService.updateStoryBook(storyBook.get());
        }

        return new StoryContinueResponseDto(part, storyText, options, imageName);
    }

    public RandomStoryResponseDto createRandomStory() {
        long requestTime = System.currentTimeMillis();

        ChatGPTResponse response = sendChatGPTRequest(
                List.of(
                        new ChatGPTMessage("user", randomPrompt)
                )
        );

        String content = response.choices().get(0).message().content();

        ChatGPTHistory chatGPTResponseHistory = new ChatGPTHistory(UUID.randomUUID(), response.id(), content, "assistant", System.currentTimeMillis());
        ChatGPTHistory chatGPTRequestHistory = new ChatGPTHistory(UUID.randomUUID(), response.id(), randomPrompt, "user", requestTime);
        storyHistoryRepository.saveStory(chatGPTRequestHistory);
        storyHistoryRepository.saveStory(chatGPTResponseHistory);

        StoryBook storyBook = storyBookService.saveStoryBook();

        List<StoryContinueResponseDto> stories = Arrays
                .stream(content.split("\n\n"))
                .parallel()
                .map(partsAndStories -> {
                    String[] storyPartAndLine = partsAndStories.split(":");
                    String part = storyPartAndLine[0].trim();
                    String[] partAndNumber = part.split(" ");
                    int pageNumber = Integer.parseInt(partAndNumber[1]);
                    String story = storyPartAndLine[1].trim();

                    String imageUrl = dalleImageGeneratorService.generateImage(story);

                    String imageName = imageBlobService.addToBlobStorage(imageUrl, storyBook.getId(), pageNumber);

                    storyService.saveStory(story, pageNumber, imageName, storyBook);

                    if (pageNumber == 1) {
                        storyBook.setCoverImage(imageName);
                        storyBookService.updateStoryBook(storyBook);
                    }

                    return new StoryContinueResponseDto(part, story, List.of(), imageName);
                })
                .toList();

        return new RandomStoryResponseDto(storyBook.getId(), stories);
    }

    public ChatGPTResponse sendChatGPTRequest(List<ChatGPTMessage> messages) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + apikey);
        headers.setContentType(MediaType.APPLICATION_JSON);

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
                        s = s.substring(0, s.length() - 1);
                    }
                    return s;
                })
                .toList();
    }
}
