package model.database.sql_statements;

public class InsertRecord {

    public final String INSERT_USER = "INSERT INTO \"User\"(username, email, password) VALUES (?, ?, ?)";

    public final String INSERT_MESSAGE = "INSERT INTO \"Message\"(content, attachmenturl, timestamp, senderid, groupchatid) VALUES (?, ?, ?, ?, ?)";
}
