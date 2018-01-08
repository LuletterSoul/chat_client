package com.kingston.chat.handler.file;

import com.google.protobuf.Message;
import com.kingston.chat.handler.MessageHandler;
import com.kingston.chat.logic.chat.ChatManager;
import com.kingston.chat.logic.user.UserManager;
import com.luv.face2face.protobuf.generate.ser2cli.file.Server;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  0:39 2018/1/9.
 * @since chat_client
 */

public class ResFileUploadCompleteHandler implements MessageHandler {

    @Override
    public void handler(Message message) {
        Server.ResFileUploadComplete complete = (Server.ResFileUploadComplete) message;
        Long souceId = complete.getFileUploadMsg().getFormUserId();
        String fileName = complete.getFileUploadMsg().getFileName();
        String filePath = complete.getServerfilePath();
        ChatManager.getInstance().handlerFileUploadComplete(souceId, filePath, fileName);
    }
}
