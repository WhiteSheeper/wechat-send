package com.white.wechatsend.service;

import com.white.wechatsend.config.SendConfiguration;
import com.white.wechatsend.domain.Weather;
import com.white.wechatsend.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

/**
 * 消息发送
 */
@Slf4j
public class SendService {
    /**
     * 消息推送主要业务代码
     */
    public static String send() {
        //1，配置
        WxMpInMemoryConfigStorage wxStorage = new WxMpInMemoryConfigStorage();
        wxStorage.setAppId(SendConfiguration.getAppId());
        wxStorage.setSecret(SendConfiguration.getSecret());
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxStorage);
        // 推送消息
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser(SendConfiguration.getUserId())
                .templateId(SendConfiguration.getTemplateId())
                .build();
        // 配置你的信息
        long nextBirthday = DateUtils.getNextBirthday(SendConfiguration.getBirthday());
        long sinceDate = DateUtils.sinceDay(SendConfiguration.getLoveDate());
        Weather weather = WeatherService.getWeather();
        if (weather == null) {
            templateMessage.addData(new WxMpTemplateData("weather", "***", "#00FFFF"));
        } else {
            templateMessage.addData(new WxMpTemplateData("weather", weather.getNow(), "#00FFFF"));
            templateMessage.addData(new WxMpTemplateData("low", weather.getLow() + "", "#173177"));
            templateMessage.addData(new WxMpTemplateData("temp", weather.getTemp() + "", "#EE212D"));
            templateMessage.addData(new WxMpTemplateData("high", weather.getHigh() + "", "#FF6347"));
            templateMessage.addData(new WxMpTemplateData("city", weather.getCity() + "", "#173177"));
        }
        //纪念日
        templateMessage.addData(new WxMpTemplateData("loveDays", sinceDate + "", "#FF1493"));
        templateMessage.addData(new WxMpTemplateData("birthdays", nextBirthday + "", "#FFA500"));
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (Exception e) {
            log.error("推送失败：{}", e.getMessage());
            return "推送失败：" + e.getMessage();
        }
        return "推送成功!";
    }
}
