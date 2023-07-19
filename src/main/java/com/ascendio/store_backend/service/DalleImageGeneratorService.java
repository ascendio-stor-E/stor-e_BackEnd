package com.ascendio.store_backend.service;

import com.ascendio.store_backend.dto.DalleImageGenerationRequest;
import com.ascendio.store_backend.dto.DalleImageGenerationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

@Service
public class DalleImageGeneratorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DalleImageGeneratorService.class);
    private static final String IMAGE_SIZE = "512x512";
    private static final Integer NUMBER_OF_IMAGES = 1;
    private static final String ILLUSTRATION_STYLE = "Seussian style cartoon: ";
    private final RestTemplate restTemplate;
    private String openAiApiKey;
    private String openAiApiUrl;
    private final String apiEndpoint = "/v1/images/generations";

    public DalleImageGeneratorService(@Value("${openai.api-key}") String openAiApiKey,
                                      @Value("${openai.api-url}") String openAiApiUrl,
                                      RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.openAiApiKey = openAiApiKey;
        this.openAiApiUrl = openAiApiUrl;
    }

    public String generateImage(String storyLine) {

        if (!StringUtils.hasText(storyLine)) {
            throw new IllegalArgumentException("StoryLine must not be empty");
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(openAiApiKey);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        String prompt = ILLUSTRATION_STYLE + storyLine;

        DalleImageGenerationRequest requestBody = new DalleImageGenerationRequest(prompt, IMAGE_SIZE, NUMBER_OF_IMAGES);

        HttpEntity<DalleImageGenerationRequest> httpEntity = new HttpEntity<>(requestBody, httpHeaders);

        ResponseEntity<DalleImageGenerationResponse> response = restTemplate.exchange(
                openAiApiUrl + apiEndpoint,
                HttpMethod.POST,
                httpEntity,
                DalleImageGenerationResponse.class
        );

        return response.getBody().data()[0].url();
    }
}
