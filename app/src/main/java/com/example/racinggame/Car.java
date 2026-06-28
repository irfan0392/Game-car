package com.example.racinggame;

public class Car {

    private float x;
    private float y;
    private float width;
    private float height;
    private float velocity = 0;
    private static final float SPEED = 15;
    private static final float MAX_SPEED = 20;

    public Car(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void moveLeft() {
        if (x > 0) {
            x -= SPEED;
        }
    }

    public void moveRight(float screenWidth) {
        if (x < screenWidth - width) {
            x += SPEED;
        }
    }

    public void moveRight() {
        x += SPEED;
    }

    public void stop() {
        // No action needed for stop
    }

    public void update(float screenWidth) {
        // Keep car within bounds
        if (x < 0) x = 0;
        if (x + width > screenWidth) x = screenWidth - width;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}
