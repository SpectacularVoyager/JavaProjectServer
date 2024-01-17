package Session;

import DatabaseUtils.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SessionUtils {
    public static Session get(String SessionID) throws SQLException {
        ResultSet rs = Database.getDatabase().executeQuery("select * from Sessions where SessionID=?",SessionID);
        if (rs.next()) {
            return new Session(rs);
        }
        return null;
    }

    public static int delete(String SessionID) throws SQLException{
        //IF 0 prolly check
        return Database.getDatabase().executeUpdate("delete from Session where SessionID=?",SessionID);
    }
}
