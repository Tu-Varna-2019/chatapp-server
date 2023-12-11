package controller.events;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import model.database.ChatDBManager;
import model.database.sql_statements.DeleteRecord;
import model.database.sql_statements.GetRecord;
import model.database.sql_statements.InsertStatement;
import model.database.sql_statements.UpdateRecord;
import view.SocketConnection;

public abstract class SharedDataEventHandler implements EventHandler {

    protected static final Logger logger = LogManager
            .getLogger(SocketConnection.class.getName());

    protected static final ChatDBManager chatDBManager = ChatDBManager.getInstance();
    protected InsertStatement insertStatement = new InsertStatement();
    protected GetRecord getRecord = new GetRecord();
    protected DeleteRecord deleteRecord = new DeleteRecord();
    protected UpdateRecord updateRecord = new UpdateRecord();
}
