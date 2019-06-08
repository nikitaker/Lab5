package client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class WindowReg {

    @FXML
    private Button enterButton;

    @FXML
    private Button registrationButton;

    @FXML
    private TextField emailField;

    @FXML
    private TextField loginField;

    @FXML
    void initialize() {
        enterButton.setOnAction(actionEvent -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("Window.fxml"));
                GUIHand.changeScene(actionEvent,root);
            }catch (Exception e){e.printStackTrace();}
        });

        registrationButton.setOnAction(actionEvent -> GUIHand.register(this));
    }


    public TextField getLoginField() {
        return loginField;
    }

    public TextField getEmailField() {
        return emailField;
    }
}
