package com.company.validation;

import com.company.tools.ConstantData;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

/**
 *
 * @author santiago.mamani
 */
public class ObjectValidationUtil {

    public static boolean isValidIdentifier(Long identifier) {
        return identifier > 0;
    }
      public static boolean isValidSize(String str, int minSize, int maxSize) {
        return str.length() >= minSize && str.length() <= maxSize;
    }
    public static boolean isLessOrEqual(int numberOne, int numberTwo){
        return numberOne<=numberTwo;
    }
     public static boolean isUsAscii(String str) {
        CharsetEncoder asciiEncoder = Charset.forName(ConstantData.US_ASCII).newEncoder();
        return asciiEncoder.canEncode(str);
    }
}
