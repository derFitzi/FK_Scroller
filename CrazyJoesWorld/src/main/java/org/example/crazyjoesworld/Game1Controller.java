package org.example.crazyjoesworld;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game1Controller {
    @FXML
    private Pane game1_pane;
    @FXML
    private Button quit;
    private Rectangle player;
    private List<Rectangle> platforms;
    private boolean left, right, jumping, canJump;
    private double gravity = 0.6;
    private double velocity = 0;
    private double jumpStrength = -10;
    private double playerSpeed = 5;

    public void initialize() {
        game1_pane.setFocusTraversable(true); // Make sure the Pane can be focused
        game1_pane.requestFocus(); // Request focus for the Pane

        player = new Rectangle(40, 40, Color.RED);
        player.setTranslateX(100);
        player.setTranslateY(100);
        game1_pane.getChildren().add(player);

        platforms = new ArrayList<>();
        generatePlatforms();

        addEventListeners(); // Add event listeners

        startGame(); // Start the game loop
    }

    private void addEventListeners() {
        game1_pane.setOnKeyPressed(this::handleKeyPressed);
        game1_pane.setOnKeyReleased(this::handleKeyReleased);
    }

    private void startGame() {
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        gameLoop.start();

        player.requestFocus();
    }

    private void generatePlatforms() {
        for (int i = 0; i < 10; i++) {
            Rectangle platform = new Rectangle(150, 20, Color.GREEN);
            platform.setTranslateX(150 * i);
            platform.setTranslateY(400);
            platforms.add(platform);
            game1_pane.getChildren().add(platform);
        }
    }

    private void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.A) {
            left = true;
        } else if (event.getCode() == KeyCode.D) {
            right = true;
        } else if (event.getCode() == KeyCode.W && canJump) {
            jumping = true;
            velocity = jumpStrength;
            canJump = false;
        }
    }

    private void handleKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.A) {
            left = false;
        } else if (event.getCode() == KeyCode.D) {
            right = false;
        }
    }

    private void update() {
        double dx = 0;
        if (left) dx -= playerSpeed;
        if (right) dx += playerSpeed;

        // Bewegung des Spielers
        player.setTranslateX(player.getTranslateX() + dx);

        // Anpassung der Plattformen basierend auf der horizontalen Bewegung des Spielers
        for (Rectangle platform : platforms) {
            platform.setTranslateX(platform.getTranslateX() - dx);
        }

        // Anpassung der Spielerposition, um in der Mitte des Bildschirms zu bleiben
        double screenWidth = game1_pane.getWidth();
        double playerX = player.getTranslateX() + dx;
        double playerHalfWidth = player.getWidth() / 2.0;
        double offset = (screenWidth / 2.0) - (playerX + playerHalfWidth);
        player.setTranslateX(player.getTranslateX() + offset);

        // Update der vertikalen Bewegung (Springen)
        player.setTranslateY(player.getTranslateY() + velocity);
        velocity += gravity;

        // Kollisionserkennung und andere Updates...
        checkCollisions();
    }

    private void checkCollisions() {
        boolean onAnyPlatform = false;

        for (Rectangle platform : platforms) {
            if (player.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                double playerBottom = player.getTranslateY() + player.getHeight();
                double platformTop = platform.getTranslateY();

                if (playerBottom >= platformTop) {
                    player.setTranslateY(platformTop - player.getHeight());
                    velocity = 0;
                    canJump = true;
                    onAnyPlatform = true;
                    break;
                }
            }
        }

        if (!onAnyPlatform) {
            canJump = false;
        }
    }

    public void zumHauptmenue() {
        System.out.println("Hauptmenue");
        game1_pane.setOnKeyPressed(null);
        game1_pane.setOnKeyReleased(null);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Menue.fxml"));
            Parent game1Root = loader.load();

            Scene currentScene = quit.getScene();
            currentScene.setRoot(game1Root);
            MainController cont = loader.getController();
            cont.play();

            System.out.println("Switched to Main Menue successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading Game1.fxml: " + e.getMessage());
        }
    }
}
