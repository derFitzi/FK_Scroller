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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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
    private Label lautstaerke_text;
    @FXML
    private Label sensibilitaet_text;
    @FXML
    private Slider lautstaerke;
    @FXML
    private Slider sensibilitaet;
    Image image = new Image("file:CrazyJoesWorld/src/main/resources/org/example/crazyjoesworld/Bild1.png");
    @FXML
    ImageView i1= new ImageView(image);
    @FXML
    Pane p_Pane;


    public void play()
    {
        play.setVisible(false);
        quit.setVisible(false);
        settings.setVisible(false);
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
    }
    public void zumHauptmenue()
    {
        lautstaerke.setVisible(false);
        lautstaerke_text.setVisible(false);
        sensibilitaet.setVisible(false);
        sensibilitaet_text.setVisible(false);
        zumHauptmenue.setVisible(false);
        play.setVisible(true);
        quit.setVisible(true);
        settings.setVisible(true);
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        //p_Pane.getChildren().add(i1);
    }

    @Override
    public void handle(long l) {

    }
}