package com.kingston.chat.handler.file;

import com.google.protobuf.Message;
import com.kingston.chat.base.IoBaseService;
import com.kingston.chat.handler.MessageHandler;
import com.kingston.chat.logic.chat.ChatManager;
import com.luv.face2face.protobuf.generate.ser2cli.file.Server;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;
import lombok.extern.slf4j.Slf4j;

import static com.luv.face2face.protobuf.generate.ser2cli.file.Server.*;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  11:36 2018/1/9.
 * @since chat_client
 */
@Slf4j
public class ResFileDownloadCompleteHandler implements MessageHandler {

    @Override
    public void handler(Message message) {
        ResFileDownloadComplete downloadComplete = (ResFileDownloadComplete) message;
        ChatManager.getInstance().handleDownloadComplete(downloadComplete);
        releaseChunkedWriteHandler();
    }
    private ChannelPipeline releaseChunkedWriteHandler() {
        Channel channel = IoBaseService.INSTANCE.getChannel();
        ChannelPipeline pipeline = channel.pipeline();
        ChannelHandler handler = pipeline.get("chunkedClientReadHandler");
        if (handler != null) {
            pipeline.remove("chunkedClientReadHandler");
        }
        return pipeline;
    }
}
