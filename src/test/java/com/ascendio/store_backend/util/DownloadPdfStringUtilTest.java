package com.ascendio.store_backend.util;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DownloadPdfStringUtilTest {


    @Test
    void breakTextToLines_ReturnSingleLineWhenLessThanLength() {

        List<String> lines = DownloadPdfStringUtil.breakTextToLines("Lorem Ipsum", 66);

        assertEquals(1, lines.size());
        assertEquals("Lorem Ipsum", lines.get(0));
    }

    @Test
    void breakTextToLines_ReturnSingleLineWhenEqualToLength() {
        List<String> lines = DownloadPdfStringUtil.breakTextToLines("Lorem Ipsum", 66);

        assertEquals(1, lines.size());
        assertEquals("Lorem Ipsum", lines.get(0));
    }

    @Test
    void breakTextToLines_ReturnMultipleLinesWhenGreaterThanLength() {
        String text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum";
        int expectedLineCount = 10;
        int lineLength = 64;

        List<String> lines = DownloadPdfStringUtil.breakTextToLines(text, lineLength);

        assertEquals(expectedLineCount, lines.size());
    }

    @Test
    void breakTextToLines_BreakWordsCorrectlyWhenGreaterThanLength() {
        String text = "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet consectetur, adipisci velit";
        String expectedLine1 = "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet";
        String expectedLine2 = "consectetur, adipisci velit";
        int lineLength = 64;

        List<String> lines = DownloadPdfStringUtil.breakTextToLines(text, lineLength);

        assertEquals(2, lines.size());
        assertEquals(expectedLine1, lines.get(0));
        assertEquals(expectedLine2, lines.get(1));
    }
}