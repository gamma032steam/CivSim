package app;

import game.World;
import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class Main extends Application {
    public static final int GAME_WIDTH = 1920;
    public static final int GAME_HEIGHT = 1080;

    @Override
    public void start(Stage primaryStage) throws Exception{
        // Set up JavaFX parameters
        primaryStage.setTitle("CivSim");
        Group root = new Group();
        Scene scene = new Scene(root);

        Canvas canvas = new Canvas(GAME_WIDTH, GAME_HEIGHT);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Game initialization
        root.getChildren().add(World.generateMap());
        World world = new World();

        // Game loop
        AnimationTimer gameTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                world.updateGame();
            }
        };
        gameTimer.start();

        // Show
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
