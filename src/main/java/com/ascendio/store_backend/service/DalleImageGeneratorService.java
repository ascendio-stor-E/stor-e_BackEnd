package com.ascendio.store_backend.service;

import com.ascendio.store_backend.dto.DalleImageGenerationRequest;
import com.ascendio.store_backend.dto.DalleImageGenerationResponse;
import com.ascendio.store_backend.config.OpenAiConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;
import org.springframework.web.reactive.function.client.WebClient;


@Service
public class DalleImageGeneratorService {

    private final OpenAiConfiguration openai;
    private final WebClient client;
//    private final String apiEndpoint = "/api/stor-e/images/generations";
    private final String apiEndpoint = "/v1/images/generations";

    public DalleImageGeneratorService(OpenAiConfiguration openai, WebClient client) {
        this.openai = openai;
        this.client = client;
    }

    public Mono<String> generateImage(String storyLine) {

        if (!StringUtils.hasText(storyLine)) {
            throw new IllegalArgumentException("Prompt must not be empty");
        }

        Logger LOGGER = LoggerFactory.getLogger(DalleImageGeneratorService.class);
        final var req = new DalleImageGenerationRequest(storyLine);

        return client.post().uri(openai.api() + apiEndpoint)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + openai.key())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(req)
                .retrieve()
                .bodyToMono(DalleImageGenerationResponse.class)
                .doOnSuccess(resp -> {
                    LOGGER.info("Received response from DALL-E for request: {}", storyLine);
                })
                .filter(resp -> resp.data().length > 0)
                .map(resp -> resp.data()[0].url());
    }
}
