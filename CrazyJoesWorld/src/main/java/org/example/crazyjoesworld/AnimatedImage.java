package org.example.crazyjoesworld;

import javafx.scene.image.Image;

public class AnimatedImage {
    public Image[] frames;
    public double duration;

    public AnimatedImage() {
        frames = new Image[6];
        for (int i = 0; i < 6; i++) {
            frames[i] = new Image( getClass().getResourceAsStream("frame_" + i + ".png") );
        }
        duration = 0.100;
    }
}