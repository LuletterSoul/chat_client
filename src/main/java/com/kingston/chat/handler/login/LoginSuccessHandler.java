package com.kingston.chat.handler.login;

import com.google.protobuf.Message;
import com.kingston.chat.handler.MessageHandler;
import com.kingston.chat.logic.login.LoginManager;
import com.luv.face2face.protobuf.generate.ser2cli.login.Server;
import lombok.extern.slf4j.Slf4j;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  20:24 2018/1/7.
 * @since chat_client
 */

@Slf4j
public class LoginSuccessHandler implements MessageHandler {

    @Override
    public void handler(Message message) {
        Server.ResServerLoginSucc msg = (Server.ResServerLoginSucc) message;
        LoginManager.getInstance().handleLoginSuccessResponse((msg));
        log.info("..............login success.from server:{{}}...................", msg.getDescription());
    }
}
