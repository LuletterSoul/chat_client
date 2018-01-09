package com.kingston.chat.handler.file;

import com.google.protobuf.Message;
import com.kingston.chat.base.IoBaseService;
import com.kingston.chat.handler.MessageHandler;
import com.kingston.chat.logic.chat.ChatManager;
import com.kingston.chat.logic.user.UserManager;
import com.kingston.chat.net.transport.SocketClient;
import com.luv.face2face.protobuf.generate.ser2cli.file.Server;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.stream.ChunkedWriteHandler;

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
        Channel channel = IoBaseService.INSTANCE.getChannel();
        ChannelPipeline pipeline = channel.pipeline();
        ChannelHandler handler = pipeline.get("chunkedWriteHandler");
        if (handler != null) {
            pipeline.remove("chunkedWriteHandler");
        }
        Long souceId = complete.getFileUploadMsg().getFormUserId();
        String fileName = complete.getFileUploadMsg().getFileName();
        Long formUserId = complete.getFileUploadMsg().getFormUserId();
        String filePath = complete.getServerfilePath();
        ChatManager.getInstance().handlerFileUploadComplete(souceId, filePath, fileName);
    }
}
