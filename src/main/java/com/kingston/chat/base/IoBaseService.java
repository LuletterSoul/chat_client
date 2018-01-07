package com.kingston.chat.base;

import com.google.protobuf.Message;
import com.kingston.chat.net.IoSession;
import com.kingston.chat.net.message.AbstractPacket;

import com.luv.face2face.service.session.UserConnectSession;
import io.netty.channel.Channel;

/**
 * 提供一些基础服务接口
 * @author kingston
 */
public enum IoBaseService {

	INSTANCE;


//	/** 通信会话 */
	private IoSession session;

	private UserConnectSession userConnectSession;

	public void registerSession(Channel channel) {

//		this.session = new IoSession(channel);
		this.userConnectSession = new UserConnectSession(channel);
	}

	public void sendServerRequest(AbstractPacket request){
		this.session.sendPacket(request);

	}

	public void sendServerRequest(Message message) {
		this.userConnectSession.sendPacket(message);
	}

	/**
	 * 是否已连上服务器
	 * @return
	 */
	public boolean isConnectedSever() {
		return this.userConnectSession != null;
	}


}
