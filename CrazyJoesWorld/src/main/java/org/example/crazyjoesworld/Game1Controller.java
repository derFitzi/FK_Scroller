package org.example.crazyjoesworld;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Game1Controller implements Initializable {
    @FXML
    private Pane p_game1;

    private Rectangle player;
    private List<Rectangle> platforms = new ArrayList<>();

    private boolean jumping = false;
    private double gravity = 0.6;
    private double velocity = 0;
    private double jumpStrength = 10;
    private double playerSpeed = 5;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        p_game1.requestFocus();
        p_game1.setFocusTraversable(true);

        player = new Rectangle(100, 100, Color.RED);
        player.setTranslateX(600);
        player.setTranslateY(300);
        p_game1.getChildren().add(player);

        Rectangle platform1 = new Rectangle(1000, 50, Color.GREEN);
        platform1.setTranslateX(500);
        platform1.setTranslateY(500);
        platforms.add(platform1);
        p_game1.getChildren().add(platform1);

        Rectangle platform2 = new Rectangle(200, 20, Color.GREEN);
        platform2.setTranslateX(300);
        platform2.setTranslateY(300);
        platforms.add(platform2);
        p_game1.getChildren().add(platform2);

        // Animation timer for game loop
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Move player vertically based on velocity
                player.setTranslateY(player.getTranslateY() + velocity);

                // Apply gravity
                velocity += gravity;

                // Check for collisions with platforms
                checkCollisions();
            }
        };
        timer.start();
    }

    @FXML
    public void keyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.A) {
            player.setTranslateX(player.getTranslateX() - playerSpeed);
        } else if (event.getCode() == KeyCode.D) {
            player.setTranslateX(player.getTranslateX() + playerSpeed);
        } else if (event.getCode() == KeyCode.SPACE && !jumping) {
            // Jump if not already jumping
            velocity = -jumpStrength;
            jumping = true;
        }
    }

    @FXML
    public void keyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.SPACE) {
            // Reset jumping flag when space key is released
            jumping = false;
        }
    }

    private void checkCollisions() {
        for (Rectangle platform : platforms) {
            if (player.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                // Collision detected, adjust player position and velocity
                double playerBottom = player.getTranslateY() + player.getHeight();
                double platformTop = platform.getTranslateY();
                if (playerBottom >= platformTop) {
                    player.setTranslateY(platformTop - player.getHeight());
                    velocity = 0;
                    jumping = false;
                }
            }
        }
    }
}

