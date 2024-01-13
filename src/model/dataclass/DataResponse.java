package model.dataclass;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import model.FriendRequest;
import model.User;
import model.Message;
import model.GroupChat;

@JsonInclude(Include.NON_NULL)
public class DataResponse {

    public List<FriendRequest> friendrequests;
    public User user;
    public List<Message> messages;
    public List<GroupChat> groupchats;

}
