package org.example.crazyjoesworld;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


import java.io.File;
import java.io.IOException;

public class DeathscreenController {

    private int aktuellesLevel;
    @FXML
    Button nocheinmal;
    @FXML
    Pane deathscreenPane;
    MediaPlayer Music;
    Media sound1 = new Media(new File("CrazyJoesWorld/src/main/resources/org/example/crazyjoesworld/fall.mp3").toURI().toString());

    public void setPlayedGame(int num) {
        aktuellesLevel=num;

        Image hintergrundMainMenue = new Image(getClass().getResourceAsStream("Bild2.png"));
        double width = 1920;
        double height = 1090;
        BackgroundImage backgroundImage = new BackgroundImage(hintergrundMainMenue, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(width, height, false, false, true, true));
        deathscreenPane.setBackground(new Background(backgroundImage));
    }

    public void nocheinmal() {
        Music.stop();
        Music.pause();
        try {
            FXMLLoader loader;
            Parent gameRoot;
            Scene currentScene = nocheinmal.getScene();
            MainController mainController;
            if (aktuellesLevel == 5) {
                loader = new FXMLLoader(getClass().getResource("Infinity.fxml"));
                gameRoot = loader.load();
                currentScene.setRoot(gameRoot);
                InfinityController contI = loader.getController();
                mainController = loader.getController();
                mainController.play();
            } else {
                loader = new FXMLLoader(getClass().getResource("Game1.fxml"));
                gameRoot = loader.load();
                currentScene.setRoot(gameRoot);
                Game1Controller cont = loader.getController();
                if (aktuellesLevel == 1) {
                    cont.levelauswahl(1);
                } else if (aktuellesLevel == 2) {
                    cont.levelauswahl(2);
                } else if (aktuellesLevel == 3) {
                    cont.levelauswahl(3);
                } else if (aktuellesLevel == 4) {
                    cont.levelauswahl(4);
                }
                mainController = loader.getController();
                mainController.play();
            }
            System.out.println("Switched to the game screen successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading FXML: " + e.getMessage());
        }
    }

    public void zurueck() {
        Music.stop();
        Music.pause();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Menue.fxml"));
            Parent gameRoot = loader.load();

            Scene currentScene = nocheinmal.getScene();
            currentScene.setRoot(gameRoot);
            MainController mainController = loader.getController();
            mainController.play();

            System.out.println("Switched to Main Menue successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading Menue.fxml: " + e.getMessage());
        }
    }

    public void CauseOfDeath(int zahl)
    {
        if (zahl==1)
        {
            Music = new MediaPlayer(sound1);
            Music.play();
            Music.setVolume(Singleton.getInstance().getLautstaerke());
        }
    }
}
