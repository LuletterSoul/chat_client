package com.kingston.chat.handler;

import com.google.protobuf.Message;
import com.kingston.chat.handler.friend.FriendLoginHandler;
import com.kingston.chat.handler.friend.FriendLogoutHandler;
import com.kingston.chat.handler.friend.ListFriendsHandler;
import com.kingston.chat.handler.login.*;
import com.luv.face2face.protobuf.analysis.ParserManager;
import com.luv.face2face.protobuf.generate.ser2cli.friend.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static com.luv.face2face.protobuf.generate.ser2cli.friend.Server.*;
import static com.luv.face2face.protobuf.generate.ser2cli.login.Server.*;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  19:27 2018/1/7.
 * @since chat_client
 */

public class DefaultHandlerManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultHandlerManager.class);


    private Map<Integer, MessageHandler> handlerMap = new HashMap<>();

    private Map<Class<?>, MessageHandler> classMessageHandlerMap = new HashMap<>();

    private Map<Class<?>, Integer> classMap = new HashMap<>();

    private ParserManager parserManager;


    public DefaultHandlerManager(ParserManager parserManager) {
        this.parserManager = parserManager;
        setClassMap(parserManager.getPacketTypeToProtocolNum());
        classMessageHandlerMap.put(ResServerLoginSucc.class, new LoginSuccessHandler());
        classMessageHandlerMap.put(ResServerLoginFailed.class, new LoginFailedHandler());
        classMessageHandlerMap.put(ResServerRegisterSucc.class, new RegistrySuccessHandler());
        classMessageHandlerMap.put(ResServerRegisterFailed.class, new RegistryFailerHandler());
        classMessageHandlerMap.put(ResListFriends.class, new ListFriendsHandler());
        classMessageHandlerMap.put(ResFriendLogin.class, new FriendLoginHandler());
        classMessageHandlerMap.put(ResFriendLogout.class, new FriendLogoutHandler());
        classMessageHandlerMap.put(ResServerRefreshProfile.class, new RefreshProfileHandler());
    }


    public MessageHandler getHandler(Message message) {
        MessageHandler messageHandler = classMessageHandlerMap.get(message.getClass());
        if (messageHandler == null) {
            throw new IllegalArgumentException("Unknown message,lost match handler.");
        }
        return messageHandler;
    }

    public MessageHandler getHandler(Integer protocolNum) {
        MessageHandler messageHandler = handlerMap.get(protocolNum);
        if (handlerMap.isEmpty() || handlerMap == null) {
            return null;
        }
        if (messageHandler == null) {
            throw new IllegalArgumentException("Unknown message,lost match handler.");
        }
        return messageHandler;
    }

    public void registerHandler(Class<?> msg, MessageHandler handler) {
        if (parserManager == null) {
            LOGGER.error("parser couldn't be null.");
            return;
        }
        // 根据消息包类型获得协议号
        int ptoNum = parserManager.getPtoNum(msg);
        if (handlerMap.get(ptoNum) == null) {
            handlerMap.put(ptoNum, handler);
        } else {
            LOGGER.error("handler has been registered in handlerMap, ptoNum: {}", ptoNum);
        }
    }

    private void setClassMap(HashMap<Class<?>, Integer> classMap) {
        this.classMap = classMap;
    }
}
