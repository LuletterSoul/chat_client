package com.kingston.chat.net.transport;

public class ClientConfigs {

    /**
     * 服务器ip
     */
//	public static String REMOTE_SERVER_IP = "47.95.113.210";
    public static String REMOTE_SERVER_IP = "127.0.0.1";
    /**
     * 服务器端口
     */
    public static int REMOTE_SERVER_PORT = 8088;
    /**
     * 客户端本地ip
     */
    public final static String LOCAL_SERVER_IP = "127.0.0.1";
    /**
     * 客户端本地端口
     */
    public final static int LOCAL_SERVER_PORT = 8087;
    /**
     * 客户端断线重连最大尝试次数
     */
    public final static int MAX_RECONNECT_TIMES = 10;

}
