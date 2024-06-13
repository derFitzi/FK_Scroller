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


public class InfinityController {
    @FXML
    private Pane infinityPane;
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

    private Rectangle wall;
    private double wallSpeed = 1;
    private double playerSpeed = 12; // normal 5

    @FXML
    private Slider lautstaerke;
    @FXML
    private Slider sensibilitaet;

    private double sensi = Singleton.getInstance().getSensi();
    private Rectangle triggerBox;
    private Rectangle deathBox;
    private ImageView flagImageView;
    private int collectedCoinCount = 0;
    @FXML
    private Label lautstaerke_text;
    @FXML
    private Label sensibilitaet_text;
    @FXML
    Rectangle settingsBG;
    @FXML
    private Button zumHauptmenue;
    AnimationTimer gameLoop;
    @FXML
    private Button weiter;

    Media sound = new Media(new File("CrazyJoesWorld/src/main/resources/org/example/crazyjoesworld/GamePlay.mp3").toURI().toString());
    MediaPlayer Music = new MediaPlayer(sound);
    int aktuellesLevel=1;
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

        infinityPane.getChildren().addAll(background1, background2);

        player = new Rectangle(70, 120);
        player.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("idle.gif"))));
        player.setTranslateX(750);
        player.setTranslateY(100);
        infinityPane.getChildren().add(player);


        deathBox = new Rectangle(1920, 30, Color.RED);
        deathBox.setStroke(Color.TRANSPARENT);
        deathBox.setTranslateX(0);
        deathBox.setTranslateY(950);
        infinityPane.getChildren().add(deathBox);

        wall = new Rectangle(100, 1080, Color.RED); // adjust size and color as needed
        wall.setTranslateX(0);
        infinityPane.getChildren().add(wall);


        addEventListeners();

        startGame();

        player.toFront();

        quit.toFront();
        Music.play();
        Music.setVolume(Singleton.getInstance().getLautstaerke());
        Music.setCycleCount(MediaPlayer.INDEFINITE);
        lautstaerke.setValue(Singleton.getInstance().getLautstaerke()*200);
        sensibilitaet.setValue(Singleton.getInstance().getSensi()*100);
        player.toFront();
        generateStartPlatforms();

    }

    private void addEventListeners() {
        infinityPane.setOnKeyPressed(this::handleKeyPressed);
        infinityPane.setOnKeyReleased(this::handleKeyReleased);
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






    private void handleKeyPressed(KeyEvent event) {
        if ((event.getCode() == KeyCode.A || event.getCode() == KeyCode.LEFT)) {
            player.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("runLeft.gif"))));
            left = true;
            rechtslaufen = true;
            playerSpeed = 12 * sensi;
        } else if ((event.getCode() == KeyCode.D || event.getCode() == KeyCode.RIGHT)) {
            player.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("runRight.gif"))));
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

    private void generateStartPlatforms()
    {
        platforms = new ArrayList<>();

        Rectangle platform0 = new Rectangle(1442, 135); platform0.setTranslateX(-1000); platform0.setTranslateY(730);
        Rectangle platform1 = new Rectangle(1442, 135); platform1.setTranslateX(150); platform1.setTranslateY(730);
        Rectangle platform2 = new Rectangle(1442, 135); platform2.setTranslateX(1590); platform2.setTranslateY(630);
        Rectangle erde1     = new Rectangle(1442, 440); erde1.setTranslateX(1590); erde1.setTranslateY(760);
        Rectangle erde2     = new Rectangle(1442, 440); erde2.setTranslateX(4400); erde2.setTranslateY(800);
        Rectangle erde3     = new Rectangle(1442, 440); erde3.setTranslateX(5842); erde3.setTranslateY(800);
        Rectangle platform3 = new Rectangle(1442, 135); platform3.setTranslateX(3000); platform3.setTranslateY(780);
        Rectangle platform4 = new Rectangle(721, 67); platform4.setTranslateX(1700); platform4.setTranslateY(430);
        Rectangle platform5 = new Rectangle(1442, 135); platform5.setTranslateX(4400); platform5.setTranslateY(710);
        Rectangle platform6 = new Rectangle(1442, 135); platform6.setTranslateX(5842); platform6.setTranslateY(710);
        Rectangle wand1 = new Rectangle(1442, 1000); wand1.setTranslateX(-1442); wand1.setTranslateY(0);
        Rectangle wand2 = new Rectangle(1442, 1000); wand2.setTranslateX(5842); wand2.setTranslateY(0);
        platforms.add(platform0);
        platforms.add(platform1);
        platforms.add(platform2);
        platforms.add(platform3);
        platforms.add(platform5);
        platforms.add(platform6);

        for (int i = 0; i < platforms.size(); i++) {
            platforms.get(i).setFill(new ImagePattern(new Image(getClass().getResourceAsStream("thinplatformtexture.png"))));
            infinityPane.getChildren().add(platforms.get(i));
        }
        platforms.add(erde1);
        platforms.add(erde2);
        platforms.add(erde3);
        platforms.add(platform4);
        platforms.add(wand1);
        platforms.add(wand2);
        wand1.setFill(Color.TRANSPARENT);
        wand1.setStroke(Color.TRANSPARENT);
        wand2.setFill(Color.TRANSPARENT);
        wand2.setStroke(Color.TRANSPARENT);
        platform4.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("thinplatformtexture.png"))));
        erde1.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("earthtexture.png"))));
        erde2.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("earthtexture.png"))));
        erde3.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("earthtexture.png"))));
        infinityPane.getChildren().add(erde1);
        infinityPane.getChildren().add(erde2);
        infinityPane.getChildren().add(erde3);
        infinityPane.getChildren().add(platform4);
        infinityPane.getChildren().add(wand1);
        infinityPane.getChildren().add(wand2);

        Image flagImage = new Image(getClass().getResourceAsStream("Flagge.png"));
        quit.toFront();
    }

    private void generateNewPlatforms(){
        int zahl1 = (int) (Math.random() * 3) + 1; // random number between 1 and 3
        int zahl2 = (int) (Math.random() * 71); // random number between 0 and 70
        int zahl3 = (int) (Math.random() * 151) - 100; // random number between -100 and 50

        // Adjust the X-coordinate based on the Y-coordinate
        int adjustedZahl1 = zahl1;
        if (zahl2 > 35) { // if the jump is high
            adjustedZahl1 = zahl1 / 2; // reduce the distance of the jump
        }

        Rectangle platform0 = new Rectangle(1442, 135);
        platform0.setTranslateX(adjustedZahl1);
        platform0.setTranslateY(zahl2);
        platforms.add(platform0);
    }

    private void handleKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.A || event.getCode() == KeyCode.LEFT) {
            left = false;
        } else if (event.getCode() == KeyCode.D || event.getCode() == KeyCode.RIGHT) {
            right = false;
        } else if (event.getCode() == KeyCode.SPACE || event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP) {
            jumping = false;
        }
        else
        {
            player.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("idle.gif"))));
        }

    }

    private void update() {
        double dx = 0;
        infinityPane.requestFocus();
        //System.out.println("X: " + player.getTranslateX() + " Y: " + player.getTranslateY() + " Velocity: " + velocity + " Gravity: " + gravity + " PlayerSpeed: " + playerSpeed + " Sensi: " + sensi + " Collected Coins: " + collectedCoinCount + " Linkslaufen: " + linkslaufen + " Rechtslaufen: " + rechtslaufen + " CanJump: " + canJump + " Jumping: " + jumping + " Left: " + left + " Right: " + right + " i: " + i);
        //System.out.print("links frei: "+rechtslaufen+"   rechts frei: "+linkslaufen);

        if (left &&linkslaufen) dx -= playerSpeed;
        if (right &&rechtslaufen) dx += playerSpeed;

        wall.setTranslateX(wall.getTranslateX() + wallSpeed -dx);

        // increase wall speed
        wallSpeed += 0.0001; // adjust as needed




        //player.setTranslateX(player.getTranslateX() + dx);

        triggerBox.setTranslateX(triggerBox.getTranslateX() - dx);

        moveBackground(dx);

        for (Rectangle platform : platforms) {
            platform.setTranslateX(platform.getTranslateX() - dx);
        }


        double screenWidth = infinityPane.getWidth();
        double playerX = player.getTranslateX() + dx;
        double playerHalfWidth = player.getWidth() / 2.0;
        double offset = (screenWidth / 2.0) - (playerX + playerHalfWidth);
        //player.setTranslateX(player.getTranslateX() + offset);

        player.setTranslateY(player.getTranslateY() + velocity);
        velocity += gravity;

        checkCollisions();
        checkBoxTrigger();
        checkDeath();
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
        boolean touchesSide = false;
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
                if (playerBottom >= platformTop && playerTop < platformTop && playerLeft < (platformRight-10) && playerRight > (platformLeft+10)) {
                    player.setTranslateY(platformTop - player.getHeight());
                    velocity = 0;
                    canJump = true;
                    onAnyPlatform = true;
                }
                //unten
                else if (playerTop <= platformBottom && playerBottom > platformBottom && playerLeft < (platformRight-5) && playerRight > (platformLeft+5)) {
                    player.setTranslateY(platformBottom);
                    velocity=gravity;
                }
                // links
                else if (playerRight >= platformLeft && playerLeft < platformLeft && playerBottom > platformTop)
                {
                    rechtslaufen=false;
                    playerSpeed=-0;
                    player.setTranslateX(platformLeft - player.getWidth()-1);
                    //toutchesSide=true;

                }
                // rechts
                else if (playerLeft <= platformRight && playerRight > platformRight && playerBottom > platformTop) {
                    linkslaufen=false;
                    playerSpeed=0;
                    player.setTranslateX(platformRight+1);
                    //toutchesSide=true;
                }
                /*
                Geht nicht, weil es nicht einen Block frühzeitig erkennt mit dem es nicht kollidiert
                // für besseres Movement
                if (playerRight >= platformLeft-20 && playerLeft < platformLeft && playerBottom > platformTop)
                {
                    touchesSide=true;

                }
                // für besseres Movement
                else if (playerLeft <= platformRight+10 && playerRight > platformRight && playerBottom > platformTop) {
                    touchesSide=true;
                }

                 */
            }
        }
        if (!onAnyPlatform) {
            canJump = false;
        }

        if (!touchesSide) {
            // wenn es nicht einen abstand von 1 hat

            System.out.println(" Keine Collision");
            // linkslaufen=true;
            //  rechtslaufen=true;
        }
        else {
            System.out.println("Collision");
        }


    }



    public void zumHauptmenue() {

        Music.stop();
        System.out.println("Hauptmenue");
        infinityPane.setOnKeyPressed(null);
        infinityPane.setOnKeyReleased(null);
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
    private void checkDeath() {
        if (player.getBoundsInParent().intersects(deathBox.getBoundsInParent())) {
            infinityPane.setOnKeyPressed(null);
            infinityPane.setOnKeyReleased(null);
            Music.stop();

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Deathscreen.fxml"));
                Parent DeathScreenRoot = loader.load();

                DeathscreenController deathScreenController = loader.getController();
                deathScreenController.setPlayedGame(aktuellesLevel);
                deathScreenController.CauseOfDeath(1);

                Scene currentScene = quit.getScene();
                currentScene.setRoot(DeathScreenRoot);
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error loading DeathScreen.fxml: " + e.getMessage());
            }
        }
    }

    private void onPlayerEnterBox() {
        infinityPane.setOnKeyPressed(null);
        infinityPane.setOnKeyReleased(null);
        Music.stop();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("WinScreen.fxml"));
            Parent winScreenRoot = loader.load();

            WinScreenController winScreenController = loader.getController();
            winScreenController.setCollectedCoinCount(collectedCoinCount);
            winScreenController.setPlayedGame(aktuellesLevel);

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
        infinityPane.requestFocus();
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

    public Media getSound() {
        return sound;
    }

    public Pane getinfinityPane() {
        return infinityPane;
    }

    public MediaPlayer getMusic() {
        return Music;
    }

    public int getAktuellesLevel() {
        return aktuellesLevel;
    }

    public double getSensi() {
        return sensi;
    }

    public Slider getLautstaerkeSlider() {
        return lautstaerke;
    }

    public Slider getSensibilitaetSlider() {
        return sensibilitaet;
    }

    public boolean isMusicPlaying() {
        return Music.isAutoPlay();
    }

    public Rectangle getPlayer() {
        return player;
    }
}
