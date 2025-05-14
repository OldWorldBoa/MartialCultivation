package com.airistotal.martial_cultivation.helpers;

/*
* https://stackoverflow.com/questions/8476588/java-equivalent-of-c-sharp-string-isnullorempty-and-string-isnullorwhitespace
* */
public class StringHelpers {
    public static boolean isNullOrEmpty(String s) {
        return s == null || s.length() == 0;
    }

    public static boolean isNullOrWhitespace(String s) {
        return isNullOrEmpty(s) || isWhitespace(s);

    }

    private static boolean isWhitespace(String s) {
        int length = s.length();
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                if (!Character.isWhitespace(s.charAt(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
