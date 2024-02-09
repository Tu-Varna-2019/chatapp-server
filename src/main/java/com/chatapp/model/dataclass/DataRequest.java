package com.chatapp.model.dataclass;

import com.chatapp.model.FriendRequest;
import com.chatapp.model.GroupChat;
import com.chatapp.model.Message;
import com.chatapp.model.User;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class DataRequest {
    public String id;
    public User user;
    public Message message;
    public FriendRequest friendrequest;
    public GroupChat groupchat;
}
