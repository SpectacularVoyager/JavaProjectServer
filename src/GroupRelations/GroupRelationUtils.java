package GroupRelations;

import DatabaseUtils.Database;
import User.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupRelationUtils {
    public static List<GroupRelation> get(String groupID) throws SQLException {
        ResultSet rs = Database.getDatabase().executeQuery("select * from ChatGroups where GroupID=?",groupID);
        List<GroupRelation> list = new ArrayList<>();
        while (rs.next()) {
            list.add(new GroupRelation(rs));
        }
        return list;
    }

    public static int delete(String GroupID,String UserID) throws SQLException{
        //IF 0 prolly check
        return Database.getDatabase().executeUpdate("delete from Message where UserID=? AND GroupID=?",UserID,GroupID);
    }

}
