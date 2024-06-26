package org.example.crazyjoesworld;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
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
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

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
    private final ArrayList<Enemy> enemy = new ArrayList<>();
    private List<Rectangle> platforms;
    private List<Rectangle> objects;
    private List<Coin> coins;
    private Rectangle background1;
    private Rectangle background2;
    private boolean left, right, jumping, canJump;
    private double gravity = 0.6;
    private double velocity = 0;
    private double jumpStrength = -16;
    private double playerSpeed = 12;
    private double dxA = 750;
    private int pjc;
    private double speedValue = 12;
    private long t = 0;
    private Rectangle atk = new Rectangle(200, 200);;
    private boolean atkA;


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

    @FXML
    private Label T1;
    @FXML
    private Label T2;
    @FXML
    private Label T3;

    Media sound;
    MediaPlayer Music;
    int aktuellesLevel=1;
    int i=0; // testvariable
    private boolean rechtslaufen=true;
    private boolean linkslaufen=true;

    private boolean gewechselt =false;

    private boolean gifRightrunning =false;
    private boolean gifLefrrunning =false;
    private boolean gifIdle =false;
    private boolean gifJumping= false;



    public void initialize() {
        sound = new Media(new File("CrazyJoesWorld/src/main/resources/org/example/crazyjoesworld/GamePlay.mp3").toURI().toString());
        Music= new MediaPlayer(sound);


        Image hintergrundGame1 = new Image(getClass().getResourceAsStream("GameHintergrund2.png"));
        Image platformTexture = new Image(getClass().getResourceAsStream("platformtexture2.png"));

        background1 = new Rectangle(1920, 1080);
        background1.setFill(new ImagePattern(hintergrundGame1));
        background1.setTranslateX(0);
        background2 = new Rectangle(1920, 1080);
        background2.setFill(new ImagePattern(hintergrundGame1));
        background2.setTranslateX(1920);

        game1_pane.getChildren().addAll(background1, background2);

        player = new Rectangle(70, 120);
        player.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("idle.gif"))));
        player.setTranslateX(750);
        player.setTranslateY(100);
        game1_pane.getChildren().add(player);

        triggerBox = new Rectangle(50, 100, Color.TRANSPARENT);
        triggerBox.setStroke(Color.TRANSPARENT);
        triggerBox.setTranslateX(5430);
        triggerBox.setTranslateY(630);
        game1_pane.getChildren().add(triggerBox);

        deathBox = new Rectangle(1920, 30, Color.RED);
        deathBox.setStroke(Color.TRANSPARENT);
        deathBox.setTranslateX(0);
        deathBox.setTranslateY(950);
        game1_pane.getChildren().add(deathBox);


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


    public void levelauswahl(int level)
    {
        aktuellesLevel = level;

        if (aktuellesLevel==1){
            generatePlatforms1(new Image(getClass().getResourceAsStream("platformtexture2.png")));
            generateCoins1();
            initEnemies1();
        }
        if (aktuellesLevel==2){
            generatePlatforms2(new Image(getClass().getResourceAsStream("miniplatform.png")));
            generateCoins2();
            initEnemies2();
        }
        if (aktuellesLevel==3){
            generatePlatforms3(new Image(getClass().getResourceAsStream("miniplatform.png")));
            generateCoins3();
            initEnemies3();
        }
        if (aktuellesLevel==4){
            generatePlatforms4(new Image(getClass().getResourceAsStream("platformtexture2.png")));
            generateCoins4();
            initEnemies4();
        }
    }

    private void generateCoins1() {
        coins = new ArrayList<>();
        String coinImagePath = "Star.png";

        // Beispielpositionen für die drei Münzen
        Coin coin1 = new Coin(coinImagePath, 3500, 700);
        Coin coin2 = new Coin(coinImagePath, 2100, 350);
        Coin coin3 = new Coin(coinImagePath, 1400, 650);

        coins.add(coin1);
        coins.add(coin2);
        coins.add(coin3);

        game1_pane.getChildren().addAll(coin1, coin2, coin3);
    }
    private void generateCoins2() {
        coins = new ArrayList<>();
        String coinImagePath = "Star.png";

        // Beispielpositionen für die drei Münzen
        Coin coin1 = new Coin(coinImagePath, 3730, 60);
        Coin coin2 = new Coin(coinImagePath, 2290, 415);
        Coin coin3 = new Coin(coinImagePath, 1345, 710);

        coins.add(coin1);
        coins.add(coin2);
        coins.add(coin3);

        game1_pane.getChildren().addAll(coin1, coin2, coin3);
    }
    private void generateCoins3() {
        coins = new ArrayList<>();
        String coinImagePath = "Star.png";

        // Beispielpositionen für die drei Münzen
        Coin coin1 = new Coin(coinImagePath, 3560, 740);
        Coin coin2 = new Coin(coinImagePath, 2460, 270);
        Coin coin3 = new Coin(coinImagePath, 1410, 625);

        coins.add(coin1);
        coins.add(coin2);
        coins.add(coin3);

        game1_pane.getChildren().addAll(coin1, coin2, coin3);
    }
    private void generateCoins4() {
        coins = new ArrayList<>();
        String coinImagePath = "Star.png";

        // Beispielpositionen für die drei Münzen
        Coin coin1 = new Coin(coinImagePath, 3305, 170);
        Coin coin2 = new Coin(coinImagePath, 2030, 770);
        Coin coin3 = new Coin(coinImagePath, 7155, 270);



        coins.add(coin1);
        coins.add(coin2);
        coins.add(coin3);

        game1_pane.getChildren().addAll(coin1, coin2, coin3);
    }

    private void generatePlatforms1(Image platformTexture) {
        platforms = new ArrayList<>();
        objects = new ArrayList<>();

        Rectangle platform0 = new Rectangle(1442, 135); platform0.setTranslateX(-1000); platform0.setTranslateY(730);
        Rectangle platform1 = new Rectangle(1442, 135); platform1.setTranslateX(150); platform1.setTranslateY(730);
        Rectangle platform2 = new Rectangle(1442, 135); platform2.setTranslateX(1590); platform2.setTranslateY(630);
        Rectangle erde1     = new Rectangle(1442, 440); erde1.setTranslateX(1590); erde1.setTranslateY(760-1);
        Rectangle erde2     = new Rectangle(1442, 440); erde2.setTranslateX(4400); erde2.setTranslateY(800-1);
        Rectangle erde3     = new Rectangle(1442, 440); erde3.setTranslateX(5842); erde3.setTranslateY(800-1);
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

        Rectangle spikes1 = new Rectangle(144, 60); spikes1.setTranslateX(3300); spikes1.setTranslateY(780-60);
        objects.add(spikes1);
        spikes1.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("spike.png"))));
        game1_pane.getChildren().add(spikes1);

        for (int i = 0; i < platforms.size(); i++) {
            platforms.get(i).setFill(new ImagePattern(platformTexture));
            game1_pane.getChildren().add(platforms.get(i));
        }
        platform0.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("platformtexture2OhneRand.png"))));
        platform1.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("platformtexture2OhneRand.png"))));
        platform5.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("platformtexture2OhneRand.png"))));
        platform6.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("platformtexture2OhneRand.png"))));
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
        erde1.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("earthtextureO.png"))));
        erde2.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("earthtextureO.png"))));
        erde3.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("earthtextureO.png"))));
        game1_pane.getChildren().add(erde1);
        game1_pane.getChildren().add(erde2);
        game1_pane.getChildren().add(erde3);
        game1_pane.getChildren().add(platform4);
        game1_pane.getChildren().add(wand1);
        game1_pane.getChildren().add(wand2);



        Image flagImage = new Image(getClass().getResourceAsStream("hausOhneBG.png"));
        flagImageView = new ImageView(flagImage);
        flagImageView.setFitWidth(505);
        flagImageView.setFitHeight(411);
        flagImageView.setTranslateX(5160);
        flagImageView.setTranslateY(310);
        game1_pane.getChildren().add(flagImageView);
        quit.toFront();


    }
    private void generatePlatforms2(Image platformTexture) {
        platforms = new ArrayList<>();
        objects = new ArrayList<>();

        Rectangle platform1 = new Rectangle(1442, 135); platform1.setTranslateX(-2364); platform1.setTranslateY(730);
        Rectangle platform0 = new Rectangle(1442, 135); platform0.setTranslateX(-922); platform0.setTranslateY(730);


        Rectangle platform2 = new Rectangle(360, 68); platform2.setTranslateX(520); platform2.setTranslateY(660);
        Rectangle platform3 = new Rectangle(180, 68); platform3.setTranslateX(880); platform3.setTranslateY(600);

        Rectangle platform4 = new Rectangle(360, 68); platform4.setTranslateX(1200); platform4.setTranslateY(600);
        Rectangle platform41 = new Rectangle(360, 68); platform41.setTranslateX(1200); platform41.setTranslateY(800);


        Rectangle platform5 = new Rectangle(180, 68); platform5.setTranslateX(1700); platform5.setTranslateY(800);
        Rectangle platform6 = new Rectangle(180, 68); platform6.setTranslateX(1880); platform6.setTranslateY(700);
        Rectangle platform7 = new Rectangle(180, 68); platform7.setTranslateX(2060); platform7.setTranslateY(600);
        Rectangle platform8 = new Rectangle(180, 68); platform8.setTranslateX(2240); platform8.setTranslateY(500);
        Rectangle platform9 = new Rectangle(180, 68); platform9.setTranslateX(2240); platform9.setTranslateY(750);

        Rectangle platform10 = new Rectangle(360, 68); platform10.setTranslateX(2800); platform10.setTranslateY(800);

        Rectangle platform11 = new Rectangle(180, 68); platform11.setTranslateX(3300); platform11.setTranslateY(820);

        //zickzack nach oben

        Rectangle platform12 = new Rectangle(180, 68); platform12.setTranslateX(3600); platform12.setTranslateY(750);
        Rectangle platform13 = new Rectangle(180, 68); platform13.setTranslateX(3300); platform13.setTranslateY(540);
        Rectangle platform16 = new Rectangle(180, 68); platform16.setTranslateX(3600); platform16.setTranslateY(370);
        Rectangle platform18 = new Rectangle(320, 68); platform18.setTranslateX(3600); platform18.setTranslateY(160);

        Rectangle platform14 = new Rectangle(1442, 135); platform14.setTranslateX(5200); platform14.setTranslateY(710);
        Rectangle platform15 = new Rectangle(1442, 135); platform15.setTranslateX(5842); platform15.setTranslateY(710);
        Rectangle platform19 = new Rectangle(180, 68); platform19.setTranslateX(4600); platform19.setTranslateY(490);


        Rectangle earth1 =new Rectangle(180, 600); earth1.setTranslateX(1700); earth1.setTranslateY(866-2);
        Rectangle earth2 =new Rectangle(180, 600); earth2.setTranslateX(1880); earth2.setTranslateY(766-2);
        Rectangle earth3 =new Rectangle(180, 600); earth3.setTranslateX(2060); earth3.setTranslateY(666-2);
        Rectangle earth4 =new Rectangle(180, 600); earth4.setTranslateX(2240); earth4.setTranslateY(566-2);
        Rectangle earth5 =new Rectangle(180, 600); earth5.setTranslateX(2240); earth5.setTranslateY(814-2);

        // groß
        Rectangle earth9 =new Rectangle(1442, 440); earth9.setTranslateX(-1300); earth9.setTranslateY(880-2);
        Rectangle earth11 =new Rectangle(1442, 440); earth11.setTranslateX(5200); earth11.setTranslateY(710+133-2);
        Rectangle earth12 =new Rectangle(1442, 440); earth12.setTranslateX(5842); earth12.setTranslateY(710+133-2);

        Rectangle earth6 =new Rectangle(180, 600); earth6.setTranslateX(3600); earth6.setTranslateY(814-2);
        Rectangle earth7 =new Rectangle(360, 600); earth7.setTranslateX(520); earth7.setTranslateY(660+66-2);
        Rectangle earth8 =new Rectangle(180, 600); earth8.setTranslateX(880); earth8.setTranslateY(666-2);

        Rectangle earth13 =new Rectangle(180, 600); earth13.setTranslateX(4600); earth13.setTranslateY(554-2);

        Rectangle wand1 = new Rectangle(1442, 1000); wand1.setTranslateX(-1442); wand1.setTranslateY(0);
        Rectangle wand2 = new Rectangle(1442, 1000); wand2.setTranslateX(5842); wand2.setTranslateY(0);


        platforms.add(platform3);
        platforms.add(platform5);
        platforms.add(platform6);
        platforms.add(platform7);
        platforms.add(platform8);
        platforms.add(platform9);
        platforms.add(platform11);
        platforms.add(platform12);
        platforms.add(platform13);
        platforms.add(platform16);
        platforms.add(platform19);

        for (int i = 0; i < platforms.size(); i++) {
            platforms.get(i).setFill(new ImagePattern(platformTexture));
            game1_pane.getChildren().add(platforms.get(i));
        }

        platforms.add(wand1);
        platforms.add(wand2);
        wand1.setFill(Color.TRANSPARENT);
        wand1.setStroke(Color.TRANSPARENT);
        wand2.setFill(Color.TRANSPARENT);
        wand2.setStroke(Color.TRANSPARENT);
        game1_pane.getChildren().add(wand1);
        game1_pane.getChildren().add(wand2);

        platforms.add(platform0);
        platforms.add(platform1);
        platforms.add(platform2);
        platforms.add(platform4);
        platforms.add(platform41);
        platforms.add(platform10);
        platforms.add(platform14);
        platforms.add(platform15);
        platforms.add(platform18);



        //jumpplatformen 0,1,2,9
        platform2.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("jumpplatform.png"))));
        game1_pane.getChildren().add(platform2);
        platform4.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("jumpplatform.png"))));
        game1_pane.getChildren().add(platform4);
        platform41.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("jumpplatform.png"))));
        game1_pane.getChildren().add(platform41);
        platform10.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("jumpplatform.png"))));
        game1_pane.getChildren().add(platform10);
        platform18.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("jumpplatform.png"))));
        game1_pane.getChildren().add(platform18);

        // normal platform

        platform0.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("platformtexture2OhneRand.png"))));
        game1_pane.getChildren().add(platform0);
        platform1.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("platformtexture2OhneRand.png"))));
        game1_pane.getChildren().add(platform1);
        platform14.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("platformtexture2OhneRand.png"))));
        game1_pane.getChildren().add(platform14);
        platform15.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("platformtexture2OhneRand.png"))));
        game1_pane.getChildren().add(platform15);



        earth1.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("miniearthtexture.png"))));
        platforms.add(earth1);
        game1_pane.getChildren().add(earth1);
        earth2.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("miniearthtextureO.png"))));
        platforms.add(earth2);
        game1_pane.getChildren().add(earth2);
        earth3.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("miniearthtextureO.png"))));
        platforms.add(earth3);
        game1_pane.getChildren().add(earth3);
        earth4.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("miniearthtextureO.png"))));
        platforms.add(earth4);
        game1_pane.getChildren().add(earth4);
        earth5.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("miniearthtextureO.png"))));
        platforms.add(earth5);
        game1_pane.getChildren().add(earth5);
        earth6.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("miniearthtexture.png"))));
        platforms.add(earth6);
        game1_pane.getChildren().add(earth6);
        earth7.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("jumpearthtextureO.png"))));
        platforms.add(earth7);
        game1_pane.getChildren().add(earth7);
        earth8.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("miniearthtextureO.png"))));
        platforms.add(earth8);

        game1_pane.getChildren().add(earth8);
        earth9.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("earthtextureO.png"))));
        platforms.add(earth9);
        game1_pane.getChildren().add(earth9);
        earth11.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("earthtextureO.png"))));
        platforms.add(earth11);
        game1_pane.getChildren().add(earth11);
        earth12.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("earthtextureO.png"))));
        platforms.add(earth12);
        game1_pane.getChildren().add(earth12);
        earth13.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("miniearthtextureO.png"))));
        platforms.add(earth13);
        game1_pane.getChildren().add(earth13);

        Image flagImage = new Image(getClass().getResourceAsStream("hausOhneBG.png"));
        flagImageView = new ImageView(flagImage);
        flagImageView.setFitWidth(505);
        flagImageView.setFitHeight(411);
        flagImageView.setTranslateX(5350);
        flagImageView.setTranslateY(310);
        game1_pane.getChildren().add(flagImageView);
        triggerBox.setTranslateX(triggerBox.getTranslateX()+200);
        quit.toFront();



    }
    private void generatePlatforms3(Image platformTexture) {
        platforms = new ArrayList<>();
        objects = new ArrayList<>();

        Rectangle platform1 = new Rectangle(1442, 135); platform1.setTranslateX(-2364); platform1.setTranslateY(730);
        Rectangle platform0 = new Rectangle(1442, 135); platform0.setTranslateX(-922); platform0.setTranslateY(730);
        Rectangle platform2 = new Rectangle(360, 68); platform2.setTranslateX(520); platform2.setTranslateY(660);
        Rectangle platform3 = new Rectangle(180, 68); platform3.setTranslateX(880); platform3.setTranslateY(585);
        Rectangle platform4 = new Rectangle(180, 68); platform4.setTranslateX(1000); platform4.setTranslateY(585);
        Rectangle platform5 = new Rectangle(180, 68); platform5.setTranslateX(1360); platform5.setTranslateY(700);
        Rectangle platform6 = new Rectangle(180, 68); platform6.setTranslateX(1680); platform6.setTranslateY(620);
        Rectangle platform7 = new Rectangle(180, 68); platform7.setTranslateX(2000); platform7.setTranslateY(500);
        Rectangle platform8 = new Rectangle(180, 68); platform8.setTranslateX(2400); platform8.setTranslateY(350);
        Rectangle platform9 = new Rectangle(360, 68); platform9.setTranslateX(3300); platform9.setTranslateY(630);
        Rectangle platform10 = new Rectangle(180, 68); platform10.setTranslateX(4000); platform10.setTranslateY(420);
        Rectangle platform11 = new Rectangle(180, 68); platform11.setTranslateX(3500); platform11.setTranslateY(820);
        Rectangle platform12 = new Rectangle(721, 68); platform12.setTranslateX(4000); platform12.setTranslateY(750);
        Rectangle platform13 = new Rectangle(180, 68); platform13.setTranslateX(4600); platform13.setTranslateY(520);
        Rectangle platform14 = new Rectangle(1442, 135); platform14.setTranslateX(5200); platform14.setTranslateY(710);
        Rectangle platform15 = new Rectangle(1442, 135); platform15.setTranslateX(5842); platform15.setTranslateY(710);

        Rectangle earth1 =new Rectangle(180, 600); earth1.setTranslateX(1360); earth1.setTranslateY(768-4);
        Rectangle earth2 =new Rectangle(180, 600); earth2.setTranslateX(1680); earth2.setTranslateY(688-4);
        Rectangle earth3 =new Rectangle(180, 600); earth3.setTranslateX(2000); earth3.setTranslateY(568-4);
        Rectangle earth4 =new Rectangle(180, 600); earth4.setTranslateX(2000); earth4.setTranslateY(568-4);
        Rectangle earth5 =new Rectangle(180, 600); earth5.setTranslateX(2400); earth5.setTranslateY(418-4);
        Rectangle earth6 =new Rectangle(180, 600); earth6.setTranslateX(4600); earth6.setTranslateY(588-4);

        //klein
        Rectangle earth7 =new Rectangle(360, 600); earth7.setTranslateX(520); earth7.setTranslateY(660+66-2);
        Rectangle earth8 =new Rectangle(180, 600); earth8.setTranslateX(880); earth8.setTranslateY(651-2);
        Rectangle earth81 =new Rectangle(180, 600); earth81.setTranslateX(1000); earth81.setTranslateY(651-2);

        // groß
        Rectangle earth9 =new Rectangle(1442, 440); earth9.setTranslateX(-1300); earth9.setTranslateY(860-2);
        Rectangle earth10 =new Rectangle(1442, 440); earth10.setTranslateX(-260); earth10.setTranslateY(880-2);
            //Ende
        Rectangle earth11 =new Rectangle(1442, 440); earth11.setTranslateX(5200); earth11.setTranslateY(710+133-2);
        Rectangle earth12 =new Rectangle(1442, 440); earth12.setTranslateX(5842); earth12.setTranslateY(710+133-2);

        //mittel
        Rectangle earth13 =new Rectangle(720, 600); earth13.setTranslateX(4000); earth13.setTranslateY(750+62-2);

        Rectangle wand1 = new Rectangle(1442, 1000); wand1.setTranslateX(-1442); wand1.setTranslateY(0);
        Rectangle wand2 = new Rectangle(1442, 1000); wand2.setTranslateX(5842); wand2.setTranslateY(0);

        Rectangle spikes1 = new Rectangle(144, 60); spikes1.setTranslateX(4300); spikes1.setTranslateY(750-60);
        objects.add(spikes1);
        spikes1.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("spike.png"))));
        game1_pane.getChildren().add(spikes1);

        platforms.add(platform3);
        platforms.add(platform4);
        platforms.add(platform5);
        platforms.add(platform6);
        platforms.add(platform7);
        platforms.add(platform8);

        platforms.add(platform10);
        platforms.add(platform11);
        platforms.add(platform13);



        for (int i = 0; i < platforms.size(); i++) {
            platforms.get(i).setFill(new ImagePattern(platformTexture));
            game1_pane.getChildren().add(platforms.get(i));
        }
        platform3.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("miniplatformO.png"))));
        platform4.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("miniplatformO.png"))));
        platforms.add(wand1);
        platforms.add(wand2);
        wand1.setFill(Color.TRANSPARENT);
        wand1.setStroke(Color.TRANSPARENT);
        wand2.setFill(Color.TRANSPARENT);
        wand2.setStroke(Color.TRANSPARENT);
        game1_pane.getChildren().add(wand1);
        game1_pane.getChildren().add(wand2);


        //jumpplatformen 0,1,2,9
        platforms.add(platform0);
        platforms.add(platform1);
        platforms.add(platform2);
        platforms.add(platform9);
        platform0.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("platformtexture2OhneRand.png"))));
        game1_pane.getChildren().add(platform0);
        platform1.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("platformtexture2OhneRand.png"))));
        game1_pane.getChildren().add(platform1);
        platform2.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("jumpplatform.png"))));
        game1_pane.getChildren().add(platform2);
        platform9.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("jumpplatform.png"))));
        game1_pane.getChildren().add(platform9);

        //thinplatformen 12
        platforms.add(platform12);
        platform12.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("thinplatformtextureO.png"))));
        game1_pane.getChildren().add(platform12);

        // normal platform 14,15

        platforms.add(platform14);
        platforms.add(platform15);
        platform14.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("platformtexture2OhneRand.png"))));
        game1_pane.getChildren().add(platform14);
        platform15.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("platformtexture2OhneRand.png"))));
        game1_pane.getChildren().add(platform15);

        earth1.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("miniearthtexture.png"))));
        platforms.add(earth1);
        game1_pane.getChildren().add(earth1);
        earth2.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("miniearthtexture.png"))));
        platforms.add(earth2);
        game1_pane.getChildren().add(earth2);
        earth3.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("miniearthtexture.png"))));
        platforms.add(earth3);
        game1_pane.getChildren().add(earth3);
        earth4.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("miniearthtexture.png"))));
        platforms.add(earth4);
        game1_pane.getChildren().add(earth4);
        earth5.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("miniearthtexture.png"))));
        platforms.add(earth5);
        game1_pane.getChildren().add(earth5);
        earth6.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("miniearthtexture.png"))));
        platforms.add(earth6);
        game1_pane.getChildren().add(earth6);
        earth7.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("jumpearthtexture.png"))));
        platforms.add(earth7);
        game1_pane.getChildren().add(earth7);
        earth8.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("miniearthtextureO.png"))));
        platforms.add(earth8);
        game1_pane.getChildren().add(earth81);
        earth81.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("miniearthtexture.png"))));
        platforms.add(earth81);
        game1_pane.getChildren().add(earth8);
        earth9.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("earthtextureO.png"))));
        platforms.add(earth9);
        game1_pane.getChildren().add(earth9);
        earth10.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("earthtextureO.png"))));
        platforms.add(earth10);
        game1_pane.getChildren().add(earth10);
        earth11.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("earthtextureO.png"))));
        platforms.add(earth11);
        game1_pane.getChildren().add(earth11);
        earth12.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("earthtextureO.png"))));
        platforms.add(earth12);
        game1_pane.getChildren().add(earth12);
        earth13.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("jumpearthtexture.png"))));
        platforms.add(earth13);
        game1_pane.getChildren().add(earth13);

        Image flagImage = new Image(getClass().getResourceAsStream("hausOhneBG.png"));
        triggerBox.setTranslateX(triggerBox.getTranslateX()+190);
        flagImageView = new ImageView(flagImage);
        flagImageView.setFitWidth(505);
        flagImageView.setFitHeight(411);
        flagImageView.setTranslateX(5350);
        flagImageView.setTranslateY(310);
        game1_pane.getChildren().add(flagImageView);

        quit.toFront();

    }
    private void generatePlatforms4(Image platformTexture) {
        platforms = new ArrayList<>();
        objects = new ArrayList<>();

        Rectangle platform0 = new Rectangle(1442, 135); platform0.setTranslateX(-1000); platform0.setTranslateY(730);
        Rectangle platform1 = new Rectangle(1442, 135); platform1.setTranslateX(150); platform1.setTranslateY(730);
        Rectangle platform2 = new Rectangle(360, 68); platform2.setTranslateX(1592); platform2.setTranslateY(665);
        Rectangle platform3 = new Rectangle(360, 68); platform3.setTranslateX(1952); platform3.setTranslateY(600);
        Rectangle platform4 = new Rectangle(721, 68); platform4.setTranslateX(1692); platform4.setTranslateY(850);
        Rectangle platform5 = new Rectangle(360, 68); platform5.setTranslateX(2152); platform5.setTranslateY(830);
        Rectangle platform6 = new Rectangle(360, 68); platform6.setTranslateX(2800); platform6.setTranslateY(650);
        Rectangle platform7 = new Rectangle(360, 68); platform7.setTranslateX(3500); platform7.setTranslateY(450);

        Rectangle platform8 = new Rectangle(180, 68); platform8.setTranslateX(3250); platform8.setTranslateY(250);
        Rectangle platform9 = new Rectangle(180, 68); platform9.setTranslateX(4000); platform9.setTranslateY(600);
        Rectangle platform10 = new Rectangle(1442, 135); platform10.setTranslateX(4300); platform10.setTranslateY(650);
        Rectangle platform11 = new Rectangle(720, 68); platform11.setTranslateX(6000); platform11.setTranslateY(650);
        Rectangle platform12 = new Rectangle(1442, 135); platform12.setTranslateX(6900); platform12.setTranslateY(650);
        Rectangle platform13 = new Rectangle(180, 68); platform13.setTranslateX(7100); platform13.setTranslateY(350);
        Rectangle platform14 = new Rectangle(180, 68); platform14.setTranslateX(7650); platform14.setTranslateY(500);
        Rectangle platform15 = new Rectangle(721, 68); platform15.setTranslateX(8100); platform15.setTranslateY(300);
        Rectangle platform16 = new Rectangle(1442, 135); platform16.setTranslateX(9100); platform16.setTranslateY(730);
        Rectangle platform161 = new Rectangle(1442, 135); platform161.setTranslateX(10542); platform161.setTranslateY(730);
        Rectangle platform17 = new Rectangle(180, 68); platform17.setTranslateX(3400); platform17.setTranslateY(770);

        Rectangle earth2 =new Rectangle(360, 600); earth2.setTranslateX(1592); earth2.setTranslateY(665+66-2);
        Rectangle earth6 =new Rectangle(360, 600); earth6.setTranslateX(2800); earth6.setTranslateY(650+66-2);
        Rectangle earth9 =new Rectangle(180, 600); earth9.setTranslateX(4000); earth9.setTranslateY(600+66-2);
        Rectangle earth10 =new Rectangle(1442, 600); earth10.setTranslateX(4300); earth10.setTranslateY(650+66-2);
        Rectangle earth11 =new Rectangle(720, 600); earth11.setTranslateX(6000); earth11.setTranslateY(650+66-2);
        Rectangle earth12 =new Rectangle(1442, 600); earth12.setTranslateX(6900); earth12.setTranslateY(650+66-2);
        Rectangle earth16 =new Rectangle(1442, 600); earth16.setTranslateX(9100); earth16.setTranslateY(730+66-2);
        Rectangle earth161 =new Rectangle(1442, 600); earth161.setTranslateX(10542); earth161.setTranslateY(730+66-2);
        Rectangle earth17 =new Rectangle(180, 600); earth17.setTranslateX(3400); earth17.setTranslateY(770+66-2);

        Rectangle spikes1 = new Rectangle(144, 60); spikes1.setTranslateX(4500); spikes1.setTranslateY(650-60);
        Rectangle spikes2 = new Rectangle(144, 60); spikes2.setTranslateX(4500+144); spikes2.setTranslateY(650-60);
        Rectangle spikes3 = new Rectangle(144, 60); spikes3.setTranslateX(4800); spikes3.setTranslateY(650-60);
        Rectangle spikes4 = new Rectangle(144, 60); spikes4.setTranslateX(4500+144); spikes4.setTranslateY(650-60);
        Rectangle spikes5 = new Rectangle(144, 60); spikes5.setTranslateX(9250); spikes5.setTranslateY(730-60);
        Rectangle spikes6 = new Rectangle(144, 60); spikes6.setTranslateX(9500); spikes6.setTranslateY(730-60);

        Rectangle wand1 = new Rectangle(1442, 1000); wand1.setTranslateX(-1442); wand1.setTranslateY(0);
        Rectangle wand2 = new Rectangle(1442, 1000); wand2.setTranslateX(11142); wand2.setTranslateY(0);

        Rectangle blocker = new Rectangle(360, 300); blocker.setTranslateX(1592); blocker.setTranslateY(660);
        blocker.setFill(Color.TRANSPARENT);

        platforms.add(blocker);
        platforms.add(wand1);
        platforms.add(wand2);
        wand1.setFill(Color.TRANSPARENT);
        wand1.setStroke(Color.TRANSPARENT);
        wand2.setFill(Color.TRANSPARENT);
        wand2.setStroke(Color.TRANSPARENT);

        platforms.add(platform0);
        platforms.add(platform1);
        platforms.add(platform2);
        platforms.add(platform3);
        platforms.add(platform4);
        platforms.add(platform5);
        platforms.add(platform6);
        platforms.add(platform7);
        platforms.add(platform8);
        platforms.add(platform9);
        platforms.add(platform10);
        platforms.add(platform11);
        platforms.add(platform12);
        platforms.add(platform13);
        platforms.add(platform14);
        platforms.add(platform15);
        platforms.add(platform16);
        platforms.add(platform161);
        platforms.add(platform17);
        platforms.add(earth2);
        platforms.add(earth6);
        platforms.add(earth9);
        platforms.add(earth10);
        platforms.add(earth11);
        platforms.add(earth12);
        platforms.add(earth16);
        platforms.add(earth161);
        platforms.add(earth17);
        objects.add(spikes1);
        objects.add(spikes2);
        objects.add(spikes3);
        objects.add(spikes4);
        objects.add(spikes5);
        objects.add(spikes6);



        platform0.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("platformtexture2OhneRand.png"))));
        platform1.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("platformtexture2OhneRand.png"))));
        platform10.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("platformtexture2.png"))));
        platform12.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("platformtexture2.png"))));
        platform16.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("platformtexture2OhneRand.png"))));
        platform161.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("platformtexture2OhneRand.png"))));

        platform2.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("jumpplatform.png"))));
        platform3.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("jumpplatform.png"))));
        platform5.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("jumpplatform.png"))));
        platform6.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("jumpplatform.png"))));
        platform7.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("jumpplatform.png"))));

        platform8.setFill(new ImagePattern( new Image(getClass().getResourceAsStream("miniplatform.png"))));
        platform9.setFill(new ImagePattern( new Image(getClass().getResourceAsStream("miniplatform.png"))));
        platform13.setFill(new ImagePattern( new Image(getClass().getResourceAsStream("miniplatform.png"))));
        platform14.setFill(new ImagePattern( new Image(getClass().getResourceAsStream("miniplatform.png"))));
        platform17.setFill(new ImagePattern( new Image(getClass().getResourceAsStream("miniplatform.png"))));

        platform4.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("thinplatformtexture.png"))));
        platform15.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("thinplatformtexture.png"))));
        platform11.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("thinplatformtexture.png"))));

        earth2.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("jumpearthtexture.png"))));
        earth6.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("jumpearthtexture.png"))));

        earth9.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("miniearthtexture.png"))));
        earth17.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("miniearthtexture.png"))));

        earth10.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("earthtexture.png"))));
        earth12.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("earthtexture.png"))));
        earth16.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("earthtextureO.png"))));
        earth161.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("earthtextureO.png"))));

        earth11.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("thinearthtexture.png"))));

        spikes1.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("spike.png"))));
        spikes2.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("spike.png"))));
        spikes3.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("spike.png"))));
        spikes4.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("spike.png"))));
        spikes5.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("spike.png"))));
        spikes6.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("spike.png"))));

        for (int i = 0; i < platforms.size(); i++) {
            game1_pane.getChildren().add(platforms.get(i));
        }




        Image flagImage = new Image(getClass().getResourceAsStream("hausOhneBG.png"));
        flagImageView = new ImageView(flagImage);
        flagImageView.setFitWidth(505);
        flagImageView.setFitHeight(411);
        flagImageView.setTranslateX(10150);
        flagImageView.setTranslateY(325);
        game1_pane.getChildren().add(flagImageView);
        quit.toFront();


        Rectangle rotatingPlatform = new Rectangle(10, 300);
        rotatingPlatform.setFill(Color.RED);
        rotatingPlatform.setTranslateX(2150);
        rotatingPlatform.setTranslateY(700);
        objects.add(rotatingPlatform);



        TranslateTransition tt = new TranslateTransition(Duration.seconds(3), rotatingPlatform);
        tt.setByY(300); // change this value to move the platform more or less
        tt.setCycleCount(Animation.INDEFINITE);
        tt.setAutoReverse(true);
        tt.play();

        /*

        Rectangle rotatingPlatform2 = new Rectangle(800, 30);
        rotatingPlatform2.setFill(Color.RED);
        rotatingPlatform2.setTranslateX(7300);
        rotatingPlatform2.setTranslateY(500);
        rotatingPlatform2.setPickOnBounds(false);
        objects.add(rotatingPlatform2);

        *



        RotateTransition rt2 = new RotateTransition(Duration.seconds(5), rotatingPlatform2);
        rt2.setAxis(Rotate.Z_AXIS);
        rt2.setByAngle(360);
        rt2.setCycleCount(Animation.INDEFINITE);
        rt2.play();

        */


        for (int i = 0; i < objects.size(); i++) {
            game1_pane.getChildren().add(objects.get(i));
        }
        triggerBox.setFill(Color.BLACK);



        triggerBox.setTranslateX(10430);
        triggerBox.setTranslateY(645);
        quit.toFront();
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
            playerAttack('r');
        }
        if (event.getCode() == KeyCode.Q){
            playerAttack('l');
        }
        if (event.getCode() == KeyCode.SHIFT){
            speedValue = 5;
        }
    }



    private void handleKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.A || event.getCode() == KeyCode.LEFT) {
            left = false;
        } else if (event.getCode() == KeyCode.D || event.getCode() == KeyCode.RIGHT) {
            right = false;
            player.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("idle.gif"))));
        } else if (event.getCode() == KeyCode.SPACE || event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP) {
            jumping = false;
        }
        if (event.getCode() == KeyCode.SHIFT){
            speedValue = 12;
        }

    }

    private void update() {
        double dx = 0;
        game1_pane.requestFocus();
        //System.out.println("X: " + player.getTranslateX() + " Y: " + player.getTranslateY() + " Velocity: " + velocity + " Gravity: " + gravity + " PlayerSpeed: " + playerSpeed + " Sensi: " + sensi + " Collected Coins: " + collectedCoinCount + " Linkslaufen: " + linkslaufen + " Rechtslaufen: " + rechtslaufen + " CanJump: " + canJump + " Jumping: " + jumping + " Left: " + left + " Right: " + right + " i: " + i);
        //System.out.print("links frei: "+rechtslaufen+"   rechts frei: "+linkslaufen);

        if (left &&linkslaufen) dx -= playerSpeed;
        if (right &&rechtslaufen) dx += playerSpeed;
        dxA += dx;
        System.out.println(dxA + " :::: " + player.getTranslateY());
        for (i=0; i<enemy.size(); i++){
            enemyMovement(i, 6, dx);
        }

        if (dx<0)
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
        else if (dx>0)
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





        playerAttack('n');
        //player.setTranslateX(player.getTranslateX() + dx);

        triggerBox.setTranslateX(triggerBox.getTranslateX() - dx);
        flagImageView.setTranslateX(flagImageView.getTranslateX() - dx);

        moveBackground(dx);

        for (Rectangle platform : platforms) {
            platform.setTranslateX(platform.getTranslateX() - dx);
        }

        for (Rectangle object : objects) {
            object.setTranslateX(object.getTranslateX() - dx);
        }

        for (Coin coin : coins) {
            coin.setTranslateX(coin.getTranslateX() - dx);
        }

        double screenWidth = game1_pane.getWidth();
        double playerX = player.getTranslateX() + dx;
        double playerHalfWidth = player.getWidth() / 2.0;
        double offset = (screenWidth / 2.0) - (playerX + playerHalfWidth);
        //player.setTranslateX(player.getTranslateX() + offset);

        player.setTranslateY(player.getTranslateY() + velocity);
        velocity += gravity;

        player.toFront();

        checkCollisions();
        checkCoinCollisions();
        checkBoxTrigger();
        checkDeath();
        checkEnemyCollision();
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

          //System.out.println(" Keine Collision");
          // linkslaufen=true;
          //  rechtslaufen=true;
        }
        else {
            System.out.println("Collision");
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
        if (player.getBoundsInParent().intersects(triggerBox.getBoundsInParent()) && !gewechselt) {
            gewechselt=true;
            onPlayerEnterBox();
        }
    }
    private void checkEnemyCollision() {
        for (int i = 0; i < enemy.size(); i++) {
            if ((player.getBoundsInParent().intersects(enemy.get(i).getBoundsInParent()) && !gewechselt)) {
                game1_pane.setOnKeyReleased(null);
                Music.stop();
                killPlayer();
            }
            if ((enemy.get(i).getBoundsInParent().intersects(atk.getBoundsInParent()))&&atkA){
                enemy.get(i).setTranslateY(2000);
            }
        }
    }
    private void checkDeath() {
        boolean death=false;
        for (Rectangle object : objects) {
            if (player.getBoundsInParent().intersects(object.getBoundsInParent())) {
                death=true;
            }
        }
        if ((player.getBoundsInParent().intersects(deathBox.getBoundsInParent()) || death) && !gewechselt) {
            killPlayer();
        }
    }
    private void killPlayer() {
        gewechselt=true;
        game1_pane.setOnKeyPressed(null);
        game1_pane.setOnKeyReleased(null);
        Music.stop();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Deathscreen.fxml"));
            Parent DeathScreenRoot = loader.load();

            DeathscreenController deathScreenController = loader.getController();
            deathScreenController.setPlayedGame(aktuellesLevel);
            deathScreenController.CauseOfDeath(1, 0);

            Scene currentScene = quit.getScene();
            currentScene.setRoot(DeathScreenRoot);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading DeathScreen.fxml: " + e.getMessage());
        }
    }

    private void onPlayerEnterBox() {
        game1_pane.setOnKeyPressed(null);
        game1_pane.setOnKeyReleased(null);
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
        T1.setVisible(true);
        T2.setVisible(true);
        T3.setVisible(true);

        settingsBG.toFront();
        T1.toFront();
        T2.toFront();
        T3.toFront();


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
        T1.setVisible(false);
        T2.setVisible(false);
        T3.setVisible(false);

    }

    public void lautsterkenregelung(){
        Singleton.getInstance().setLautstaerke((lautstaerke.getValue() / 200.0));
        Music.setVolume(Singleton.getInstance().getLautstaerke());
    }

    public void sensibilitaetregelung() {
        Singleton.getInstance().setSensi((sensibilitaet.getValue()/100));
        sensi = Singleton.getInstance().getSensi();
    }

    public void enemyMovement (int id, double eVelocity, double dx){
        if (enemy.get(id).getR()) {
            enemy.get(id).setTranslateX(enemy.get(id).getTranslateX() - eVelocity - dx);
            enemy.get(id).setdxA(enemy.get(id).getdxA() + dx);
            if (enemy.get(id).getTranslateX() <= enemy.get(id).getStartingPosition() - enemy.get(id).getdxA()) {
                enemy.get(id).setR(false);
            }
        }

        if (!enemy.get(id).getR()) {
            enemy.get(id).setTranslateX(enemy.get(id).getTranslateX() + eVelocity - dx);
            enemy.get(id).setdxA(enemy.get(id).getdxA() + dx);
            if (enemy.get(id).getTranslateX() >= enemy.get(id).getStartingPosition() + enemy.get(id).getRange() - enemy.get(id).getdxA()) {
                enemy.get(id).setR(true);
            }
        }
    }

    private void initEnemies1(){
        for (int i = 0; i <= enemy.size()-1; i++) {
            game1_pane.getChildren().remove(enemy.get(i));
            enemy.remove(i);
        }
        enemy.add(new Enemy(70, 70, 900, 660, 600));
        enemy.add(new Enemy(70, 70, 1600, 560, 1420));
        enemy.add(new Enemy(70, 70, 3100, 715, 1100));

        for (int i = 0; i <= enemy.size()-1; i++) {
            game1_pane.getChildren().add(enemy.get(i));
        }
    }
    private void initEnemies2(){
        for (int i = 0; i <= enemy.size()-1; i++) {
            game1_pane.getChildren().remove(enemy.get(i));
            enemy.remove(i);
        }
        enemy.add(new Enemy(70, 70, 1170, 530, 300));
        enemy.add(new Enemy(70, 70, 2780, 730, 300));

        for (int i = 0; i <= enemy.size()-1; i++) {
            game1_pane.getChildren().add(enemy.get(i));
        }
    }
    private void initEnemies3(){
        for (int i = 0; i <= enemy.size()-1; i++) {
            game1_pane.getChildren().remove(enemy.get(i));
            enemy.remove(i);
        }
        enemy.add(new Enemy(70, 70, 1350, 630, 160));
        enemy.add(new Enemy(70, 70, 3320, 565, 300));
        enemy.add(new Enemy(70, 70, 3985, 675, 600));

        for (int i = 0; i <= enemy.size()-1; i++) {
            game1_pane.getChildren().add(enemy.get(i));
        }
    }
    private void initEnemies4(){
        for (int i = 0; i <= enemy.size()-1; i++) {
            game1_pane.getChildren().remove(enemy.get(i));
            enemy.remove(i);
        }
        enemy.add(new Enemy(70, 70, 900, 660, 600));
        enemy.add(new Enemy(70, 70, 2152, 760, 300));
        enemy.add(new Enemy(70, 70, 6000, 580, 670));

        for (int i = 0; i <= enemy.size()-1; i++) {
            game1_pane.getChildren().add(enemy.get(i));
        }
    }

    public void playerAttack(char c){

        if (c=='r') {
            game1_pane.getChildren().add(atk);
            t = System.currentTimeMillis();
            atk.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("Attack.gif"))));
            atk.setVisible(true);
            atk.setTranslateX(player.getTranslateX() + 35);
            atk.setTranslateY(player.getTranslateY() - 70);
            atkA = true;
        }
        else if (c=='l'){
            game1_pane.getChildren().add(atk);
            t = System.currentTimeMillis();
            atk.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("AttackLeft.gif"))));
            atk.setVisible(true);
            atk.setTranslateX(player.getTranslateX() - 175);
            atk.setTranslateY(player.getTranslateY() - 30);
            atkA = true;
        }
        if ((System.currentTimeMillis()>=t+210)&& atkA && c=='n'){
            System.out.println("HALLOO");
            t = System.currentTimeMillis();
            atkA=false;
            game1_pane.getChildren().remove(atk);
        }

    }


    public Pane getGame1_pane() {
        return game1_pane;
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

    public double getPlayerSpeed() {
        return playerSpeed;
    }
    public int getaktuellesLevle() {
        return aktuellesLevel;
    }

    public List<Coin> getCoins() {
        return coins;
    }


    public double getVelocity() {
        return velocity;
    }


}
