package User;

import DatabaseUtils.Database;
import Session.Session;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserUtils {
    public static User getUser(String userID) throws SQLException {
        ResultSet rs = Database.getDatabase().executeQuery("select * from Users where UserID=?",userID);
        if (rs.next()) {
            return new User(rs);
        }
        return null;
    }
    public static User getUser(Session session) throws SQLException {
        if(session==null){
            return null;
        }
        return getUser(session.getUserID());
    }


    @Deprecated
    public static List<User> getUsers(String userID) throws SQLException {
        ResultSet rs = Database.getDatabase().executeQuery("select * from Users");
        List<User> list = new ArrayList<User>();
        while (rs.next()) {
            list.add(new User(rs));
        }
        return list;
    }

    public static int delete(String UserID) throws SQLException{
        //IF 0 prolly check
        return Database.getDatabase().executeUpdate("delete from Users where UserID=?",UserID);
    }
}
