package com.white.wechatsend.service;

import com.alibaba.fastjson.JSONObject;

import com.white.wechatsend.config.SendConfiguration;
import com.white.wechatsend.domain.Weather;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取今日天气
 * auth: whiteD
 */
@Component
public class WeatherService {
    public static Weather getWeather() {
        String url = "https://api.map.baidu.com/weather/v1/?district_id={district_id}&data_type=all&ak={ak}";
        Map<String, String> params = new HashMap<>();
        params.put("district_id", SendConfiguration.getDistrict_id());
        params.put("ak", SendConfiguration.getAk());
        RestTemplate restTemplate = new RestTemplate();
        String res = restTemplate.getForObject(url, String.class, params);
        JSONObject json = JSONObject.parseObject(res);
        if (json == null || json.getJSONObject("result") == null) {
            return null; // 接口地址有误或者接口没调通
        }
        JSONObject result = json.getJSONObject("result");
        List<Weather> weathers = result.getJSONArray("forecasts").toJavaList(Weather.class);
        Weather weather = weathers.get(0);
        JSONObject now = result.getJSONObject("now");
        JSONObject location = result.getJSONObject("location");
        weather.setNow(now.getString("text"));
        weather.setTemp(now.getString("temp"));
        weather.setCity(location.getString("city"));
        return weather;
    }
}
