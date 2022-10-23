package com.example.gb.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一社会信用代码
 * Unified Social Credit Identifier
 * <p>
 * 一共18位，由0~9，A~Z（除I,O,Z,S,V）每位含义如下：
 * 1 -> 登记管理部门代码
 * 2 -> 机构类别代码
 * 3~8 -> 登记管理机关行政区划码
 * 9~17 -> 主体标识码（组织机构代码）
 * 18 -> 校验码
 */
public class USCIUtils {

    // 登记管理部门代码
    private static final Map<String, String> DEPT_CODE = new HashMap<String, String>() {{
        put("1", "机构编制");
        put("5", "民政");
        put("9", "工商");
        put("Y", "其它");
    }};

    // 机构类别代码
    private static final Map<String, Map<String, String>> DEPT_TYPE = new HashMap<String, Map<String, String>>() {{
        put("1", new HashMap<String, String>(){{
            put("1", "机关");
            put("2", "事业单位");
            put("3", "中央编办直接管理机构编制的群众团体");
            put("9", "其他");
        }});
        put("5", new HashMap<String, String>(){{
            put("1", "社会团体");
            put("2", "民办非企业单位");
            put("3", "基金会");
            put("9", "其他");
        }});
        put("9", new HashMap<String, String>(){{
            put("1", "企业");
            put("2", "个体工商户");
            put("3", "农民专业合作社");
        }});
        put("Y", new HashMap<String, String>(){{
            put("1", "");
        }});
    }};

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

}
