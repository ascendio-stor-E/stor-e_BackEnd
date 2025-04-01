package com.ascendio.store_backend.dalle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class DalleImageGeneratorService {
    private final RestTemplate restTemplate;
    private final String openAiApiKey;
    private final String openAiApiUrl;

    public DalleImageGeneratorService(
            @Value("${openai.api-key}") String openAiApiKey,
            @Value("${openai.api-url}") String openAiApiUrl,
            RestTemplate restTemplate
    ) {
        this.restTemplate = restTemplate;
        this.openAiApiKey = openAiApiKey;
        this.openAiApiUrl = openAiApiUrl;
    }

    public String generateImage(String storyText) {

        if (!StringUtils.hasText(storyText)) {
            throw new IllegalArgumentException("storyText must not be empty");
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(openAiApiKey);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        String ILLUSTRATION_STYLE = "Seussian style cartoon: ";
        String prompt = ILLUSTRATION_STYLE + storyText;

        String IMAGE_SIZE = "512x512";
        Integer NUMBER_OF_IMAGES = 1;
        DalleImageGenerationRequest requestBody = new DalleImageGenerationRequest(prompt, IMAGE_SIZE, NUMBER_OF_IMAGES);

        HttpEntity<DalleImageGenerationRequest> httpEntity = new HttpEntity<>(requestBody, httpHeaders);

        String apiEndpoint = "/v1/images/generations";
        ResponseEntity<DalleImageGenerationResponse> response = restTemplate.exchange(
                openAiApiUrl + apiEndpoint,
                HttpMethod.POST,
                httpEntity,
                DalleImageGenerationResponse.class
        );

        return Objects.requireNonNull(response.getBody()).data()[0].url();
    }
}
