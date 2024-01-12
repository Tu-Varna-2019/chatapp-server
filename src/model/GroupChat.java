package model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import controller.helpers.Helpers;

public class GroupChat {
    private int id;
    private String name;
    private Integer[] userids;
    private List<User> users;

    public GroupChat(int id, String name, Integer[] userids) {
        this.id = id;
        this.name = name;
        this.userids = userids;
    }

    // Constructor for Jackson to deserialize
    @JsonCreator
    public GroupChat(@JsonProperty("id") Integer id, @JsonProperty("name") String name,
            @JsonProperty("users") List<User> users) {
        this.id = id == null ? 0 : id;
        this.name = name;
        this.users = users == null ? new ArrayList<>() : users;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer[] getUserids() {
        return userids;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return String.format("{\"id\":%d, \"name\":\"%s\", \"users\":%s}",
                id, name, Helpers.usersToJson(users));
    }

}
