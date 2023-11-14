
package com.example.careold.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 功能：日期工具类
 * 	1.日期类型
 * 	2.判断日期格式
 * 	3.日期格式转换为字符串
 * 	4.获取前第n天的日期
 * 	5.获取一天 0，1，2... ...23，24 点 时间数组
 * 	6.获取0，2，4，6，8，10，12，14，16，18，20，22，24 时间数组
 * 	7.获取过去n天时间列表
 * 	8.日期转星期
 *
 */
public class DateConst {
	private DateConst(){}
	public static final String DATE_MODEL_1 = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String DATE_MODEL_2 = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_MODEL_3 = "yyyy-MM-dd HH:mm";
	public static final String DATE_MODEL_4 = "yyyy-MM-dd HH";
	public static final String DATE_MODEL_5 = "yyyy-MM-dd";
	public static final String DATE_MODEL_6 = "yyyy-MM";

	public static final String DATE_MODEL_7 = "yyyyMMddHHmmss.SSS";
	public static final String DATE_MODEL_8 = "yyyyMMddHHmmss";
	public static final String DATE_MODEL_9 = "yyyyMMddHHmm";
	public static final String DATE_MODEL_10 = "yyyyMMddHH";
	public static final String DATE_MODEL_11 = "yyyyMMdd";
	public static final String DATE_MODEL_12 = "yyyyMM";

	public static final String DATE_MODEL_13 = "yyyy/MM/dd HH:mm:ss.SSS";
	public static final String DATE_MODEL_14 = "yyyy/MM/dd HH:mm:ss";
	public static final String DATE_MODEL_15 = "yyyy/MM/dd HH:mm";
	public static final String DATE_MODEL_16 = "yyyy/MM/dd HH";
	public static final String DATE_MODEL_17 = "yyyy/MM/dd";
	public static final String DATE_MODEL_18 = "yyyy/MM";

	public static final String DATE_MODEL_19 = "yyyyMMddHHmmssSSS";

	public static final String DATE_MODEL_99 = "yyyy";


	/**
	 * 功能：日期格式转换为字符串
	 * @author yanghd_bill
	 * @create 2018/9/21
	 * @param dateModel
	 * @return
	 */
	public static String dateToString(Date date,String dateModel){
		//可以方便地修改日期格式
		SimpleDateFormat dateFormat = new SimpleDateFormat( dateModel );
		return dateFormat.format( date );
	}

	/*
	 * 将时间转换为时间戳
	 */
	public static String dateToStamp(String s) throws ParseException {
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_MODEL_2);
		Date date = simpleDateFormat.parse(s);
		long ts = date.getTime();
		res = String.valueOf(ts);
		return res;
	}


	/*
	 * 将时间戳转换为时间
	 */
	public static String stampToDate(Long s){
		if (s == 0){
			return null;
		}
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_MODEL_2);
		Date date = new Date(s);
		res = simpleDateFormat.format(date);
		return res;
	}
}
