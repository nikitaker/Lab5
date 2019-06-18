package client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

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











        est.setOnAction(actionEvent -> {
            GUIHand.laguage = "est";
        });
        fra.setOnAction(actionEvent -> {
            GUIHand.laguage = "fra";
        });
        rus.setOnAction(actionEvent -> {
            GUIHand.laguage = "rus";
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
