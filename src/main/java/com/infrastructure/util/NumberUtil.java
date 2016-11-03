package com.infrastructure.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/10/13.
 */

public class NumberUtil {

    /**
     * 保留0位小数
     * @param n
     * @return
     */
    public static String numToString0(float n) {
        DecimalFormat df = new DecimalFormat("0");
        return  df.format(n);
    }
    /**
     * 保留0位小数
     * @param n
     * @return
     */
    public static String numToString0(double n) {
        DecimalFormat df = new DecimalFormat("0");
        return  df.format(n);
    }
    /**
     * 保留0位小数
     * @param n
     * @return
     */
    public static String numToString0(int n) {
        DecimalFormat df = new DecimalFormat("0");
        return  df.format(n);
    }
    /**
     * 保留2位小数
     * @param n
     * @return
     */
    public static String numToString2(float n) {
        DecimalFormat df = new DecimalFormat("0.00");
        return  df.format(n);
    }
    /**
     * 保留2位小数
     * @param n
     * @return
     */
    public static String numToString2(double n) {
        DecimalFormat df = new DecimalFormat("0.00");
        return  df.format(n);
    }
    /**
     * 保留2位小数
     * @param n
     * @return
     */
    public static String numToString2(int n) {
        DecimalFormat df = new DecimalFormat("0.00");
        return  df.format(n);
    }

    public static String dateToStr(long milliseconds) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(new Date(milliseconds));
    }

    /**
     *
     *
     * @param pattern yyyy-MM-dd hh:mm:ss,yyyy-MM-dd
     * @return
     */
    public static String dateToStr(long milliseconds,String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.format(new Date(milliseconds));
    }

    public static Date strToDate(String yyyyMMdd) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return df.parse(yyyyMMdd);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
