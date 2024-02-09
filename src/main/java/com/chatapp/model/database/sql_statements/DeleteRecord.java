package com.chatapp.model.database.sql_statements;

public class DeleteRecord {

    private final String DELETE_FROM = "DELETE FROM ";
    private final String WHERE_ID = "WHERE id= ? ;";

    public final String DeleteUserEQID = DELETE_FROM + " \"User\" " + WHERE_ID;

    public final String DeleteMessageEQID = DELETE_FROM + " \"Message\" " + WHERE_ID;

    public final String DeleteGroupChatEQID = DELETE_FROM + " \"GroupChat\" " + WHERE_ID;

    public final String DeleteFriendRequestEQID = DELETE_FROM + " \"FriendRequest\" " + WHERE_ID;

}
