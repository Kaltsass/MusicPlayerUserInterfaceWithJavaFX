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


    private HelloController mainController;
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


   // Σύνδεση με τον HelloController για να στέλνει το όνομα του νέου Playlist
    public void setMainController(HelloController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void createPlaylist() {
        String playlistName = playlistNameField.getText();
        if (playlistName != null && !playlistName.trim().isEmpty()) {
            mainController.addPlaylist(playlistName); // Προσθήκη στην main list το playlistName
            okButton.getScene().getWindow().hide(); // Κλείσιμο του popup
        }
    }

    private void handleOkButton() {
        playlistName = playlistNameField.getText().trim();

        if (playlistName.isEmpty()) {
            // Εμφάνιση alert αν το όνομα του playlist είναι κενό
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Το Playlist Name είναι κενό!");
            alert.setContentText("Παρακαλούμε εισάγετε ένα όνομα για το νέο σας playlist");
            alert.showAndWait();
        } else {
            if (mainController.isPlaylistExist(playlistName)) {
                // Εμφάνιση alert αν το playlist υπάρχει ήδη
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Duplicate Playlist Name");
                alert.setContentText("Αυτό το όνομα playlist υπάρχει ήδη. Παρακαλούμε εισάγετε άλλο όνομα για το Playlist σας.");
                alert.showAndWait();
            } else {
                // Αν δεν υπάρχει το playlist, προσθέτουμε το νέο playlist στο HelloController
                mainController.addPlaylist(playlistName);

                // Κλείσιμο του popup
                Stage stage = (Stage) okButton.getScene().getWindow();
                stage.close();
            }
        }
    }

    private void handleCancelButton() {
        // Κλείσιμο του popup παραθύρου χωρίς καμία άλλη λειτουργία
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}