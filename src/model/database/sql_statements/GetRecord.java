package model.database.sql_statements;

public class GetRecord {

    private static final String SELECT_ALL_FROM = "SELECT * FROM ";
    private static final String WHERE_ID = "WHERE id= ? ;";

    public final String getUserEQEmail =

            SELECT_ALL_FROM + " \"User\" WHERE email= ? ;";

    public final String getUserEQID =

            SELECT_ALL_FROM + " \"User\" " + WHERE_ID;

    public final String getUsersByIDS =

            SELECT_ALL_FROM + " \"User\" WHERE id IN (?);";

    public final String getGroupChatEQUserID =

            SELECT_ALL_FROM + " \"GroupChat\" WHERE ? = ANY(userids);";

    public final String getGroupChatEQID =

            SELECT_ALL_FROM + " \"GroupChat\" " + WHERE_ID;

    public final String getGroupChatEQName =

            SELECT_ALL_FROM + " \"GroupChat\" WHERE name= ?;";

    public final String getMessageEQGroupID = SELECT_ALL_FROM + " \"Message\" WHERE groupchatid= ? ;";

    public final String getMessageEQID =

            SELECT_ALL_FROM + " \"Message\" " + WHERE_ID;

    public final String getFriendRequestEQSenderID = SELECT_ALL_FROM + " \"FriendRequest\" WHERE senderid= ? ;";

    public final String getFriendRequestPendingEQSenderID = SELECT_ALL_FROM
            + " \"FriendRequest\" WHERE senderid= ? AND status='Pending' ;";

    public final String getReceivedFriendRequests =

            SELECT_ALL_FROM + " \"FriendRequest\" WHERE recipientid= ? AND status='Pending' ;";

    public final String getFriendRequestAcceptedEQSenderID = SELECT_ALL_FROM
            + " \"FriendRequest\" WHERE ((senderid= ? OR recipientid= ?) AND status='Accepted');";

    public final String checkIfFriendRequestExistsEQSenderRecipientID = SELECT_ALL_FROM
            + " \"FriendRequest\" WHERE ((senderid = ? AND recipientid = ?) OR (senderid = ? AND recipientid = ?)) AND status = ?;";

    public final String checkIfUserAlreadyInGroupChat = "SELECT EXISTS( SELECT 1 FROM \"GroupChat\" WHERE id = ? AND ? = ANY(userids));";

};
