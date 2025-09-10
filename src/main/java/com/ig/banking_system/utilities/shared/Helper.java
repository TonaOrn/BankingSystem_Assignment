package com.ig.banking_system.utilities.shared;

import java.util.Random;

public class Helper {
    public static String randomNumber(int length) {
        var str = "123456789";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int idx = new Random().nextInt(str.length());
            sb.append(str.charAt(idx));
        }
        return sb.toString();
    }
}
