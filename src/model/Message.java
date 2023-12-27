package model;

import java.sql.Timestamp;

public class Message {

    private int id;
    private String content;
    private String attachmentURL;
    private Timestamp timestamp;
    private User sender;

    public Message(int id, String content, String attachmentURL, Timestamp timestamp, User sender) {
        this.id = id;
        this.content = content;
        this.attachmentURL = attachmentURL;
        this.timestamp = timestamp;
        this.sender = sender;
    }

    public int getId() {
        return id;
    }

    public User getSender() {
        return sender;
    }

    public String getAttachmentURL() {
        return attachmentURL;
    }

    public String getContent() {
        return content;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAttachmentURL(String attachmentURL) {
        this.attachmentURL = attachmentURL;
    }

    @Override
    public String toString() {
        return String.format(
                "{\"message\": {\"id\":\"%d\",\"content\":\"%s\",\"attachmentURL\":\"%s\",\"timestamp\":\"%s\", \"sender\":%s}}",
                id, content, attachmentURL, timestamp.toString(), sender.toString());
    }

}
