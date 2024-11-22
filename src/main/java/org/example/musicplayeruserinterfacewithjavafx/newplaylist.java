package org.example.musicplayeruserinterfacewithjavafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class newplaylist {

    @FXML
    private Button button7; // Button to trigger the popup for creating a new playlist


    @FXML
    public void initialize() {
        setupButtonAction();
    }
    private void setupButtonAction() {
        button7.setOnAction(event -> {
            try {
                // Load the popup FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/musicplayeruserinterfacewithjavafx/new-playlist-popup.fxml"));
                Scene popupScene = new Scene(loader.load());

                // Create a new stage for the popup
                Stage popupStage = new Stage();
                popupStage.setTitle("Create New Playlist");
                popupStage.setScene(popupScene);
                popupStage.setResizable(false);

                // Show the popup as a modal window
                popupStage.showAndWait();

                // Retrieve data from the popup controller after it's closed
                PopupControl popupController = loader.getController();
                String playlistName = popupController.getPlaylistName();
                if (playlistName != null && !playlistName.isEmpty()) {
                    System.out.println("New playlist created: " + playlistName);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }



}
