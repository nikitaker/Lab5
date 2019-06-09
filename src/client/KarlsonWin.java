package client;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import shared.Clothes;
import shared.Karlson;

public class KarlsonWin {


    @FXML
    private MenuItem import1;

    @FXML
    private MenuItem mapView;

    @FXML
    private TableColumn<Karlson, String> karlsonName;

    @FXML
    private TableColumn<Karlson, String> clothesColor;
    @FXML
    private TableColumn<Karlson, String> clothesName;

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
    private TableColumn<Karlson, String> karlsonUsername;

    @FXML
    private MenuItem his;

    @FXML
    private MenuItem addElem;

    @FXML
    private MenuItem fra;

    @FXML
    private TableView<Karlson> karlsonTable;

    @FXML
    private TableColumn<Karlson, Long> karlsonSpeed;

    @FXML
    private TableColumn<Karlson, String> karlsonDate;

    @FXML
    private MenuItem deleteElem;

    @FXML
    private TableColumn<Karlson, String> karlsonClothes;

    @FXML
    private MenuItem close;

    @FXML
    private Parent mainroot;



    @FXML
    void initialize(){

        tableView.setOnAction(actionEvent -> {
            GUIHand.show();
            karlsonName.setCellValueFactory(new PropertyValueFactory<>("name"));
            karlsonSpeed.setCellValueFactory(new PropertyValueFactory<>("flyspeed"));
            karlsonDate.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
            karlsonUsername.setCellValueFactory(new PropertyValueFactory<>("owner"));
            try {
                clothesName.setCellValueFactory(cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getClothes().getName()));
                clothesColor.setCellValueFactory(cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getClothes().getColor()));
            }catch (NullPointerException e){}

            karlsonTable.setItems(FXCollections.observableArrayList(GUIHand.storage));

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
                Parent root = FXMLLoader.load(getClass().getResource("KarlsonWinEst.fxml"));
                Stage stage = (Stage)mainroot.getScene().getWindow();
                GUIHand.changeScene(stage,root);
            }catch (Exception e){}
        });
        fra.setOnAction(actionEvent -> {GUIHand.laguage = "fra";
            try {
                Parent root = FXMLLoader.load(getClass().getResource("KarlsonWinFra.fxml"));
                Stage stage = (Stage)mainroot.getScene().getWindow();
                GUIHand.changeScene(stage,root);
            }catch (Exception e){}
        });
        rus.setOnAction(actionEvent -> {GUIHand.laguage = "rus";
            try {
                Parent root = FXMLLoader.load(getClass().getResource("KarlsonWin.fxml"));
                Stage stage = (Stage)mainroot.getScene().getWindow();
                GUIHand.changeScene(stage,root);
            }catch (Exception e){}
        });
        his.setOnAction(actionEvent -> {
            GUIHand.laguage = "his";
            try {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Внимание");
                alert.setHeaderText("Язык: Испания(Гондурас)");
                alert.setContentText("А ты Пидорас");
                alert.showAndWait();

                Parent root = FXMLLoader.load(getClass().getResource("KarlsonWinHis.fxml"));
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

        save.setOnAction(actionEvent -> GUIHand.save());

        close.setOnAction(actionEvent -> {
            Stage stage = (Stage)mainroot.getScene().getWindow();
            stage.close();});
    }

}
