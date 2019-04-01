package com.bootdo.common.utils;

import com.alibaba.druid.sql.visitor.functions.Char;

import java.util.Arrays;
import java.util.Random;

/**
 * 生成验证码
 */

public class RandomCode {

    private static final char [] CHARS = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',  '2', '3', '4', '5', '6', '7', '8', '9'};

    private static final char[] NUMS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

     // (不能与自定义进制有重复)
    private static final char b = 'o';

     // 进制长度
    private static final int binLen = CHARS.length;

     // 序列最小长度
    private static final int s = 8;


    public static String getCode() {
        return random(NUMS, 4);
    }

    public static String getCode(int type, int size) {
        if (type == 1) {
            return random(NUMS, size);
        }
        if (type == 2) {
            return random(CHARS, size);
        }
        return random(NUMS, 4);
    }

    private static String random(char[] chars, int size) {
        if(size>chars.length){
            return "超出最大长度!";
        }
        StringBuilder sb = new StringBuilder();
        Random rend = new Random();
        for (int i = 0; i < size; i++) {
            int rendIndx = rend.nextInt(chars.length);
            sb.append(chars[rendIndx]);
            chars[rendIndx] = chars[chars.length - 1];
            chars = Arrays.copyOf(chars, chars.length - 1);
        }
        return sb.toString();

    }

    public static void main(String[] args) {
//        System.out.println(getCode(2, 8));
        System.out.println(toSerialCode(123));
    }





    /**
     * 根据ID生成六位随机码
     *
     * @param id ID
     * @return 随机码
     */
    public static String toSerialCode(long id) {
        char[] buf = new char[32];
        int charPos = 32;

        while ((id / binLen) > 0) {
            int ind = (int) (id % binLen);
            // System.out.println(num + "-->" + ind);
            buf[--charPos] = CHARS[ind];
            id /= binLen;
        }
        buf[--charPos] = CHARS[(int) (id % binLen)];
        // System.out.println(num + "-->" + num % binLen);
        String str = new String(buf, charPos, (32 - charPos));
        // 不够长度的自动随机补全
        if (str.length() < s) {
            StringBuilder sb = new StringBuilder();
            sb.append(b);
            Random rnd = new Random();
            for (int i = 1; i < s - str.length(); i++) {
                sb.append(CHARS[rnd.nextInt(binLen)]);
            }
            str += sb.toString();
        }
        return str;
    }

    public static long codeToId(String code) {
        char chs[] = code.toCharArray();
        long res = 0L;
        for (int i = 0; i < chs.length; i++) {
            int ind = 0;
            for (int j = 0; j < binLen; j++) {
                if (chs[i] == CHARS[j]) {
                    ind = j;
                    break;
                }
            }
            if (chs[i] == b) {
                break;
            }
            if (i > 0) {
                res = res * binLen + ind;
            } else {
                res = ind;
            }
            // System.out.println(ind + "-->" + res);
        }
        return res;
    }


}
