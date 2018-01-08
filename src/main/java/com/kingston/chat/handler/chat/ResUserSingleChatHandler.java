package com.kingston.chat.handler.chat;

import com.google.protobuf.Message;
import com.kingston.chat.handler.MessageHandler;
import com.kingston.chat.logic.chat.ChatManager;
import com.kingston.chat.logic.friend.FriendManager;
import com.kingston.chat.logic.user.UserManager;
import com.luv.face2face.protobuf.generate.cli2srv.chat.Chat;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  14:08 2018/1/8.
 * @since chat_client
 */

public class ResUserSingleChatHandler implements MessageHandler {

    @Override
    public void handler(Message message) {
        Chat.ResponseChatToUserMsg msg = (Chat.ResponseChatToUserMsg) message;
//        ChatManager.getInstance().receiveFriendPrivateMessage(msg.getFromToUserId(), msg.getContent());
        ChatManager.getInstance().receiveFriendPrivateMessage(msg.getFromToUserId(), msg.getContent());
    }
}
