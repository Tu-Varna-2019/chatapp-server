package model;

public class FriendRequest {
    private int id;
    private String status;
    private User recipient;

    public FriendRequest(int id, String status, User recipient) {
        this.id = id;
        this.status = status;

        this.recipient = recipient;
    }

    public String getStatus() {
        return status;
    }

    public User getRecipient() {
        return recipient;
    }

    @Override
    public String toString() {
        return String.format("{\"id\":\"%d\",\"status\":\"%s\", \"recipient\":%s}",
                id, status, recipient.toString());
    }

}
