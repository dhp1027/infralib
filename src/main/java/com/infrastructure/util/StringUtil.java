package com.infrastructure.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/10/13.
 */

public class StringUtil {

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
     * @param pattern yyyy-MM-dd hh:mm:ss,yyyy-MM-dd,yyyMMdd
     * @return
     */
    public static String dateToStr(long milliseconds,String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.format(new Date(milliseconds));
    }

    /**
     *
     * @param yyyyMMdd 要求格式为yyyy-MM-dd
     * @return
     */
    public static Date strToDate(String yyyyMMdd) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return df.parse(yyyyMMdd);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param yyyyMMdd
     * @param inPattern 字符串格式可以为yyyy-MM-dd hh:mm:ss,yyyy-MM-dd,yyyMMdd等等
     * @return
     */
    public static Date strToDate(String yyyyMMdd,String inPattern) {
        SimpleDateFormat df = new SimpleDateFormat(inPattern);
        try {
            return df.parse(yyyyMMdd);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isChineseChar(String str){
        boolean result = false;
        Pattern p=Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m=p.matcher(str);
        if(m.find()){
            result =  true;
        }
        return result;
    }
}
