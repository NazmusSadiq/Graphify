package org.example.graphify;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WelcomeController {

    public void initialize() {
        // Create a timeline with a delay of 3 seconds
        Duration delay = Duration.seconds(3);
        Timeline timeline = new Timeline(new KeyFrame(delay, event -> openPanel1Scene()));
        timeline.play();
    }

    private void openPanel1Scene() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Panel1FXML.fxml"));
            Parent root = fxmlLoader.load();
            GraphifyMain.primaryStage.setScene(new Scene(root));
            }
         catch (IOException ex) {
            Logger.getLogger(Panel1Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
