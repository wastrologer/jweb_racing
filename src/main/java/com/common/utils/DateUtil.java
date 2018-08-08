package com.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	private static final String dateFormat1 = "yyyy-MM-dd";

	private static final String dateFormat2 = "yyyyMMdd";

	public static final String DATE_FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";

	private static final String dateFormat4 = "yyyy-MM-dd HH:mm";

	private static final String dateFormat5 = "HH:mm";
	// dateFormat="yyyy-MM-dd"
	private static ThreadLocal<DateFormat> dateFormat1Thread = new ThreadLocal<DateFormat>();
	// dateFormat="yyyyMMdd"
	private static ThreadLocal<DateFormat> dateFormat2Thread = new ThreadLocal<DateFormat>();
	// dateFormat="yyyy-MM-dd HH:mm:ss"
	private static ThreadLocal<DateFormat> dateFormat3Thread = new ThreadLocal<DateFormat>();
	// dateFormat="yyyy-MM-dd HH:mm"
	private static ThreadLocal<DateFormat> dateFormat4Thread = new ThreadLocal<DateFormat>();
	// dateFormat="HH:mm"
	private static ThreadLocal<DateFormat> dateFormat5Thread = new ThreadLocal<DateFormat>();

	public static DateFormat getDateFormat1() {
		DateFormat df = (DateFormat) dateFormat1Thread.get();
		if (df == null) {
			df = new SimpleDateFormat(dateFormat1);
			dateFormat1Thread.set(df);
		}
		return df;
	}

	public static DateFormat getDateFormat2() {
		DateFormat df = (DateFormat) dateFormat2Thread.get();
		if (df == null) {
			df = new SimpleDateFormat(dateFormat2);
			dateFormat2Thread.set(df);
		}
		return df;
	}

	public static DateFormat getDateFormat3() {
		DateFormat df = (DateFormat) dateFormat3Thread.get();
		if (df == null) {
			df = new SimpleDateFormat(DATE_FORMAT_DATETIME);
			dateFormat3Thread.set(df);
		}
		return df;
	}

	public static DateFormat getDateFormat4() {
		DateFormat df = (DateFormat) dateFormat4Thread.get();
		if (df == null) {
			df = new SimpleDateFormat(dateFormat4);
			dateFormat4Thread.set(df);
		}
		return df;
	}

	public static DateFormat getDateFormat5() {
		DateFormat df = (DateFormat) dateFormat5Thread.get();
		if (df == null) {
			df = new SimpleDateFormat(dateFormat5);
			dateFormat5Thread.set(df);
		}
		return df;
	}

	/**
	 * 时间分钟、秒钟计算
	 * 
	 * @param day
	 *            初始时间
	 * @param type
	 *            "SECOND"秒、"MINUTE"分钟、"HOUR"小时、"DAY"日 、"MONTH"日
	 * @param offset
	 *            偏移量
	 * @return
	 */
	public static String addDateMinut(String day, String type, int offset) {
		DateFormat format = getDateFormat3();
		Date date = null;
		try {
			date = format.parse(day);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (date == null) {
			return "";
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// 24小时制
		if ("MINUTE".equals(type)) {
			cal.add(Calendar.MINUTE, offset);
		} else if ("SECOND".equals(type)) {
			cal.add(Calendar.SECOND, offset);
		} else if ("HOUR".equals(type)) {
			cal.add(Calendar.HOUR, offset);
		} else if ("DAY".equals(type)) {
			cal.add(Calendar.DATE, offset);
		}else if("MONTH".equals(type)){
			cal.add(Calendar.MONTH, offset);
		}
		date = cal.getTime();
		cal = null;
		return format.format(date);
	}

	/**
	 * 比较时间 date1<date2,>0;date1=date2,=0;date1>date2,<0;日期格式:yyyy-MM-dd
	 * HH:mm:ss
	 * 
	 * @param date1
	 *            时间格式 2014-06-20 15:16:34
	 * @param date2
	 *            时间格式 2014-06-20 15:16:34
	 * @return
	 */
	public static int compareDateTime(String date1, String date2) {
		try {
			DateFormat df = getDateFormat3();
			return df.parse(date2).compareTo(df.parse(date1));
		} catch (ParseException e) {
			return 0;
		}
	}

	/**
	 * 比较日期 date1<date2,>0;date1=date2,=0;date1>date2,<0;日期格式：yyyy-MM-dd
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int compareDate(String date1, String date2) {
		try {
			DateFormat df = getDateFormat1();
			return df.parse(date2).compareTo(df.parse(date1));
		} catch (ParseException e) {
			return 0;
		}
	}

	/**
	 * 获取字符串时间
	 * 
	 * @param aMask
	 * @param aDate
	 * @return
	 */
	public static final String getDateTime(String aMask, Date aDate) {
		String returnValue = "";
		DateFormat df = null;
		if (aMask.equals(dateFormat1)) {
			df = getDateFormat1();
		} else if (aMask.equals(dateFormat2)) {
			df = getDateFormat2();
		} else {
			df = new SimpleDateFormat(aMask);
		}
		if (aDate != null) {
			returnValue = df.format(aDate);
		}
		return (returnValue);
	}
	
	
	
	
    /**
     * 获得指定日期增量的date
     *
     * @param add
     * @return
     * @throws Exception
     */
    public static Date getSpecifiedDay(Integer add) {//可以用new Date().toLocalString()传递参数
        Calendar c = Calendar.getInstance();
        Date date = new Date();
        c.setTime(date);
        c.add(Calendar.DATE, add);
        Date dayBefore = c.getTime();
        return dayBefore;
    }
}
