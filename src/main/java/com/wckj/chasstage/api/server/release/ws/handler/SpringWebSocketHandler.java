package com.wckj.chasstage.api.server.release.ws.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpringWebSocketHandler extends TextWebSocketHandler {
    private static final Logger log = LoggerFactory.getLogger(SpringWebSocketHandler.class);
    //单位编号 key name
    public static final String OEG_ID = "WEBSOCKET_ORG";

    private static final Map<String, List<WebSocketSession>> clients=  new HashMap<>();  //Map来存储WebSocketSession，key用USER_ID 即在线用户列表
 

    public SpringWebSocketHandler() {}
 
    /**
     * 连接成功时候，会触发页面上onopen方法
     */
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        log.debug("成功建立websocket连接!");
        //将连接的客户端，根据办案区单位编号分组
        String userId = (String) session.getAttributes().get(OEG_ID);
        if(!clients.containsKey(userId)){
            clients.put(userId,new ArrayList<>());
        }
        clients.get(userId).add(session);
        //session.sendMessage(new TextMessage("test"));
    }
 
    /**
     * 关闭连接时触发
     */
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        String userId= (String) session.getAttributes().get(OEG_ID);
        //log.debug("用户"+userId+"已退出！");
        if(clients.containsKey(userId)){
            clients.get(userId).remove(session);
        }
    }
 
    /**
     * js调用websocket.send时候，会调用该方法
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
 
        super.handleTextMessage(session, message);
 
//        /**
//         * 收到消息，自定义处理机制，实现业务
//         */
//        System.out.println("服务器收到消息："+message);
//
//        if(message.getPayload().startsWith("#anyone#")){ //单发某人
//
//             sendMessageToUser((String)session.getAttributes().get(OEG_ID), new TextMessage("服务器单发：" +message.getPayload())) ;
//
//        }else if(message.getPayload().startsWith("#everyone#")){
//
//             sendMessageToUsers(new TextMessage("服务器群发：" +message.getPayload()));
//
//        }else{
//
//        }
 
    }
 
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if(session.isOpen()){
            session.close();
        }
        log.error("传输出现异常，关闭websocket连接... ");
        String userId= (String) session.getAttributes().get(OEG_ID);
        clients.get(userId).remove(session);
    }
 
    public boolean supportsPartialMessages() {
 
        return false;
    }
 
 
    /**
     * 给某个用户发送消息
     *
     * @param orgCode
     * @param message
     */
    public void sendMessageToUser(String orgCode, TextMessage message) {
        if(clients.containsKey(orgCode)){
            List<WebSocketSession> list = clients.get(orgCode);
            for (int i=0;i<list.size();i++){
                if(list.get(i).isOpen()){
                    try {
                        list.get(i).sendMessage(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void sendLocaltion(String org,String info){
        try {
            sendMessageToUser(org,new TextMessage(info));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
