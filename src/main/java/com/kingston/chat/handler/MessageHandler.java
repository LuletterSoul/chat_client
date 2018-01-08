package com.kingston.chat.handler;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  19:24 2018/1/7.
 * @since chat_client
 */

import com.google.protobuf.Message;

/**
 * 客户端不同类型消息包的处理器
 */
public interface MessageHandler {
    void handler(Message message);
}
