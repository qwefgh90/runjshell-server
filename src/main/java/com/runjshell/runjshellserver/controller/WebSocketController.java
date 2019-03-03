package com.runjshell.runjshellserver.controller;


import com.runjshell.runjshellserver.model.BrowserRequest;
import com.runjshell.runjshellserver.model.BrowserResponse;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/req")
    @SendTo("/topic/res")
    public BrowserResponse greeting(BrowserRequest message) throws Exception {
//        Thread.sleep(1000); // simulated delay
        return new BrowserResponse();
    }

}
