package com.ekingsoft.core.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer;

public class AppUtil {

    /**
     * 返回当前日期 yyyy-mm-dd
     *
     * @return String
     */
    public static String getNowDateString(String sign) {
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("yyyy" + sign + "MM" + sign + "dd");
        String dateString = formatter.format(new Date());
        return dateString.trim();
    }

    /**
     * 返回当前系统采用的日期分隔符
     */
    public static String getDateSplit() {
        return ".";
    }

    /**
     * 返回当前系统采用的日期分隔符组成的当前日期字符串
     */
    public static String getNowDate() {
        return getNowDateString(AppUtil.getDateSplit());
    }

    /**
     * 返回昨天日期
     *
     * @return
     */
    public static String getYesterDay() {
        return addDays(getNowDateString(AppUtil.getDateSplit()), -1);
    }

    /**
     * 在指定的日期上增减天数
     *
     * @param date
     * @param days
     * @return
     */
    public static Date addDays(Date date, int days) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    /**
     * 为字符型日期增加天数
     */
    public static String addDays(String dateStr, int days) {
        Date date = getStrDateToDate(dateStr);
        date = addDays(date, days);

        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("yyyy" + getDateSplit() + "MM" + getDateSplit() + "dd");
        String dateString = formatter.format(date);

        return dateString.trim();
    }

    /**
     * 在指定的日期上增减月数
     *
     * @param date
     * @param months
     * @return
     */
    public static Date addMonths(Date date, int months) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.MONTH, months);
        return cal.getTime();
    }

    /**
     * 为字符型日期增加月数
     */
    public static String addMonths(String dateStr, int months) {
        Date date = getStrDateToDate(dateStr);
        date = addMonths(date, months);

        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("yyyy" + getDateSplit() + "MM" + getDateSplit() + "dd");
        String dateString = formatter.format(date);

        return dateString.trim();
    }

    /**
     * 为字符型日期增加月数，相应减去一天
     */
    public static String addMonths2(String dateStr, int months) {
        Date date = getStrDateToDate(dateStr);
        date = addMonths(date, months);
        date = addDays(date, -1);

        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("yyyy" + getDateSplit() + "MM" + getDateSplit() + "dd");
        String dateString = formatter.format(date);

        return dateString.trim();
    }

    /**
     * 转换字符日期值到日期符，字符日期值的日期分隔符为jsite-config.xml定义
     */
    public static Date getStrDateToDate(String dateStr) {
        String dateSplit = getDateSplit();
        if (dateSplit.equals("."))
            dateSplit = "\\.";
        StringTokenizer filter = new StringTokenizer(dateStr, dateSplit);
        int year = Integer.parseInt(filter.nextToken());
        int mon = Integer.parseInt(filter.nextToken()) - 1;
        int day = Integer.parseInt(filter.nextToken());

        Calendar cal = new GregorianCalendar(year, mon, day);
        return cal.getTime();
    }

    /**
     * 返回指定日期的日期值
     */
    public static String getDateDay(String date) {
        String day = "";

        String dateSplit = getDateSplit();
        if (dateSplit.equals("."))
            dateSplit = "\\.";

        StringTokenizer filter = new StringTokenizer(date, dateSplit);
        day = filter.nextToken();
        day = filter.nextToken();
        day = filter.nextToken();

        return day;
    }

    /**
     * 返回指定日期的月份值
     */
    public static String getDateMonth(String date) {
        String month = "";

        String dateSplit = getDateSplit();
        if (dateSplit.equals("."))
            dateSplit = "\\.";

        StringTokenizer filter = new StringTokenizer(date, dateSplit);
        month = filter.nextToken();
        month = filter.nextToken();

        return month;
    }

    /**
     * 返回指定日期的年份值
     */
    public static String getDateYear(String date) {
        String year = "";

        String dateSplit = getDateSplit();
        if (dateSplit.equals("."))
            dateSplit = "\\.";

        StringTokenizer filter = new StringTokenizer(date, dateSplit);
        year = filter.nextToken();

        return year;
    }

    /**
     * 从日期型中取得日期字符串
     */
    public static String getDateStringFromDate(Date date) {
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("yyyy" + getDateSplit() + "MM" + getDateSplit() + "dd");
        String dateString = formatter.format(date);

        return dateString.trim();
    }

    // 返回两个日期之间天数
    public static long differenceInDays(Calendar date1, Calendar date2) {
        final long msPerDay = 1000 * 60 * 60 * 24;

        final long date1Milliseconds = date1.getTime().getTime();
        final long date2Milliseconds = date2.getTime().getTime();
        final long result = (date1Milliseconds - date2Milliseconds) / msPerDay;

        return result;
    }

    /**
     * 返回两个日期之间的天数（相同天数，返回值为0）
     *
     * @param startDate
     *            开始日期
     * @param endDate
     *            结束日期
     * @return 天数
     */
    public static long getDayTotal(String startDate, String endDate) {

        Calendar startCal, endCal;
        int lyear, lmonth, lday;

        lyear = Integer.parseInt(AppUtil.getDateYear(startDate));
        lmonth = Integer.parseInt(AppUtil.getDateMonth(startDate));
        lday = Integer.parseInt(AppUtil.getDateDay(startDate));
        startCal = new GregorianCalendar(lyear, lmonth - 1, lday);

        lyear = Integer.parseInt(AppUtil.getDateYear(endDate));
        lmonth = Integer.parseInt(AppUtil.getDateMonth(endDate));
        lday = Integer.parseInt(AppUtil.getDateDay(endDate));
        endCal = new GregorianCalendar(lyear, lmonth - 1, lday);

        return differenceInDays(endCal, startCal);
    }

    /**
     * 返回当前日期年份的第一天
     *
     * @return
     */
    public static String getThisYearFirstDate() {
        String now = getNowDate();
        String thisYear = getDateYear(now);

        Calendar lcal = new GregorianCalendar(Integer.parseInt(thisYear), 0, 1);
        return getDateStringFromDate(lcal.getTime());
    }

    /**
     * 返回两个年份的差值 year2 - year1
     */
    public static int getYearsBetween(String year1, String year2) throws NumberFormatException {
        return Integer.parseInt(year2) - Integer.parseInt(year1);
    }

    /**
     * 返回两个yyyy.mm值的月份差值 ym2 - ym1
     */
    public static int getYearMonthBetween(String ym1, String ym2) throws NumberFormatException {
        StringTokenizer filter = new StringTokenizer(ym1, "\\.");
        int year1 = Integer.parseInt(filter.nextToken());
        int mon1 = Integer.parseInt(filter.nextToken());
        if (mon1 > 12) {
            // log.error("月格式错");
            throw new NumberFormatException("月格式错");
        }

        StringTokenizer filter2 = new StringTokenizer(ym2, "\\.");
        int year2 = Integer.parseInt(filter2.nextToken());
        int mon2 = Integer.parseInt(filter2.nextToken());
        if (mon2 > 12) {
            // log.error("月格式错");
            throw new NumberFormatException("月格式错");
        }

        int defYear = year2 - year1;
        if (defYear == 0) {
            return mon2 - mon1;
        } else {
            return (defYear - 1) * 12 + (12 - mon1) + mon2;
        }
    }

    /**
     * 从yyyy.mm格式返回一个日期值
     *
     * @param ym1
     * @param type
     * @return
     * @throws NumberFormatException
     */
    public static String getDateFromYYYYMM(String ym1, String type) throws NumberFormatException {
        StringTokenizer filter = new StringTokenizer(ym1, "\\.");
        String year = filter.nextToken();
        String mon = filter.nextToken();
        if (mon.compareTo("12") > 0) {
            // log.error("月格式错");
            throw new NumberFormatException("月格式错");
        }

        if (type.equalsIgnoreCase("first")) {
            return year + "." + mon + "." + "01";
        } else if (type.equalsIgnoreCase("end")) {
            String next = addMonths(year + "." + mon + "." + "01", 1);
            return addDays(next, -1);
        } else {
            return year + "." + mon + "." + "01";
        }

    }

    /**
     * 将浮点数转换成货币格式的字符串(#,##0.00)
     *
     * @param money
     *            需转换的浮点数 (double)
     * @return 货币格式字符串(#,##0.00)
     */
    public static String getMoneyFormat(double money) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return df.format(money);
    }

    /**
     * 创建手动指派的ID值，如生成数据库中的ID主健值。
     * @param maxId 所有连续ID值中最大的ID，
     *      程序将根据maxId生成比该maxId值大1的连续ID值。
     *      如：有GB000001、GB000002、GB000003这三个连续的ID值，
     *      那么最大的ID值就是GB000003，这时程序生成的连续ID值就是GB000004。
     * @param headId 头ID标识。如：规定要生成的ID值是GB000001这种形式的值，
     *      那么GB就是头ID标识。
     * @return 生成比maxId值大1的连续ID值。
     */
    public static String buildAssignedId(String maxId,String headId){
        // 手动指派的ID值
        String buildId = null;
        // 补零
        StringBuffer fillZero = null;
        // 数字位的位数
        int numberBit = maxId.replaceFirst(headId, "").length();
        // 获得当前数字
        int number = Integer.parseInt(maxId.replaceFirst(headId, ""));
        // 获得下一位数字的位数
        int nextNumBit = String.valueOf(number + 1).length();
        // 产生下一位数字
        number += 1;
        // 创建手动指派的ID值
        if(numberBit - nextNumBit > 0){
            fillZero = new StringBuffer();
            // 补零
            for (int i = 0; i < numberBit - nextNumBit; i++) {
                fillZero.append(0);
            }
            buildId = headId + fillZero.toString() + number;
        } else {
            buildId = headId + number;
        }

        return buildId;
    }
    /**
     * 批量创建手动指派的ID值，如生成数据库中的ID主健值。
     * @param maxId 所有连续ID值中最大的ID，
     *      程序将根据该maxId生成比该maxId值大1的连续ID值，
     *      如：有GB000001、GB000002、GB000003这三个连续ID值，
     *      那么最大的ID值就是GB000003，这时程序生成的连续ID值就是GB000004。
     * @param headId 头ID标识，如：规定要生成的ID值是GB000001这种形式的值，
     *      那么GB就是头ID标识。
     * @param idNum 要批量生成的连续ID的数量。如要批量生成5个连续的ID，则该数量应该是 5
     * @return 生成比 maxId大1的连续ID值的List列表
     */
    public static List<String> buildAssignedIds(String maxId,String headId
                                                ,int idNum){
        // 已创建的ID列表
        List<String> idList = new ArrayList<String>();
        // 手动指派的ID值
        String buildId = null;
        // 补零
        StringBuffer fillZero = null;
        // 数字位的位数
        int numberBit = maxId.replaceFirst(headId, "").length();
        // 获得当前数字
        int number = Integer.parseInt(maxId.replaceFirst(headId, ""));
        // 获得下一位数字的位数
        int nextNumBit = String.valueOf(number + 1).length();
        // 产生下一位数字
        for (int i = 0; i < idNum; i++) {
            number += 1;

            if(numberBit - nextNumBit > 0){
                if(fillZero == null)
                    fillZero = new StringBuffer();
                // 补零
                for (int j = 0; j < numberBit - nextNumBit; j++) {
                    fillZero.append(0);
                }
                buildId = headId + fillZero.toString() + number;
                // 获得下一位数字的位数
                nextNumBit = String.valueOf(number + 1).length();
                // 重置fillZero
                fillZero.delete(0, fillZero.length());

                idList.add(buildId);
            } else {
                buildId = headId + number;
                // 获得下一位数字的位数
                nextNumBit = String.valueOf(number + 1).length();

                idList.add(buildId);
            }
        }

        return idList;
    }

}
