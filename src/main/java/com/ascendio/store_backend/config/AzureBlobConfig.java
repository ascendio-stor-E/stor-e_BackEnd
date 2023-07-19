package com.ascendio.store_backend.config;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.storage.blob.BlobServiceAsyncClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.common.policy.RequestRetryOptions;
import com.azure.storage.common.policy.RetryPolicyType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class AzureBlobConfig {

    private static final int MAX_TRIES = 5;
    private static final long TRY_TIMEOUT_SECONDS = 300L;

    private String clientId;
    private String clientSecret;
    private String tenantId;
    private String storageEndpoint;
    private String storageContainer;

    public AzureBlobConfig(
            @Value("${app.config.azure.client-id}") String clientId,
            @Value("${app.config.azure.client-secret}") String clientSecret,
            @Value("${app.config.azure.tenant-id}") String tenantId,
            @Value("${app.config.azure.storage-endpoint}") String storageEndpoint,
            @Value("${app.config.azure.storage.container}") String storageContainer ) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.tenantId = tenantId;
        this.storageEndpoint = storageEndpoint;
        this.storageContainer = storageContainer;
    }

    @Bean
    public BlobServiceClientBuilder blobServiceClientBuilder() {
        return new BlobServiceClientBuilder()
                .credential(getAzureClientCredentials())
                .endpoint(storageEndpoint);
    }

    @Bean
    public BlobServiceClient blobServiceClient(BlobServiceClientBuilder blobServiceClientBuilder) {
        return blobServiceClientBuilder.retryOptions(
                new RequestRetryOptions(
                        RetryPolicyType.EXPONENTIAL,
                        MAX_TRIES,
                        Duration.ofSeconds(TRY_TIMEOUT_SECONDS),
                        null,
                        null,
                        null)).buildClient();
    }

    @Bean
    public BlobServiceAsyncClient blobServiceAsyncClient(
            BlobServiceClientBuilder blobServiceClientBuilder) {
        return blobServiceClientBuilder.retryOptions(
                new RequestRetryOptions(
                        RetryPolicyType.EXPONENTIAL,
                        MAX_TRIES,
                        Duration.ofSeconds(TRY_TIMEOUT_SECONDS),
                        null,
                        null,
                        null)).buildAsyncClient();
    }

    public String getStorageContainer() {
        return storageContainer;
    }

    private ClientSecretCredential getAzureClientCredentials() {
        return new ClientSecretCredentialBuilder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .tenantId(tenantId)
                .build();
    }

}
