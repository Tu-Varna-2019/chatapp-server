package model.dataclass;

import model.FriendRequest;
import model.GroupChat;
import model.Message;
import model.User;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class DataRequest {
    public String id;
    public User user;
    public Message message;
    public FriendRequest friendrequest;
    public String groupchatid;
    public GroupChat groupchat;
}
