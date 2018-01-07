package com.kingston.chat.handler.login;

import com.google.protobuf.Message;
import com.kingston.chat.handler.MessageHandler;
import com.kingston.chat.logic.user.UserManager;
import lombok.extern.slf4j.Slf4j;

import static com.luv.face2face.protobuf.generate.ser2cli.login.Server.*;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  20:24 2018/1/7.
 * @since chat_client
 */

@Slf4j
public class RegistrySuccessHandler implements MessageHandler {
    @Override
    public void handler(Message message) {
        ResServerRegisterSucc msg = (ResServerRegisterSucc) message;
        log.info("Register successfully.from server:[{}].New account:[{}]", msg.getDescription(), msg.getUserId());
        UserManager.getInstance().handleRegistrySuccResponse(msg);
    }
}
