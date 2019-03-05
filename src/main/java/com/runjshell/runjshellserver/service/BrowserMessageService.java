package com.runjshell.runjshellserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class BrowserMessageService {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public BrowserMessageService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // ...

    public void afterTradeExecuted() {
        this.messagingTemplate.convertAndSendToUser(
                "username", "/queue/position-updates", "result");
    }
}