package com.ascendio.store_backend.shared.utils;

import com.ascendio.store_backend.storybooks.StoryBookResponseDto;
import com.ascendio.store_backend.stories.StoryDTO;
import com.ascendio.store_backend.stories.StoryResponseDto;
import com.ascendio.store_backend.stories.Story;
import com.ascendio.store_backend.storybooks.StoryBook;

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
                storyBook.getStories().size(),
                storyBook.getLastModifiedDate()
        );
    }

    public static List<StoryResponseDto> toStoryResponseDtoList(List<Story> stories) {
        return stories.stream().map(Converter::toStoryResponseDto).toList();
    }

    public static List<StoryBookResponseDto> toStoryBookResponseDtoList(List<StoryBook> storyBooks) {
        return storyBooks.stream().map(Converter::toStoryBookResponseDto).toList();
    }

    public static List<StoryDTO> storyListToDTO(List<Story> stories) {
        return stories.stream().map(s -> new StoryDTO(s.getId(), s.getTextContent(), s.getPageNumber(), s.getImage())).toList();
    }

}
