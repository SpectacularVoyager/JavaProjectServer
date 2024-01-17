package User;



import DatabaseUtils.Database;
import Session.Session;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    String ID;
    String UserName;

    public User(String ID,String userName) {
        this.ID = ID;
        this.UserName=userName;
    }


    public User(ResultSet r) throws SQLException {
        this(r.getString("UserID"),r.getString("Username"));
    }

    @Override
    public String toString() {
        return String.format("(%s\t%s)",ID,UserName);
    }


    public void insert() throws SQLException {
        Database.getDatabase().executeUpdate("insert into Users (UserID,Username) Values (?,?)",ID,UserName);
    }

    public String getID() {
        return ID;
    }

    public String getUserName() {
        return UserName;
    }
}
