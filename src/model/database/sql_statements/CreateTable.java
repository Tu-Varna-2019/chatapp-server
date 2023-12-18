package model.database.sql_statements;

public class CreateTable {

        public static final String createUserTableQuery = "CREATE TABLE IF NOT EXISTS \"User\" ("
                        + "ID SERIAL PRIMARY KEY,"
                        + "Username VARCHAR(255) NOT NULL,"
                        + "Email VARCHAR(255) NOT NULL,"
                        + "Password VARCHAR(255) NOT NULL"
                        + ");";
        public static final String createFriendRequestTableQuery = "CREATE TABLE IF NOT EXISTS \"FriendRequest\" ("
                        + "ID SERIAL PRIMARY KEY,"
                        + "Status VARCHAR(20) DEFAULT 'Pending' CHECK (Status IN ('Pending', 'Accepted', 'Rejected')),"
                        + "SenderID INT,"
                        + "RecipientID INT,"
                        + "FOREIGN KEY (SenderID) REFERENCES \"User\"(ID),"
                        + "FOREIGN KEY (RecipientID) REFERENCES \"User\"(ID)"
                        + ");";
        public static final String createGroupChatTableQuery = "CREATE TABLE IF NOT EXISTS \"GroupChat\" ("
                        + "ID SERIAL PRIMARY KEY,"
                        + "Name VARCHAR(255),"
                        + "UserIDs INT[]"
                        + ");";
        public static final String createMessageTableQuery = "CREATE TABLE IF NOT EXISTS \"Message\" ("
                        + "ID SERIAL PRIMARY KEY,"
                        + "Content VARCHAR(255),"
                        + "AttachmentURL VARCHAR(255),"
                        + "TimeStamp TIMESTAMP,"
                        + "SenderID INT,"
                        + "GroupChatID INT,"
                        + "FOREIGN KEY (SenderID) REFERENCES \"User\"(ID),"
                        + "FOREIGN KEY (ChatID) REFERENCES \"GroupChat\"(ID)"
                        + ");";
}
