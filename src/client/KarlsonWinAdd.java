package client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import shared.Karlson;

import java.util.Locale;
import java.util.ResourceBundle;

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
        nameFiels.setPromptText(bundle.getString("table_name"));
        speedField.setPromptText(bundle.getString("table_speed"));
        colourField.setPromptText(bundle.getString("table_color"));
        clothesField.setPromptText(bundle.getString("table_clothes"));
        addButton.setText(bundle.getString("menu_add"));
        addMaxButton.setText(bundle.getString("menu_add_max"));
        addMinButtin.setText(bundle.getString("menu_add_min"));






        addButton.setOnAction(actionEvent -> {
            String s = "add {\""+ speedField.getText()+ "\":{\"name\":\""+nameFiels.getText()+"\",\"flyspeed\":\"" +
                    speedField.getText()+"\",\"clothes\":{\"name\":\""+ clothesField.getText() + "\",\"color\":\""+
                    colourField.getText()+"\"}}}";
            GUIHand.add(s);
        });



        karlsonUserText.setText(GUIHand.username);

        deleteElem.setOnAction(actionEvent -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("KarlsonWinDel.fxml"));
                Stage stage = (Stage)mainroot.getScene().getWindow();
                GUIHand.changeScene(stage,root);
            }catch (Exception e){e.printStackTrace();}
        });

        addElem.setOnAction(actionEvent -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("KarlsonWinAdd.fxml"));
                Stage stage = (Stage)mainroot.getScene().getWindow();
                GUIHand.changeScene(stage,root);
            }catch (Exception e){e.printStackTrace();}
        });

        mapView.setOnAction(actionEvent -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("KarlsonWinMap.fxml"));
                Stage stage = (Stage)mainroot.getScene().getWindow();
                GUIHand.changeScene(stage,root);
            }catch (Exception e){e.printStackTrace();}
        });


        tableView.setOnAction(actionEvent -> {
            GUIHand.show();
            try {
                Parent root;
                root = FXMLLoader.load(getClass().getResource("KarlsonWin.fxml"));
                Stage stage = (Stage)mainroot.getScene().getWindow();
                GUIHand.changeScene(stage,root);
            }catch (Exception e){e.printStackTrace();}
        });
        import1.setOnAction(actionEvent -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("import");
            dialog.setHeaderText("Enter path to JSON file.");
            dialog.showAndWait();
            GUIHand.import1(dialog.getEditor().getText());
        });

        save.setOnAction(actionEvent -> GUIHand.save());

        close.setOnAction(actionEvent -> {
            Stage stage = (Stage)mainroot.getScene().getWindow();
            stage.close();});
    }

}
