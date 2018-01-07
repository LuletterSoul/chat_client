package com.kingston.chat.net.transport;

import com.google.protobuf.Message;
import com.kingston.chat.base.IoBaseService;
import com.kingston.chat.handler.DefaultHandlerManager;
import com.kingston.chat.handler.MessageDispatcher;
import com.kingston.chat.net.PacketManager;
import com.kingston.chat.net.message.AbstractPacket;

import com.luv.face2face.protobuf.analysis.ParserManager;
import com.luv.face2face.service.session.ChannelUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientTransportHandler extends ChannelHandlerAdapter implements MessageDispatcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientTransportHandler.class);

    private DefaultHandlerManager handlerManager;

    public ClientTransportHandler() {
        handlerManager = new DefaultHandlerManager(new ParserManager());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        LOGGER.info("channel [] active.................", ctx.channel().remoteAddress());
        //注册session
        IoBaseService.INSTANCE.registerSession(ctx.channel());
    }

    /**
     * 在这里进行客户端分发
     * 消息类型与业务处理分离
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        LOGGER.info("Receive message from [{}],message type:[{}]",
                ChannelUtils.getIp(ctx.channel()), msg.getClass().getSimpleName());
        if (handlerManager == null) {
            throw new IllegalAccessException("handler manager couldn't be null.");
        }
        this.dispatch((Message) msg);
    }

    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise promise) {
        System.err.println("TCP closed...");
        ctx.close(promise);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.err.println("客户端关闭1");
    }

    @Override
    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        ctx.disconnect(promise);
        System.err.println("客户端关闭2");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.err.println("客户端关闭3");
        Channel channel = ctx.channel();
        cause.printStackTrace();
        if (channel.isActive()) {
            System.err.println("simpleclient" + channel.remoteAddress() + "异常");
        }
    }


    @Override
    public void dispatch(Message message) {
        handlerManager.getHandler(message).handler(message);
    }
}
