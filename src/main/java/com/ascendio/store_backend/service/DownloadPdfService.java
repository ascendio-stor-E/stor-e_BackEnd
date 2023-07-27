package com.ascendio.store_backend.service;

import com.ascendio.store_backend.model.Story;
import com.ascendio.store_backend.model.StoryBook;
import com.ascendio.store_backend.model.StoryBookStatus;
import com.ascendio.store_backend.util.DownloadPdfStringUtil;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class DownloadPdfService {

    private static final PDRectangle PAGE_SIZE = PDRectangle.A5;
    private static final int TITLE_FONT_SIZE = 16;
    private static final int TEXT_FONT_SIZE = 14;
    private static final PDType1Font TITLE_FONT = PDType1Font.TIMES_BOLD;
    private static final PDType1Font TEXT_FONT = PDType1Font.COURIER;
    private final float VERTICAL_SPACE_BETWEEN_LINES = 14.5f;

    private StoryBookService storyBookService;
    private AzureBlobService azureBlobService;

    public DownloadPdfService(StoryBookService storyBookService, AzureBlobService azureBlobService) {
        this.storyBookService = storyBookService;
        this.azureBlobService = azureBlobService;
    }

    public byte[] generateStoryBookPdf(UUID storyBookId) {

        StoryBook storyBook = storyBookService.getStoryBookById(storyBookId, Set.of(StoryBookStatus.FAVOURITE, StoryBookStatus.COMPLETE));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (PDDocument storyBookPdf = new PDDocument()) {
            createCoverPage(storyBookPdf, storyBook.getTitle());
            storyBook.getStories().stream()
                    .sorted(Comparator.comparingInt(Story::getPageNumber))
                    .forEach(story -> {
                        byte[] imageBytes = azureBlobService.getImage(story.getImage());
                        try {
                            createPage(storyBookPdf, story.getTextContent(), imageBytes, story.getPageNumber());
                        } catch (Exception ex) {
                            throw new RuntimeException(ex.getMessage());
                        }
                    });
            createCoverPage(storyBookPdf, "The end.");
            storyBookPdf.save(byteArrayOutputStream);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
        return byteArrayOutputStream.toByteArray();
    }

    private void createCoverPage(PDDocument storyBookPdf, String coverText) throws Exception {

        final int PAGE_WIDTH = 400;
        final int CONVERTION_UNIT = 100;
        final int LINE_START_VERTICAL = 350;

        PDPage coverPage = new PDPage(PAGE_SIZE);
        storyBookPdf.addPage(coverPage);

        try (PDPageContentStream pdPageContentStream = new PDPageContentStream(storyBookPdf, coverPage)) {
            //Draw cover template
            byte[] coverImageBytes = getTemplate("pdf_cover1.png");
            PDImageXObject coverImage = PDImageXObject.createFromByteArray(storyBookPdf, coverImageBytes, "");
            pdPageContentStream.drawImage(coverImage, 0, 0, PAGE_SIZE.getWidth(), PAGE_SIZE.getHeight());

            //Draw title
            pdPageContentStream.beginText();

            pdPageContentStream.setFont(PDType1Font.TIMES_ROMAN, TITLE_FONT_SIZE);
            pdPageContentStream.setLeading(VERTICAL_SPACE_BETWEEN_LINES);

            int lineStartHorizontal = (int) (PAGE_WIDTH / 2 - TEXT_FONT.getStringWidth(coverText) / (CONVERTION_UNIT * 2));
            pdPageContentStream.newLineAtOffset(lineStartHorizontal, LINE_START_VERTICAL);

            pdPageContentStream.showText(coverText);
            pdPageContentStream.endText();
        }
    }

    private void createPage(PDDocument storyBookPdf, String storyText, byte[] imageBytes, int pageNumber) throws Exception {

        final int TEXT_LINE_LENGTH = 68;
        final int PG_NUMBER_LINE_LENGTH = 100;
        final int IMAGE_WIDTH = 360;
        final int IMAGE_HEIGHT = 360;
        final int IMAGE_X_COORDINATE = 30;
        final int IMAGE_Y_COORDINATE = 220;
        final int TEXT_X_COORDINATE = 15;
        final int TEXT_Y_COORDINATE = 180;
        final int PG_NUMBER_X_COORDINATE = 25;
        final int PG_NUMBER_Y_COORDINATE = 20;

        PDPage storyPage = new PDPage(PAGE_SIZE);
        storyBookPdf.addPage(storyPage);

        List<String> lines = DownloadPdfStringUtil.breakTextToLines(storyText, TEXT_LINE_LENGTH);

        try (PDPageContentStream pdPageContentStream = new PDPageContentStream(storyBookPdf, storyPage)) {
            //Draw image
            PDImageXObject image = PDImageXObject.createFromByteArray(storyBookPdf, imageBytes, "");
            pdPageContentStream.drawImage(image, IMAGE_X_COORDINATE, IMAGE_Y_COORDINATE, IMAGE_WIDTH, IMAGE_HEIGHT);
            //Add content
            pdPageContentStream.beginText();
            pdPageContentStream.setFont(PDType1Font.TIMES_ROMAN, TEXT_FONT_SIZE);
            pdPageContentStream.setLeading(VERTICAL_SPACE_BETWEEN_LINES);
            pdPageContentStream.newLineAtOffset(TEXT_X_COORDINATE, TEXT_Y_COORDINATE);

            for (String line : lines) {
                pdPageContentStream.showText(line);
                pdPageContentStream.newLine();
            }
            pdPageContentStream.endText();
            //Add page number
            pdPageContentStream.beginText();
            pdPageContentStream.newLineAtOffset(PG_NUMBER_X_COORDINATE, PG_NUMBER_Y_COORDINATE);
            pdPageContentStream.showText(DownloadPdfStringUtil.centerString("(" + pageNumber + ")", PG_NUMBER_LINE_LENGTH));
            pdPageContentStream.endText();
        }
    }

    private byte[] getTemplate(String templateName) throws Exception {
        ClassPathResource classPathResource = new ClassPathResource("images/" + templateName);
        byte[] binaryData = FileCopyUtils.copyToByteArray(classPathResource.getInputStream());
        return binaryData;

    }
}
