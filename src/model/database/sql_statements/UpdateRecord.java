package model.database.sql_statements;

public class UpdateRecord {
    public final String UpdateUsernameEQEmail =

            "UPDATE \"User\" SET username = ? WHERE email= ?;";

    public final String UpdateEmailEQID =

            "UPDATE \"User\" SET email = ? WHERE id= ?;";

    public final String UpdatePasswordEQID =

            "UPDATE \"User\" SET password = ? WHERE id= ?;";

    public final String RemoveUserFromGroupChatEQGPID =

            "UPDATE \"GroupChat\" SET userids= array_remove(userids,?) WHERE id= ?;";

    public final String AddUserFromGroupChatEQGPID =

            "UPDATE \"GroupChat\" SET userids= array_append(userids,?) WHERE id= ?;";

    public final String UpdateFriendRequestStatusEQID = "UPDATE \"FriendRequest\" SET status= ? WHERE id= ?;";

}
