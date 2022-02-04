package com.hidiu.utils;

public class StringUtils {

    public static String randomStr(int len){
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        String result = "";
        String[] strs = str.split("");
        while(result.length() < len){
            result += strs[(int) (Math.random() * str.length())];
        }
        return result;
    }

    public static void main(String[] args){
        System.out.println(randomStr(10));
    }
}
