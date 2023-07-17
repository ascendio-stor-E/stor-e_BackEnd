package com.ascendio.store_backend.service;

import com.ascendio.store_backend.dto.*;
import com.ascendio.store_backend.model.ChatGPTHistory;
import com.ascendio.store_backend.repository.StoryHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class ChatGPTService {

    @Value("${openai.api.url}")
    String chatCompletionURL;

    @Value("${openai.model}")
    String chatCompletionModel;

    @Value("${openai.api.initialprompt}")
    String initialPrompt;

    @Value("${openai.api.secondprompt}")
    String secondPrompt;

    @Value("${openai.api.key}")
    String apikey;

    @Autowired
    StoryHistoryRepository storyHistoryRepository;

    RestTemplate restTemplate = new RestTemplate();
    public StoryStartResponseDto startStoryBook(){

        long requestTime = System.currentTimeMillis();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + apikey);

        HttpEntity<ChatGPTRequest> entity = new HttpEntity<>(
                new ChatGPTRequest(
                        chatCompletionModel,
                        List.of(
                                new ChatGPTMessage("user", initialPrompt)
                        ),
                        1,
                        1.0,
                        null
                ),
                headers);

        ChatGPTResponse response =  restTemplate.postForObject(chatCompletionURL, entity,
                ChatGPTResponse.class);

        System.out.println(response);

      List<String> options=  Arrays.stream(response.choices().get(0).message().content().split("\\n"))
                .filter(line -> line.startsWith("Option "))
                .map(optionLine ->optionLine.split(":"))
                .map(option -> option[1])
                .toList();

        System.out.println(options);

        ChatGPTHistory chatGPTResponseHistory = new ChatGPTHistory(UUID.randomUUID(), response.id(),response.choices().get(0).toString(), "assistant",System.currentTimeMillis());
        ChatGPTHistory chatGPTRequestHistory = new ChatGPTHistory(UUID.randomUUID(),response.id(), initialPrompt, "user",requestTime);
        storyHistoryRepository.saveStory(chatGPTRequestHistory);
        storyHistoryRepository.saveStory(chatGPTResponseHistory);

      return new StoryStartResponseDto(options, response.id());

    }

    public StoryContinueResponseDto continueStoryBook(int optionChoice, String conversationId) {
         List<ChatGPTHistory> previousMessages = storyHistoryRepository.findPreviousMessages(conversationId);
         ChatGPTHistory continueStory = new ChatGPTHistory(UUID.randomUUID(),conversationId, "I chose Option " + optionChoice + ". " + secondPrompt, "user",System.currentTimeMillis());
         previousMessages.add(continueStory);
         storyHistoryRepository.saveStory(continueStory);


        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + apikey);

        HttpEntity<ChatGPTRequest> entity = new HttpEntity<>(
                new ChatGPTRequest(chatCompletionModel,
                        List.of(new ChatGPTMessage("user", "I chose Option " + optionChoice + ". " + secondPrompt)),
                        1,
                        1.0,
                        conversationId),
                headers);

        ChatGPTResponse response =  restTemplate.postForObject(chatCompletionURL, entity,
                ChatGPTResponse.class);


        System.out.println("second "+ response);

        ChatGPTHistory chatGPTResponseHistory = new ChatGPTHistory(UUID.randomUUID(), response.id(),response.choices().get(0).toString(), "assistant",System.currentTimeMillis());
        storyHistoryRepository.saveStory(chatGPTResponseHistory);

        String [] lines = response.choices().get(0).message().content().split("\n");
        String part = lines[0];
        System.out.println("part :" + part);

        String story = Arrays.stream(lines).filter(line -> !line.startsWith("Part ") && !line.isEmpty())
                .findFirst().orElseThrow();
        System.out.println("story :" + story);

        List<String> options= Arrays.stream(lines)
                .filter(line -> line.startsWith("Option "))
                .map(optionLine ->optionLine.split(":"))
                .map(option -> option[1])
                .toList();
        System.out.println("options :" + options);

        return new StoryContinueResponseDto(part,story,options);

    }
}
