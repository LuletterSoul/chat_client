package com.kingston.chat.handler;

import com.google.protobuf.Message;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  19:41 2018/1/7.
 * @since chat_client
 */

public interface MessageDispatcher  {

    void dispatch(Message message);
}
