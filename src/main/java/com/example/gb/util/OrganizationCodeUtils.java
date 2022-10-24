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

    // 长度
    private static final int LENGTH = 9;

    // 代码字符集 0-9，A-Z
    private static final String CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    // 校验位
    private static final List<Character> CHAR_LIST = CHARS.chars().mapToObj(c -> (char) c).collect(Collectors.toList());

    // 加权因子：依次从左到右
    private static final int[] W = {3, 7, 9, 10, 5, 8, 4, 2};

    public static boolean validate(String code) {
        if (!validateLengthAndChars(code)) {
            return false;
        }

        return validateCheckNumber(code);
    }

    // 校验长度
    private static boolean validateLengthAndChars(String code) {
        if (code == null || code.length() != LENGTH) {
            return false;
        }
        for (char c : code.toCharArray()) {
            if (!CHAR_LIST.contains(c)) {
                return false;
            }
        }

        return true;
    }

    // 核对校验位：11 - SUM(a[i] * W[i]) % 11
    // 10：'X', 11：0, other：1 ~ 9
    private static boolean validateCheckNumber(String code) {
        try {
            int sum = 0;
            char[] c = code.toCharArray();
            for (int i = 0; i < c.length - 1; i++) {
                sum += Character.getNumericValue(c[i]) * W[i];
            }
            int checkNumber = 11 - sum % 11;

            int x = Character.getNumericValue(c[c.length - 1]);
            if (checkNumber == 10) {
                return Character.getNumericValue('X') == x;
            } else if (checkNumber == 11) {
                return 0 == x;
            } else {
                return checkNumber == x;
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
