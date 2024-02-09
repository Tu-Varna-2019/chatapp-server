package com.chatapp.model.database.sql_statements;

import java.util.Collections;

public class GetRecord {

        private static final String SELECT_ALL_FROM = "SELECT * FROM ";
        private static final String WHERE_ID = "WHERE id= ? ;";

        public final String GET_USER_EQ_EMAIL =

                        SELECT_ALL_FROM + " \"User\" WHERE email= ? ;";

        public final String GET_USER_EQ_ID =

                        SELECT_ALL_FROM + " \"User\" " + WHERE_ID;

        public final String GET_USER_EQ_IN_IDS(int size) {
                String parametrized = String.join(",", Collections.nCopies(size, "?"));

                return SELECT_ALL_FROM + " \"User\" WHERE id IN (" + parametrized + ");";
        };

        public final String GET_GROUPCHAT_EQ_USER_ID =

                        SELECT_ALL_FROM + " \"GroupChat\" WHERE ? = ANY(userids);";

        public final String GET_GROUPCHAT_EQ_NAME =

                        SELECT_ALL_FROM + " \"GroupChat\" WHERE name= ?;";

        public final String GET_MESSAGE_EQ_GROUPCHAT_ID = SELECT_ALL_FROM + " \"Message\" WHERE groupchatid= ? ;";

        public final String GET_MESSAGE_EQ_ID =

                        SELECT_ALL_FROM + " \"Message\" " + WHERE_ID;

        public final String GET_FRIENDREQUEST_EQ_SENDER_ID = SELECT_ALL_FROM + " \"FriendRequest\" WHERE senderid= ? ;";

        public final String GET_FRIENDREQUEST_EQ__PENDING_EQ_SENDER_ID = SELECT_ALL_FROM
                        + " \"FriendRequest\" WHERE senderid= ? AND status='Pending' ;";

        public final String GET_FRIENDREQUEST_EQ__PENDING_EQ_RECIPIENT_ID =

                        SELECT_ALL_FROM + " \"FriendRequest\" WHERE recipientid= ? AND status='Pending' ;";

        public final String GET_FRIENDREQUEST_EQ_ACCEPTED_EQ_SENDER_ID = SELECT_ALL_FROM
                        + " \"FriendRequest\" WHERE ((senderid= ? OR recipientid= ?) AND status='Accepted');";

        public final String GET_FRIENDREQUEST_EQ_SENDER_ID_OR_RECIPIENT_ID = SELECT_ALL_FROM
                        + " \"FriendRequest\" WHERE ((senderid = ? AND recipientid = ?) OR (senderid = ? AND recipientid = ?)) AND status = ?;";

        public final String GET_FRIENDREQUEST_EQ_ID_AND_IN_USERIDS = "SELECT EXISTS( SELECT 1 FROM \"GroupChat\" WHERE id = ? AND ? = ANY(userids));";

};
