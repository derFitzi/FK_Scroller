package org.example.crazyjoesworld;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Platform extends Rectangle {
    public Platform(double x, double y) {
        super(x, y, 150, 20);
        setFill(Color.GREEN);
    }
}
