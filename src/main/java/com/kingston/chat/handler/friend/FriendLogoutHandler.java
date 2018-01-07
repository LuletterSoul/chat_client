package com.kingston.chat.handler.friend;

import com.google.protobuf.Message;
import com.kingston.chat.handler.MessageHandler;
import com.kingston.chat.logic.friend.FriendManager;
import com.luv.face2face.protobuf.generate.ser2cli.friend.Server;
import lombok.extern.slf4j.Slf4j;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  23:25 2018/1/7.
 * @since chat_client
 */

@Slf4j
public class FriendLogoutHandler implements MessageHandler {

    @Override
    public void handler(Message message) {
        Server.ResFriendLogout logout = (Server.ResFriendLogout) message;
        FriendManager.getInstance().onFriendLogout(logout.getFriendId());
    }
}
