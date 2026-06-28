package com.example.racinggame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread gameThread;
    private Paint paint;
    private Car playerCar;
    private Obstacle[] obstacles;
    private Enemy[] enemies;
    private int score = 0;
    private int speed = 0;
    private boolean gameOver = false;
    private float screenWidth, screenHeight;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
        
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        screenWidth = getWidth();
        screenHeight = getHeight();

        // Initialize game objects
        playerCar = new Car(screenWidth / 2, screenHeight - 150, 60, 100);
        
        // Create obstacles
        obstacles = new Obstacle[5];
        for (int i = 0; i < obstacles.length; i++) {
            obstacles[i] = new Obstacle((float) Math.random() * (screenWidth - 50), 
                                       -100 - i * 300, 50, 80);
        }

        // Create enemy cars
        enemies = new Enemy[2];
        enemies[0] = new Enemy(screenWidth / 4, -200, 60, 100);
        enemies[1] = new Enemy(screenWidth * 3 / 4, -400, 60, 100);

        gameThread = new GameThread(getHolder(), this);
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                gameThread.setRunning(false);
                gameThread.join();
                retry = false;
            } catch (InterruptedException e) {
                Log.e("GameView", "Thread interrupted");
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            float x = event.getX();
            // Move player car left or right based on touch position
            if (x < screenWidth / 3) {
                playerCar.moveLeft();
            } else if (x > screenWidth * 2 / 3) {
                playerCar.moveRight();
            } else {
                playerCar.stop();
            }
        }
        return true;
    }

    public void update() {
        if (gameOver) return;

        // Update player car
        playerCar.update(screenWidth);

        // Update obstacles
        for (Obstacle obs : obstacles) {
            obs.update();
            if (obs.getY() > screenHeight) {
                obs.reset((float) Math.random() * (screenWidth - 50), -80);
                score += 10;
            }

            // Check collision with player
            if (checkCollision(playerCar, obs)) {
                gameOver = true;
            }
        }

        // Update enemies
        for (Enemy enemy : enemies) {
            enemy.update();
            if (enemy.getY() > screenHeight) {
                enemy.reset((float) Math.random() * (screenWidth - 60), -100);
                score += 5;
            }

            // Check collision with enemies
            if (checkCollision(playerCar, enemy)) {
                gameOver = true;
            }
        }

        speed = score / 10;
    }

    private boolean checkCollision(Car car, Obstacle obstacle) {
        return car.getX() < obstacle.getX() + obstacle.getWidth() &&
               car.getX() + car.getWidth() > obstacle.getX() &&
               car.getY() < obstacle.getY() + obstacle.getHeight() &&
               car.getY() + car.getHeight() > obstacle.getY();
    }

    private boolean checkCollision(Car car, Enemy enemy) {
        return car.getX() < enemy.getX() + enemy.getWidth() &&
               car.getX() + car.getWidth() > enemy.getX() &&
               car.getY() < enemy.getY() + enemy.getHeight() &&
               car.getY() + car.getHeight() > enemy.getY();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        if (canvas == null) return;

        // Draw background (road)
        canvas.drawColor(Color.GRAY);

        // Draw road lines
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(5);
        for (int i = 0; i < screenHeight; i += 100) {
            canvas.drawLine(screenWidth / 2, i, screenWidth / 2, i + 50, paint);
        }

        // Draw obstacles
        paint.setColor(Color.RED);
        for (Obstacle obs : obstacles) {
            canvas.drawRect(obs.getX(), obs.getY(), 
                          obs.getX() + obs.getWidth(), 
                          obs.getY() + obs.getHeight(), paint);
        }

        // Draw enemies
        paint.setColor(Color.BLUE);
        for (Enemy enemy : enemies) {
            canvas.drawRect(enemy.getX(), enemy.getY(), 
                          enemy.getX() + enemy.getWidth(), 
                          enemy.getY() + enemy.getHeight(), paint);
        }

        // Draw player car
        paint.setColor(Color.GREEN);
        canvas.drawRect(playerCar.getX(), playerCar.getY(), 
                       playerCar.getX() + playerCar.getWidth(), 
                       playerCar.getY() + playerCar.getHeight(), paint);

        // Draw UI
        paint.setColor(Color.WHITE);
        paint.setTextSize(50);
        canvas.drawText("Score: " + score, 20, 60, paint);
        canvas.drawText("Speed: " + speed, 20, 120, paint);

        if (gameOver) {
            paint.setColor(Color.BLACK);
            paint.setAlpha(200);
            canvas.drawRect(0, 0, screenWidth, screenHeight, paint);
            
            paint.setColor(Color.WHITE);
            paint.setTextSize(80);
            canvas.drawText("GAME OVER", screenWidth / 2 - 200, screenHeight / 2, paint);
            
            paint.setTextSize(50);
            canvas.drawText("Final Score: " + score, screenWidth / 2 - 150, screenHeight / 2 + 100, paint);
        }
    }

    public void pause() {
        if (gameThread != null) {
            gameThread.setRunning(false);
        }
    }

    public void resume() {
        if (gameThread != null && !gameThread.isRunning()) {
            gameThread = new GameThread(getHolder(), this);
            gameThread.start();
        }
    }
}
