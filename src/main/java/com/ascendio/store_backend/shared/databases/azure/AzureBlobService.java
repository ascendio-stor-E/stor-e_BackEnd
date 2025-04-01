package com.ascendio.store_backend.shared.databases.azure;

import com.ascendio.store_backend.shared.config.AzureBlobConfig;
import com.azure.core.util.Context;
import com.azure.storage.blob.*;
import com.azure.storage.blob.models.DownloadRetryOptions;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

@Service
@RequiredArgsConstructor
public class AzureBlobService {
    private final BlobServiceClient blobServiceClient;
    private final AzureBlobConfig azureBlobConfig;

    private static final Logger LOGGER = LoggerFactory.getLogger(AzureBlobService.class);

    public void uploadToAzureBlob(String imageName, byte[] data) {
        BlobClient blobClient = getBlobClient(azureBlobConfig.getStorageContainer(), imageName);

        try {
            blobClient.upload(new ByteArrayInputStream(data), true);
            LOGGER.info("Successfully uploaded {}", imageName);
        } catch (Exception e) {
            LOGGER.error("Error occurred while uploading", e);
        }
    }

    @Cacheable("images")
    public byte[] getImage(String imageName) {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        downloadBlobFromAzure(bout, azureBlobConfig.getStorageContainer(), imageName);
        return bout.toByteArray();
    }

    private void downloadBlobFromAzure(
            OutputStream outputStream,
            String container,
            String blobName)
    {
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
