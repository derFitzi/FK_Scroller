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
        oldGameCont = "Game" + num + "Controller";
        oldGameScreen = "Game" + num;
    }

    public void nocheinmal() {
        try {
            FXMLLoader loader;
            if (oldGameScreen.equals("Game1")) {
                loader = new FXMLLoader(getClass().getResource("Game1.fxml"));
            } else if (oldGameScreen.equals("Game2")) {
                loader = new FXMLLoader(getClass().getResource("Game2.fxml"));
            } else if (oldGameScreen.equals("Game3")) {
                loader = new FXMLLoader(getClass().getResource("Game3.fxml"));
            } else if (oldGameScreen.equals("Game4")) {
                loader = new FXMLLoader(getClass().getResource("Game4.fxml"));
            } else {
                loader = new FXMLLoader(getClass().getResource("Menue.fxml"));
            }

            Parent gameRoot = loader.load();
            Scene currentScene = winText.getScene();
            currentScene.setRoot(gameRoot);

            if (oldGameCont.equals("Game1Controller")) {
                Game1Controller cont = loader.getController();
            } else if (oldGameCont.equals("Game2Controller")) {
                // Game2Controller cont = loader.getController();
            } else if (oldGameCont.equals("Game3Controller")) {
                // Game3Controller cont = loader.getController();
            } else if (oldGameCont.equals("Game4Controller")) {
                // Game4Controller cont = loader.getController();
            }

            // Assuming this line is to start some initial process in the main controller
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
