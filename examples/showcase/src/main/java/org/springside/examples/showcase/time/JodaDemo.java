package org.springside.examples.showcase.time;

import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.util.Assert;

public class JodaDemo {

	public static void main(String[] args) {
		testTimeZone();
		testLocale();
		testGetAge();
	}

	public static void testTimeZone() {

		System.out.println("演示时区");

		String format = "yyyy-MM-dd HH:mm:ss zZZ";

		//DateTime的毫秒即System的毫秒,即1970到现在的UTC的毫秒数.
		System.out.println(new DateTime().getMillis() + " " + System.currentTimeMillis());

		//将日期按默认时区打印
		DateTime fooDate = new DateTime(1978, 6, 1, 12, 10, 8, 0);
		System.out.println(fooDate.toString(format) + " " + fooDate.getMillis()); //"1978-06-01 12:10:08" 

		//将日期按UTC时区打印
		DateTime zoneWithUTC = fooDate.withZone(DateTimeZone.UTC);
		System.out.println(zoneWithUTC.toString(format) + " " + zoneWithUTC.getMillis());//"1978-06-01 04:10:08", sameMills

		//按不同的时区分析字符串,得到不同的时间
		String dateString = "1978-06-01 12:10:08";
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

		DateTime parserResult1 = fmt.withZone(DateTimeZone.forID("US/Pacific")).parseDateTime(dateString);
		DateTime parserResult2 = fmt.withZone(DateTimeZone.UTC).parseDateTime(dateString);

		System.out.println(parserResult1.toString(format) + " " + parserResult1.getMillis());
		System.out.println(parserResult2.toString(format) + " " + parserResult2.getMillis());
	}

	public static void testLocale() {

		System.out.println("演示Locale");

		DateTime dateTime = new DateTime().withZone(DateTimeZone.UTC);

		//打印中文与英文下不同长度的日期格式串
		System.out.println("S:  " + DateUtils.formatDateTime(dateTime, "SS", "zh"));
		System.out.println("M:  " + DateUtils.formatDateTime(dateTime, "MM", "zh"));
		System.out.println("L:  " + DateUtils.formatDateTime(dateTime, "LL", "zh"));
		System.out.println("XL: " + DateUtils.formatDateTime(dateTime, "FF", "zh"));
		System.out.println("");

		System.out.println("S:  " + DateUtils.formatDateTime(dateTime, "SS", "en"));
		System.out.println("M:  " + DateUtils.formatDateTime(dateTime, "MM", "en"));
		System.out.println("L:  " + DateUtils.formatDateTime(dateTime, "LL", "en"));
		System.out.println("XL: " + DateUtils.formatDateTime(dateTime, "FF", "en"));
		System.out.println("");
		System.out.println("");

		//直接打印TimeStamp, 日期是M,时间是L
		DateTimeFormatter formatter = DateTimeFormat.forStyle("ML").withLocale(new Locale("zh")).withZone(
				DateTimeZone.UTC);

		System.out.println("ML Mix: " + formatter.print(dateTime.getMillis()));

		//只打印日期不打印时间
		System.out.println("Date only :" + DateUtils.formatDateTime(dateTime, "M-", "zh"));

	}

	public static void testGetAge() {
		DateTime now = new DateTime();

		DateTime oneYearsAgo = now.minusYears(2).plusDays(20);
		DateTime twoYearsAgo = now.minusYears(2);
		Assert.isTrue(1 == DateUtils.getAge(oneYearsAgo));
		Assert.isTrue(2 == DateUtils.getAge(twoYearsAgo));
	}
}
