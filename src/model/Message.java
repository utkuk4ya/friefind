package model;

import java.time.LocalDateTime;

public class Message {
	
    private User sender;
    private User receiver;
    private String content;
    private LocalDateTime timestamp;
    private boolean isRead;

    public Message(User sender, User receiver, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.timestamp = LocalDateTime.now();
        this.isRead = false;
    }

    public void markAsRead() {
        this.isRead = true;
    }

    public String getFormattedMessage() {
        return "[" + timestamp.getHour() + ":" + timestamp.getMinute() + "] " 
               + sender.getName() + ": " + content;
    }
}