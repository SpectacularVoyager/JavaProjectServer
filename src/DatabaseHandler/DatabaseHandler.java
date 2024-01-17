package DatabaseHandler;

import org.java_websocket.WebSocket;

import java.sql.SQLException;

public class DatabaseHandler {

    public static String DELIMITER = " ";

    public void HandleMessage(WebSocket con, String message) {
        try {
            ConnectionMessageManager.getMessage(con, message);

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }
}
