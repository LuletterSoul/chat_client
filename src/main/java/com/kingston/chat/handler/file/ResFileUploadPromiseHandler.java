package com.kingston.chat.handler.file;

import com.google.protobuf.Message;
import com.kingston.chat.base.IoBaseService;
import com.kingston.chat.handler.MessageHandler;
import com.kingston.chat.logic.chat.ChatManager;
import com.kingston.chat.logic.user.UserManager;
import com.kingston.chat.net.transport.SocketClient;
import com.luv.face2face.protobuf.generate.ser2cli.file.Server;
import com.luv.face2face.protobuf.generate.ser2cli.file.Server.ResFileUploadPromise;
import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.stream.ChunkedWriteHandler;

import static com.kingston.chat.net.transport.SocketClient.chunkedWriteHandler;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  20:36 2018/1/8.
 * @since chat_client
 */

public class ResFileUploadPromiseHandler implements MessageHandler {

    @Override
    public void handler(Message message) {
        Channel channel = IoBaseService.INSTANCE.getChannel();
        ChannelPipeline pipeline = channel.pipeline();
        chunkedWriteHandler  = new ChunkedWriteHandler();
        pipeline.addFirst("chunkedWriteHandler", chunkedWriteHandler);
        ChatManager.getInstance().receiveResFileUploadPromise((ResFileUploadPromise) message);
        chunkedWriteHandler.resumeTransfer();
    }
}
