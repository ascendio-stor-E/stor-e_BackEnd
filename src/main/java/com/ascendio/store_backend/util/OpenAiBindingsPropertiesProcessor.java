package com.ascendio.store_backend.util;

import org.springframework.cloud.bindings.Bindings;
import org.springframework.cloud.bindings.boot.BindingsPropertiesProcessor;
import org.springframework.core.env.Environment;

import java.util.Map;

public class OpenAiBindingsPropertiesProcessor implements BindingsPropertiesProcessor{

    public static final String TYPE = "openai";

    @Override
    public void process(Environment environment, Bindings bindings, Map<String, Object> properties) {
        bindings.filterBindings(TYPE).forEach(binding -> {
            properties.putIfAbsent("openai.key", binding.getSecret().get("key"));
            properties.putIfAbsent("openai.api", binding.getSecret().get("api"));
        });
    }
}
