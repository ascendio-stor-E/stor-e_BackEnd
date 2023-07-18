package com.ascendio.store_backend.service;

import com.ascendio.store_backend.exception.ImageSaveException;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.stereotype.Service;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.UUID;

@Service
public class ImageBlobService {

    private static final int BYTE_BUFFER_SIZE = 4096;

    public String addToBlobStorage(String imageUrl, UUID storyBookId, int pageNumber) {
        String imageName = storyBookId.toString().concat(String.valueOf(pageNumber));
        try {
            // download file from url
            // take it as a byte array
            byte[] imageBytes = downloadImageToByteArray(imageUrl);

            // TODO: remove this comment
            // files.write
//            Files.write(
//                    Paths.get("/Users/salt-dev/Documents/Vijani/labs/week12and13/final_project/data/a.png"),
//                    imageBytes);


            // TODO: call Azure blob service and pass byte array


        } catch (Exception ex) {
            throw new ImageSaveException(ex.getMessage());
        }
        return imageName;
    }

    private byte[] downloadImageToByteArray(String imageUrl) throws Exception {
        URL imageUrlObject = new URL(imageUrl);
        ReadableByteChannel channel = Channels.newChannel(imageUrlObject.openStream());
        ByteBuffer buffer = ByteBuffer.allocate(BYTE_BUFFER_SIZE);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            while (channel.read(buffer) != -1) {
                buffer.flip();
                while (buffer.hasRemaining()) {
                    outputStream.write(buffer.get());
                }
                buffer.clear();
            }
        } finally {
            channel.close();
        }

        return outputStream.toByteArray();
    }
}
