package DatabaseHandler;

import DatabaseUtils.Database;
import GroupRelations.GroupRelation;
import GroupRelations.GroupRelationUtils;
import Groups.Group;
import Groups.GroupUtils;
import JSON.jsonObject;
import Messages.Message;
import Messages.MessageUtils;
import Session.SessionUtils;
import User.*;
import org.java_websocket.WebSocket;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ConnectionMessageManager {

    public static String MESSAGE_LOAD = "load_group";
    public static String MESSAGE_TEXT = "text_message";
    public static String MESSAGE_JOIN_GROUP = "join_group";
    public static String MESSAGE_CREATE_GROUP = "create_group";
    public static String MESSAGE_IMAGE = "image_message";
    private static final HashMap<String, WebSocket> sockets = new HashMap<>();
    private static final JSONParser parser = new JSONParser();


    public static void loadStuff(WebSocket con, String userID) throws SQLException {

        //get groups
        ResultSet rs = Database.getDatabase().executeQuery("select * from ChatGroups where UserID=? ", userID);
        String _json = "";
        while (rs.next()) {

            //GET GROUP ID
            String groupID = rs.getString("GroupID");
            Group group = GroupUtils.get(groupID);

            //GET USER ID
            List<GroupRelation> relations = GroupRelationUtils.get(groupID);

            //GET USERNAME
            List<User> users = new ArrayList<>();
            for (GroupRelation relation : relations) {
                users.add(UserUtils.getUser(relation.getUserID()));
            }
            //GET MESSAGES
            List<Message> messages = MessageUtils.getMessages(groupID);
            List<String> usernames = new ArrayList<>();
            List<String> _messages = new ArrayList<>();
            for (User u : users) {
                usernames.add(u.getUserName());
            }
            HashMap<String, String> userMap = new HashMap<>();
            users.forEach(x -> userMap.put(x.getID(), x.getUserName()));
            for (Message _message : messages) {
                _messages.add(String.format("{ \"user_name\" : \"%s\"  , \"message\" : \"%s\" , \"timestamp\" : \"%d\" , \"type\" : \"%s\"}",
                        userMap.get(_message.getUserID()), _message.getMessage(), _message.getTimestamp(), _message.getType()));
            }
            jsonObject object = new jsonObject();
            object.addString("RequestType", "GROUP LOAD");
            object.addString("groupID", group.getGroupID());
            object.addString("group_name", group.getName());
            object.addString("group_desc", group.getDescription());
            object.addList("users", usernames);
            object.addList("messages", _messages, false);
            _json += ",{" + object.toString() + "}";
        }
        if (!_json.isEmpty()) {
            _json = "[" + _json.substring(1) + "]";
        }
        System.out.println(_json);
        con.send(_json);
    }

    public static void loadStuff(WebSocket con, String userID, String _groupID) throws SQLException {
        //get groups
        String _json = "";
        //GET GROUP ID
        String groupID = _groupID;
        Group group = GroupUtils.get(groupID);

        //GET USER ID
        List<GroupRelation> relations = GroupRelationUtils.get(groupID);

        //GET USERNAME
        List<User> users = new ArrayList<>();
        for (GroupRelation relation : relations) {
            users.add(UserUtils.getUser(relation.getUserID()));
        }
        //GET MESSAGES
        List<Message> messages = MessageUtils.getMessages(groupID);
        List<String> usernames = new ArrayList<>();
        List<String> _messages = new ArrayList<>();
        for (User u : users) {
            usernames.add(u.getUserName());
        }
        HashMap<String, String> userMap = new HashMap<>();
        users.forEach(x -> userMap.put(x.getID(), x.getUserName()));
        for (Message _message : messages) {
            _messages.add(String.format("{ \"user_name\" : \"%s\"  , \"message\" : \"%s\" , \"timestamp\" : \"%d\" , \"type\" : \"%s\"}",
                    userMap.get(_message.getUserID()), _message.getMessage(), _message.getTimestamp(), _message.getType()));
        }
        jsonObject object = new jsonObject();
        object.addString("RequestType", "GROUP LOAD");
        object.addString("groupID", group.getGroupID());
        object.addString("group_name", group.getName());
        object.addString("group_desc", group.getDescription());
        object.addList("users", usernames);
        object.addList("messages", _messages, false);
        _json += ",{" + object.toString() + "}";

        if (!_json.isEmpty()) {
            _json = "[" + _json.substring(1) + "]";
        }
        System.out.println(_json);
        con.send(_json);
    }

    public static List<String> getGroup(String groupID) throws SQLException {

        List<String> users = new ArrayList<>();
        GroupRelationUtils.get(groupID).forEach(x -> users.add(x.getUserID()));
        return users;

    }

    //TO avoid null errors
    public static String getStringFromJSON(JSONObject o, String s) {
        Object child = o.get(s);
        if (child == null) {
            return null;
        }
        return child.toString();
    }

    public static void getMessage(WebSocket con, String message) throws SQLException {
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) parser.parse(message);
        } catch (ParseException e) {
            System.err.println("COULD NOT PARSE:\t" + message);
            return;
        }
        System.out.println("RECIEVED:\t" + message);
        String SessionID = getStringFromJSON(jsonObject, "SessionID");
        String type = getStringFromJSON(jsonObject, "Type");

        String message_content = getStringFromJSON(jsonObject, "message_content");
        User u = UserUtils.getUser(SessionUtils.get(SessionID));

        if (u == null) {
            JSONObject _response = new JSONObject();
            _response.put("RequestType", "INVALID_SESSION_ID");
            con.send(_response.toJSONString());
        }

        if (type.equalsIgnoreCase(MESSAGE_TEXT)||type.equalsIgnoreCase(MESSAGE_IMAGE)) {
            String groupID = getStringFromJSON(jsonObject, "GroupID");
            //return new ConnectionMessageText

            Message pendingMessage = new Message(u.getID(), groupID, System.currentTimeMillis(), message_content, type);
            System.out.println(pendingMessage);
            new ConnectionMessageText(SessionID, pendingMessage).resolve();

            //broadcast
            List<String> users = getGroup(groupID);
            System.out.println(users.size());
            for (String _userID : users) {

                WebSocket socket = sockets.get(_userID);
                System.out.printf("%s %s\n", _userID, socket.toString());
                jsonObject object = new jsonObject();
                object.addString("RequestType", "MESSAGE");
                object.addString("groupID", pendingMessage.getGroupID());
                object.addString("username", u.getUserName());
                object.addString("message", pendingMessage.getMessage());
                object.addString("type", pendingMessage.getType());
                object.addString("timestamp", String.valueOf(pendingMessage.getTimestamp()));
                socket.send("{" + object.toString() + "}");
            }
        } else if (type.equalsIgnoreCase(MESSAGE_LOAD)) {
            System.out.printf("put ( %s , %s ) in hashmap\n", u.getID(), con);
            sockets.put(u.getID(), con);
            loadStuff(con, u.getID());
        } else if (type.equalsIgnoreCase(MESSAGE_JOIN_GROUP)) {
            String groupID = getStringFromJSON(jsonObject, "GroupID");
            GroupRelation relation=new GroupRelation(u.getID(), groupID);
            System.out.println(relation);
            relation.insert();
            System.out.printf("put ( %s , %s ) in hashmap\n", u.getID(), con);
            sockets.put(u.getID(), con);
            loadStuff(con, u.getID(),groupID);
        } else if (type.equalsIgnoreCase(MESSAGE_CREATE_GROUP)) {
            Group g = new Group(UUID.randomUUID().toString(), getStringFromJSON(jsonObject, "group_name"), "");
            g.insert();
            new GroupRelation(u.getID(), g.getGroupID()).insert();
            loadStuff(con, u.getID(),g.getGroupID());

        } else {
            System.out.println("INVALID MESSAGE PARAMETER:\t" + message + "\t" + type);
        }
    }
}
