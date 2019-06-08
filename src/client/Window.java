package client;

import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Window {
    @FXML
    private Button registerButton;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField loginField;

    @FXML
    void initialize() {

        loginButton.setOnAction(actionEvent -> GUIHand.login(this,actionEvent));

        registerButton.setOnAction(actionEvent -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("WindowReg.fxml"));
                GUIHand.changeScene(actionEvent,root);
            }catch (Exception e){}
        });
    }

    public String getLoginField() {
        return loginField.getText();
    }

    public String getPasswordField() {
        return passwordField.getText();
    }
}
