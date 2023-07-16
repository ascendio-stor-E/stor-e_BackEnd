package com.ascendio.store_backend.util;

import com.ascendio.store_backend.dto.StoryBookRequestDto;
import com.ascendio.store_backend.dto.StoryBookResponseDto;
import com.ascendio.store_backend.dto.StoryRequestDto;
import com.ascendio.store_backend.dto.StoryResponseDto;
import com.ascendio.store_backend.model.Story;
import com.ascendio.store_backend.model.StoryBook;

import java.util.List;

public class Converter {

    private Converter() {
    }

    public static Story fromStoryRequestDto(StoryRequestDto dto) {
        return new Story();
    }

    public static List<StoryResponseDto> toStoryResponseDtoList(List<Story> stories) {
        List<StoryResponseDto> storyResponses = null;
        return storyResponses;
    }

    public static StoryResponseDto toStoryResponseDto(Story story) {
        StoryResponseDto storyResponse = null;
        return storyResponse;
    }

    public static StoryBook fromStoryBookRequestDto(StoryBookRequestDto dto) {
        return new StoryBook();
    }

    public static List<StoryBookResponseDto> toStoryBookResponseDtoList(List<StoryBook> storyBooks) {
        List<StoryBookResponseDto> storyBookResponses = null;
        return storyBookResponses;
    }

    public static StoryBookResponseDto toStoryBookResponseDto(StoryBook storyBook) {
        StoryBookResponseDto storyBookResponse = null;
        return storyBookResponse;
    }
}
