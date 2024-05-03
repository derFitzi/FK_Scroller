package org.example.crazyjoesworld;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.shape.Rectangle;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController extends AnimationTimer implements Initializable {
    @FXML
    private Button play;
    @FXML
    private Button quit;
    @FXML
    private Button settings;
    @FXML
    private Button zumHauptmenue;
    @FXML
    private Button zumHauptmenueGameSelector;
    @FXML
    private Label lautstaerke_text;
    @FXML
    private Label sensibilitaet_text;
    @FXML
    private Label t1;
    @FXML
    private Label t2;
    @FXML
    private Label t3;
    @FXML
    private Label t4;
    @FXML
    private Label t5;
    @FXML
    private Slider lautstaerke;
    @FXML
    private Slider sensibilitaet;
    @FXML
    Pane p_Pane;
    @FXML
    Rectangle settingsBG;
    @FXML
    Rectangle g1;
    @FXML
    Rectangle g2;
    @FXML
    Rectangle g3;
    @FXML
    Rectangle g4;
    @FXML
    Rectangle g5;


    public void play()
    {
        play.setVisible(false);
        quit.setVisible(false);
        settings.setVisible(false);
        zumHauptmenueGameSelector.setVisible(true);
        g1.setVisible(true);
        g2.setVisible(true);
        g3.setVisible(true);
        g4.setVisible(true);
        g5.setVisible(true);
        t1.setVisible(true);
        t2.setVisible(true);
        t3.setVisible(true);
        t4.setVisible(true);
        t5.setVisible(true);
        Image hintergrundMainMenue = new Image(getClass().getResourceAsStream("/org/example/crazyjoesworld/GameSelection.png"));
        double width = 1920;
        double height = 1080;


        BackgroundImage backgroundImage = new BackgroundImage(hintergrundMainMenue, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(width, height, false, false, true, true));
        p_Pane.setBackground(new Background(backgroundImage));
    }
    public void quit() {
        Platform.exit();
    }
    public void settings()
    {
        play.setVisible(false);
        quit.setVisible(false);
        settings.setVisible(false);
        lautstaerke.setVisible(true);
        lautstaerke_text.setVisible(true);
        sensibilitaet.setVisible(true);
        sensibilitaet_text.setVisible(true);
        zumHauptmenue.setVisible(true);
        settingsBG.setVisible(true);
    }
    public void zumHauptmenue()
    {
        lautstaerke.setVisible(false);
        lautstaerke_text.setVisible(false);
        sensibilitaet.setVisible(false);
        sensibilitaet_text.setVisible(false);
        zumHauptmenue.setVisible(false);
        settingsBG.setVisible(false);
        zumHauptmenueGameSelector.setVisible(false);
        g1.setVisible(false);
        g2.setVisible(false);
        g3.setVisible(false);
        g4.setVisible(false);
        g5.setVisible(false);
        t1.setVisible(false);
        t2.setVisible(false);
        t3.setVisible(false);
        t4.setVisible(false);
        t5.setVisible(false);
        play.setVisible(true);
        quit.setVisible(true);
        settings.setVisible(true);
        Image hintergrundMainMenue = new Image(getClass().getResourceAsStream("/org/example/crazyjoesworld/Bild1.png"));
        double width = 1920;
        double height = 1080;


        BackgroundImage backgroundImage = new BackgroundImage(hintergrundMainMenue, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(width, height, false, false, true, true));
        p_Pane.setBackground(new Background(backgroundImage));

    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        zumHauptmenue();
    }

    @Override
    public void handle(long l) {

    }
}