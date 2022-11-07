package ru.mirea.websocketserver.dto;

public class RequestMessageDto {

    private String usernameOfSender;
    private String text;


    public RequestMessageDto() {}
    
    public RequestMessageDto(String usernameOfSender, String text) {
        this.usernameOfSender = usernameOfSender;
        this.text = text;
    }


    public String getUsernameOfSender() {
        return usernameOfSender;
    }

    public void setUsernameOfSender(String usernameOfSender) {
        this.usernameOfSender = usernameOfSender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
