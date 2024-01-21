package model.database.sql_statements;

public class UpdateRecord {
        public final String UPDATE_USER_USERNAME_EQ_EMAIL =

                        "UPDATE \"User\" SET username = ? WHERE email= ?;";

        public final String UPDATE_USER_EMAIL_EQ_ID =

                        "UPDATE \"User\" SET email = ? WHERE id= ?;";

        public final String UPDATE_USER_PASSWORD_EQ_ID =

                        "UPDATE \"User\" SET password = ? WHERE id= ?;";

        public final String UPDATE_GROUPCHAT_USERIDS_REMOVE =

                        "UPDATE \"GroupChat\" SET userids= array_remove(userids,?) WHERE id= ?;";

        public final String UPDATE_GROUPCHAT_USERIDS_ADD =

                        "UPDATE \"GroupChat\" SET userids= array_append(userids,?) WHERE id= ?;";

        public final String UPDATE_FRIENDREQUEST_STATUS_EQ_ID = "UPDATE \"FriendRequest\" SET status= ? WHERE id= ?;";

}
