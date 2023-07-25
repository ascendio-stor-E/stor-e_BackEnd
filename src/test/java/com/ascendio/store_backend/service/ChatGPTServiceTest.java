package com.ascendio.store_backend.service;

import com.ascendio.store_backend.dto.chatgpt.ChatGPTMessage;
import com.ascendio.store_backend.dto.chatgpt.ChatGPTResponse;
import com.ascendio.store_backend.dto.chatgpt.Choice;
import com.ascendio.store_backend.dto.store.StoryContinueResponseDto;
import com.ascendio.store_backend.dto.store.StoryStartResponseDto;
import com.ascendio.store_backend.model.ChatGPTHistory;
import com.ascendio.store_backend.model.Story;
import com.ascendio.store_backend.model.StoryBook;
import com.ascendio.store_backend.model.StoryBookStatus;
import com.ascendio.store_backend.repository.StoryHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChatGPTServiceTest {
    StoryHistoryRepository storyHistoryRepository;
    StoryBookService storyBookService;
    DalleImageGeneratorService dalleImageGeneratorService;
    StoryService storyService;
    RestTemplate restTemplate;
    ImageBlobService imageBlobService;
    ChatGPTService chatGPTService;

    @BeforeEach
    void setUp() {
        storyHistoryRepository = mock();
        storyBookService = mock();
        dalleImageGeneratorService = mock();
        storyService = mock();
        imageBlobService = mock();
        restTemplate = mock();

        chatGPTService = new ChatGPTService(
                "chatCompletionURL",
                "chatCompletionModel",
                "initialPrompt",
                "secondPrompt",
                "randomPrompt",
                "apikey",
                storyHistoryRepository,
                storyBookService,
                storyService,
                dalleImageGeneratorService,
                imageBlobService,
                restTemplate
        );
    }

    @Test
    public void startStoryBookShouldGenerateAndExtract3Options() {

        UUID uuid =  UUID.randomUUID();
        when(restTemplate.postForObject(anyString(), any(), any())).thenReturn(
                        new ChatGPTResponse("testConversationId1234",
                        List.of(new Choice(
                                     new ChatGPTMessage(
                                                "assistant",
                                                "Option 1:The Mischievous Little Squirrel\n" +
                                                        "Option 2:The Magical Adventures of Coco the Cat\n" +
                                                        "Option 3:The Brave Teddy Bear's Treasure Hunt"))
                        )));

        when(storyBookService.createStoryBook()).thenReturn(
                new StoryBook(
                        uuid,
                        "title",
                        "image",
                        StoryBookStatus.COMPLETE));

        StoryStartResponseDto storyStartResponseDto = chatGPTService.startStoryBook("Ayse");


        StoryStartResponseDto expected = new StoryStartResponseDto(
                List.of(
                        "The Mischievous Little Squirrel",
                        "The Magical Adventures of Coco the Cat",
                        "The Brave Teddy Bear's Treasure Hunt"
                ), "testConversationId1234", uuid);

        assertEquals(expected, storyStartResponseDto);
    }

    @Test
    public void continueStoryBookShouldGenerateStoryForGivenPartAnd3OptionsForNextPart(){

        UUID uuid =  UUID.randomUUID();
        UUID uuid2 =  UUID.randomUUID();
        StoryBook storyBook = new StoryBook();
        Story story = new Story("The Curious Case of Sammy the Squirrel",1,null,storyBook);
        story.setId(uuid2);
        ArrayList<ChatGPTHistory> previousMessage = new ArrayList<>();
        previousMessage.add(new ChatGPTHistory(uuid,"testConversationId1234",
                "The Curious Case of Sammy the Squirrel",
                "user",0L,storyBook));
        previousMessage.add(new ChatGPTHistory(uuid,"testConversationId1234",
                "The Curious Case of Sammy the Squirrel",
                "user",0L,storyBook));
        when(storyHistoryRepository.findPreviousMessages(
                "testConversationId1234")).thenReturn(previousMessage);

        when(restTemplate.postForObject(anyString(),any(),any())).thenReturn(
                new ChatGPTResponse("testConversationId1234",
                List.of(new Choice(
                        new ChatGPTMessage(
                                "assistant",
                                "Part 1\n\n" +
                                        "The Curious Case of Sammy the Squirrel\n\n" +
                                        "Option 1:The Mischievous Little Squirrel\n" +
                                        "Option 2:The Magical Adventures of Coco the Cat\n" +
                                        "Option 3:The Brave Teddy Bear's Treasure Hunt"))
                )));

        when(dalleImageGeneratorService.generateImage("The Curious Case of Sammy the Squirrel"))
                .thenReturn("ImageUrl");

        when(storyBookService.getStoryBookById(uuid, Set.of(StoryBookStatus.DRAFT))).thenReturn(storyBook);
        when(imageBlobService.addToBlobStorage("ImageUrl",uuid,1)).thenReturn("ImageName");
        when(storyService.saveStory("The Curious Case of Sammy the Squirrel",
                1,
                null,
                storyBook)).thenReturn(story);


        StoryContinueResponseDto storyStartResponseDto = chatGPTService.continueStoryBook(
                1,
                "testConversationId1234",
                uuid,
                1
                );

        StoryContinueResponseDto expected = new StoryContinueResponseDto(
                "Part 1",
                "The Curious Case of Sammy the Squirrel",
                List.of(
                        "The Mischievous Little Squirrel",
                        "The Magical Adventures of Coco the Cat",
                        "The Brave Teddy Bear's Treasure Hunt"
                ), null, uuid2);

        assertEquals(expected, storyStartResponseDto);
    }
}