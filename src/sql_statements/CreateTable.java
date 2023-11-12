package sql_statements;

public class CreateTable {

    private static String createUserTableQuery;
    private static String createFriendRequestTableQuery;
    private static String createGroupChatTableQuery;
    private static String createMessageTableQuery;

    static {

        createUserTableQuery = "CREATE TABLE IF NOT EXISTS \"User\" ("
                + "ID SERIAL PRIMARY KEY,"
                + "Username VARCHAR(255) NOT NULL,"
                + "Email VARCHAR(255) NOT NULL,"
                + "Password VARCHAR(255) NOT NULL"
                + ");";

        createFriendRequestTableQuery = "CREATE TABLE IF NOT EXISTS \"FriendRequest\" ("
                + "ID SERIAL PRIMARY KEY,"
                + "Status VARCHAR(20) DEFAULT 'Pending' CHECK (Status IN ('Pending', 'Accepted', 'Rejected')),"
                + "SentUserID INT,"
                + "InvitedUserID INT,"
                + "FOREIGN KEY (SentUserID) REFERENCES \"User\"(ID),"
                + "FOREIGN KEY (InvitedUserID) REFERENCES \"User\"(ID)"
                + ");";

        createGroupChatTableQuery = "CREATE TABLE IF NOT EXISTS \"GroupChat\" ("
                + "ID SERIAL PRIMARY KEY,"
                + "Name VARCHAR(255),"
                + "UserID INT,"
                + "FOREIGN KEY (UserID) REFERENCES \"User\"(ID)" // ID или Username
                + ");";

        createMessageTableQuery = "CREATE TABLE IF NOT EXISTS \"Message\" ("
                + "ID SERIAL PRIMARY KEY,"
                + "Content VARCHAR(255),"
                + "AttachmentURL VARCHAR(255),"
                + "TimeStamp TIMESTAMP,"
                + "SenderID INT,"
                + "ChatID INT,"
                + "FOREIGN KEY (SenderID) REFERENCES \"User\"(ID),"
                + "FOREIGN KEY (ChatID) REFERENCES \"GroupChat\"(ID)"
                + ");";
    }

    public static String getCreateUserTableQuery(){
        return createUserTableQuery;
    }

    public static String getCreateFriendRequestTableQuery(){
        return createFriendRequestTableQuery;
    }

    public static String getCreateGroupChatTableQuery(){
        return createGroupChatTableQuery;
    }

    public static String getCreateMessageTableQuery(){
        return createMessageTableQuery;
    }
}

