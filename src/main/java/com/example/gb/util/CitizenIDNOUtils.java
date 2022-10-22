package com.example.gb.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 中文名称：公民身份号码（原名：社会保障号码）
 * 英文名称：Citizen Identification Number
 * <p>
 * 国家标准：GB 11643-1999
 * 规则
 * 1. 公民身份号码一共18位，17位主体码（master number）和1位校验码（check number）
 * 2. 主体码：从左到右，依次分为地址码（6位），出生日期码（8位）和顺序吗（3位）
 * 3. 地址码：表示编码对象常驻户口所在县（市、旗、区）的行政区划代码，按照GB/T 2260的规定执行
 * 4. 出生日期码：表示编码对象出生的年、月、日，按照GB/T 7408的规定执行
 * 5. 顺序码：表示在同一地址码所标识的区域范围内，对同年、同月、同日出生的人编定的顺序号，顺序码的奇数分配给男性，偶数分配给女性
 * 6. 校验码：采用ISO 7064:1983，MOD 11-2校验码系统
 * <p>
 * 台湾身份证 https://www.aicesu.cn/twcard/
 */
public class CitizenIDNOUtils {

    // 加权因子：公民身份号码中各位的加权因子，依次从左到右
    private final static int[] W = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

    // 校验码
    private final static String[] X = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};

    // 字符集
//    private final static char[] C = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'X'};

    // 省地址码
    private final static Map<String, String> provinceNumbers = new HashMap<String, String>() {{
        put("11", "北京");
        put("12", "天津");
        put("13", "河北");
        put("14", "山西");
        put("15", "内蒙古");
        put("21", "辽宁");
        put("22", "吉林");
        put("23", "黑龙江");
        put("31", "上海");
        put("32", "江苏");
        put("33", "浙江");
        put("34", "安徽");
        put("35", "福建");
        put("36", "江西");
        put("37", "山东");
        put("41", "河南");
        put("42", "湖北");
        put("43", "湖南");
        put("44", "广东");
        put("45", "广西");
        put("46", "海南");
        put("50", "重庆");
        put("51", "四川");
        put("52", "贵州");
        put("53", "云南");
        put("54", "西藏");
        put("61", "陕西");
        put("62", "甘肃");
        put("63", "青海");
        put("64", "宁夏");
        put("65", "新疆");
        put("71", "台湾");
        put("81", "香港");
        put("82", "澳门");
        //issue#1277，台湾身份证号码以83开头，但是行政区划为71
        put("83", "台湾");
        put("91", "国外");
    }};

    // 号码长度
    private final static int LENGTH = 18;

    public static boolean validate(String cin) {
        if (cin == null || cin.length() != LENGTH) {
            return false;
        }

        // 地址码
        if (!validateAddressCode(cin)) {
            return false;
        }

        // 出生日期
        if (!validateBirthdate(cin)) {
            return false;
        }

        // 性别
        if (!validateGender(cin)) {
            return false;
        }

        // 校验位
        return validateCheckNumber(cin);
    }

    public static boolean validateCheckNumber(String cin) {
        int sum = 0;
        try {
            String masterNumber = cin.substring(0, 17); // 本体码
            String checkNumber = cin.substring(17); // 校验码
            char[] charArray = masterNumber.toCharArray();
            for (int i = 0; i < charArray.length; i++) {
                sum += W[i] * Integer.parseInt(String.valueOf(charArray[i]));
            }
            int result = sum % 11;

            return X[result].equals(checkNumber);
        } catch (Exception e) {
            return false;
        }
    }

    // 行政区划代码：1~6位（由左到右）
    public static boolean validateAddressCode(String cin) {
        String provinceNumber = cin.substring(0, 2);

        return provinceNumbers.containsKey(provinceNumber);
    }

    public static String getAddressCode(String cin) {
        return cin.substring(0, 6);
    }

    // 出生日期：7~14位（由左到右）
    private static boolean validateBirthdate(String cin) {
        String birthdate = cin.substring(6, 14);
        try {
            LocalDate date = LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("yyyyMMdd"));
            if (date == null || date.getYear() < 1800) { // 年份过于久远，标记为异常
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static String getBirthdateString(String cin) {
        return cin.substring(6, 14);
    }

    public static Date getBirthdate(String cin) {
        String birthdate = getBirthdateString(cin);
        Date date;
        try {
            date = new SimpleDateFormat("yyyyMMdd").parse(birthdate);
        } catch (Exception e) {
            return null;
        }
        return date;
    }

    public static LocalDate getBirthdateLocalDate(String cin) {
        String birthdate = getBirthdateString(cin);
        LocalDate date;
        try {
            date = LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("yyyyMMdd"));
        } catch (Exception e) {
            return null;
        }
        return date;
    }

    public static Integer getBirthdateYear(String cin) {
        final LocalDate localDate = getBirthdateLocalDate(cin);
        if (localDate == null) {
            return null;
        }
        return localDate.getYear();
    }

    public static Integer getBirthdateMonth(String cin) {
        final LocalDate localDate = getBirthdateLocalDate(cin);
        if (localDate == null) {
            return null;
        }
        return localDate.getMonthValue();
    }

    public static Integer getBirthdateDay(String cin) {
        final LocalDate localDate = getBirthdateLocalDate(cin);
        if (localDate == null) {
            return null;
        }
        return localDate.getDayOfMonth();
    }

    // 性别：17位（由左到右）
    public static boolean validateGender(String cin) {
        String gender = cin.substring(16, 17);
        try {
            Integer.parseInt(gender);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static Integer getGenderInteger(String cin) {
        String gender = cin.substring(16, 17);
        try {
            return Integer.parseInt(gender) % 2;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Boolean getGenderBoolean(String cin) {
        Integer genderInteger = getGenderInteger(cin);
        if (genderInteger == null) {
            return null;
        }
        return genderInteger == 1;
    }

    public static String getGender(String cin) {
        final Integer genderInteger = getGenderInteger(cin);
        if (genderInteger == null) {
            return "未知";
        }
        return genderInteger == 0 ? "女" : "男";
    }

    public static void main(String[] args) {
        String[] cinArray = {"12010119900307310X", "12010119900307652X", "12010119900307564X", "120101199003078103", "120101199003070806"};

        for (String cin : cinArray) {
            System.err.println(CitizenIDNOUtils.validate(cin));
        }

        for (String cin : cinArray) {
            System.err.println("--------------------------------------");
            System.err.println("--------------------------------------");
            // addressCode
            System.err.println("地址码：" + CitizenIDNOUtils.getAddressCode(cin));
            System.err.println();
            // birthdate
            System.err.println("出生日期[S]：" + CitizenIDNOUtils.getBirthdateString(cin));
            System.err.println("出生日期[LD]：" + CitizenIDNOUtils.getBirthdateLocalDate(cin));
            System.err.println("出生日期[D]：" + CitizenIDNOUtils.getBirthdate(cin));
            System.err.println();

            System.err.println("出生日期[yyyy]：" + CitizenIDNOUtils.getBirthdateYear(cin));
            System.err.println("出生日期[MM]：" + CitizenIDNOUtils.getBirthdateMonth(cin));
            System.err.println("出生日期[dd]：" + CitizenIDNOUtils.getBirthdateDay(cin));
            System.err.println();

            // gender
            System.err.println("性别[I]：" + CitizenIDNOUtils.getGenderInteger(cin));
            System.err.println("性别[B]：" + CitizenIDNOUtils.getGenderBoolean(cin));
            System.err.println("性别[C]：" + CitizenIDNOUtils.getGender(cin));
            System.err.println();

            System.err.println("X：" + CitizenIDNOUtils.validateCheckNumber(cin));
        }

    }

}
