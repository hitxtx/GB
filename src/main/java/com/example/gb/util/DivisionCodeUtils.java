package com.example.gb.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 中文名称：中国人民共和国行政区划代码
 * Codes for the administrative divisions of the People's Republic of China
 * 国家标准：《GB/T 2260-2007》
 *
 */
public class DivisionCodeUtils {

    public static final Map<String, String> PROVINCE_CODES = new HashMap<>();

    static {
        PROVINCE_CODES.put("11", "北京");
        PROVINCE_CODES.put("12", "天津");
        PROVINCE_CODES.put("13", "河北");
        PROVINCE_CODES.put("14", "山西");
        PROVINCE_CODES.put("15", "内蒙古");
        PROVINCE_CODES.put("21", "辽宁");
        PROVINCE_CODES.put("22", "吉林");
        PROVINCE_CODES.put("23", "黑龙江");
        PROVINCE_CODES.put("31", "上海");
        PROVINCE_CODES.put("32", "江苏");
        PROVINCE_CODES.put("33", "浙江");
        PROVINCE_CODES.put("34", "安徽");
        PROVINCE_CODES.put("35", "福建");
        PROVINCE_CODES.put("36", "江西");
        PROVINCE_CODES.put("37", "山东");
        PROVINCE_CODES.put("41", "河南");
        PROVINCE_CODES.put("42", "湖北");
        PROVINCE_CODES.put("43", "湖南");
        PROVINCE_CODES.put("44", "广东");
        PROVINCE_CODES.put("45", "广西");
        PROVINCE_CODES.put("46", "海南");
        PROVINCE_CODES.put("50", "重庆");
        PROVINCE_CODES.put("51", "四川");
        PROVINCE_CODES.put("52", "贵州");
        PROVINCE_CODES.put("53", "云南");
        PROVINCE_CODES.put("54", "西藏");
        PROVINCE_CODES.put("61", "陕西");
        PROVINCE_CODES.put("62", "甘肃");
        PROVINCE_CODES.put("63", "青海");
        PROVINCE_CODES.put("64", "宁夏");
        PROVINCE_CODES.put("65", "新疆");
        PROVINCE_CODES.put("71", "台湾");
        PROVINCE_CODES.put("81", "香港");
        PROVINCE_CODES.put("82", "澳门");
        PROVINCE_CODES.put("83", "台湾"); // 台湾身份证号码以83开头，但是行政区划为71

        PROVINCE_CODES.put("91", "国外");
    }


}
