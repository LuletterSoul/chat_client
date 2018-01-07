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
public class LoginFailedHandler implements MessageHandler

{
    @Override
    public void handler(Message message) {
        Server.ResServerLoginFailed msg = (Server.ResServerLoginFailed) message;
        LoginManager.getInstance().handleLoginFailedResponse((msg));
        log.info("..............login failed.from server:{{}}...................", msg.getDescription());
    }
}
