package com.chatapp.controller.events.handlers.shared;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.chatapp.model.database.ChatDBManager;
import com.chatapp.model.database.sql_statements.DeleteRecord;
import com.chatapp.model.database.sql_statements.GetRecord;
import com.chatapp.model.database.sql_statements.InsertRecord;
import com.chatapp.model.database.sql_statements.UpdateRecord;
import com.chatapp.view.SocketConnection;

public abstract class SharedEventValues {

    protected static final Logger logger = LogManager
            .getLogger(SocketConnection.class.getName());

    protected static final ChatDBManager chatDBManager = ChatDBManager.getInstance();
    protected InsertRecord insertStatement = new InsertRecord();
    protected GetRecord getRecord = new GetRecord();
    protected DeleteRecord deleteRecord = new DeleteRecord();
    protected UpdateRecord updateRecord = new UpdateRecord();

}
