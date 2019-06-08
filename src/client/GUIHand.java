package client;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.*;
import javafx.stage.Stage;
import shared.Karlson;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class GUIHand extends Application {

    static public ArrayList<Karlson> storage;

    static ActionEvent actionEvent1;

    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Window.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Karlson");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        try {
            client = new Client("ubuntu",8888);
            client.testServerConnection();
        }
        catch (Exception e){e.printStackTrace();}
        launch(args);

    }
    static Client client;

    static void login(Window window,ActionEvent actionEvent) {
        actionEvent1 = actionEvent;
        client.username = window.getLoginField();
        client.password = window.getPasswordField();
        client.fromGUI = "login";
        client.start();
    }

    static void register(WindowReg window) {
        client.username = window.getLoginField().getText();
        client.email = window.getEmailField().getText();
        client.fromGUI = "register";
        client.start();
    }





    static void changeScene(ActionEvent actionEvent, Parent root) {
        try {
            actionEvent1 = actionEvent;
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void changeScene(Stage stage2, Parent root) {
        try {
            Scene scene = new Scene(root);
            Stage stage = stage2;
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void changeScene(Parent root) {
        try {
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) actionEvent1.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void show(){
        client.fromGUI = "show";
        client.start();
    }

    static void save(){
        client.fromGUI = "save";
        client.start();
    }

    static void add(String s){
        client.fromGUI = s;
        client.start();
    }
}
