package client;

import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import shared.Karlson;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

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

    private static final int UI_ANIMATION_TIME_MSEC = 30000;
    private static final double MIN_RADIUS = 0.0;
    private static final double MAX_RADIUS = 70.0;

    private void moveKarlson(Karlson karlson,Karlson karlson2){
        for (Node node:mainroot.getChildren()){
            if(node.getUserData() != null) {
                if (node.getUserData().equals(karlson.getFlyspeed())) {
                    double x = (karlson.getX() - karlson2.getX())*10;
                    double y = (karlson.getY() - karlson2.getY())*10;
                    Shape shape =(Shape)node;
                    KeyValue k1 = new KeyValue(shape.translateXProperty(), x);
                    KeyValue k2 = new KeyValue(shape.translateYProperty(), y);
                    KeyFrame keyFrame = new KeyFrame(Duration.millis(3000),k1,k2);
                    Timeline timeline = new Timeline(keyFrame);
                    timeline.play();
                    try{
                        Line line = (Line) node;
                        KeyFrame keyFrame1;
                        if(line.getEndX() == karlson2.getX()*10 - (Math.log(karlson2.getFlyspeed()))*4){keyFrame1 = new KeyFrame(Duration.millis(500),new KeyValue(line.endXProperty(), karlson2.getX()*10 + (Math.log(karlson2.getFlyspeed()))*4));}
                        else {keyFrame1 = new KeyFrame(Duration.millis(500),new KeyValue(line.endXProperty(), karlson2.getX()*10 - (Math.log(karlson.getFlyspeed()))*4));}
                        Timeline timeline1 = new Timeline(keyFrame1);
                        timeline1.setCycleCount(6);
                        timeline1.setAutoReverse(true);
                        timeline1.play();
                    }catch (Exception e){}
                }
            }
        }
    }

    ArrayList<Karlson> karlsons;
    ArrayList<Shape> shapes = new ArrayList<>();

    @FXML
    void initialize(){

        GUIHand.show();
        while (GUIHand.storage == null)
        {
        }
        for (Karlson karlson:GUIHand.storage)
        {
            System.out.println(karlson.getName());
            Ellipse ellipse = new Ellipse();
            Circle circle = new Circle();
            Circle knopka = new Circle();
            Line line = new Line();
            Line line1 = new Line();
            Arc hair = new Arc();
            Arc pants = new Arc();
            Circle arm = new Circle();
            Rectangle leg = new Rectangle();
            double x = karlson.getX();
            double y = karlson.getY();

            leg.setX(x*10 + (Math.log(karlson.getFlyspeed()))*6);
            leg.setHeight((Math.log(karlson.getFlyspeed()))*5);
            leg.setWidth((Math.log(karlson.getFlyspeed()))*3);
            leg.setY(y*10);
            leg.setFill(Color.RED);
            leg.setUserData(karlson.getFlyspeed());

            arm.setCenterX(x*10 - (Math.log(karlson.getFlyspeed()))*2);
            arm.setRadius((Math.log(karlson.getFlyspeed()))*1.5);
            arm.setCenterY(y*10);
            arm.setFill(Color.BLANCHEDALMOND);
            arm.setUserData(karlson.getFlyspeed());


            hair.setCenterX(x*10 - (Math.log(karlson.getFlyspeed()))*9);
            hair.setCenterY(y*10);
            hair.setRadiusX((Math.log(karlson.getFlyspeed()))*5);
            hair.setRadiusY((Math.log(karlson.getFlyspeed()))*5);
            hair.setStartAngle(0);
            hair.setLength(150);
            hair.setFill(Color.DARKORANGE);
            hair.setUserData(karlson.getFlyspeed());


            pants.setCenterX(x*10);
            pants.setCenterY(y*10);
            pants.setRadiusX((Math.log(karlson.getFlyspeed()))*8);
            pants.setRadiusY((Math.log(karlson.getFlyspeed()))*5);
            pants.setStartAngle(-80);
            pants.setLength(160);
            pants.setFill(Color.SNOW);
            pants.setUserData(karlson.getFlyspeed());

            line.setStrokeWidth((Math.log(karlson.getFlyspeed())*2));
            line1.setStrokeWidth((Math.log(karlson.getFlyspeed())*2));
            line.setStroke(Color.RED);
            line1.setStroke(Color.RED);
            line.setStartX(x*10);
            line.setStartY(y*10 -(Math.log(karlson.getFlyspeed()))*4);
            line1.setStartX(x*10);
            line1.setStartY(y*10 - (Math.log(karlson.getFlyspeed()))*4);
            line.setEndX(x*10 - (Math.log(karlson.getFlyspeed()))*4);
            line.setEndY(y*10 - 2*(Math.log(karlson.getFlyspeed()))*4);
            line1.setEndX(x*10 + (Math.log(karlson.getFlyspeed()))*4);
            line1.setEndY(y*10 - 2*(Math.log(karlson.getFlyspeed()))*4);
            line.setUserData(karlson.getFlyspeed());
            line1.setUserData(karlson.getFlyspeed());

            ellipse.setCenterX(x*10);
            ellipse.setCenterY(y*10);
            knopka.setCenterX(x*10);
            knopka.setCenterY(y*10 + (Math.log(karlson.getFlyspeed()))*5);
            knopka.setRadius((Math.log(karlson.getFlyspeed()))*1.5);
            knopka.setFill(Color.RED);
            knopka.setUserData(karlson.getFlyspeed());
            circle.setCenterX(x*10 - (Math.log(karlson.getFlyspeed()))*9);
            circle.setCenterY(y*10);
            ellipse.setRadiusX((Math.log(karlson.getFlyspeed()))*8);
            ellipse.setRadiusY((Math.log(karlson.getFlyspeed()))*5);
            circle.setRadius((Math.log(karlson.getFlyspeed()))*5);
            ellipse.setFill(javafx.scene.paint.Color.rgb(karlson.getOwnerId()+20,karlson.getOwnerId()+65,karlson.getOwnerId()+30));
            ellipse.setUserData(karlson.getFlyspeed());
            ellipse.setStroke(Color.GRAY);
            ellipse.setStrokeWidth(3);
            circle.setFill(Color.BLANCHEDALMOND);
            circle.setStroke(Color.BLACK);
            circle.setUserData(karlson.getFlyspeed());
            mainroot.getChildren().add(line);
            mainroot.getChildren().add(line1);
            mainroot.getChildren().add(ellipse);
            mainroot.getChildren().add(pants);
            mainroot.getChildren().add(circle);
            mainroot.getChildren().add(hair);
            mainroot.getChildren().add(knopka);
            mainroot.getChildren().add(arm);
            mainroot.getChildren().add(leg);
        }

        mainroot.setOnMouseClicked(mouseEvent -> {
        for (Karlson karlson : GUIHand.storage) {
            long ln1 = karlson.getFlyspeed();
            for (Karlson karlson2: GUIHand.memoryStorage) {
                long ln2 = karlson2.getFlyspeed();
                if(ln1 == ln2){
                    if(!karlson.getX().equals(karlson2.getX()) || !karlson.getY().equals(karlson2.getY())){
                        moveKarlson(karlson,karlson2);}

                }
            }
        }
        if(GUIHand.storage.size() > GUIHand.memoryStorage.size()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Был добавлен карлсон");
            alert.showAndWait();
            Karlson karlson2;
            Karlson karlson = new Karlson("hih",123L);
            karlson2 = karlson;
            karlson.setX(0);
            karlson.setY(0);
            karlson2.setX(500);
            karlson2.setY(500);
            moveKarlson(karlson,karlson2);
            initialize();
        }
        if(GUIHand.storage.size() < GUIHand.memoryStorage.size()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Был удален карлсон");
            alert.showAndWait();
        }});


        for (Node shape:mainroot.getChildren()) {
            shape.setOnMouseClicked(mouseEvent -> {
                if (shape.getUserData() != null){
                long ln = (Long) shape.getUserData();
                for (Karlson karlson : GUIHand.storage) {
                    if (karlson.getFlyspeed() == ln) {
                        karlsonInfo.setText(karlson.getName() + "\n" + karlson.getFlyspeed() + "\n" + karlson.getOwner() + "\n"
                                + karlson.getClothes().getName() + " " + karlson.getClothes().getColor());
                    }
                }}
            });
        }


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

        karlsonUserText.setText(GUIHand.username);

        deleteElem.setOnAction(actionEvent -> {
            try { Parent root = FXMLLoader.load(getClass().getResource("KarlsonWinDel.fxml"));
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

        addElem.setOnAction(actionEvent -> {
            try {
                Parent root;
                    root = FXMLLoader.load(getClass().getResource("KarlsonWinAdd.fxml"));
                Stage stage = (Stage)mainroot.getScene().getWindow();
                GUIHand.changeScene(stage,root);
            }catch (Exception e){e.printStackTrace();}
        });

        mapView.setOnAction(actionEvent -> {
            try {
                Parent root;
                    root = FXMLLoader.load(getClass().getResource("KarlsonWinMap.fxml"));
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

        save.setOnAction(actionEvent -> GUIHand.save());

        close.setOnAction(actionEvent -> {
            Stage stage = (Stage)mainroot.getScene().getWindow();
            stage.close();});
    }

}

