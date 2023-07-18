package com.ascendio.store_backend.service;

import com.ascendio.store_backend.dto.*;
import com.ascendio.store_backend.repository.StoryHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChatGPTServiceTest {
    // @Mock
    StoryHistoryRepository storyHistoryRepository;
    //@Mock
    RestTemplate restTemplate;

    ChatGPTService chatGPTService;

    @BeforeEach
    void setUp() {
        storyHistoryRepository = mock();
        restTemplate = mock();
        chatGPTService = new ChatGPTService(
                "chatCompletionURL",
                "chatCompletionModel",
                "initialPrompt",
                "secondPrompt",
                "apikey",
                storyHistoryRepository,
                restTemplate
        );
    }

    @Test
    public void shoulReturnInitialStory() {
        when(restTemplate.postForObject(anyString(), any(), any())).thenReturn(
                        new ChatGPTResponse("testConversationId1234",
                        List.of(
                                new Choice(
                                        new ChatGPTMessage(
                                                "assistant",
                                                "Option 1:The Mischievous Little Squirrel\n" +
                                                        "Option 2:The Magical Adventures of Coco the Cat\n" +
                                                        "Option 3:The Brave Teddy Bear's Treasure Hunt"))
                        )));

        StoryStartResponseDto storyStartResponseDto = chatGPTService.startStoryBook();

        StoryStartResponseDto expected = new StoryStartResponseDto(
                List.of(
                        "The Mischievous Little Squirrel",
                        "The Magical Adventures of Coco the Cat",
                        "The Brave Teddy Bear's Treasure Hunt"
                ), "testConversationId1234");

        assertEquals(expected, storyStartResponseDto);
    }
}