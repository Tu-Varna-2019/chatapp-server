package model;

public class FriendRequest {
    private int id;
    private String status;
    private User sender;
    private User recipient;

    public FriendRequest(int id, String status,User sender, User recipient) {
        this.id = id;
        this.status = status;
        this.sender = sender;
        this.recipient = recipient;
    }

    public String getStatus() {
        return status;
    }

    public User getSender() {
        return sender;
    }

    public User getRecipient() {
        return recipient;
    }

    @Override
    public String toString() {

        //recipient ? ChadDBManager
        return String.format("{\"id\":\"%d\",\"status\":\"%s\", \"recipient\":%s, \"sender\":%s}",
                id, status, recipient.toString(), sender.toString());
    }

}
