package com.kingston.chat.handler.file;

import com.google.protobuf.Message;
import com.kingston.chat.base.IoBaseService;
import com.kingston.chat.handler.MessageHandler;
import com.kingston.chat.logic.chat.ChatManager;
import com.kingston.chat.net.transport.ChunkedClientReadHandler;
import com.luv.face2face.protobuf.generate.ser2cli.file.Server;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;

import java.io.FileNotFoundException;

import static com.luv.face2face.protobuf.generate.ser2cli.file.Server.*;
import static com.luv.face2face.protobuf.generate.ser2cli.file.Server.ResFileUploadComplete;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  0:39 2018/1/9.
 * @since chat_client
 */

public class ResFileUploadCompleteHandler implements MessageHandler {

    @Override
    public void handler(Message message) {
        ResFileUploadComplete complete = (ResFileUploadComplete) message;
        ChannelPipeline pipeline = releaseChunkedWriteHandler();
        Long sourceId = complete.getFileUploadMsg().getFormUserId();
        String fileName = complete.getFileUploadMsg().getFileName();
        String filePath = complete.getServerfilePath();
        ChatManager.getInstance().handlerFileUploadComplete(sourceId, fileName,complete.getServerfilePath());
        try {
            pipeline.addFirst("chunkedClientReadHandler", new ChunkedClientReadHandler(complete.getFileUploadMsg()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
//        ChatManager.getInstance().handleRequestDownloadFile(sourceId,fileName,filePath);

    }

//    private ReqFileDownloadMsg buildReqDownloadFIleMsg(Long sourceId, String fileName, String filePath) {
//        ReqFileDownloadMsg.Builder builder = ReqFileDownloadMsg.newBuilder();
//        builder.setFileName(fileName);
//        builder.setFilePath(filePath);
//        builder.setFormUserId(UserManager.getInstance().getMyUserId());
//        builder.setSourceUserId(sourceId);
//        builder.build();
//        return builder.build();
//    }

    private ChannelPipeline releaseChunkedWriteHandler() {
        Channel channel = IoBaseService.INSTANCE.getChannel();
        ChannelPipeline pipeline = channel.pipeline();
        ChannelHandler handler = pipeline.get("chunkedWriteHandler");
        if (handler != null) {
            pipeline.remove("chunkedWriteHandler");
        }
        return pipeline;
    }
}
