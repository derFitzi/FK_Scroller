package org.example.crazyjoesworld;


import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player extends Rectangle {
    private double velocityY;
    private boolean onGround;

    public Player(double x, double y) {
        super(x, y, 40, 40);
        setFill(Color.RED);
        this.velocityY = 0;
        this.onGround = false;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public void move(double dx, double dy) {
        setTranslateX(getTranslateX() + dx);
        setTranslateY(getTranslateY() + dy);
    }
}
