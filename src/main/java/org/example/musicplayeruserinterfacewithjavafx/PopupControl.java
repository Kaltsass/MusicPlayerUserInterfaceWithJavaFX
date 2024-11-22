package org.example.musicplayeruserinterfacewithjavafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PopupControl {


        static {
            System.out.println("PopupControl class loaded.");
        }


    @FXML
    private TextField playlistNameField;

    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;

    @FXML
    public void openPlaylistPopup() {
        try {
            // Φορτώνει το FXML του Popup
            FXMLLoader loader = new FXMLLoader(getClass().getResource("new-playlist-popup.fxml"));
            AnchorPane root = loader.load();


            // Δημιουργεί και ρυθμίζει τη σκηνή του Popup
            Stage popupstage = new Stage();
            popupstage.setTitle("Create Playlist");

            // Set the scene
            Scene scene = new Scene(loader.load());
            popupstage.setScene(scene);

            // Παίρνει τον controller του popup
            PopupControl popupControl = loader.getController();

            popupstage.showAndWait(); // Παρουσιάζει το παράθυρο μέχρι να το κλείσει ο χρήστης

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String playlistName; // Για να αποθηκεύσουμε το όνομα του playlist

    public String getPlaylistName() {
        return playlistName;
    }

    public void OnButtonClick() {

        System.out.println("Pressed");

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
            // Show an alert if the playlist name is empty
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Playlist Name");
            alert.setContentText("Please enter a valid playlist name.");
            alert.showAndWait();
        } else {
            // Close the popup window
            Stage stage = (Stage) okButton.getScene().getWindow();
            stage.close();
        }
    }

    private void handleCancelButton() {
        // Close the popup without doing anything
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}



