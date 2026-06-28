# Racing Game - Android

A complete 2D top-down racing game built with Java for Android Studio.

## Features

✅ **2D Top-Down Racing Gameplay**
- Player controls car with touch input
- Avoid red obstacles and blue enemy cars
- Score increases as you avoid obstacles

✅ **Game Mechanics**
- Smooth collision detection
- Score tracking system
- Speed indicator
- Game over screen with final score
- Enemy AI with random movement

✅ **Controls**
- Touch left side of screen to move car left
- Touch right side of screen to move car right
- Touch center to stop

## Project Structure

```
Game-car/
├── app/src/main/
│   ├── java/com/example/racinggame/
│   │   ├── MainActivity.java          # Entry point
│   │   ├── GameView.java              # Main game canvas
│   │   ├── GameThread.java            # Game loop
│   │   ├── Car.java                   # Player car class
│   │   ├── Obstacle.java              # Obstacle class
│   │   └── Enemy.java                 # Enemy AI class
│   ├── res/
│   │   └── values/
│   │       ├── strings.xml
│   │       ├── styles.xml
│   │       └── colors.xml
│   └── AndroidManifest.xml
├── build.gradle
├── .gitignore
└── README.md
```

## Setup Instructions

### 1. Create New Project
- Open Android Studio
- File → New → New Android Project
- Choose "Empty Views Activity"
- Name: Game Car
- Package: com.example.racinggame

### 2. Copy Files from Repository
- Clone or download this repository
- Copy all Java files to `app/src/main/java/com/example/racinggame/`
- Copy resource files to `app/src/main/res/values/`
- Replace AndroidManifest.xml and build.gradle with provided files

### 3. Build and Run
- Click "Sync Now" to sync gradle files
- Click "Run" (green play button)
- Select your emulator or physical device

## Game Rules

🚗 **Objective**: Avoid obstacles and enemies to maximize your score

📊 **Scoring**:
- +10 points for each obstacle avoided
- +5 points for each enemy avoided
- Speed increases with score (Speed = Score / 10)

💥 **Collision**: 
- Hitting a red obstacle = Game Over
- Hitting a blue enemy car = Game Over

## Customization

### Change Colors
Edit GameView.java in the `draw()` method:
```java
paint.setColor(Color.RED);      // Change RED to any color
```

### Adjust Speeds
Edit Obstacle.java and Enemy.java:
```java
private static final float OBSTACLE_SPEED = 8;  // Increase for faster obstacles
private static final float ENEMY_SPEED = 7;     // Increase for faster enemies
```

### Modify Difficulty
Edit GameView.java:
- Add more obstacles/enemies in `surfaceCreated()`
- Adjust spawn rates

### Change Car Size
Edit Car.java constructor or GameView.java:
```java
playerCar = new Car(screenWidth / 2, screenHeight - 150, 80, 120);  // width, height
```

## Game Loop Flow

1. **Update Phase**: Update positions of all game objects
2. **Collision Detection**: Check collisions between player and obstacles/enemies
3. **Draw Phase**: Render all objects on canvas
4. **Frame Rate**: Maintains 60 FPS

## Technical Details

### Classes Overview
- **MainActivity**: Entry point, initializes GameView
- **GameView**: SurfaceView that handles rendering and game logic
- **GameThread**: Manages game loop at 60 FPS
- **Car**: Player car with movement mechanics
- **Obstacle**: Red obstacles that move down the screen
- **Enemy**: Blue AI cars with random movement patterns

### Game Loop
The game runs at 60 frames per second:
1. Update all game objects (car, obstacles, enemies)
2. Check for collisions
3. Render everything on canvas
4. Display score and speed

## Requirements

- Android API Level 21+
- Android Studio 4.0+
- Java 11+
- Minimum SDK: Android 5.0 (API 21)

## License

Free to use and modify for your own projects!

---

Enjoy your racing game! 🏎️💨
