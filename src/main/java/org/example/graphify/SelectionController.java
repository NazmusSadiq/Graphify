package org.example.graphify;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;


public class SelectionController implements Initializable {

    static boolean directed = false, undirected = false, weighted = false, unweighted = false,init=false;

    @FXML
    public Button panel1Next,panel1Help, panel2Back;
    @FXML
    public Label lbPossible;
    @FXML
    private RadioButton dButton, udButton, wButton, uwButton;
    @FXML
    private AnchorPane panel1;
    @FXML
    private ImageView imgv,imgc;
    static CanvasController cref;
    Boolean canvas=true;
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        dButton.setSelected(directed);
        wButton.setSelected(weighted);
        udButton.setSelected(undirected);
        uwButton.setSelected(unweighted);
        panel1Help.setOnMouseEntered(e -> {
            imgc.setImage(new Image(getClass().getResourceAsStream("/idea.png")));
        });

        panel1Help.setOnMouseExited(e -> {
            imgc.setImage(new Image(getClass().getResourceAsStream("/confused.png")));
        });

        // Thread for button control
        panel1Next.setDisable(true);
        imgv.setVisible(false);
        if(init){
            changePossibles();
        }
        Thread t = new Thread(() -> {
            while (!(directed || undirected) || !(weighted || unweighted)) {
                try {
                    Thread.sleep(100); // Sleep for a short duration to avoid busy-waiting
                } catch (InterruptedException e) {
                    // Handle interruption if necessary
                    e.printStackTrace();
                }
            }

            // Update UI elements once the required conditions are met
            Platform.runLater(() -> {
                panel1Next.setDisable(false);
                imgv.setVisible(true);
                panel1Next.setStyle("-fx-background-color : #487eb0;");
                lbPossible.setVisible(true);
            });
            init=true;
            System.out.println("Exiting thread");
        });
        t.start();


        // Button Action listeners
        dButton.setOnAction(e -> {
            directed = true;
            undirected = false;
            System.out.println("dButton");
            changePossibles();
        });
        udButton.setOnAction(e -> {
            directed = false;
            undirected = true;
            System.out.println("udButton");
            changePossibles();
        });
        wButton.setOnAction(e -> {
            weighted = true;
            unweighted = false;
            System.out.println("wButton");
            changePossibles();
        });
        uwButton.setOnAction(e -> {
            weighted = false;
            unweighted = true;
            System.out.println("uwButton");
            changePossibles();
        });
        panel1Next.setOnAction(e -> {
            canvas=true;
            FadeOut();
        });
        panel1Help.setOnAction(e -> {
            canvas=false;
            FadeOut();
            System.out.println("Help!!!");
        });

    }

    private void changePossibles() {
        if(weighted && directed){
            lbPossible.setText("Possible Algorithms: Dijkstra, Bellman-Ford, Floyd Warshall");
        }
        else if(unweighted && directed){
            lbPossible.setText("Possible Algorithms: BFS,DFS");
        }
        else if (weighted && undirected) {
            lbPossible.setText("Possible Algorithms: Dijkstra, Bellman-Ford, Floyd Warshall,Kruskal, Prim's");
        }
        else if(unweighted && undirected){
            lbPossible.setText("Possible Algorithms: BFS,DFS");
        }
    }

    void FadeOut() {
        FadeTransition ft = new FadeTransition();
        ft.setDuration(Duration.millis(1000));
        ft.setNode(panel1);
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.setOnFinished(e -> {
            if(canvas) {
                loadCanvas();
            }
            else{
                loadHelp();
            }
        });
        ft.play();
    }

    void loadCanvas() {
        //FXMLLoader loader;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Canvas.fxml"));
            Parent root = loader.load();
            Scene newScene = new Scene(root);
            newScene.getStylesheets().add(getClass().getResource("/Styling.css").toExternalForm());
            GraphifyMain.primaryStage.setScene(newScene);
        } catch (IOException ex) {
            Logger.getLogger(SelectionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    void loadHelp() {
        //FXMLLoader loader;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Help.fxml"));
            Parent root = loader.load();
            Scene newScene = new Scene(root);
            GraphifyMain.primaryStage.setScene(newScene);
        } catch (IOException ex) {
            Logger.getLogger(SelectionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
