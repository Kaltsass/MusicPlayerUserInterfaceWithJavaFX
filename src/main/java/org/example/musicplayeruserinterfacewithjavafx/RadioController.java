package org.example.musicplayeruserinterfacewithjavafx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class RadioController {

    public void openRadioWindow() {
        try {
            // Load the Radio window FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            AnchorPane radioPane = loader.load();

            // Create a new stage (popup window)
            Stage popupStage = new Stage();
            popupStage.setTitle("Radio");
            popupStage.initModality(Modality.APPLICATION_MODAL); // Makes it modal
            popupStage.setResizable(false);

            // Set the scene for the new stage
            Scene popupScene = new Scene(radioPane);
            popupStage.setScene(popupScene);

            // Show the popup window
            popupStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}