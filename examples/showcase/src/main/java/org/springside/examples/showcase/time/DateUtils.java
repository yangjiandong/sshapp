package org.springside.examples.showcase.time;

import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateUtils {

	/**
	 * 根据生日获得年龄的Utils方法.
	 */
	public static int getAge(DateTime birthDate) {
		return Years.yearsBetween(birthDate, new DateTime()).getYears();
	}

	/**
	 * 打印各种语言各种长度默认格式的日期时间串的Utils方法.
	 */
	public static String formatDateTime(DateTime dateTime, String style, String lang) {
		DateTimeFormatter formatter = DateTimeFormat.forStyle("M-").withLocale(new Locale(lang));
		return dateTime.toString(formatter);
	}

}
