package com.ascendio.store_backend.util;

import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.List;

public class DownloadPdfStringUtil {

    private DownloadPdfStringUtil() {
    }

    public static List<String> breakTextToLines(String text, int lineCharLength) {
        List<String> lines = new ArrayList<>();
        if(text.length()  <= lineCharLength) {
            lines.add(text);
            return lines;
        }
        String[] parts = text.split(" ");

        StringBuilder sbLine = new StringBuilder();

        for (String part : parts) {
            if(sbLine.length() + part.length() > lineCharLength) {
                lines.add(sbLine.toString().trim());
                sbLine.setLength(0);
            }
            sbLine.append(" " + part);
        }
        if (sbLine.length() > 0) {
            lines.add(sbLine.toString().trim());
        }

        return lines;
    }

    public static String centerString(String text, int characterLength) {
        int padSize = characterLength - text.length();

        text = Strings.padStart(text, text.length() + padSize / 2, ' ');
        text = Strings.padEnd(text, characterLength, ' ');

        return text;
    }
}
