package org.example.musicplayeruserinterfacewithjavafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;

public class RadioController {

    @FXML
    private Button backButton;

    public void handleBackButtonClick() {
        try {
            // Load the HelloView.fxml file (the main page)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            BorderPane helloPage = loader.load();  // Use BorderPane here instead of AnchorPane

            // Create a new Scene for the Hello page
            Scene helloScene = new Scene(helloPage);

            // Get the current stage (the window)
            Stage stage = (Stage) backButton.getScene().getWindow();

            // Set the new scene to the current stage (back to the Hello page)
            stage.setScene(helloScene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

