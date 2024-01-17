import GroupRelations.GroupRelation;
import GroupRelations.GroupRelationUtils;
import Groups.Group;
import Messages.Message;
import Messages.MessageUtils;
import Session.*;
import User.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

public class Application {
    public static void main(String[] args) throws InterruptedException, IOException, SQLException, ParseException {



//        new GroupRelation("3","1").insert();

        //System.out.println(UserUtils.getUser(SessionUtils.get("123")));
        int port = 8887;
        ChatServer s = new ChatServer(port);
        s.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String in = reader.readLine();
            //s.broadcast(in);
            if (in.equals("exit")) {
                s.stop(1000);
                break;
            }
        }

    }
}

