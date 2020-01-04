package edu.udacity.java.nano.chat;

import com.alibaba.fastjson.JSON;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.Console;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket Server
 *
 * @see ServerEndpoint WebSocket Client
 * @see Session   WebSocket Session
 */

@Component
//@ServerEndpoint("/chat")
@ServerEndpoint("/chat/{username}")
public class WebSocketChatServer {

    /**
     * All chat sessions.
     */
    private static Map<String, Session> onlineSessions = new ConcurrentHashMap<>();
    private static Map<String, String>  sessionToNameMap = new ConcurrentHashMap<>();

    private static void sendMessageToAll(String jsonStr,String type) {
        Message receivedmessage = JSON.parseObject(jsonStr,Message.class);
        Message message = new Message(type,receivedmessage.getUsername(),receivedmessage.getMsg(),onlineSessions.size());


        onlineSessions.forEach((sessionId, session) -> {
            if(session.isOpen()){
                try{
                    session.getBasicRemote().sendText(message.toString());
                }
                catch(IOException er){
                    er.printStackTrace();
                }
            }
        });
    }

    /**
     * Open connection, 1) add session, 2) add user.
     */
    @OnOpen
    public void onOpen(
            Session session,
            @PathParam("username") String username) throws IOException {

        String participantJoinedMessage = username+" has joined the chat.";
        System.out.println("Session Opened by: " + username);
        sessionToNameMap.put(session.getId(),username);
        onlineSessions.put(session.getId(),session);

        Message message = new Message("INFO",username,participantJoinedMessage,onlineSessions.size());
        sendMessageToAll(message.toString(),"INFO");


    }

    /**
     * Send message, 1) get username and session, 2) send message to all.
     */
    @OnMessage

    public void onMessage(Session session, String jsonStr) {
        sendMessageToAll(jsonStr,"SPEAK");
        System.out.println("In On Message");
    }

    /**
     * Close connection, 1) remove session, 2) update user.
     */
    @OnClose
    public void onClose(Session session) {
       String participantLeftMessage = sessionToNameMap.get(session.getId())+" left the ChatRoom.";

        System.out.println("Session for "+ session.getId()+" has ended");
        try {
            session.close();
        }
        catch (IOException er) {
            er.printStackTrace();
        }

        onlineSessions.remove(session.getId());
        Message message = new Message("INFO",sessionToNameMap.get(session.getId()),participantLeftMessage,onlineSessions.size());
        sendMessageToAll(message.toString(),"INFO");

    }

    /**
     * Print exception.
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

}
