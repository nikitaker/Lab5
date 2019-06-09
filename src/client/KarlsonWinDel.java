package client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class KarlsonWinDel {

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
    private MenuItem rus;

    @FXML
    private MenuItem his;

    @FXML
    private MenuItem addElem;

    @FXML
    private MenuItem fra;

    @FXML
    private Text respondeText;

    @FXML
    private TextField speedField;

    @FXML
    private TextField nameField;

    @FXML
    private MenuItem deleteElem;

    @FXML
    private Button removeButton;

    @FXML
    private MenuItem close;

    @FXML
    private Button removeLowButton;


    @FXML
    private Parent mainroot;

    @FXML
    void initialize(){

        removeButton.setOnAction(actionEvent -> {
            String s = "remove {\""+ speedField.getText()+ "\":{\"name\":\""+ nameField.getText() + "\",\"flyspeed\":\"" +
                    speedField.getText()+"\"}}";
            GUIHand.add(s);
            respondeText.setText(GUIHand.output);
        });

        removeLowButton.setOnAction(actionEvent -> {
            String s = "remove_lower {\""+ speedField.getText()+ "\":{\"name\":\""+ nameField.getText() +"\",\"flyspeed\":\"" +
                    speedField.getText()+"\"}}";
            GUIHand.add(s);
            respondeText.setText(GUIHand.output);
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

        est.setOnAction(actionEvent -> {GUIHand.laguage = "est";
            try {
                Parent root = FXMLLoader.load(getClass().getResource("KarlsonWinDelEst.fxml"));
                Stage stage = (Stage)mainroot.getScene().getWindow();
                GUIHand.changeScene(stage,root);
            }catch (Exception e){}
        });
        fra.setOnAction(actionEvent -> {GUIHand.laguage = "fra";
        try {
            Parent root = FXMLLoader.load(getClass().getResource("KarlsonWinDelFra.fxml"));
            Stage stage = (Stage)mainroot.getScene().getWindow();
            GUIHand.changeScene(stage,root);
        }catch (Exception e){}
        });
        rus.setOnAction(actionEvent -> {GUIHand.laguage = "rus";
            try {
                Parent root = FXMLLoader.load(getClass().getResource("KarlsonWinDel.fxml"));
                Stage stage = (Stage)mainroot.getScene().getWindow();
                GUIHand.changeScene(stage,root);
            }catch (Exception e){}
        });
        his.setOnAction(actionEvent -> {
            GUIHand.laguage = "his";
            try {
                Parent root = FXMLLoader.load(getClass().getResource("KarlsonWinDel.fxml"));
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
