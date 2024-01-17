package Groups;

import DatabaseUtils.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupUtils {
    public static Group get(String groupID) throws SQLException {
        ResultSet rs = Database.getDatabase().executeQuery("select * from ChatGroup where GroupID=?",groupID);
        List<Group> list = new ArrayList<>();
        if (rs.next()) {
            return new Group(rs);
        }
        return null;
    }

}
