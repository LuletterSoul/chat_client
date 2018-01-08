package com.kingston.chat.handler.login;

import com.google.protobuf.Message;
import com.kingston.chat.handler.MessageHandler;
import com.kingston.chat.logic.user.UserManager;
import com.luv.face2face.protobuf.generate.ser2cli.login.Server;
import lombok.extern.slf4j.Slf4j;

import static com.luv.face2face.protobuf.generate.ser2cli.login.Server.*;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  9:34 2018/1/8.
 * @since chat_client
 */

@Slf4j
public class RefreshProfileHandler implements MessageHandler {

    @Override
    public void handler(Message message) {
        ResServerRefreshProfile profile = (ResServerRefreshProfile) message;
        UserManager.getInstance().updateMyProfile(profile);
        log.info("user profile refreshed.[{}]", profile.toString());
    }
}
