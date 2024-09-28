package com.joaovictor.picpaysimplificado.utils;

public final class StringUtil {
    private StringUtil() {
        throw new IllegalArgumentException("This is an utility class!");
    }

    public static String toCPFMask(String unmaskedValue) {
        if (unmaskedValue.length() == 11) {
            return unmaskedValue.substring(0, 3)
                  + "."
                  + unmaskedValue.substring(3, 3)
                  + "."
                  + unmaskedValue.substring(6, 3)
                  + "-"
                  + unmaskedValue.substring(8, 2);
        }
        throw new RuntimeException();
    }

    public static String toCNPJMask(String unmaskedValue) {
        if (unmaskedValue.length() == 14) {
            return new StringBuilder(unmaskedValue).substring(0, 2).concat(
                  "." + unmaskedValue.substring(2, 3)
                        + "."
                        + unmaskedValue.substring(4, 3)
                        + "/"
                        + unmaskedValue.substring(7, 4)
                        + "-"
                        + unmaskedValue.substring(11, 2)
            );
        }
        throw new RuntimeException();
    }
}
