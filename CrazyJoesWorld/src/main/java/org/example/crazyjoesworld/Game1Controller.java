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
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.File;
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
    private double jumpStrength = -16;
    private double playerSpeed = 12; // normal 5

    private double sensi = Singleton.getInstance().getSensi();
    private Rectangle triggerBox;
    private ImageView flagImageView;
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
    AnimationTimer gameLoop;
    @FXML
    private Button weiter;

    Media sound = new Media(new File("CrazyJoesWorld/src/main/resources/org/example/crazyjoesworld/GamePlay.mp3").toURI().toString());
    MediaPlayer Music = new MediaPlayer(sound);

    int i=0; // testvariable

    private boolean rechtslaufen=true;
    private boolean linkslaufen=true;

    public void initialize() {
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

        triggerBox = new Rectangle(50, 100, Color.TRANSPARENT);
        triggerBox.setStroke(Color.BLUE);
        triggerBox.setTranslateX(5430);
        triggerBox.setTranslateY(630);
        game1_pane.getChildren().add(triggerBox);


        Image flagImage = new Image(getClass().getResourceAsStream("Flagge.png"));
        flagImageView = new ImageView(flagImage);
        flagImageView.setFitWidth(738);
        flagImageView.setFitHeight(338);
        flagImageView.setTranslateX(5000);
        flagImageView.setTranslateY(400);
        game1_pane.getChildren().add(flagImageView);

        platforms = new ArrayList<>();
        generatePlatforms(platformTexture);

        coins = new ArrayList<>();
        generateCoins();

        addEventListeners();

        startGame();

        player.toFront();

        quit.toFront();
        Music.play();
        Music.setVolume(Singleton.getInstance().getLautstaerke());
        Music.setCycleCount(MediaPlayer.INDEFINITE);
        lautstaerke.setValue(Singleton.getInstance().getLautstaerke()*200);
        sensibilitaet.setValue(Singleton.getInstance().getSensi()*100);

    }

    private void addEventListeners() {
        game1_pane.setOnKeyPressed(this::handleKeyPressed);
        game1_pane.setOnKeyReleased(this::handleKeyReleased);
    }

    private void startGame() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        gameLoop.start();

        player.requestFocus();
    }

    private void generatePlatforms(Image platformTexture) {
        Rectangle platform0 = new Rectangle(1442, 135); platform0.setTranslateX(-1000); platform0.setTranslateY(730);
        Rectangle platform1 = new Rectangle(1442, 135); platform1.setTranslateX(150); platform1.setTranslateY(730);
        Rectangle platform2 = new Rectangle(1442, 135); platform2.setTranslateX(1590); platform2.setTranslateY(630);
        Rectangle erde1     = new Rectangle(1442, 135); erde1.setTranslateX(1590); erde1.setTranslateY(760);
        Rectangle platform3 = new Rectangle(1442, 135); platform3.setTranslateX(3000); platform3.setTranslateY(780);
        Rectangle platform4 = new Rectangle(721, 67); platform4.setTranslateX(1700); platform4.setTranslateY(430);
        Rectangle platform5 = new Rectangle(1442, 135); platform5.setTranslateX(4400); platform5.setTranslateY(730);
        Rectangle platform6 = new Rectangle(1442, 135); platform6.setTranslateX(5842); platform6.setTranslateY(730);
        Rectangle wand1 = new Rectangle(1442, 1000); wand1.setTranslateX(-1442); wand1.setTranslateY(0);
        Rectangle wand2 = new Rectangle(1442, 1000); wand2.setTranslateX(5842); wand2.setTranslateY(0);
        platforms.add(platform0);
        platforms.add(platform1);
        platforms.add(platform2);
        platforms.add(platform3);
        platforms.add(platform5);
        platforms.add(platform6);

        for (int i = 0; i < platforms.size(); i++) {
            platforms.get(i).setFill(new ImagePattern(platformTexture));
            game1_pane.getChildren().add(platforms.get(i));
        }
        platforms.add(erde1);
        platforms.add(platform4);
        platforms.add(wand1);
        platforms.add(wand2);
        wand1.setFill(Color.TRANSPARENT);
        wand1.setStroke(Color.ALICEBLUE);
        wand2.setFill(Color.TRANSPARENT);
        wand2.setStroke(Color.ALICEBLUE);
        platform4.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("thinplatformtexture.png"))));
        erde1.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("earthtexture.png"))));
        game1_pane.getChildren().add(erde1);
        game1_pane.getChildren().add(platform4);
        game1_pane.getChildren().add(wand1);
        game1_pane.getChildren().add(wand2);
    }

    private void generateCoins() {
        // Beispielpositionen für die drei Münzen
        Coin coin1 = new Coin(3500, 730, 30);
        Coin coin2 = new Coin(2100, 380, 30);
        Coin coin3 = new Coin(1400, 680, 30);

        coins.add(coin1);
        coins.add(coin2);
        coins.add(coin3);

        game1_pane.getChildren().addAll(coin1, coin2, coin3);
    }

    private void handleKeyPressed(KeyEvent event) {
        if ((event.getCode() == KeyCode.A || event.getCode() == KeyCode.LEFT) && linkslaufen) {
            left = true;
            rechtslaufen = true;
            playerSpeed = 12 * sensi;
        } else if ((event.getCode() == KeyCode.D || event.getCode() == KeyCode.RIGHT) && rechtslaufen) {
            right = true;
            linkslaufen = true;
            playerSpeed = 12 * sensi;
        } else if ((event.getCode() == KeyCode.SPACE || event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP) && canJump) {
            jumping = true;
            velocity = jumpStrength;
            canJump = false;
            linkslaufen = true;
            rechtslaufen = true;
        }
    }

    private void handleKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.A || event.getCode() == KeyCode.LEFT) {
            left = false;
        } else if (event.getCode() == KeyCode.D || event.getCode() == KeyCode.RIGHT) {
            right = false;
        } else if (event.getCode() == KeyCode.SPACE || event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP) {
            jumping = false;
        }
    }

    private void update() {
        game1_pane.requestFocus();

        double dx = 0;
        if (left) dx -= playerSpeed;
        if (right) dx += playerSpeed;

        player.setTranslateX(player.getTranslateX() + dx);

        triggerBox.setTranslateX(triggerBox.getTranslateX() - dx);
        flagImageView.setTranslateX(flagImageView.getTranslateX() - dx);

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
        //System.out.println("Rechts: "+rechtslaufen+"    Links: "+linkslaufen);

        for (Rectangle platform : platforms) {
            if (player.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                double playerBottom = player.getTranslateY() + player.getHeight();
                double playerTop = player.getTranslateY();
                double playerLeft = player.getTranslateX();
                double playerRight = player.getTranslateX() + player.getWidth();

                double platformTop = platform.getTranslateY();
                double platformBottom = platform.getTranslateY() + platform.getHeight();
                double platformLeft = platform.getTranslateX();
                double platformRight = platform.getTranslateX() + platform.getWidth();

                //oben
                if (playerBottom >= platformTop && playerTop < platformTop && playerLeft < platformRight && playerRight > platformLeft) {
                    player.setTranslateY(platformTop - player.getHeight());
                    velocity = 0;
                    canJump = true;
                    onAnyPlatform = true;
                }
                //unten
                else if (playerTop <= platformBottom && playerBottom > platformBottom && playerLeft < platformRight && playerRight > platformLeft) {
                    player.setTranslateY(platformBottom);
                    velocity=gravity;
                }
                // links
                else if (playerRight >= platformLeft && playerLeft < platformLeft) {
                    player.setTranslateX(platformLeft - player.getWidth()+1);
                    //rechtslaufen=false;
                    playerSpeed=0;
                }
                // rechts
                else if (playerLeft <= platformRight && playerRight > platformRight) {
                    player.setTranslateX(platformRight);
                    //linkslaufen=false;
                    playerSpeed=0;
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

        Music.stop();
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

    public void settings() {
        quit.setVisible(false);
        lautstaerke.setVisible(true);
        lautstaerke_text.setVisible(true);
        sensibilitaet.setVisible(true);
        sensibilitaet_text.setVisible(true);
        zumHauptmenue.setVisible(true);
        settingsBG.setVisible(true);
        weiter.setVisible(true);

        settingsBG.toFront();
        lautstaerke_text.toFront();
        lautstaerke.toFront();
        sensibilitaet_text.toFront();
        sensibilitaet.toFront();
        zumHauptmenue.toFront();
        weiter.toFront();
        gameLoop.stop();
    }

    public void weiter() {
        quit.setVisible(true);
        lautstaerke.setVisible(false);
        lautstaerke_text.setVisible(false);
        sensibilitaet.setVisible(false);
        sensibilitaet_text.setVisible(false);
        zumHauptmenue.setVisible(false);
        settingsBG.setVisible(false);
        weiter.setVisible(false);
        game1_pane.requestFocus();
        gameLoop.start();
    }

    public void lautsterkenregelung(){
        Singleton.getInstance().setLautstaerke((lautstaerke.getValue() / 200.0));
        Music.setVolume(Singleton.getInstance().getLautstaerke());
    }

    public void sensibilitaetregelung() {
        Singleton.getInstance().setSensi((sensibilitaet.getValue()/100));
        sensi = Singleton.getInstance().getSensi();
    }

}
