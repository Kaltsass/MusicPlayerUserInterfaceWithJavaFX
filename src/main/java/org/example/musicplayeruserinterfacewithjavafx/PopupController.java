package org.example.musicplayeruserinterfacewithjavafx;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class PopupController {

    @FXML
    private TextField playlistNameField;

    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;


    private HelloController mainController;
    private PlaylistManager playlistManager; // Χειριστής για τα playlists

    private String playlistName; // Για να αποθηκεύσουμε το όνομα του playlist

    private List<String> playlists = new ArrayList<>();
    private Runnable onCloseRequest;

    @FXML
    public void initialize() {

        // Set up the OK and Cancel button actions
        okButton.setOnAction(event -> handleOkButton());
        cancelButton.setOnAction(event -> handleCancelButton());
        // Δημιουργία αντικειμένου PlaylistManager
        playlistManager = new PlaylistManager();
    }

    @FXML
    private void getPlaylistName(ActionEvent event) {
        String playlistName = playlistNameField.getText().trim();
        System.out.println("Playlist Name: " + playlistName);
        // Επεξεργασία του ονόματος ή άλλες λειτουργίες
    }

    public List<String> getPlaylists() {
        List<String> playlists = new ArrayList<>();
        File file = new File("playlists.txt");

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.trim().isEmpty()) {
                        playlists.add(line.trim());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return playlists;
    }

    // Προσθέτει νέο playlist
    public void addPlaylist(String playlistName) {
        playlists.add(playlistName);
    }

    public void OnButtonClick() {

        System.out.println("Pressed"); // Βγάζει μήνυμα στο τερματικό ότι πατήθηκε το κουμπί - να το σβήσουμε μετά

    }


   // Σύνδεση με τον HelloController για να στέλνει το όνομα του νέου Playlist
    public void setMainController(HelloController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void createPlaylist() {
        String playlistName = playlistNameField.getText();
        if (playlistName != null && !playlistName.trim().isEmpty()) {
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
            if (playlistManager.isPlaylistExist(playlistName)) {
                // Εμφάνιση alert αν το playlist υπάρχει ήδη
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Duplicate Playlist Name");
                alert.setContentText("Αυτό το όνομα playlist υπάρχει ήδη. Παρακαλούμε εισάγετε άλλο όνομα για το Playlist σας.");
                alert.showAndWait();
            } else {

                // Αν το playlist δεν υπάρχει, το προσθέτουμε
                playlistManager.addPlaylist(playlistName);
                if (onCloseRequest != null) {
                    onCloseRequest.run();  // Καλεί την μέθοδο ανανέωσης
                }
                // Κλείνει το παράθυρο
                Stage stage = (Stage) cancelButton.getScene().getWindow();
                stage.close();

            }
        }
    }


    // Ορίζει το listener που θα καλείται όταν το popup κλείνει
    public void setOnCloseRequest(Runnable onCloseRequest) {
        this.onCloseRequest = onCloseRequest;
    }


    private void handleCancelButton() {
        // Κλείσιμο του popup παραθύρου χωρίς καμία άλλη λειτουργία
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}