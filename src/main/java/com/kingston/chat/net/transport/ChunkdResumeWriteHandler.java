package com.kingston.chat.net.transport;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  10:01 2018/1/9.
 * @since chat_client
 */

public class ChunkdResumeWriteHandler extends ChunkedWriteHandler {
    /**
     * 解决重复上传才能触发的问题
     * @param ctx
     * @param msg
     * @param promise
     * @throws Exception
     */
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        super.write(ctx, msg, promise);
        this.resumeTransfer();
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }
}
