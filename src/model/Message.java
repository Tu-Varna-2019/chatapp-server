package model;

import java.sql.Timestamp;

public class Message {

    private String content;
    private String attchmentURL;
    private Timestamp timestamp;

    public Message(String content, String attchmentURL, Timestamp timestamp) {
        this.content = content;
        this.attchmentURL = attchmentURL;
        this.timestamp = timestamp;
    }

    public String getAttchmentURL() {
        return attchmentURL;
    }

    public String getContent() {
        return content;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

}
