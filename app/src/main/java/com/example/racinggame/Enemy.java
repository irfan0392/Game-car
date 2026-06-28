package com.example.racinggame;

public class Enemy {

    private float x;
    private float y;
    private float width;
    private float height;
    private static final float ENEMY_SPEED = 7;
    private float moveDirection = 1; // 1 for right, -1 for left
    private static final float MOVE_CHANGE_RATE = 0.02f;

    public Enemy(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void update() {
        y += ENEMY_SPEED;

        // Random movement left and right
        if (Math.random() < MOVE_CHANGE_RATE) {
            moveDirection *= -1;
        }

        x += moveDirection * 3;

        // Keep enemy on screen
        if (x < 0) x = 0;
        if (x + width > 400) x = 400 - width; // Assuming screen width ~400
    }

    public void reset(float x, float y) {
        this.x = x;
        this.y = y;
        moveDirection = (Math.random() > 0.5) ? 1 : -1;
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
