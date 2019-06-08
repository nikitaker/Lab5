package client;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import shared.Karlson;

public class KarlsonWin {


    @FXML
    private MenuItem import1;

    @FXML
    private MenuItem mapView;

    @FXML
    private TableColumn<?, ?> karlsonName;

    @FXML
    private MenuItem est;

    @FXML
    private MenuItem save;

    @FXML
    private MenuItem tableView;

    @FXML
    private MenuItem rus;

    @FXML
    private TableColumn<?, ?> karlsonUsername;

    @FXML
    private MenuItem his;

    @FXML
    private MenuItem addElem;

    @FXML
    private MenuItem fra;

    @FXML
    private TableView<Karlson> karlsonTable;

    @FXML
    private TableColumn<?, ?> karlsonSpeed;

    @FXML
    private TableColumn<?, ?> karlsonDate;

    @FXML
    private MenuItem deleteElem;

    @FXML
    private TableColumn<?, ?> karlsonClothes;

    @FXML
    private MenuItem close;

    @FXML
    private Parent mainroot;

    @FXML
    void initialize(){



        tableView.setOnAction(actionEvent -> {
            GUIHand.show();
        });

        addElem.setOnAction(actionEvent -> {
            try {
                System.out.println("KOK");
                Parent root = FXMLLoader.load(getClass().getResource("KarlsonWinAdd.fxml"));
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
