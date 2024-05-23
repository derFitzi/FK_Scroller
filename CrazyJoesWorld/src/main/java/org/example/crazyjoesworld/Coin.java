package org.example.crazyjoesworld;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Coin extends Circle {
    public Coin(double centerX, double centerY, double radius) {
        super(centerX, centerY, radius);
        setFill(Color.YELLOW);
    }
}
