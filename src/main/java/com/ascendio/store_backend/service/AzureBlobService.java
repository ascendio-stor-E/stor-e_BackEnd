package com.ascendio.store_backend.service;

import com.ascendio.store_backend.config.AzureBlobConfig;
import com.azure.core.util.Context;
import com.azure.storage.blob.*;
import com.azure.storage.blob.models.DownloadRetryOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

@Service
public class AzureBlobService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AzureBlobService.class);

    private BlobServiceClient blobServiceClient;
    private AzureBlobConfig azureBlobConfig;

    public AzureBlobService(BlobServiceClient blobServiceClient, AzureBlobConfig azureBlobConfig) {
        this.blobServiceClient = blobServiceClient;
        this.azureBlobConfig = azureBlobConfig;
    }

    public void uploadToAzureBlob(String imageName, byte[] data) {
        BlobClient blobClient = getBlobClient(azureBlobConfig.getStorageContainer(), imageName);

        try {
            blobClient.upload(new ByteArrayInputStream(data), true);
            LOGGER.info("Successfully uploaded {}", imageName);
        } catch (Exception e) {
            LOGGER.error("Error occurred while uploading", e);
        }
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
}
