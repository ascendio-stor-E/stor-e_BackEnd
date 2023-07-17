package com.ascendio.store_backend.config;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "openai")
public record OpenAiConfiguration(@NotEmpty String key, @NotEmpty String api) {


}
