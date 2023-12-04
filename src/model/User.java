package model;

public class User {
    private String email;
    private String username;
    private String password;

    public User() {
    }

    public User(String username, String email, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getEmail() {
        return email;
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
        return "User [email=" + email + ", password=" + password + ", username=" + username + "]";
    }

}
