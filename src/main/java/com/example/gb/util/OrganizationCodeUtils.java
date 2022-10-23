package com.example.gb.util;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 全国组织机构代码编制规则
 * Rule of coding for the representation of organization
 * 国标：GB 11714-1997
 * 8位本体码和1位校验啊，由数字或大写拉丁字母组成
 */
public class OrganizationCodeUtils {

    // 加权因子：依次从左到右
    private final static int[] W = {3, 7, 9, 10, 5, 8, 4, 2};

    // 代码字符集
    private final static String C = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    // 字符集， 0-9，A-Z
    private final static List<Character> CHARACTER_LIST = C.chars().mapToObj(c -> (char) c).collect(Collectors.toList());

    // 长度
    private final static int LENGTH = 9;

    public static boolean validate(String code) {
        if (!validateLengthAndCharset(code)) {
            return false;
        }

        return validateCheckNumber(code);
    }

    // 校验长度
    private static boolean validateLengthAndCharset(String code) {
        if (code == null || code.length() != LENGTH) {
            return false;
        }
        for (char c : code.toCharArray()) {
            if (!CHARACTER_LIST.contains(c)) {
                return false;
            }
        }

        return true;
    }

    // 核对校验位
    private static boolean validateCheckNumber(String code) {
        try {
            int sum = 0;
            char[] c = code.toCharArray();
            for (int i = 0; i < c.length - 1; i++) {
                sum += Character.getNumericValue(c[i]) * W[i];
            }
            int checkNumber = 11 - sum % 11;

            int cn = Character.getNumericValue(c[c.length - 1]);
            if (checkNumber == 10) {
                return Character.getNumericValue('X') == cn;
            } else if (checkNumber == 11) {
                return 0 == cn;
            } else {
                return checkNumber == cn;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static void main(String[] args) {
        String[] companyUSCIArray = {"469003196001012039"};

        for (String s : companyUSCIArray) {
            System.err.println(OrganizationCodeUtils.validate(s.substring(8, 17)));
        }
    }

}
