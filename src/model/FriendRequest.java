package model;

public class FriendRequest {
    private int id;
    private String status;
    private User sender;
    private User recipient;

    public FriendRequest() {
    }

    public FriendRequest(int id, String status, User sender, User recipient) {
        this.id = id;
        this.status = status;
        this.sender = sender;
        this.recipient = recipient;

    }

    public int getId() {
        return id;
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

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    @Override
    public String toString() {
        return "FriendRequest{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", sender=" + sender +
                ", recipient=" + recipient +
                '}';
    }

}
