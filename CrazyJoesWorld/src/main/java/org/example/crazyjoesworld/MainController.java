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
import javafx.scene.paint.ImagePattern;
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
    private Label T1;
    @FXML
    private Label T2;
    @FXML
    private Label T3;
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

    Media sound1 = new Media(new File("CrazyJoesWorld/src/main/resources/org/example/crazyjoesworld/MainMenueSound.mp3").toURI().toString());
    MediaPlayer mainMenueMusic = new MediaPlayer(sound1);
    Media sound2 = new Media(new File("CrazyJoesWorld/src/main/resources/org/example/crazyjoesworld/GameSound.mp3").toURI().toString());
    MediaPlayer gameMusic = new MediaPlayer(sound2);

    public void play() {
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
        gameMusic.setVolume(Singleton.getInstance().getLautstaerke());

        Image hintergrundGameSelection = new Image(getClass().getResourceAsStream("Ground.png"));
        double width = 1920;
        double height = 1080;
        BackgroundImage backgroundImage = new BackgroundImage(hintergrundGameSelection, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(width, height, false, false, true, true));
        p_Pane.setBackground(new Background(backgroundImage));


        Image g1Image = new Image(getClass().getResourceAsStream("Game1vorschau.png"));
        Image g2Image = new Image(getClass().getResourceAsStream("Game2vorschau.png"));
        Image g3Image = new Image(getClass().getResourceAsStream("Game3vorschau.png"));
        Image g4Image = new Image(getClass().getResourceAsStream("Game4vorschau.png"));
        Image g5Image = new Image(getClass().getResourceAsStream("Infinityvorschau.png"));
        g1.setFill(new ImagePattern(g1Image));
        g2.setFill(new ImagePattern(g2Image));
        g3.setFill(new ImagePattern(g3Image));
        g4.setFill(new ImagePattern(g4Image));
        g5.setFill(new ImagePattern(g5Image));
    }

    public void quit() {
        Platform.exit();
    }

    public void settings() {
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
        T1.setVisible(true);
        T2.setVisible(true);
        T3.setVisible(true);
    }

    public void zumHauptmenue() {
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
        T1.setVisible(false);
        T2.setVisible(false);
        T3.setVisible(false);

        Image hintergrundMainMenue = new Image(getClass().getResourceAsStream("Bild1.png"));
        double width = 1920;
        double height = 1090;
        BackgroundImage backgroundImage = new BackgroundImage(hintergrundMainMenue, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(width, height, false, false, true, true));
        p_Pane.setBackground(new Background(backgroundImage));

        gameMusic.stop();
        mainMenueMusic.play();
        mainMenueMusic.setVolume(Singleton.getInstance().getLautstaerke());
    }

    public void lautsterkenregelung() {
        Singleton.getInstance().setLautstaerke((lautstaerke.getValue() / 200.0));
        mainMenueMusic.setVolume(Singleton.getInstance().getLautstaerke());
        gameMusic.setVolume(Singleton.getInstance().getLautstaerke());
        System.out.println(Singleton.getInstance().getLautstaerke());
    }
    public void sensibilitaetregelung() {
        Singleton.getInstance().setSensi((sensibilitaet.getValue()/100));
        System.out.println("Sens Menuü"+Singleton.getInstance().getSensi());
    }


    public void game1() {
        gameMusic.stop();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Game1.fxml"));
            Parent game1Root = loader.load();

            Scene currentScene = play.getScene();
            currentScene.setRoot(game1Root);

            Game1Controller game1Controller = loader.getController();
            game1Controller.levelauswahl(1);

            System.out.println("Switched to game screen successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading Game1.fxml: " + e.getMessage());
        }
    }
    public void game2() {
        gameMusic.stop();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Game1.fxml"));
            Parent game1Root = loader.load();

            Scene currentScene = play.getScene();
            currentScene.setRoot(game1Root);

            Game1Controller game1Controller = loader.getController();
            game1Controller.levelauswahl(2);

            System.out.println("Switched to game screen successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading Game1.fxml: " + e.getMessage());
        }
    }
    public void game3() {
        gameMusic.stop();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Game1.fxml"));
            Parent game1Root = loader.load();

            Scene currentScene = play.getScene();
            currentScene.setRoot(game1Root);

            Game1Controller game1Controller = loader.getController();
            game1Controller.levelauswahl(3);

            System.out.println("Switched to game screen successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading Game1.fxml: " + e.getMessage());
        }
    }
    public void game4() {
        gameMusic.stop();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Game1.fxml"));
            Parent game1Root = loader.load();

            Scene currentScene = play.getScene();
            currentScene.setRoot(game1Root);

            Game1Controller game1Controller = loader.getController();
            game1Controller.levelauswahl(4);

            System.out.println("Switched to game screen successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading Game1.fxml: " + e.getMessage());
        }
    }
    public void infinity() {
        gameMusic.stop();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Infinity.fxml"));
            Parent game1Root = loader.load();

            Scene currentScene = play.getScene();
            currentScene.setRoot(game1Root);

            InfinityController infinityController = loader.getController();

            System.out.println("Switched to game screen successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading Game1.fxml: " + e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        zumHauptmenue();

        // Set looping for media players
        mainMenueMusic.setCycleCount(MediaPlayer.INDEFINITE);
        gameMusic.setCycleCount(MediaPlayer.INDEFINITE);
        lautstaerke.setValue(Singleton.getInstance().getLautstaerke()*200);
        sensibilitaet.setValue(Singleton.getInstance().getSensi()*100);
    }

    @Override
    public void handle(long l) {
        // Handle animation frame updates
    }

    public Button getPlay() {
        return play;
    }

    public Button getQuit() {
        return quit;
    }

    public Button getSettings() {
        return settings;
    }

    public Button getZumHauptmenue() {
        return zumHauptmenue;
    }

    public Button getZumHauptmenueGameSelector() {
        return zumHauptmenueGameSelector;
    }

    public Slider getLautstaerke() {
        return lautstaerke;
    }

    public Slider getSensibilitaet() {
        return sensibilitaet;
    }

    public Pane getP_Pane() {
        return p_Pane;
    }

    public Pane getP_game1() {
        return p_game1;
    }

    public Rectangle getG1() {
        return g1;
    }

    public Media getSound1() {
        return sound1;
    }

    public MediaPlayer getMainMenueMusic() {
        return mainMenueMusic;
    }

    public Media getSound2() {
        return sound2;
    }

    public MediaPlayer getGameMusic() {
        return gameMusic;
    }
}
