package com.white.wechatsend.controller;

import com.white.wechatsend.service.SendService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("wechat")
public class WechatSendController {

    @GetMapping("/send")
    public void senMsg(){
        SendService.send();
    }
}
