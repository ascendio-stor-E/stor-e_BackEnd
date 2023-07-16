package com.ascendio.store_backend.service;

import com.ascendio.store_backend.dto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

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


    RestTemplate restTemplate = new RestTemplate();
    public StoryStartResponseDto startStoryBook(){

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + apikey);

        HttpEntity<ChatGPTRequest> entity = new HttpEntity<>(
                new ChatGPTRequest(chatCompletionModel,
                List.of(new ChatGPTMessage("user", initialPrompt)),
                1,
                1.0,
                        null),
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

      return new StoryStartResponseDto(options, response.id());

    }

    public StoryContinueResponseDto continueStoryBook(int optionChoice, String conversationId) {
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
