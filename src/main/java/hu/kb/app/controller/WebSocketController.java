package hu.kb.app.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;

@CrossOrigin
@Controller
class WebSocketController {

    @MessageMapping("/hello/")
    @SendTo("/topic/greetings")
    public String greeting(@PathVariable String roomId, String message) throws Exception {
        System.out.println("SOCKET MESSAGE COME WITH THE FOLLOWING MESSEAGE " + message);
        System.out.println("WITH THE ID OF " + roomId);
        Thread.sleep(1000); // simulated delay
        return "SOCKET MESSAGE COME WITH THE FOLLOWING MESSEAGE " + message + "WITH THE ID OF " + roomId;
    }
}
