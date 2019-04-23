package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import visual.Hexagon;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("CivSim");
        StackPane layout = new StackPane();

        layout.getChildren().add(new Hexagon(20, 500, 500).getPolygon());

        primaryStage.setScene(new Scene(layout, 1920, 1080));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
