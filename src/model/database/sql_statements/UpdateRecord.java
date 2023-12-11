package model.database.sql_statements;

public class UpdateRecord {
    public final String UpdateUsernameEQEmail(String username, String email) {

        return "UPDATE \"User\" SET USERNAME = '" + username + "' WHERE email= '" + email + "';";
    };
}
