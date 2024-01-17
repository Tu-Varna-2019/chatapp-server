package model.database.sql_statements;

public class UpdateRecord {
    public final String UpdateUsernameEQEmail(String username, String email) {

        return "UPDATE \"User\" SET username = '" + username + "' WHERE email= '" + email + "';";
    };

    public final String UpdateEmailEQID(String email, int id) {

        return "UPDATE \"User\" SET email = '" + email + "' WHERE id= " + id + ";";
    };

    public final String UpdatePasswordEQID(String password, int id) {

        return "UPDATE \"User\" SET password = '" + password + "' WHERE id= " + id + ";";
    };

    public final String RemoveUserFromGroupChatEQGPID(int groupchatid, int userid) {

        return "UPDATE \"GroupChat\" SET userids= array_remove(userids," + userid + ") WHERE id= " + groupchatid + ";";

    };

    public final String AddUserFromGroupChatEQGPID(int groupchatid, int userid) {

        return "UPDATE \"GroupChat\" SET userids= array_append(userids," + userid + ") WHERE id= " + groupchatid + ";";

    };

    public final String UpdateFriendRequestStatusEQID(String status, int friendrequestid) {
        return "UPDATE \"FriendRequest\" SET status = '" + status + "' WHERE id = " + friendrequestid + " ;";
    }
}
