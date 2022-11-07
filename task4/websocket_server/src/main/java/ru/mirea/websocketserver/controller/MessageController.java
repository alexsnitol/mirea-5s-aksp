package ru.mirea.websocketserver.controller;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;
import ru.mirea.websocketserver.dto.RequestMessageDto;
import ru.mirea.websocketserver.model.Message;
import ru.mirea.websocketserver.model.User;
import ru.mirea.websocketserver.repository.MessageRepository;
import ru.mirea.websocketserver.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Controller
public class MessageController {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    private User systemUser;

    public MessageController(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    @PostConstruct
    void init() {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByUsername("SYSTEM"));

        if (userOptional.isPresent()) {
            systemUser = userOptional.get();
        } else {
            systemUser = new User();
            systemUser.setUsername("SYSTEM");

            userRepository.save(systemUser);
        }
    }


    @MessageMapping("/connect")
    @SendTo({"/topic/messages"})
    public Message connect(User user) throws AuthenticationException {
        Message message;

        User userInDb = userRepository.findByUsername(user.getUsername());

        if (userInDb != null) {
            if (user.getPassword().equals(userInDb.getPassword())) {
                message = new Message(systemUser, HtmlUtils.htmlEscape(user.getUsername()) + " returned!");
            } else {
                message = new Message(systemUser, "Invalid credentials");
            }
        } else {
            userRepository.save(user);
            message = new Message(systemUser, HtmlUtils.htmlEscape(user.getUsername()) + " with us now!");
        }

        return messageRepository.save(message);
    }

    @MessageMapping("/disconnect")
    @SendTo("/topic/messages")
    public Message disconnect(User user) {
        Message message
                = new Message(systemUser, HtmlUtils.htmlEscape(user.getUsername()) + " has been disconnected.");

        return messageRepository.save(message);
    }

    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public Message message(RequestMessageDto messageDto) {
        User sender = userRepository.findByUsername(messageDto.getUsernameOfSender());

        Message message = new Message();
        message.setSender(sender);
        message.setText(messageDto.getText());

        return messageRepository.save(message);
    }

}
