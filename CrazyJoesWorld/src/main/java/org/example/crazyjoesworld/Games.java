package org.example.crazyjoesworld;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Games {
    private Pane p_game1;
    public Rectangle player;
    public List<Rectangle> platforms = new ArrayList<>();

    public Games(Pane p_game1) {
        this.p_game1 = p_game1;
    }

    public void game1Initialise() {
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
    }
}
