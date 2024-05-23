package org.example.crazyjoesworld;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

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

        // Debug: Check if the image is loaded correctly
        if (starImage.isError()) {
            System.err.println("Error loading the star image.");
            return;
        }
        System.out.println("Star image loaded successfully.");

        for (int i = 0; i < count; i++) {
            ImageView star = new ImageView(starImage);
            star.setFitWidth(50); // Width of the star image
            star.setFitHeight(50); // Height of the star image
            star.setTranslateX(690 + (205 * i)); // Position of the star
            star.setTranslateY(300); // Position of the star

            // Debug: Check the star's position and size
            System.out.println("Star " + (i + 1) + " position: (" + star.getTranslateX() + ", " + star.getTranslateY() + ")");
            System.out.println("Star " + (i + 1) + " size: (" + star.getFitWidth() + ", " + star.getFitHeight() + ")");

            winScreenPane.getChildren().add(star);
        }
        winText.setText("Du hast " + count + " von 3 Sternen eingesammelt");
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
