package com.white.wechatsend.domain;

import lombok.Builder;
import lombok.Data;

/**
 * 天气实体类
 */
@Data
@Builder
public class Weather {
    //当前日期
    private String date;
    // 当前天气
    private String now;
    //最高气温
    private String high;
    //最低气温
    private String low;
    // 当前温度
    private String temp;
    // 当前城市
    private String city;
}