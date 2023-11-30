package controller.events;

import java.util.logging.Logger;

import model.database.ChatDBManager;
import model.database.sql_statements.InsertStatement;
import view.SocketConnection;

public abstract class SharedDataEventHandler implements EventHandler {

    protected static final Logger logger = Logger.getLogger(SocketConnection.class.getName());

    protected static final ChatDBManager chatDBManager = ChatDBManager.getInstance();
    protected InsertStatement insertStatement = new InsertStatement();
}
