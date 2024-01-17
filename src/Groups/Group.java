package Groups;


import DatabaseUtils.Database;
import User.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Group {
    String GroupID;
    String Name;
    String Description;


    public Group(String GroupID,String Name,String Description ) {
        this.Name =Name;
        this.GroupID=GroupID;
        this.Description=Description;
    }

    public Group(ResultSet r) throws SQLException {
        this(r.getString("GroupID"), r.getString("Name"),r.getString("Description"));
    }

    @Override
    public String toString() {
        return String.format("(%s\t%s)",GroupID, Name);
    }


    public void insert() throws SQLException {
        Database.getDatabase().executeUpdate(
                "insert into ChatGroup (GroupID,Name,Description) Values (?,?,?)",
                GroupID,
                Name,
                Description
        );
    }

    public String getGroupID() {
        return GroupID;
    }

    public String getName() {
        return Name;
    }

    public String getDescription() {
        return Description;
    }
//CHECK PLS PROLLY WONT CAUSE BUGS AT THIS SCALE

}
