package org.example.crazyjoesworld;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.IOException;

public class WinScreenController {
    @FXML
    private Pane winScreenPane;
    @FXML
    private Label winText;
    private String oldGameCont;
    private String oldGameScreen;

    private int aktuellesLevel;

    public void setCollectedCoinCount(int count) {
        System.out.println("Number of collected coins: " + count);
        Image starImage = new Image(getClass().getResourceAsStream("Star.png")); // Path to the star image
               for (int i = 0; i < count; i++) {
            ImageView star = new ImageView(starImage);
            star.setFitWidth(70);
            star.setFitHeight(70);
            star.setTranslateX(550 + (205 * i));
            star.setTranslateY(300);
            winScreenPane.getChildren().add(star);
        }
        winText.setText("Du hast " + count + " von 3 Sternen eingesammelt");

        Image hintergrundMainMenue = new Image(getClass().getResourceAsStream("Bild1.png"));
        double width = 1920;
        double height = 1090;
        BackgroundImage backgroundImage = new BackgroundImage(hintergrundMainMenue, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(width, height, false, false, true, true));
        winScreenPane.setBackground(new Background(backgroundImage));

    }

    public void setPlayedGame(int num) {
        aktuellesLevel=num;
    }

    public void nocheinmal() {
        try {
            FXMLLoader loader;
            loader = new FXMLLoader(getClass().getResource("Game1.fxml"));
            Parent gameRoot = loader.load();
            Scene currentScene = winText.getScene();
            currentScene.setRoot(gameRoot);
            Game1Controller cont = loader.getController();
            if (aktuellesLevel==1) {
                cont.levelauswahl(1);
            }
            if (aktuellesLevel==2) {
                cont.levelauswahl(2);
            }
            if (aktuellesLevel==3) {
                cont.levelauswahl(3);
            }
            if (aktuellesLevel==4) {
                cont.levelauswahl(4);
            }


            MainController mainController = loader.getController();
            mainController.play();

            System.out.println("Switched to the game screen successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading FXML: " + e.getMessage());
        }
    }

    public void zurueck() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Menue.fxml"));
            Parent gameRoot = loader.load();

            Scene currentScene = winText.getScene();
            currentScene.setRoot(gameRoot);
            MainController mainController = loader.getController();
            mainController.play();

            System.out.println("Switched to Main Menue successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading Menue.fxml: " + e.getMessage());
        }
    }
}
