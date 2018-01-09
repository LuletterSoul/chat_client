package com.kingston.chat.ui.controller;

import java.io.File;
import java.io.IOException;

import com.kingston.chat.base.UiBaseService;
import com.kingston.chat.logic.chat.ChatManager;
import com.kingston.chat.logic.user.UserManager;
import com.kingston.chat.ui.ControlledStage;
import com.kingston.chat.ui.R;
import com.kingston.chat.ui.StageController;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import net.sf.jmimemagic.*;

@Slf4j
public class ChatToPointController implements ControlledStage {

    @FXML
    private Label userIdUi;

    @FXML
    private TextArea msgInput;

    @FXML
    private ScrollPane outputMsgUi;

    @FXML
    private void sendMessage() throws IOException {
        final long userId = Long.parseLong(userIdUi.getText());
        String message = msgInput.getText();
        ChatManager.getInstance().sendMessageTo(userId, message);
    }

    /**
     * 处理文件上传
     * @throws IOException
     */
    @FXML
    private void transportFile() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(getMyStage());
        if (file == null) {
            return;
        }
        ChatManager.getInstance().handleRequestUploadFile(file, Long.parseLong(userIdUi.getText()));
    }


    @Override
    public Stage getMyStage() {
        StageController stageController = UiBaseService.INSTANCE.getStageController();
        return stageController.getStageBy(R.id.ChatToPoint);
    }

    @FXML
    private void close() {
        UiBaseService.INSTANCE.getStageController().closeStge(R.id.ChatToPoint);
    }


}


