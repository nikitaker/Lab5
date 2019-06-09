package client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import shared.Karlson;
import java.util.ArrayList;

public class KarlsonWinMap {

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
    private AnchorPane mainroot;

    @FXML
    private MenuItem deleteElem;

    @FXML
    private MenuItem close;

    @FXML
    private Text karlsonInfo;

    ArrayList<Karlson> karlsons;
    ArrayList<Ellipse> ellipses = new ArrayList<>();

    @FXML
    void initialize(){

        GUIHand.show();
        while (GUIHand.storage == null)
        {
            System.out.println("Пизда");
        }
        karlsons = GUIHand.storage;
        for (Karlson karlson:karlsons)
        {
            System.out.println(karlson.getName());
            Ellipse ellipse = new Ellipse();
            ellipse.setCenterX(Math.random()*500);
            ellipse.setCenterY(Math.random()*500);
            ellipse.setRadiusX((Math.log(karlson.getFlyspeed()))*5);
            ellipse.setRadiusY((Math.log(karlson.getFlyspeed()))*8);
            ellipse.setFill(javafx.scene.paint.Color.rgb(karlson.getOwnerId()+20,karlson.getOwnerId(),karlson.getOwnerId()+65));
            ellipses.add(ellipse);
            mainroot.getChildren().add(ellipse);
        }


        for (Ellipse ellipse:ellipses) {
            ellipse.setOnMouseClicked(mouseEvent -> {
                long ln = Math.round(Math.pow(Math.E, (ellipse.getRadiusX()) / 5));
                for (Karlson karlson : karlsons) {
                    if (karlson.getFlyspeed() == ln) {
                        karlsonInfo.setText(karlson.getName() + "\n" + karlson.getFlyspeed() + "\n" + karlson.getOwner() + "\n"
                                + karlson.getClothes().getName() + " " + karlson.getClothes().getColor());
                    }
                }
            });
        }














        est.setOnAction(actionEvent -> {GUIHand.laguage = "est";
            try {
                Parent root = FXMLLoader.load(getClass().getResource("KarlsonWinMap.fxml"));
                Stage stage = (Stage)mainroot.getScene().getWindow();
                GUIHand.changeScene(stage,root);
            }catch (Exception e){}
        });
        fra.setOnAction(actionEvent -> {GUIHand.laguage = "fra";
            try {
                Parent root = FXMLLoader.load(getClass().getResource("KarlsonWinMapFra.fxml"));
                Stage stage = (Stage)mainroot.getScene().getWindow();
                GUIHand.changeScene(stage,root);
            }catch (Exception e){}
        });
        rus.setOnAction(actionEvent -> {GUIHand.laguage = "rus";
            try {
                Parent root = FXMLLoader.load(getClass().getResource("KarlsonWinMap.fxml"));
                Stage stage = (Stage)mainroot.getScene().getWindow();
                GUIHand.changeScene(stage,root);
            }catch (Exception e){}
        });
        his.setOnAction(actionEvent -> {
            GUIHand.laguage = "his";
            try {
                Parent root = FXMLLoader.load(getClass().getResource("KarlsonWinMapHis.fxml"));
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

