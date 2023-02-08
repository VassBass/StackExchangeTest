package util;

import java.util.Arrays;

public class StringHelper {
    public static String intArrayToString(int ... ints) {
        String regex = "\\]|\\[|\\s";
        return Arrays.toString(ints)
                .replaceAll(regex, "")
                .replace(',', ';');
    }
}
