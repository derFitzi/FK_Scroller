package org.example.crazyjoesworld;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.shape.Rectangle;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController extends AnimationTimer implements Initializable {
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
    private Label mainMenueName;
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
    Pane p_game1;
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
    private Stage stage;
    private Scene scene;
    private Parent root;

    public Games game;

    Media sound1 = new Media(new File("CrazyJoesWorld/src/main/resources/org/example/crazyjoesworld/MainMenueSound.mp3").toURI().toString());
    MediaPlayer mainMenueMusic = new MediaPlayer(sound1);
    Media sound2 = new Media(new File("CrazyJoesWorld/src/main/resources/org/example/crazyjoesworld/GameSound.mp3").toURI().toString());
    MediaPlayer gameMusic = new MediaPlayer(sound2);


    public void play()
    {
        play.setVisible(false);
        quit.setVisible(false);
        settings.setVisible(false);
        zumHauptmenueGameSelector.setVisible(true);
        mainMenueName.setVisible(false);
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

        mainMenueMusic.stop();
        gameMusic.play();

        Image hintergrundGameSelection = new Image(getClass().getResourceAsStream("org/example/crazyjoesworld/Ground.png"));
        double width = 1920;
        double height = 1090;
        BackgroundImage backgroundImage = new BackgroundImage(hintergrundGameSelection, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(width, height, false, false, true, true));
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
        mainMenueName.setVisible(false);
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
        mainMenueName.setVisible(true);
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
        double height = 1090;
        BackgroundImage backgroundImage = new BackgroundImage(hintergrundMainMenue, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(width, height, false, false, true, true));
        p_Pane.setBackground(new Background(backgroundImage));

        gameMusic.stop();
        mainMenueMusic.play();

    }

    public void lautsterkenregelung()
    {
        mainMenueMusic.setVolume(lautstaerke.getValue() / 200.0);
        gameMusic.setVolume(lautstaerke.getValue() / 200.0);
        // weitere Songs hier hinzufügen
    }
    public void game1() {
        gameMusic.stop();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Game1.fxml"));
            Parent game1Root = loader.load();

            Scene currentScene = play.getScene();
            currentScene.setRoot(game1Root);

            Game1Controller game1Controller = loader.getController();

            System.out.println("Switched to game screen successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading Game1.fxml: " + e.getMessage());
        }
    }







    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        zumHauptmenue();
    }

    @Override
    public void handle(long l) {

    }


}