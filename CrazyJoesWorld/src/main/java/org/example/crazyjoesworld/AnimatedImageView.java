package org.example.crazyjoesworld;

import javafx.scene.image.ImageView;

public class AnimatedImageView extends ImageView {
    private AnimatedImage imgs;
    private long startTime;

    public AnimatedImageView(AnimatedImage imgs) {
        this.imgs = imgs;
        startTime = System.nanoTime();
    }

    public void update() {
        int index = (int)((System.nanoTime() - startTime) / 1_000_000_000.0 * imgs.duration) % imgs.frames.length;
        setImage(imgs.frames[index]);
    }
}