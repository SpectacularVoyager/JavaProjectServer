package DatabaseHandler;

import Messages.Message;

import java.sql.SQLException;

public class ConnectionMessageText implements ConnectionMessage{

    private String SessionID;

    private final Message message;

    public ConnectionMessageText(String sessionID, Message message) {
        SessionID = sessionID;
        this.message = message;
    }

    @Override
    public void resolve() throws SQLException {
        message.insert();
        System.out.println("sent message:\t"+message);
    }
}
