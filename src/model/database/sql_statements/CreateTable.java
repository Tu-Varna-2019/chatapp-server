package model.database.sql_statements;

public class CreateTable {

        private final static String CREATE_TABLE_NOT_EXIST = "CREATE TABLE IF NOT EXISTS ";
        private final static String ID_PRIMARY_KEY = "ID SERIAL PRIMARY KEY,";

        private final static String CREATE_USER_TABLE = CREATE_TABLE_NOT_EXIST + " \"User\" ("
                        + ID_PRIMARY_KEY
                        + "Username VARCHAR(255) NOT NULL,"
                        + "Email VARCHAR(255) NOT NULL,"
                        + "Password VARCHAR(255) NOT NULL"
                        + ");";
        private final static String CREATE_FRIENDREQUEST_TABLE = CREATE_TABLE_NOT_EXIST + " \"FriendRequest\" ("
                        + ID_PRIMARY_KEY
                        + "Status VARCHAR(20) DEFAULT 'Pending' CHECK (Status IN ('Pending', 'Accepted', 'Rejected')),"
                        + "SenderID INT,"
                        + "RecipientID INT,"
                        + "FOREIGN KEY (SenderID) REFERENCES \"User\"(ID),"
                        + "FOREIGN KEY (RecipientID) REFERENCES \"User\"(ID)"
                        + ");";
        private final static String CREATE_GROUPCHAT_TABLE = CREATE_TABLE_NOT_EXIST + " \"GroupChat\" ("
                        + ID_PRIMARY_KEY
                        + "Name VARCHAR(255),"
                        + "UserIDs INT[]"
                        + ");";
        private final static String CREATE_MESSAGE_TABLE = CREATE_TABLE_NOT_EXIST + " \"Message\" ("
                        + ID_PRIMARY_KEY
                        + "Content VARCHAR(255),"
                        + "AttachmentURL VARCHAR(255),"
                        + "TimeStamp TIMESTAMP,"
                        + "SenderID INT,"
                        + "GroupChatID INT,"
                        + "FOREIGN KEY (SenderID) REFERENCES \"User\"(ID),"
                        + "FOREIGN KEY (ChatID) REFERENCES \"GroupChat\"(ID)"
                        + ");";

        public static String[] getCreateTableQueries() {
                return new String[] { CREATE_USER_TABLE, CREATE_FRIENDREQUEST_TABLE, CREATE_GROUPCHAT_TABLE,
                                CREATE_MESSAGE_TABLE };
        }
}
