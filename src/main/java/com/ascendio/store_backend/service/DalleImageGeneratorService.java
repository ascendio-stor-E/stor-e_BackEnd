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
    private final RestTemplate restTemplate;
    @Value("${openai.api-key}")
    private String openAiApiKey;
    @Value("${openai.api-url}")
    private String openAiApiUrl;
    private final String apiEndpoint = "/v1/images/generations";

    public DalleImageGeneratorService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String generateImage(String storyLine) {

        if (!StringUtils.hasText(storyLine)) {
            throw new IllegalArgumentException("StoryLine must not be empty");
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(openAiApiKey);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        DalleImageGenerationRequest requestBody = new DalleImageGenerationRequest(storyLine);

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
