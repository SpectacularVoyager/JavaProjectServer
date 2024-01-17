package GroupRelations;


import DatabaseUtils.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupRelation {
    String GroupID;
    String UserID;


    public GroupRelation(String UserID, String GroupID) {
        this.UserID=UserID;
        this.GroupID=GroupID;
    }

    public GroupRelation(ResultSet r) throws SQLException {
        this(r.getString("UserID"), r.getString("GroupID"));
    }

    @Override
    public String toString() {
        return String.format("(%s\t%s)",GroupID,UserID);
    }


    public void insert() throws SQLException {
        Database.getDatabase().executeUpdate(
                "insert into ChatGroups (UserID,GroupID) Values (?,?)",
                UserID,
                GroupID
        );
    }

    public String getGroupID() {
        return GroupID;
    }

    public String getUserID() {
        return UserID;
    }
    //CHECK PLS PROLLY WONT CAUSE BUGS AT THIS SCALE

}
