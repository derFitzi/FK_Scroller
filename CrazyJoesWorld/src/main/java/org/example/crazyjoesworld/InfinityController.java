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
import java.util.Random;


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
    private double playerSpeed = 12;
    private double dxA;
    private int pjc;
    private double speedValue = 12;
    private long t = 0;
    private Rectangle atk = new Rectangle(200, 200);;
    private boolean atkA;
    private Rectangle wall;
    private double wallSpeed = 7;

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

    private boolean gewechselt =false;

    Media sound = new Media(new File("CrazyJoesWorld/src/main/resources/org/example/crazyjoesworld/GamePlay.mp3").toURI().toString());
    MediaPlayer Music = new MediaPlayer(sound);
    int aktuellesLevel=1;
    int i=0; // testvariable
    private boolean rechtslaufen=true;
    private boolean linkslaufen=true;
    private boolean gifRightrunning =false;
    private boolean gifLefrrunning =false;
    private boolean gifIdle =false;
    private boolean gifJumping= false;



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

        wall = new Rectangle(100, 1080, Color.RED);
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
            left = true;
            rechtslaufen = true;
            playerSpeed = speedValue * sensi;
        }
        if ((event.getCode() == KeyCode.D || event.getCode() == KeyCode.RIGHT)) {
            right = true;
            linkslaufen = true;
            playerSpeed = speedValue * sensi;
        }
        if ((event.getCode() == KeyCode.SPACE || event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP) && canJump) {
            jumping = true;
            velocity = jumpStrength;
            canJump = false;
            linkslaufen = true;
            rechtslaufen = true;
        }
        if (event.getCode() == KeyCode.E){
            playerAttack(true);
        }
        if (event.getCode() == KeyCode.SHIFT){
            speedValue = 5;
        }
    }

    private void generateStartPlatforms()
    {
        platforms = new ArrayList<>();
        Rectangle platformminus6 = new Rectangle(10, 10); platformminus6.setTranslateX(-2582); platformminus6.setTranslateY(730);
        Rectangle platformminus5 = new Rectangle(10, 10); platformminus5.setTranslateX(-2572); platformminus5.setTranslateY(730);
        Rectangle platformminus4 = new Rectangle(10, 10); platformminus4.setTranslateX(-2562); platformminus4.setTranslateY(730);
        Rectangle platformminus3 = new Rectangle(10, 10); platformminus3.setTranslateX(-2552); platformminus3.setTranslateY(730);
        Rectangle platformminus2 = new Rectangle(10, 10); platformminus2.setTranslateX(-2542); platformminus2.setTranslateY(730);
        Rectangle platformminus1 = new Rectangle(1442, 135); platformminus1.setTranslateX(-2442); platformminus1.setTranslateY(730);
        Rectangle platform0 = new Rectangle(1442, 135); platform0.setTranslateX(-1000); platform0.setTranslateY(730);
        Rectangle platform1 = new Rectangle(1442, 135); platform1.setTranslateX(150); platform1.setTranslateY(730);

        platforms.add(platformminus6);
        platforms.add(platformminus5);
        platforms.add(platformminus4);
        platforms.add(platformminus3);
        platforms.add(platformminus2);
        platforms.add(platformminus1);
        platforms.add(platform0);
        platforms.add(platform1);


        for (int i = 0; i < platforms.size(); i++) {
            platforms.get(i).setFill(new ImagePattern(new Image(getClass().getResourceAsStream("thinplatformtextureO.png"))));
            infinityPane.getChildren().add(platforms.get(i));
        }
        quit.toFront();
    }

    private void generateNewPlatforms(){
        System.out.println("Generated new platform");

        Rectangle platformNeu;
        Rectangle erdeNeu;
        int zahl1 = (int) (Math.random() * 3)+1;
        int zahl2 = (int) (Math.random() * 50);
        int zahl3 = (int) (Math.random() * 120)+50;
        int zahl4 = (int) (Math.random() * 2+1);


        if (zahl4==2) {
            zahl3=zahl3*-1;
        }



        if (zahl1==1) {
            platformNeu = new Rectangle(721, 67);
            erdeNeu =new Rectangle(721, 600);
            erdeNeu.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("jumpearthtexture.png"))));
            platformNeu.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("thinplatformtexture.png"))));
        }
        else if (zahl1==2) {
            platformNeu = new Rectangle(180, 68);
            //erdeNeu =new Rectangle(180, 600);
            //erdeNeu.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("miniearthtexture.png"))));
            platformNeu.setFill(new ImagePattern( new Image(getClass().getResourceAsStream("miniplatform.png"))));
        }
        else {
            platformNeu = new Rectangle(360, 68);
            erdeNeu =new Rectangle(360, 600);
            erdeNeu.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("jumpearthtexture.png"))));
            platformNeu.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("jumpplatform.png"))));
        }

        double maxX = 0;
        double lastY = 0;
        for (Rectangle platform : platforms) {
            if (platform.getTranslateX() > maxX) {
                maxX = platform.getTranslateX();
            }
            if (platform.getTranslateX()==maxX)
            {
                maxX=maxX+platform.getWidth(); // damit es nicht übereinander ist, sondern nebeneinander
                lastY=platform.getTranslateY();
            }
        }

        if (lastY<230)
        {
            lastY=230;
        }
        if (lastY>680)
        {
            lastY=680;
        }


        int adjustedZahl2 = zahl2;
        if (zahl3 > 50) {
            adjustedZahl2 = zahl2 / 2;
        }

        platformNeu.setTranslateX(maxX + adjustedZahl2);
        platformNeu.setTranslateY(zahl3+lastY);
        //erdeNeu.setTranslateX(maxX + adjustedZahl2);
        //erdeNeu.setTranslateY(zahl3+lastY+66);
        platforms.add(platformNeu);
        //platforms.add(erdeNeu);
        infinityPane.getChildren().add(platformNeu);
        //infinityPane.getChildren().add(erdeNeu);

        Rectangle platformWithLowestX = null;
        for (Rectangle platform : platforms) {
            if (platformWithLowestX == null || platform.getTranslateX() < platformWithLowestX.getTranslateX()) {
                platformWithLowestX = platform;
            }
        }
        if (platformWithLowestX != null && platformWithLowestX.getTranslateX() < 0) {
            platforms.remove(platformWithLowestX);
            infinityPane.getChildren().remove(platformWithLowestX);
        }
        quit.toFront();




    }

    private void handleKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.A || event.getCode() == KeyCode.LEFT) {
            left = false;
        }
        if (event.getCode() == KeyCode.D || event.getCode() == KeyCode.RIGHT) {
            right = false;
        }
        if (event.getCode() == KeyCode.SPACE || event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP) {
            jumping = false;
        }
        if (event.getCode() == KeyCode.SHIFT){
            speedValue = 12;
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
        Random rdm = new Random();
        int number = rdm.nextBoolean() ? 1 : 0;
        number=number/100;
        wallSpeed =wallSpeed+number;


        if (left)
        {
            if (!gifLefrrunning)
            {
                gifIdle=false;
                gifLefrrunning=true;
                gifJumping=false;
                gifRightrunning=false;
                player.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("runLeft.gif"))));
            }
        }
        else if (right)
        {
            if (!gifRightrunning) {
                gifIdle = false;
                gifJumping=false;
                gifLefrrunning = false;
                gifRightrunning = true;
                player.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("runRight.gif"))));
            }
        }
        else if (jumping) {
            if (!gifJumping) {
                gifJumping=true;
                gifIdle = false;
                gifLefrrunning = false;
                gifRightrunning = false;
                player.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("mid air.gif"))));
            }
        }
        else if (!gifIdle){
            gifIdle=true;
            gifLefrrunning=false;
            gifRightrunning=false;
            gifJumping=false;
            player.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("idle.gif"))));
        }


        playerAttack(false);

        //player.setTranslateX(player.getTranslateX() + dx);

        moveBackground(dx);

        for (Rectangle platform : platforms) {
            platform.setTranslateX(platform.getTranslateX() - dx);
        }

        Rectangle platformWithHighestX = null;
        for (Rectangle platform : platforms) {
            if (platformWithHighestX == null || platform.getTranslateX() > platformWithHighestX.getTranslateX()) {
                platformWithHighestX = platform;
            }
        }



        if (platformWithHighestX != null && platformWithHighestX.getTranslateX() <= 1920) {
            generateNewPlatforms();
        }



        double screenWidth = infinityPane.getWidth();
        double playerX = player.getTranslateX() + dx;
        double playerHalfWidth = player.getWidth() / 2.0;
        double offset = (screenWidth / 2.0) - (playerX + playerHalfWidth);
        //player.setTranslateX(player.getTranslateX() + offset);

        player.setTranslateY(player.getTranslateY() + velocity);
        velocity += gravity;

        checkCollisions();
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

        /*
        if (!touchesSide) {
            // wenn es nicht einen abstand von 1 hat

            System.out.println(" Keine Collision");
            // linkslaufen=true;
            //  rechtslaufen=true;
        }
        else {
            System.out.println("Collision");
        }
v
         */


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

    private void checkDeath() {
        if ((player.getBoundsInParent().intersects(deathBox.getBoundsInParent()) || player.getBoundsInParent().intersects(wall.getBoundsInParent()))&&!gewechselt) {
            gewechselt=true;
            infinityPane.setOnKeyPressed(null);
            infinityPane.setOnKeyReleased(null);
            Music.stop();

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Deathscreen.fxml"));
                Parent DeathScreenRoot = loader.load();

                DeathscreenController deathScreenController = loader.getController();
                deathScreenController.setPlayedGame(aktuellesLevel);
                deathScreenController.CauseOfDeath(1);
                deathScreenController.setPlayedGame(5);

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

    public void playerAttack(boolean doA){

        if (doA) {
            infinityPane.getChildren().add(atk);
            t = System.currentTimeMillis();
            atk.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("Attack.gif"))));
            atk.setVisible(true);
            atk.setTranslateX(player.getTranslateX() + 35);
            atk.setTranslateY(player.getTranslateY() - 70);
            atkA = true;
        }
        else if ((System.currentTimeMillis()>=t+210)&& atkA){
            System.out.println("HALLOO");
            t = System.currentTimeMillis();
            atkA=false;
            infinityPane.getChildren().remove(atk);
        }

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
