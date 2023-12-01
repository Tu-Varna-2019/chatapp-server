package controller.events;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import model.database.ChatDBManager;
import model.database.sql_statements.InsertStatement;
import view.SocketConnection;

public abstract class SharedDataEventHandler implements EventHandler {

    protected static final Logger logger = LogManager
            .getLogger(SocketConnection.class.getName());

    protected static final ChatDBManager chatDBManager = ChatDBManager.getInstance();
    protected InsertStatement insertStatement = new InsertStatement();
}
