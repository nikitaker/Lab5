package client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import shared.Karlson;

public class KarlsonWinAdd {

    @FXML
    private MenuItem import1;

    @FXML
    private MenuItem mapView;

    @FXML
    private Text karlsonUserText;

    @FXML
    private MenuItem est;

    @FXML
    private MenuItem save;

    @FXML
    private MenuItem tableView;

    @FXML
    private Button addButton;

    @FXML
    private MenuItem rus;

    @FXML
    private MenuItem his;

    @FXML
    private MenuItem addElem;

    @FXML
    private TextField clothesField;

    @FXML
    private Button addMinButtin;

    @FXML
    private MenuItem fra;

    @FXML
    private TextField nameFiels;

    @FXML
    private TextField speedField;

    @FXML
    private TextField colourField;

    @FXML
    private Button addMaxButton;

    @FXML
    private MenuItem deleteElem;

    @FXML
    private MenuItem close;

    @FXML
    private Parent mainroot;

    @FXML
    void initialize(){

        addButton.setOnAction(actionEvent -> {
            String s = "add {\""+ speedField.getText()+ "\":{\"name\":\""+nameFiels.getText()+"\",\"flyspeed\":\"" +
                    speedField.getText()+"\",\"Clothes\":{\"name\":\""+ clothesField.getText() + "\",\"color\":\""+
                    colourField.getText()+"\"}}}";
            GUIHand.add(s);
        });


        addElem.setOnAction(actionEvent -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("KarlsonWinAdd.fxml"));
                Stage stage = (Stage)mainroot.getScene().getWindow();
                GUIHand.changeScene(stage,root);
            }catch (Exception e){e.printStackTrace();}
        });


        tableView.setOnAction(actionEvent -> {
            GUIHand.show();
        });

        save.setOnAction(actionEvent -> GUIHand.save());

        close.setOnAction(actionEvent -> {
            Stage stage = (Stage)mainroot.getScene().getWindow();
            stage.close();});
    }

}
