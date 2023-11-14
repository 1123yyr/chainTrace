package com.example.careold.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReturnCodeUtil {
    public static final String falseCode = "0000";
    public static final String successCode = "1111";
    public static final String returnCode="returnCode";


    /**
     * 利用正则表达式判断字符串是否是数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
}
