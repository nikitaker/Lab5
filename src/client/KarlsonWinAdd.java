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
                    speedField.getText()+"\",\"clothes\":{\"name\":\""+ clothesField.getText() + "\",\"color\":\""+
                    colourField.getText()+"\"}}}";
            GUIHand.add(s);
        });




        est.setOnAction(actionEvent -> {GUIHand.laguage = "est";
            try {
                Parent root = FXMLLoader.load(getClass().getResource("KarlsonWinAddEst.fxml"));
                Stage stage = (Stage)mainroot.getScene().getWindow();
                GUIHand.changeScene(stage,root);
            }catch (Exception e){}
        });
        fra.setOnAction(actionEvent -> {GUIHand.laguage = "fra";
            try {
                Parent root = FXMLLoader.load(getClass().getResource("KarlsonWinAddFra.fxml"));
                Stage stage = (Stage)mainroot.getScene().getWindow();
                GUIHand.changeScene(stage,root);
            }catch (Exception e){}
        });
        rus.setOnAction(actionEvent -> {GUIHand.laguage = "rus";
            try {
                Parent root = FXMLLoader.load(getClass().getResource("KarlsonWinAdd.fxml"));
                Stage stage = (Stage)mainroot.getScene().getWindow();
                GUIHand.changeScene(stage,root);
            }catch (Exception e){}
        });
        his.setOnAction(actionEvent -> {
            GUIHand.laguage = "his";
            try {
                Parent root = FXMLLoader.load(getClass().getResource("KarlsonWinAddHis.fxml"));
                Stage stage = (Stage) mainroot.getScene().getWindow();
                GUIHand.changeScene(stage, root);
            } catch (Exception e) {
            }
        });

        karlsonUserText.setText(GUIHand.username);

        deleteElem.setOnAction(actionEvent -> {
            try {
                Parent root;
                if(GUIHand.laguage.equals("fra")){
                    root = FXMLLoader.load(getClass().getResource("KarlsonWinDelFra.fxml"));}
                else{
                    root = FXMLLoader.load(getClass().getResource("KarlsonWinDel.fxml"));}
                Stage stage = (Stage)mainroot.getScene().getWindow();
                GUIHand.changeScene(stage,root);
            }catch (Exception e){e.printStackTrace();}
        });

        addElem.setOnAction(actionEvent -> {
            try {
                Parent root;
                if(GUIHand.laguage.equals("fra")){
                    root = FXMLLoader.load(getClass().getResource("KarlsonWinAddFra.fxml"));}
                else{
                    root = FXMLLoader.load(getClass().getResource("KarlsonWinAdd.fxml"));}
                Stage stage = (Stage)mainroot.getScene().getWindow();
                GUIHand.changeScene(stage,root);
            }catch (Exception e){e.printStackTrace();}
        });

        mapView.setOnAction(actionEvent -> {
            try {
                Parent root;
                if(GUIHand.laguage.equals("fra")){
                    root = FXMLLoader.load(getClass().getResource("KarlsonWinMapFra.fxml"));}
                else{
                    root = FXMLLoader.load(getClass().getResource("KarlsonWinMap.fxml"));}
                Stage stage = (Stage)mainroot.getScene().getWindow();
                GUIHand.changeScene(stage,root);
            }catch (Exception e){e.printStackTrace();}
        });


        tableView.setOnAction(actionEvent -> {
            GUIHand.show();
            try {
                Parent root;
                if(GUIHand.laguage.equals("fra")){
                    root = FXMLLoader.load(getClass().getResource("KarlsonWinFra.fxml"));}
                else{
                    root = FXMLLoader.load(getClass().getResource("KarlsonWin.fxml"));}
                Stage stage = (Stage)mainroot.getScene().getWindow();
                GUIHand.changeScene(stage,root);
            }catch (Exception e){e.printStackTrace();}
        });

        save.setOnAction(actionEvent -> GUIHand.save());

        close.setOnAction(actionEvent -> {
            Stage stage = (Stage)mainroot.getScene().getWindow();
            stage.close();});
    }

}
