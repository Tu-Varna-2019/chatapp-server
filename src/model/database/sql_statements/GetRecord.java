package model.database.sql_statements;

public class GetRecord {

    public final String getUserEQEmail(String email) {

        return "SELECT * FROM \"User\" WHERE email='" + email + "';";
    };
};
