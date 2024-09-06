package com.white.wechatsend.utils;

import cn.hutool.core.date.ChineseDate;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;

import java.util.Date;

public class DateUtils {




    /**
     * 计算距离下一个农历生日还有多少天
     *
     * @param birthday 生日日期字符串，格式为 "yyyy-MM-dd"
     * @return 距离下一个农历生日的天数
     */
    public static long getNextBirthday(String birthday) {
        Date birthdayDate = DateUtil.parse(birthday);
        ChineseDate chineseBirthday = new ChineseDate(birthdayDate);
        int year = DateUtil.year(new Date());
        Date nextBirthday = getNextChineseBirthday(chineseBirthday, year);
        return DateUtil.between(new Date(), nextBirthday, DateUnit.DAY);
    }

    /**
     * 计算距离下一个农历生日还有多少天（基于农历输入）
     *
     * @return 距离下一个农历生日的天数
     */
    public static long daysUntilNextChineseBirthday(int year, int month, int day) {
        int currentYear = DateUtil.year(new Date());
        ChineseDate chineseDate = new ChineseDate(year, month, day);
        Date nextBirthday = getNextChineseBirthday(chineseDate, currentYear);
        return DateUtil.between(new Date(), nextBirthday, DateUnit.DAY);
    }

    /**
     * 获取下一个农历生日的公历日期
     */
    private static Date getNextChineseBirthday(ChineseDate chineseBirthday, int year) {
        ChineseDate nextBirthday = new ChineseDate(year, chineseBirthday.getMonth(), chineseBirthday.getDay());
        Date gregorianDate = nextBirthday.getGregorianDate();
        
        if (DateUtil.compare(gregorianDate, new Date()) < 0) {
            nextBirthday = new ChineseDate(year + 1, chineseBirthday.getMonth(), chineseBirthday.getDay());
            gregorianDate = nextBirthday.getGregorianDate();
        }
        
        return gregorianDate;
    }

    /**
     * 计算从指定日期到今天已经过了多少天
     *
     * @param date 指定的日期字符串，格式为 "yyyy-MM-dd"
     * @return 从指定日期到今天的天数
     */
    public static long sinceDay(String date) {
        Date specifiedDate = DateUtil.parse(date);
        return DateUtil.between(specifiedDate, new Date(), DateUnit.DAY);
    }


}