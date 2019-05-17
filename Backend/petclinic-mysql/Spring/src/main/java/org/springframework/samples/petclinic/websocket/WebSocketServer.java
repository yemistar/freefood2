package org.springframework.samples.petclinic.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.Event.Event2;
import org.springframework.samples.petclinic.Event.Event2Repository;
import org.springframework.samples.petclinic.Event.EventInfo;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@ServerEndpoint("/websocket/{username}/{eventId}/{eventInfoId}")
@Component
@Controller
public class WebSocketServer {


    private int id;
    private int infoId;
    // Store all socket session and their corresponding username.
    private static Map<Session, String> sessionUsernameMap = new HashMap<>();
    private static Map<String, Session> usernameSessionMap = new HashMap<>();

    private final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

    @OnOpen
    public void onOpen(
        Session session,
        @PathParam("username") String username,@PathParam("eventId") int id, @PathParam("eventInfoId") int infoId) throws IOException
    {
        logger.info("Entered into Open");
        logger.info("Person " + username + " is connected");
        this.id=id;
        this.infoId=infoId;

        sessionUsernameMap.put(session, username);
        usernameSessionMap.put(username, session);


        String message="User:" + username + " Is updating event "+id;
        broadcast(message);

    }

    /**
     *
     * @param text what needs to update and new text
     */
    private void UpdateDB(String text){
        logger.info("Entered into UpdateDB");
        String[] temp=text.split(":");
        String message=temp[0];
        String code=temp[1];

    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException
    {
        // Handle new messages
        logger.info("Entered into Message: Got Message:"+message);
        String username = sessionUsernameMap.get(session);


        if (message.startsWith("@")) // Direct message to a user using the format "@username <message>"
        {
            String destUsername = message.split(" ")[0].substring(1); // don't do this in your code!
            sendMessageToPArticularUser(destUsername, "[DM] " + username + ": " + message);
            sendMessageToPArticularUser(username, "[DM] " + username + ": " + message);
        }
        else // Message to whole chat
        {
            broadcast(username + ": " + message);
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException
    {
        logger.info("Entered into Close");

        String username = sessionUsernameMap.get(session);
        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(username);

        String message= username + " disconnected";
        broadcast(message);
    }

    @OnError
    public void onError(Session session, Throwable throwable)
    {
        // Do error handling here
        logger.info("Entered into Error");
    }

    private void sendMessageToPArticularUser(String username, String message)
    {
        try {
            usernameSessionMap.get(username).getBasicRemote().sendText(message);
        } catch (IOException e) {
            logger.info("Exception: " + e.getMessage().toString());
            e.printStackTrace();
        }
    }

    private static void broadcast(String message)
        throws IOException
    {
        sessionUsernameMap.forEach((session, username) -> {
            synchronized (session) {
                try {
                    session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
