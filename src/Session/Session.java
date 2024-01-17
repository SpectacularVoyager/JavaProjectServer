package Session;



import DatabaseUtils.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Session {
    String SessionID;
    String UserID;

    public Session(String SessionID, String userID) {
        this.SessionID = SessionID;
        this.UserID = userID;
    }

    public Session(ResultSet r) throws SQLException {
        this(r.getString("SessionID"),r.getString("UserID"));
    }

    @Override
    public String toString() {
        return String.format("(%s\t%s)", SessionID, UserID);
    }


    public void insert() throws SQLException {
        Database.getDatabase().executeUpdate("insert into Sessions (SessionID,UserID) Values (?,?)", SessionID, UserID);
    }

    public String getUserID() {
        return UserID;
    }
}
