package com.ascendio.store_backend.service;

import com.ascendio.store_backend.config.AzureBlobConfig;
import com.azure.core.util.Context;
import com.azure.storage.blob.*;
import com.azure.storage.blob.models.DownloadRetryOptions;
import com.azure.storage.blob.models.ParallelTransferOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

@Service
public class AzureBlobService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AzureBlobService.class);
    private static final int MAX_CONCURRENCY = 5;

    private BlobServiceClient blobServiceClient;
    private BlobServiceAsyncClient blobServiceAsyncClient;
    private AzureBlobConfig azureBlobConfig;

    public AzureBlobService(BlobServiceClient blobServiceClient, BlobServiceAsyncClient blobServiceAsyncClient, AzureBlobConfig azureBlobConfig) {
        this.blobServiceClient = blobServiceClient;
        this.blobServiceAsyncClient = blobServiceAsyncClient;
        this.azureBlobConfig = azureBlobConfig;
    }

    public void uploadToAzureBlob(String imageName, byte[] data) {
        BlobAsyncClient blobAsyncClient = getBlobAsyncClient(azureBlobConfig.getStorageContainer(), imageName);

        blobAsyncClient.upload(Flux.just(ByteBuffer.wrap(data)), getTransferOptions(), true)
                .doOnSuccess(blockBlobItem -> LOGGER.info("Successfully uploaded {}", imageName))
                .doOnError(throwable -> LOGGER.error("Error occurred while uploading. Exception: {}", throwable.getMessage()))
                .subscribe();
    }

    public byte[] getImage(String imageName) {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        downloadBlobFromAzure(bout, azureBlobConfig.getStorageContainer(), imageName);
        return bout.toByteArray();
    }

    private void downloadBlobFromAzure(OutputStream outputStream, String container,
                                       String blobName) {
        BlobClient blobClient = getBlobClient(container, blobName);
        blobClient.downloadStreamWithResponse(outputStream, null,
                new DownloadRetryOptions().setMaxRetryRequests(5),
                null, false, null, Context.NONE);
    }

    private BlobClient getBlobClient(String container, String blobName) {
        BlobContainerClient blobContainerClient = getBlobContainerClient(container);
        return blobContainerClient.getBlobClient(blobName);
    }

    private BlobContainerClient getBlobContainerClient(String container) {
        return blobServiceClient.getBlobContainerClient(container);
    }

    private BlobAsyncClient getBlobAsyncClient(String container, String blobName) {
        BlobContainerAsyncClient blobContainerAsyncClient =
                blobServiceAsyncClient.getBlobContainerAsyncClient(container);
        return blobContainerAsyncClient.getBlobAsyncClient(blobName);
    }

    private ParallelTransferOptions getTransferOptions() {
        long blockSize = 2L * 1024L * 1024L; //2MB
        return new ParallelTransferOptions()
                .setBlockSizeLong(blockSize)
                .setMaxConcurrency(MAX_CONCURRENCY)
                .setProgressReceiver(
                        bytesTransferred -> LOGGER.info("Uploading bytes:{}", bytesTransferred));
    }
}
