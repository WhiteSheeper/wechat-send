package com.white.wechatsend.times;


import com.white.wechatsend.service.SendService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class SendScheduled {

    @Scheduled(cron = "0 0 8 * * ?")
    public void sendMsg() {
        SendService.send();
    }
}
