package ru.mirea.websocketserver.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;
import ru.mirea.websocketserver.model.Message;
import ru.mirea.websocketserver.model.User;

@Controller
public class ExampleController {

    @MessageMapping("/connect")
    @SendTo("/topic/messages")
    public Message connect(User user) throws Exception {
        return new Message("SYSTEM", HtmlUtils.htmlEscape(user.getUsername()) + " with us now!");
    }

    @MessageMapping("/disconnect")
    @SendTo("/topic/messages")
    public Message disconnect(User user) throws Exception {
        return new Message("SYSTEM", HtmlUtils.htmlEscape(user.getUsername()) + " has been disconnected.");
    }

    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public Message message(Message message) throws Exception {
        return message;
    }

}
