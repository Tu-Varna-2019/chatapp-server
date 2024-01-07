package model;

import java.util.List;

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
