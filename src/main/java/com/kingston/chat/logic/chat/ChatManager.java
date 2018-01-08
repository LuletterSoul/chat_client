package com.kingston.chat.logic.chat;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.kingston.chat.base.IoBaseService;
import com.kingston.chat.base.UiBaseService;
import com.kingston.chat.fxextend.event.DoubleClickEventHandler;
import com.kingston.chat.logic.chat.message.req.ReqChatToUserPacket;
import com.kingston.chat.logic.friend.FriendManager;
import com.kingston.chat.logic.user.UserManager;
import com.kingston.chat.ui.R;
import com.kingston.chat.ui.StageController;

import com.luv.face2face.protobuf.generate.cli2srv.chat.Chat;
import com.luv.face2face.protobuf.generate.ser2cli.file.Server;
import javafx.event.Event;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import static com.luv.face2face.protobuf.generate.cli2srv.chat.Chat.*;
import static com.luv.face2face.protobuf.generate.ser2cli.file.Server.*;

@Slf4j
public class ChatManager {

    private static ChatManager self = new ChatManager();

    private ChatManager() {
    }

    public static ChatManager getInstance() {
        return self;
    }

//	public void sendMessageTo(long friendId, String content) {
//		ReqChatToUserPacket request = new ReqChatToUserPacket();
//		request.setToUserId(friendId);
//		request.setContent(content);
//		IoBaseService.INSTANCE.sendServerRequest(request);
//
//	}

    public void sendMessageTo(Long friendId, String content) {
        RequestChatToUserMsg.Builder chatToUserMsg = RequestChatToUserMsg.newBuilder();
        chatToUserMsg.setChatFromUserId(UserManager.getInstance().getMyUserId());
        chatToUserMsg.setChatToUserId(friendId);
        chatToUserMsg.setContent(content);
        IoBaseService.INSTANCE.sendServerRequest(chatToUserMsg.build());
        log.debug("Send message to [{}],message content:[{}]", friendId, content);
    }

    public void receiveFriendPrivateMessage(long sourceId, String content) {
        log.info("from user [{}]--------------->message:[{}]", sourceId, content);
        StageController stageController = UiBaseService.INSTANCE.getStageController();
        Stage stage = stageController.getStageBy(R.id.ChatToPoint);
        VBox msgContainer = (VBox) stage.getScene().getRoot().lookup("#msgContainer");

        UiBaseService.INSTANCE.runTaskInFxThread(() -> {
            Pane pane = null;
            if (sourceId == UserManager.getInstance().getMyUserId()) {
                pane = stageController.load(R.layout.PrivateChatItemRight, Pane.class);
            } else {
                pane = stageController.load(R.layout.PrivateChatItemLeft, Pane.class);
            }

            decorateChatRecord(content, pane);
            msgContainer.getChildren().add(pane);
        });

    }

    private Label decorateChatRecord(String message, Pane chatRecord) {
        Hyperlink _nikename = (Hyperlink) chatRecord.lookup("#nameUi");
        _nikename.setText(message);
        _nikename.setVisible(false);
        Label _createTime = (Label) chatRecord.lookup("#timeUi");
        _createTime.setText(new SimpleDateFormat("yyyy年MM月dd日  HH:mm:ss").format(new Date()));
        Label _body = (Label) chatRecord.lookup("#contentUi");
        _body.setText(message);
        return _body;
    }


    public void handlerFileUploadComplete(Long sourceId, String filePath, String fileName) {
        StageController stageController = UiBaseService.INSTANCE.getStageController();
        Stage stage = stageController.getStageBy(R.id.ChatToPoint);
        VBox msgContainer = (VBox) stage.getScene().getRoot().lookup("#msgContainer");
        UiBaseService.INSTANCE.runTaskInFxThread(() -> {
            Pane pane = null;
            if (sourceId == UserManager.getInstance().getMyUserId()) {
                pane = stageController.load(R.layout.FileUploadCompleteRight, Pane.class);
                decorateFileNotifyPane(pane, "上传成功");
                Hyperlink _nikename = (Hyperlink) pane.lookup("#nameUi");
                _nikename.setText(UserManager.getInstance().getMyProfile().getUserName().toString());
                _nikename.setVisible(false);
                msgContainer.getChildren().add(pane);
            } else {
                pane = stageController.load(R.layout.FileUploadCompleteLeft, Pane.class);
                Hyperlink _nikename = (Hyperlink) pane.lookup("#nameUi");
                String _friendNickName = FriendManager.getInstance().getFrendItemVoById(sourceId).getNickname();
                _nikename.setText(_friendNickName);
                _nikename.setVisible(false);
                decorateFileNotifyPane(pane, "双击我下载:" + fileName);
                pane.setOnMouseClicked(new DoubleClickEventHandler<Event>() {
                    @Override
                    public void handle(Event event) {
                        StageController stageController = UiBaseService.INSTANCE.getStageController();
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("Save Resource File");
                        File file = fileChooser.showSaveDialog(stageController.getStageBy(R.id.ChatToPoint));
//                        UserManager.getInstance().
//                                handleRequestUploadFile(file, Long.parseLong(userIdUi.getText()));
                    }
                });
                msgContainer.getChildren().add(pane);
            }
        });
    }

    private void decorateFileNotifyPane(Pane pane, String text) {
        Label _createTime = (Label) pane.lookup("#timeUi");
        _createTime.setText(new SimpleDateFormat("yyyy年MM月dd日  HH:mm:ss").format(new Date()));
        Label _body = (Label) pane.lookup("#contentUi");
        _body.setText(text);
    }

}
