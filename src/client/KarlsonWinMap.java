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
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
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

    private void applyEffect(Node node) {
        GaussianBlur blur = new GaussianBlur(MIN_RADIUS);
        node.setEffect(blur);
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(blur.radiusProperty(), MAX_RADIUS);
        KeyFrame kf = new KeyFrame(Duration.millis(UI_ANIMATION_TIME_MSEC), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    ArrayList<Karlson> karlsons;
    ArrayList<Shape> shapes = new ArrayList<>();

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
            Circle circle = new Circle();
            Circle knopka = new Circle();
            Line line = new Line();
            Line line1 = new Line();
            double x = karlson.getX();
            double y = karlson.getY();

            line.setStrokeWidth(4);
            line1.setStrokeWidth(4);
            line.setStartX(x*10);
            line.setStartY(y*10 -(Math.log(karlson.getFlyspeed()))*5);
            line1.setStartX(x*10);
            line1.setStartY(y*10 - (Math.log(karlson.getFlyspeed()))*5);
            line.setEndX(x*10 - (Math.log(karlson.getFlyspeed()))*5);
            line.setEndY(y*10 - 2*(Math.log(karlson.getFlyspeed()))*5);
            line1.setEndX(x*10 + (Math.log(karlson.getFlyspeed()))*5);
            line1.setEndY(y*10 - 2*(Math.log(karlson.getFlyspeed()))*5);
            line.setUserData(karlson.getFlyspeed());
            line1.setUserData(karlson.getFlyspeed());

            ellipse.setCenterX(x*10);
            ellipse.setCenterY(y*10);
            knopka.setCenterX(x*10);
            knopka.setCenterY(y*10 + (Math.log(karlson.getFlyspeed()))*5);
            knopka.setRadius((Math.log(karlson.getFlyspeed()))*2);
            knopka.setFill(Color.RED);
            knopka.setUserData(karlson.getFlyspeed());
            circle.setCenterX(x*10 - (Math.log(karlson.getFlyspeed()))*8);
            circle.setCenterY(y*10);
            ellipse.setRadiusX((Math.log(karlson.getFlyspeed()))*8);
            ellipse.setRadiusY((Math.log(karlson.getFlyspeed()))*5);
            circle.setRadius((Math.log(karlson.getFlyspeed()))*5);
            Shape shape = Shape.union(ellipse,circle);
            shape.setFill(javafx.scene.paint.Color.rgb(karlson.getOwnerId()+20,karlson.getOwnerId()+65,karlson.getOwnerId()+65));
            shape.setUserData(karlson.getFlyspeed());
            shape.setStroke(Color.PURPLE);
            shape.setStrokeWidth(3);
            shapes.add(shape);
            mainroot.getChildren().add(shape);
            mainroot.getChildren().add(knopka);
            mainroot.getChildren().add(line);
            mainroot.getChildren().add(line1);
        }


        for (Node shape:mainroot.getChildren()) {
            shape.setOnMouseClicked(mouseEvent -> {
                applyEffect(shape);
                long ln = (Long) shape.getUserData();
                for (Karlson karlson : karlsons) {
                    if (karlson.getFlyspeed() == ln) {
                        karlsonInfo.setText(karlson.getName() + "\n" + karlson.getFlyspeed() + "\n" + karlson.getOwner() + "\n"
                                + karlson.getClothes().getName() + " " + karlson.getClothes().getColor());
                    }
                }
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

