package Messages;


import DatabaseUtils.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Message {
    String UserID;
    String GroupID;
    long timestamp;
    String message;
    String type;


    public Message(String UserID, String GroupID, long timestamp, String message, String type) {
        this.UserID = UserID;
        this.GroupID = GroupID;
        this.timestamp = timestamp;
        this.message = message;
        this.type = type;
    }

    public Message(ResultSet r) throws SQLException {
        this(r.getString("UserID"), r.getString("GroupID"), Long.parseLong(r.getString("Timestamp")), r.getString("Message"), r.getString("Type"));
    }

    @Override
    public String toString() {
        return String.format("%s\t|%s->\t%s\t\t\t%d", GroupID, UserID, message, timestamp);
    }


    public void insert() throws SQLException {
        Database.getDatabase().executeUpdate(
                "insert into Message (UserID,GroupID,Timestamp,Message,Type) Values (?,?,?,?,?)",
                UserID,
                GroupID,
                timestamp,
                message,
                type
        );
    }

    public String getUserID() {
        return UserID;
    }

    public String getGroupID() {
        return GroupID;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

}
