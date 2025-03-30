package com.endpoint;

public class StringUtils {

    public static boolean isAlphanumeric(String str) {
        return str != null && str.matches("[a-zA-Z0-9]+");
    }

}
