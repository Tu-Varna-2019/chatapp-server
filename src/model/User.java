package model;

public class User {
    private int id;
    private String email;
    private String username;
    private String password;

    public User() {
    }

    public User(int id, String username, String email, String password) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    @Override
    public String toString() {
        return String.format("{\"id\":\"%d\", \"email\":\"%s\", \"password\":\"%s\", \"username\":\"%s\"}",
                id, email, password, username);
    }

}
