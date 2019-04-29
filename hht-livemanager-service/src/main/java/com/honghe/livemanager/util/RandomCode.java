package com.honghe.livemanager.util;

import java.util.Random;
public class RandomCode {

   public static String code() {
        char[] codeArray = { 'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'P', 'L', 'K', 'J', 'H', 'G', 'F', 'D', 'S', 'A', 'Z', 'X', 'C', 'V', 'B', 'N', 'M', '2', '3', '4', '5', '6', '7', '8', '9'};
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        int count = 0;
        while (true) {

            char c = codeArray[random.nextInt(codeArray.length)];

            if (stringBuilder.indexOf(c + "") == -1) {
                stringBuilder.append(c);
                count++;
                if (count == 6) {
                    break;
                }

            }
        }

     return  stringBuilder.toString();
    }
}
