package com.ascendio.store_backend.util;

import com.ascendio.store_backend.dto.*;
import com.ascendio.store_backend.model.Story;
import com.ascendio.store_backend.model.StoryBook;

import java.util.List;

public class Converter {

    public static StoryResponseDto toStoryResponseDto(Story story) {
        return new StoryResponseDto(
                story.getId(),
                story.getTextContent(),
                story.getPageNumber(),
                story.getImage(),
                story.getStoryBook().getId(),
                story.getStoryBook().getTitle(),
                story.getStoryBook().getCoverImage(),
                story.getStoryBook().getStatus()
        );
    }

    public static StoryBookResponseDto toStoryBookResponseDto(StoryBook storyBook) {

        return new StoryBookResponseDto(
                storyBook.getId(),
                storyBook.getTitle(),
                storyBook.getCoverImage(),
                storyBook.getStatus(),
                storyBook.getStoryUser().getId(),
                storyBook.getStoryUser().getName(),
                storyBook.getStoryUser().getEmail()
        );
    }

    public static List<StoryResponseDto> toStoryResponseDtoList(List<Story> stories) {
        List<StoryResponseDto> storyResponseDtoList = stories.stream().map(story -> toStoryResponseDto(story)).toList();
        return storyResponseDtoList;
    }

    public static List<StoryBookResponseDto> toStoryBookResponseDtoList(List<StoryBook> storyBooks) {
        List<StoryBookResponseDto> storyBookResponses = storyBooks.stream().map(storyBook -> toStoryBookResponseDto(storyBook)).toList();
        return storyBookResponses;
    }

    public static List<StoryDTO> storyListToDTO(List<Story> stories) {
        return stories.stream().map(s -> new StoryDTO(s.getId(), s.getTextContent(), s.getPageNumber(), s.getImage())).toList();
    }

}
