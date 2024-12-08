package org.example.musicplayeruserinterfacewithjavafx;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PopupController {

    @FXML
    private TextField playlistNameField;

    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;

    private String playlistName; // Για να αποθηκεύσουμε το όνομα του playlist

    public String getPlaylistName() {
        return playlistName;
    }

    public void OnButtonClick() {

        System.out.println("Pressed"); // Βγάζει μήνυμα στο τερματικό ότι πατήθηκε το κουμπί - να το σβήσουμε μετά

    }
    @FXML
    public void initialize() {
        // Set up the OK and Cancel button actions
        okButton.setOnAction(event -> handleOkButton());
        cancelButton.setOnAction(event -> handleCancelButton());
    }

    private void handleOkButton() {
        playlistName = playlistNameField.getText().trim();

        if (playlistName.isEmpty()) {
            // Βγάζει μήνυμα στον χρήστη ότι το πεδίο playlist name είναι κενό
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Playlist Name");
            alert.setContentText("Please enter a valid playlist name.");
            alert.showAndWait();
        } else {
            // Κλείσιμο του popup παραθύρου
            Stage stage = (Stage) okButton.getScene().getWindow();
            stage.close();
        }
    }

    private void handleCancelButton() {
        // Κλείσιμο του popup παραθύρου χωρίς καμία άλλη λειτουργία
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}