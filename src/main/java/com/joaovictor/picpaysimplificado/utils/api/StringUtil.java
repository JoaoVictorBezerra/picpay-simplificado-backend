package com.joaovictor.picpaysimplificado.utils.api;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringUtil {
    private static String toCPFMask(String unmaskedValue) {
        return unmaskedValue.substring(0, 3)
              + "."
              + unmaskedValue.substring(3, 6)
              + "."
              + unmaskedValue.substring(6, 9)
              + "-"
              + unmaskedValue.substring(9, 11);
    }

    private static String toCNPJMask(String unmaskedValue) {
        return unmaskedValue
              .substring(0, 2) + "." + unmaskedValue.substring(2, 5)
              + "."
              + unmaskedValue.substring(5, 8)
              + "/"
              + unmaskedValue.substring(8, 12)
              + "-"
              + unmaskedValue.substring(12, 13);

    }

    public static String setMaskOnDocument(String document) {
        if(document.length() == 11) return toCPFMask(document);
        return toCNPJMask(document);
    }
}

