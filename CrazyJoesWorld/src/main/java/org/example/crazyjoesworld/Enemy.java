package org.example.crazyjoesworld;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Enemy extends Rectangle {
    private double velocityY;
    private boolean onGround;
    private boolean movedLeft = true;
    private boolean r = false;
    private double range;
    private double dxA;
    private double startingPosition;

    public Enemy(double width, double height, double locX, double locY, double range) {
        super(width, height);
        setFill(Color.RED);
        this.velocityY = 0;
        this.onGround = false;
        this.setVisible(true);
        this.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("SlimeAnimation.gif"))));
        this.setTranslateX(locX);
        this.setTranslateY(locY);
        this.range = range;
        this.startingPosition = locX;
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

    public boolean getR(){
        return r;
    }
    public void setR(boolean r){
        this.r = r;
    }

    public double getdxA(){
        return dxA;
    }
    public void setdxA(double dxA){
        this.dxA = dxA;
    }

    public double getRange(){
        return range;
    }
    public double getStartingPosition(){
        return startingPosition;
    }
}