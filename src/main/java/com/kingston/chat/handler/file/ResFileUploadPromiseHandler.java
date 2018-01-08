package com.kingston.chat.handler.file;

import com.google.protobuf.Message;
import com.kingston.chat.handler.MessageHandler;
import com.kingston.chat.logic.user.UserManager;
import com.luv.face2face.protobuf.generate.ser2cli.file.Server;
import com.luv.face2face.protobuf.generate.ser2cli.file.Server.ResFileUploadPromise;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  20:36 2018/1/8.
 * @since chat_client
 */

public class ResFileUploadPromiseHandler implements MessageHandler {

    @Override
    public void handler(Message message) {
        UserManager.getInstance().receiveResFileUploadPromise((ResFileUploadPromise) message);
    }
}
