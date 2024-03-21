package org.example.graphify;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GraphifyMain extends Application {

    public static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {

        primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(GraphifyMain.class.getResource("Welcome.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        primaryStage.setTitle("Graphify");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}