package com.kingston.chat.logic.user;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.*;

/**
 * 用户个人信息
 *
 * @author kingston
 */
@Data
@Getter
@Setter
public class UserModel {

    private long userId;
    /**
     * 账号昵称
     */
    private StringProperty userName = new SimpleStringProperty("");
    /**
     * 个性签名
     */
    private StringProperty signature = new SimpleStringProperty("");
    /**
     * 性别
     */
    private String sex;

    private String password;

//    public final StringProperty userNameProperty() {
//        signature.
//        return userName;
//    }
//
//    public final StringProperty signaturePropertiy() {
//        return this.signature;
//    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public void setSignature(String signature) {
        this.signature.set(signature);
    }
}
