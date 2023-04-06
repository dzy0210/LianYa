package com.hfut.lianya.utils;

public class QRCodeUtil {
    public static String getCodeType(String barCode) {
        String []str = barCode.split("|");
        return str[0];
    }
}
