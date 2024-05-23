package org.example.crazyjoesworld;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
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
    private List<Coin> coins;
    private Rectangle background; // Hintergrundrechteck
    private boolean left, right, jumping, canJump;
    private double gravity = 0.6;
    private double velocity = 0;
    private double jumpStrength = -10;
    private double playerSpeed = 5;

    public void initialize() {
        game1_pane.setFocusTraversable(true);
        game1_pane.requestFocus();

        // Erstelle das Hintergrundrechteck
        Image hintergrundGame1 = new Image(getClass().getResourceAsStream("platformtexture.jpg"));
        background = new Rectangle(1920, 1080);
        background.setFill(new ImagePattern(hintergrundGame1));
        game1_pane.getChildren().add(background);

        player = new Rectangle(40, 40, Color.RED);
        player.setTranslateX(100);
        player.setTranslateY(100);
        game1_pane.getChildren().add(player);

        platforms = new ArrayList<>();
        generatePlatforms(hintergrundGame1);

        coins = new ArrayList<>();
        generateCoins();

        addEventListeners();

        startGame();
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

    private void generatePlatforms(Image platformTexture) {
        for (int i = 0; i < 10; i++) {
            Rectangle platform = new Rectangle(150, 20);
            platform.setFill(new ImagePattern(platformTexture)); // Setze die Textur auf die Plattform
            platform.setTranslateX(150 * i);
            platform.setTranslateY(400);
            platforms.add(platform);
            game1_pane.getChildren().add(platform);
        }
    }

    private void generateCoins() {
        // Beispielpositionen für die drei Münzen
        Coin coin1 = new Coin(200, 360, 30); // Erste Münze
        Coin coin2 = new Coin(300, 360, 30); // Zweite Münze
        Coin coin3 = new Coin(400, 360, 30); // Dritte Münze

        coins.add(coin1);
        coins.add(coin2);
        coins.add(coin3);

        game1_pane.getChildren().addAll(coin1, coin2, coin3);
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

        player.setTranslateX(player.getTranslateX() + dx);

        // Bewege den Hintergrund
        background.setTranslateX(background.getTranslateX() - dx);

        for (Rectangle platform : platforms) {
            platform.setTranslateX(platform.getTranslateX() - dx);
        }

        for (Coin coin : coins) {
            coin.setTranslateX(coin.getTranslateX() - dx);
        }

        double screenWidth = game1_pane.getWidth();
        double playerX = player.getTranslateX() + dx;
        double playerHalfWidth = player.getWidth() / 2.0;
        double offset = (screenWidth / 2.0) - (playerX + playerHalfWidth);
        player.setTranslateX(player.getTranslateX() + offset);

        player.setTranslateY(player.getTranslateY() + velocity);
        velocity += gravity;

        checkCollisions();
        checkCoinCollisions();
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

    private void checkCoinCollisions() {
        List<Coin> collectedCoins = new ArrayList<>();
        for (Coin coin : coins) {
            if (player.getBoundsInParent().intersects(coin.getBoundsInParent())) {
                collectedCoins.add(coin);
            }
        }
        for (Coin coin : collectedCoins) {
            coins.remove(coin);
            game1_pane.getChildren().remove(coin);
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
