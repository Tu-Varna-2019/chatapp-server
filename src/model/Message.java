package model;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import model.dataclass.CustomTimestampDeserializer;

public class Message {

    private int id;
    private String content;
    private String attachmentURL;

    @JsonDeserialize(using = CustomTimestampDeserializer.class)
    private Timestamp timestamp;
    private User sender;

    @JsonCreator
    public Message(@JsonProperty("id") int id, @JsonProperty("content") String content,
            @JsonProperty("attachmentURL") String attachmentURL,
            @JsonProperty("timestamp") Timestamp timestamp,
            @JsonProperty("sender") User sender) {
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
                "{\"id\":\"%d\",\"content\":\"%s\",\"attachmentURL\":\"%s\",\"timestamp\":\"%s\", \"sender\":%s}",
                id, content, attachmentURL, timestamp.toString(), sender.toString());
    }

}
