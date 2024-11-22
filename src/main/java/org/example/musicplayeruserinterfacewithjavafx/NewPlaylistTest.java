package org.example.musicplayeruserinterfacewithjavafx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class  NewPlaylistTest extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {


        // Create a button
        Button btnCreatePlaylist = new Button();
        btnCreatePlaylist.setText("Create Playlist");

        // button action
        btnCreatePlaylist.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Action: Create a new playlist
                createNewPlaylist();
            }
        });

        // Set up the layout
        StackPane root = new StackPane();
        root.getChildren().add(btnCreatePlaylist);

        // Set up the scene
        Scene scene = new Scene(root, 300, 250);

        // Set up the stage
        primaryStage.setTitle("Playlist Creator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to simulate the creation of a new playlist
    private void createNewPlaylist() {
        System.out.println("A new playlist has been created!");
        // You can add more logic here for the playlist creation, e.g., saving the playlist to a file
    }
}