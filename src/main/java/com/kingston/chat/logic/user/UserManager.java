package com.kingston.chat.logic.user;

import com.kingston.chat.base.IoBaseService;
import com.kingston.chat.base.UiBaseService;
import com.kingston.chat.ui.R;
import com.kingston.chat.ui.StageController;

import io.netty.channel.*;
import io.netty.handler.stream.ChunkedFile;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;


import static com.luv.face2face.protobuf.generate.cli2srv.login.Auth.*;
import static com.luv.face2face.protobuf.generate.ser2cli.file.Server.*;
import static com.luv.face2face.protobuf.generate.ser2cli.login.Server.*;

@Slf4j
@Setter
@Getter
public class UserManager {

    private static UserManager instance = new UserManager();

    private UserModel profile = new UserModel();

    private String password;

    //登录过
    private boolean logined = false;

    //意外登出
    private boolean disconnectException = false;


    public static UserManager getInstance() {
        return instance;
    }

//    public void updateMyProfile(ResUserInfoPacket userInfo) {
//        profile.setSex(userInfo.getSex());
//        profile.setSignature(userInfo.getSignature());
//        profile.setUserId(userInfo.getUserId());
//        profile.setUserName(userInfo.getUserName());
//    }

    public void updateMyProfile(ResServerRefreshProfile profile) {
        this.profile.setSex(profile.getSex());
        this.profile.setSignature(profile.getSignature());
        this.profile.setUserId(profile.getUserId());
        this.profile.setUserName(profile.getNickname());
    }


    public UserModel getMyProfile() {
        return this.profile;
    }

    public long getMyUserId() {
        return this.profile.getUserId();
    }

    public void registerAccount(byte sex, String nickName, String password) {
        RequestUserRegisterMsg.Builder builder = RequestUserRegisterMsg.newBuilder();
        builder.setNickname(nickName);
        builder.setPassword(password);
        builder.setSex("男");
        System.err.println("向服务端发送注册请求");
        IoBaseService.INSTANCE.sendServerRequest(builder.build());
    }

    public void handleRegistrySuccResponse(ResServerRegisterSucc resServerRegisterSucc) {
//		boolean isSucc = resultCode == Constants.TRUE;
        StageController stageController = UiBaseService.INSTANCE.getStageController();
        Stage stage = stageController.getStageBy(R.id.RegisterView);
        Label errorTips = (Label) stage.getScene().getRoot().lookup("#errorTips");
        UiBaseService.INSTANCE.runTaskInFxThread(() -> {
            errorTips.setVisible(true);
            errorTips.setText("注册成功,您的账号为:" + resServerRegisterSucc.getUserId());
//            gotoLoginPanel(resServerRegisterSucc.getUserId());
        });
    }

    public void handleRegisterFailerResponse(ResServerRegisterFailed resServerRegisterFaileds) {
        StageController stageController = UiBaseService.INSTANCE.getStageController();
        Stage stage = stageController.getStageBy(R.id.RegisterView);
        Label errorTips = (Label) stage.getScene().getRoot().lookup("#errorText");
        UiBaseService.INSTANCE.runTaskInFxThread(() -> {
            errorTips.setVisible(true);
            errorTips.setText(resServerRegisterFaileds.getDescription());
        });
    }

    private void gotoLoginPanel(long userId) {
        StageController stageController = UiBaseService.INSTANCE.getStageController();
        stageController.switchStage(R.id.LoginView, R.id.RegisterView);
        Stage stage = stageController.getStageBy(R.id.LoginView);
        TextField userIdField = (TextField) stage.getScene().getRoot().lookup("#userId");
        userIdField.setText(String.valueOf(userId));
    }

    public void handleRequestUploadFile(File file, Long toUserId) {
//        try {
//            MagicMatch match = Magic.getMagicMatch(file, false);
        ReqFileUploadMsg.Builder builder = ReqFileUploadMsg.newBuilder();
        builder.setFileLength(file.length());
        builder.setFileName(file.getName());
        builder.setFileType("exe");
        builder.setFormUserId(getMyUserId());
        builder.setLocalPath(file.getPath());
        builder.setToUserId(toUserId);
        IoBaseService.INSTANCE.sendServerRequest(builder.build());
//        }
//        catch (MagicParseException | MagicMatchNotFoundException | MagicException e) {
//            e.printStackTrace();
//        }
    }


    public void receiveResFileUploadPromise(ResFileUploadPromise resFileUploadPromise) {
        log.debug("Receive response file upload promise.[{}]", resFileUploadPromise.toString());
        String localPath = resFileUploadPromise.getYourFilePath();
//        EventLoopGroup group = new NioEventLoopGroup(1);
//        try{
//            Bootstrap b  = new Bootstrap();
//            b.group(group).channel(NioSocketChannel.class)
//                    .handler(new ChannelInitializer<SocketChannel>(){
//                        @Override
//                        protected void initChannel(SocketChannel arg0)
//                                throws Exception {
//                            ChannelPipeline pipeline = arg0.pipeline();
////                            pipeline.addLast(new ClientTransportHandler());
//                            pipeline.addLast(new ChunkedWriteHandler());
//                        }
//
//                    });
//
//            ChannelFuture f = b.connect(new InetSocketAddress(ClientConfigs.REMOTE_SERVER_IP, ClientConfigs.REMOTE_SERVER_PORT))
//                    .sync();
//            f.channel().closeFuture().sync();
//        }catch(Exception e){
//            e.printStackTrace();
//        }
        Channel channel = IoBaseService.INSTANCE.getChannel();
//        ChannelPipeline pipeline = channel.pipeline();
        try {
            IoBaseService.INSTANCE.getChannel().write(new ChunkedFile(new File(localPath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
