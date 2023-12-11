package model.database.sql_statements;

public class DeleteRecord {

    public final String DeleteUserEQID(int id) {

        return "DELETE FROM \"User\" WHERE id= " + id + ";";
    };

}
