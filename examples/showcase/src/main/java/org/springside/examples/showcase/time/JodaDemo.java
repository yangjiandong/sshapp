package org.springside.examples.showcase.time;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Years;

public class JodaDemo {

	public static void main(String[] args) throws Exception {
		//test day init and print to string
		DateTime fooDateTime = new DateTime(1978, 6, 1, 12, 10, 8, 0);
		System.out.println(fooDateTime.toString("yyyy-MM-dd HH:mm:ss")); //"1978-06-01 12:10:08"

		//test minus/plus and years/days between function
		DateTime now = new DateTime();
		DateTime nowLater = now.plusHours(22);
		DateTime tomorrow = now.plusHours(25);
		System.out.println(isSameDay(now, nowLater)); //true
		System.out.println(isSameDay(now, tomorrow));//false

		DateTime oneYearsAgo = now.minusYears(2).plusDays(20);
		DateTime twoYearsAgo = now.minusYears(2);
		System.out.println(getAge(oneYearsAgo));//1
		System.out.println(getAge(twoYearsAgo));//2
	}

	public static int getAge(DateTime birthDate) {
		return Years.yearsBetween(birthDate, new DateTime()).getYears();
	}

	public static boolean isSameDay(DateTime dt1, DateTime dt2) {
		return (Days.daysBetween(dt1, dt2).getDays() < 1);
	}

}
