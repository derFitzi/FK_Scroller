package org.example.crazyjoesworld;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Coin extends ImageView {
    public Coin(String imagePath, double x, double y) {
        setTranslateX(x);
        setTranslateY(y);

        Image coinImage = new Image(getClass().getResourceAsStream(imagePath));
        setImage(coinImage);
        setFitWidth(70);
        setFitHeight(70);
    }
}
