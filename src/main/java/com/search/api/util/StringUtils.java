package com.search.api.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    public static String removeTag(String content) {
        Pattern tags = Pattern.compile("<(/)?([a-zA-Z]*)(\\\\s[a-zA-Z]*=[^>]*)?(\\\\s)*(/)?>");

        Matcher m = tags.matcher(content);
        content = m.replaceAll("");

        return content;
    }

    public static String replaceAddressName(String roadAddressName) {
        String patternAddress="(\uC2DC)|(\uAD70)|(\uAD6C)";
        String[] roadAddressNames = roadAddressName.split(patternAddress);
        int blankIdx = roadAddressNames[0].indexOf(" ");

        roadAddressName = roadAddressName.substring(blankIdx).trim();

        return roadAddressName;
    }
}
