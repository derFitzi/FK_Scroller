package org.example.crazyjoesworld;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
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
    private Rectangle background1;
    private Rectangle background2;
    private boolean left, right, jumping, canJump;
    private double gravity = 0.6;
    private double velocity = 0;
    private double jumpStrength = -15;
    private double playerSpeed = 5;
    private Rectangle triggerBox;
    private int collectedCoinCount = 0;
    @FXML
    private Label lautstaerke_text;
    @FXML
    private Label sensibilitaet_text;
    @FXML
    private Slider lautstaerke;
    @FXML
    private Slider sensibilitaet;
    @FXML
    Rectangle settingsBG;
    @FXML
    private Button zumHauptmenue;
    @FXML
    private Button weiter;

    public void initialize() {
        game1_pane.setFocusTraversable(true);
        game1_pane.requestFocus();

        // Erstelle die Hintergrundrechtecke
        Image hintergrundGame1 = new Image(getClass().getResourceAsStream("GameHintergrund2.png"));
        Image platformTexture = new Image(getClass().getResourceAsStream("platformtexture2.png"));

        background1 = new Rectangle(1920, 1080);
        background1.setFill(new ImagePattern(hintergrundGame1));
        background1.setTranslateX(0);
        background2 = new Rectangle(1920, 1080);
        background2.setFill(new ImagePattern(hintergrundGame1));
        background2.setTranslateX(1920);

        game1_pane.getChildren().addAll(background1, background2);

        player = new Rectangle(70, 70, Color.RED);
        player.setTranslateX(100);
        player.setTranslateY(100);
        game1_pane.getChildren().add(player);

        triggerBox = new Rectangle(100, 100, Color.TRANSPARENT);
        triggerBox.setStroke(Color.BLUE);
        triggerBox.setTranslateX(500);
        triggerBox.setTranslateY(300);
        game1_pane.getChildren().add(triggerBox);



        platforms = new ArrayList<>();
        generatePlatforms(platformTexture);

        coins = new ArrayList<>();
        generateCoins();

        addEventListeners();

        startGame();


        quit.toFront();
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
        Rectangle platform1 = new Rectangle(1442, 135); platform1.setTranslateX(150); platform1.setTranslateY(800);
        Rectangle platform2 = new Rectangle(1442, 135); platform2.setTranslateX(1600); platform2.setTranslateY(700);
        Rectangle platform3 = new Rectangle(1442, 135); platform3.setTranslateX(500); platform3.setTranslateY(800);
        Rectangle platform4 = new Rectangle(1442, 135); platform4.setTranslateX(500); platform4.setTranslateY(800);
        Rectangle platform5 = new Rectangle(1442, 135); platform5.setTranslateX(500); platform5.setTranslateY(800);
        Rectangle platform6 = new Rectangle(1442, 135); platform6.setTranslateX(500); platform6.setTranslateY(800);
        Rectangle platform7 = new Rectangle(1442, 135); platform7.setTranslateX(500); platform7.setTranslateY(800);
        platforms.add(platform1);
        platforms.add(platform2);

        for (int i = 0; i < platforms.size(); i++)
        {
            platforms.get(i).setFill(new ImagePattern(platformTexture));
            game1_pane.getChildren().add(platforms.get(i));
        }
        /*
        for (int i = 0; i < 10; i++) {
            Rectangle platform = new Rectangle(474, 45);
            platform.setFill(new ImagePattern(platformTexture)); // Setze die Textur auf die Plattform
            platform.setTranslateX(150 * i);
            platform.setTranslateY(474);
            platforms.add(platform);
            game1_pane.getChildren().add(platform);
        }

         */
    }

    private void generateCoins() {
        // Beispielpositionen für die drei Münzen
        Coin coin1 = new Coin(800, 360, 30); // Erste Münze
        Coin coin2 = new Coin(1000, 360, 30); // Zweite Münze
        Coin coin3 = new Coin(1200, 360, 30); // Dritte Münze

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

        // Move the trigger box
        triggerBox.setTranslateX(triggerBox.getTranslateX() - dx);

        // Move the backgrounds
        moveBackground(dx);

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
        checkBoxTrigger();
    }

    private void moveBackground(double dx) {
        background1.setTranslateX(background1.getTranslateX() - dx);
        background2.setTranslateX(background2.getTranslateX() - dx);

        if (background1.getTranslateX() <= -1920) {
            background1.setTranslateX(background2.getTranslateX() + 1920);
        } else if (background1.getTranslateX() >= 1920) {
            background1.setTranslateX(background2.getTranslateX() - 1920);
        }

        if (background2.getTranslateX() <= -1920) {
            background2.setTranslateX(background1.getTranslateX() + 1920);
        } else if (background2.getTranslateX() >= 1920) {
            background2.setTranslateX(background1.getTranslateX() - 1920);
        }
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
            collectedCoinCount++;
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

    private void checkBoxTrigger() {
        if (player.getBoundsInParent().intersects(triggerBox.getBoundsInParent())) {
            onPlayerEnterBox();
        }
    }

    private void onPlayerEnterBox() {
        game1_pane.setOnKeyPressed(null);
        game1_pane.setOnKeyReleased(null);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("WinScreen.fxml"));
            Parent winScreenRoot = loader.load();

            WinScreenController winScreenController = loader.getController();
            winScreenController.setCollectedCoinCount(collectedCoinCount);
            winScreenController.setPlayedGame(1);
            // Zahl muss bei jedem verschiedenen Spiel geändert werden

            Scene currentScene = quit.getScene();
            currentScene.setRoot(winScreenRoot);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading WinScreen.fxml: " + e.getMessage());
        }
    }

    public void settings()
    {
        quit.setVisible(false);
        lautstaerke.setVisible(true);
        lautstaerke_text.setVisible(true);
        sensibilitaet.setVisible(true);
        sensibilitaet_text.setVisible(true);
        zumHauptmenue.setVisible(true);
        settingsBG.setVisible(true);
    }

}
