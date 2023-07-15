package com.ascendio.store_backend.util;

import com.ascendio.store_backend.dto.StoryBookRequestDto;
import com.ascendio.store_backend.dto.StoryBookResponseDto;
import com.ascendio.store_backend.dto.StoryRequestDto;
import com.ascendio.store_backend.dto.StoryResponseDto;
import com.ascendio.store_backend.model.Story;
import com.ascendio.store_backend.model.StoryBook;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Converter {

    public Story fromStoryRequestDto(StoryRequestDto dto) {
        return new Story();
    }

    public List<StoryResponseDto> toStoryResponseDtoList(List<Story> stories) {
        List<StoryResponseDto> storyResponses = null;
        return storyResponses;
    }

    public StoryResponseDto toStoryResponseDto(Story story) {
        StoryResponseDto storyResponse = null;
        return storyResponse;
    }

    public StoryBook fromStoryBookRequestDto(StoryBookRequestDto dto) {
        return new StoryBook();
    }

    public List<StoryBookResponseDto> toStoryBookResponseDtoList(List<StoryBook> storyBooks) {
        List<StoryBookResponseDto> storyBookResponses = null;
        return storyBookResponses;
    }

    public StoryBookResponseDto toStoryBookResponseDto(StoryBook storyBook) {
        StoryBookResponseDto storyBookResponse = null;
        return storyBookResponse;
    }
}
