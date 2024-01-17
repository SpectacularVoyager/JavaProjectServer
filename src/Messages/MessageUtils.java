package Messages;

import DatabaseUtils.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageUtils {
    public static List<Message> getMessages(String groupID) throws SQLException {
        ResultSet rs = Database.getDatabase().executeQuery("select * from Message where GroupID=? order by Timestamp",groupID);
        List<Message> list = new ArrayList<>();
        while (rs.next()) {
            list.add(new Message(rs));
        }
        return list;
    }
    public static List<Message> getMessages(String groupID,int offset,int limit) throws SQLException {
        ResultSet rs = Database.getDatabase().executeQuery("select * from Message where GroupID=? order by Timestamp desc limit "+offset+","+limit ,groupID);
        System.out.println();
        List<Message> list = new ArrayList<>();
        while (rs.next()) {
            list.add(new Message(rs));
        }
        return list;
    }
    public static int delete(String UserID,long timestamp) throws SQLException{
        //IF 0 prolly check
        return Database.getDatabase().executeUpdate("delete from Message where UserID=? AND Timestamp=?",UserID,timestamp);
    }



}
