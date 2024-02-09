package com.chatapp.model.database.sql_statements;

public class InsertRecord {

    public final String INSERT_USER = "INSERT INTO \"User\"(username, email, password) VALUES (?, ?, ?)";

    public final String INSERT_MESSAGE = "INSERT INTO \"Message\"(content, attachmenturl, timestamp, senderid, groupchatid) VALUES (?, ?, ?, ?, ?)";

    public final String INSERT_GROUPCHAT = "INSERT INTO \"GroupChat\"(name, userids) VALUES (?, ?)";

    public final String INSERT_FRIEND_REQUEST = "INSERT INTO \"FriendRequest\"(status, senderid, recipientid) VALUES (?, ?, ?)";
}
