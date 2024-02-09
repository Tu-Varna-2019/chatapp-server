package com.chatapp.model.dataclass;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import com.chatapp.model.FriendRequest;
import com.chatapp.model.User;
import com.chatapp.model.Message;
import com.chatapp.model.GroupChat;

@JsonInclude(Include.NON_NULL)
public class DataResponse {

    public List<FriendRequest> friendrequests;
    public User user;
    public List<Message> messages;
    public List<GroupChat> groupchats;

}
