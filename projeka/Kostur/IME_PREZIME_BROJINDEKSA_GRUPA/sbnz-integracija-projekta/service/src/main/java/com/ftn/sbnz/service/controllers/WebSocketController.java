package com.ftn.sbnz.service.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/topic/8") // Match the destination of your WebSocket messages
    public void handleWebSocketMessage(@Payload String message) {
        System.out.println("Message received on WebSocket destination '/topic/8': " + message);
        // You can add further processing logic here if needed
    }
}
