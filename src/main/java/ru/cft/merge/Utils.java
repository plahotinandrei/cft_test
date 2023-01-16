package ru.cft.merge;

import java.util.regex.Pattern;

public class Utils {
    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean noSpace(String str) {
        return Pattern.matches("^\\S*$", str);
    }
}
