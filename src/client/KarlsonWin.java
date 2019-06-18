package client;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.LongStringConverter;
import javafx.util.converter.NumberStringConverter;
import shared.Clothes;
import shared.Karlson;

import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

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

    void error(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Внимание");
        alert.setHeaderText("Это не ваш карлсон");
        alert.showAndWait();
        karlsonTable.refresh();
    }


    @FXML
    void initialize() {
        Locale locale;
        switch (GUIHand.laguage){
            case "fra":
                locale = new Locale("fr");
                break;
            case "est":
                locale = new Locale("et");
                break;
            case "his":
                locale = new Locale("es","HN");
                break;
            default:
                locale = new Locale("ru");
                break;
        }
        ResourceBundle bundle = ResourceBundle.getBundle("Lang.lang",locale);
        save.getParentMenu().setText(bundle.getString("menu_file"));
        save.setText(bundle.getString("menu_save"));
        import1.setText(bundle.getString("menu_import"));
        close.setText(bundle.getString("menu_close"));
        rus.getParentMenu().setText(bundle.getString("menu_lang"));
        rus.setText(bundle.getString("menu_rus"));
        fra.setText(bundle.getString("menu_fra"));
        his.setText(bundle.getString("menu_his"));
        est.setText(bundle.getString("menu_est"));
        mapView.getParentMenu().setText(bundle.getString("menu_view"));
        mapView.setText(bundle.getString("menu_map"));
        tableView.setText(bundle.getString("menu_table"));
        addElem.getParentMenu().setText(bundle.getString("menu_elem"));
        addElem.setText(bundle.getString("menu_add"));
        deleteElem.setText(bundle.getString("menu_dell"));




        karlsonName.setCellValueFactory(new PropertyValueFactory<>("name"));
        karlsonName.setCellFactory(TextFieldTableCell.<Karlson> forTableColumn());
        karlsonSpeed.setCellValueFactory(new PropertyValueFactory<>("flyspeed"));
        karlsonSpeed.setCellFactory(TextFieldTableCell.<Karlson,Long> forTableColumn(new LongStringConverter()));
        karlsonDate.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        karlsonUsername.setCellValueFactory(new PropertyValueFactory<>("owner"));
        karlsonUsername.setCellFactory(TextFieldTableCell.<Karlson> forTableColumn());
        try {
            clothesName.setCellValueFactory(cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getClothes().getName()));
            clothesName.setCellFactory(TextFieldTableCell.<Karlson> forTableColumn());
            clothesColor.setCellValueFactory(cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getClothes().getColor()));
            clothesColor.setCellFactory(TextFieldTableCell.<Karlson> forTableColumn());
        } catch (NullPointerException e) {e.printStackTrace();}

        if (GUIHand.storage != null){ karlsonTable.setItems(FXCollections.observableArrayList(GUIHand.storage));}
        karlsonTable.setEditable(true);

        tableView.setOnAction(actionEvent -> {
            GUIHand.show();
            if (GUIHand.storage != null){
                karlsonTable.setItems(FXCollections.observableArrayList(GUIHand.storage));}
        });



        karlsonName.setOnEditCommit(karlsonStringCellEditEvent -> {
            TablePosition<Karlson, String> pos = karlsonStringCellEditEvent.getTablePosition();
            String newName = karlsonStringCellEditEvent.getNewValue();
            Karlson karlson = karlsonStringCellEditEvent.getTableView().getItems().get(pos.getRow());
            if(GUIHand.username.equals(karlson.getOwner())){
                karlson.setName(newName);
            }
            else {
                error();
            }
        });

        karlsonSpeed.setOnEditCommit(karlsonLongCellEditEvent -> {
            TablePosition<Karlson, Long> pos = karlsonLongCellEditEvent.getTablePosition();
            Long newSpeed = karlsonLongCellEditEvent.getNewValue();
            Karlson karlson  = karlsonLongCellEditEvent.getTableView().getItems().get(pos.getRow());
            karlson.setFlyspeed(newSpeed);
            if(GUIHand.username.equals(karlson.getOwner())){
                karlson.setFlyspeed(newSpeed);
            }
            else {
                error();
            }
        });
        clothesName.setOnEditCommit(karlsonStringCellEditEvent -> {
            TablePosition<Karlson, String> pos = karlsonStringCellEditEvent.getTablePosition();
            String newName = karlsonStringCellEditEvent.getNewValue();
            Karlson karlson = karlsonStringCellEditEvent.getTableView().getItems().get(pos.getRow());
            if(GUIHand.username.equals(karlson.getOwner())){
                karlson.getClothes().setName(newName);
            }
            else {
                error();

            }

        });
        clothesColor.setOnEditCommit(karlsonStringCellEditEvent -> {
            TablePosition<Karlson, String> pos = karlsonStringCellEditEvent.getTablePosition();
            String newName = karlsonStringCellEditEvent.getNewValue();
            Karlson karlson = karlsonStringCellEditEvent.getTableView().getItems().get(pos.getRow());
            if(GUIHand.username.equals(karlson.getOwner())){
                karlson.getClothes().setColor(newName);
            }
            else {
               error();
            }
        });

        mapView.setOnAction(actionEvent -> {
            try {
                Parent root;
                root = FXMLLoader.load(getClass().getResource("KarlsonWinMap.fxml"));
                Stage stage = (Stage) mainroot.getScene().getWindow();
                GUIHand.changeScene(stage, root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        est.setOnAction(actionEvent -> {
            GUIHand.laguage = "est";
            initialize();
        });
        fra.setOnAction(actionEvent -> {
            GUIHand.laguage = "fra";
            initialize();
        });
        rus.setOnAction(actionEvent -> {
            GUIHand.laguage = "rus";
            initialize();
        });
        his.setOnAction(actionEvent -> {
            GUIHand.laguage = "his";
            try {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Внимание");
                alert.setHeaderText("Язык: Испания(Гондурас)");
                alert.setContentText("А ты Пидорас");
                alert.showAndWait();
            } catch (Exception e){}
            initialize();
        });

        karlsonUserText.setText(GUIHand.username);

        deleteElem.setOnAction(actionEvent -> {
            try {
                Parent root;
                root = FXMLLoader.load(getClass().getResource("KarlsonWinDel.fxml"));
                Stage stage = (Stage) mainroot.getScene().getWindow();
                GUIHand.changeScene(stage, root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        addElem.setOnAction(actionEvent -> {
            try {
                Parent root;
                    root = FXMLLoader.load(getClass().getResource("KarlsonWinAdd.fxml"));
                Stage stage = (Stage) mainroot.getScene().getWindow();
                GUIHand.changeScene(stage, root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        save.setOnAction(actionEvent -> GUIHand.save());

        close.setOnAction(actionEvent -> {
            Stage stage = (Stage) mainroot.getScene().getWindow();
            stage.close();
        });
    }
}
