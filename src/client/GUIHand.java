package client;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import shared.Comands;
import shared.Command;
import shared.Karlson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class GUIHand extends Application {

    static public ArrayList<Karlson> storage;
    static public ArrayList<Karlson> memoryStorage = new ArrayList<Karlson>();

    static ActionEvent actionEvent1;

    static String username;
    static String password;
    static String output;
    static String laguage;

    public static void alert(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("Сервер недоступен");
        alert.showAndWait();
        System.exit(1);
    }
    public static void alertJSON(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Внимание");
        alert.setHeaderText("Неверный ввод");
        alert.showAndWait();
    }

    public static void generateStorage(){
        memoryStorage.clear();
        for(Karlson karlson:storage){memoryStorage.add(new Karlson(karlson));}
    }

    public void start(Stage primaryStage) throws Exception{

        try {
            client = new Client("ubuntu",8888);
            client.testServerConnection();
        } catch (Exception e){e.printStackTrace();}

        Parent root = FXMLLoader.load(getClass().getResource("Window.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Karlson");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    static Client client;

    static void login(Window window,ActionEvent actionEvent) {
        actionEvent1 = actionEvent;
        client.username = window.getLoginField();
        username = window.getLoginField();
        client.password = window.getPasswordField();
        password = window.getPasswordField();
        client.fromGUI = "login";
        laguage ="rus";
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
            show();
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
            show();
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
            show();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) actionEvent1.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void import1(String path){
        client.fromGUI = "import " + path;
        client.start();
    }

    static void show(){
        client.fromGUI = "show";
        client.start();
    }

    static void save(){
        Command c = new Command("loadHash", null, username + " " + password);
        c.setData(storage);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(outputStream)){
            oos.writeObject(c);
            oos.flush();
            ByteBuffer buffer = ByteBuffer.allocate(8192);
            buffer.clear();
            buffer.put(outputStream.toByteArray());
            buffer.flip();
            client.udpChanel.send(buffer, client.serverAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void add(String s){
        client.fromGUI = s;
        client.username = username;
        client.password = password;
        client.start();
    }
}
