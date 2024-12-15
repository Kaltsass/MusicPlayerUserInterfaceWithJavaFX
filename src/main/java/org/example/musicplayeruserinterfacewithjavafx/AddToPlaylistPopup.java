package org.example.musicplayeruserinterfacewithjavafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class AddToPlaylistPopup {

    @FXML
    private Button addSongToPlaylist;
    @FXML
    private Button btnnewplaylist;
    @FXML
    private Button closeButton;
    @FXML
    private Button deleteplaylist;
    @FXML
    private ListView<String> playlistListView;

    private ObservableList<String> playlistItems;
    private HelloController mainController;
    private PopupController popupController;
    private String playlistName; // Για να αποθηκεύσουμε το όνομα του playlist

    // Σύνδεση με τον HelloController
    public void setMainController(HelloController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void initialize() {

        playlistItems = FXCollections.observableArrayList(loadPlaylistsFromFile()); // Φορτώνουμε τα playlists από το αρχείο σε ObservableList
        playlistListView.setItems(playlistItems); // Σύνδεση της ObservableList με το ListView

        // Για την επιλογή ενός playlist από το ListView
        playlistListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println("Selected playlist: " + newValue);
            }
        });
    }
    // Φόρτωση του αρχειου με τα playlist names
    private List<String> loadPlaylistsFromFile() {

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

    public void OnBtnClickAddToPlaylist(){
        System.out.println("Pressed"); // Βγάζει μήνυμα στο τερματικό ότι πατήθηκε το κουμπί - να το σβήσουμε μετά
        // Να βάζει το τραγούδι στη λίστα με τα liked songs
    }

    private void openPlaylistPopup() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("new-playlist-popup.fxml"));
            Parent root = loader.load();

            // Get the PopupController instance
            PopupController popupController = loader.getController();

            // Περνάμε την αναφορά του HelloController στον PopupController
            popupController.setMainController(mainController);

            // Ορίζουμε έναν listener για να ενημερώσουμε τη λίστα όταν το popup κλείσει
            popupController.setOnCloseRequest(() -> {
                refreshPlaylists();
            });

            Stage popupplaylist = new Stage();
            popupplaylist.initStyle(StageStyle.UNDECORATED);
            popupplaylist.setScene(new Scene(root, 600, 401));
            popupplaylist.show();

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void OnBtnClickNewPlaylist() {
        // Βγάζει μήνυμα στο τερματικό ότι πατήθηκε το κουμπί - να το σβήσουμε μετά
        System.out.println("Pressed");
        // Ανοίγει το popup για εισαγωγή νέου ονόματος
        openPlaylistPopup();
        // Ενημερώνει τη λίστα
        refreshPlaylists();
    }

    // Ανανέωση του αρχείου playlist
    private void refreshPlaylists() {
        if (popupController != null) {
            List<String> playlists = popupController.getPlaylists();
            playlistListView.getItems().clear();
            playlistListView.getItems().addAll(playlists);
            playlistListView.setItems(playlistItems);
        } else {
            System.out.println("PopupController not initialized.");
        }
    }

    public void OnBtnClickClose() {
        System.out.println("Pressed"); // Βγάζει μήνυμα στο τερματικό ότι πατήθηκε το κουμπί - να το σβήσουμε μετά
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onDeletePlaylistButtonClick() {
        String selectedPlaylist = playlistListView.getSelectionModel().getSelectedItem();

        if (selectedPlaylist != null) {
            // Διαγραφή από το ListView
            playlistItems.remove(selectedPlaylist);

            // Διαγραφή από το αρχείο
            deletePlaylistFromFile(selectedPlaylist);

            // Ενημέρωση του ListView
            refreshPlaylists();
        } else {
            System.out.println("No playlist selected.");
        }
    }

    // Διαγραφή ενός playlist απο το αρχείο
    private void deletePlaylistFromFile(String playlistName) {
        File file = new File("playlists.txt");
        if (file.exists()) {
            List<String> playlists = new ArrayList<>();

            // Διαβάζουμε όλες τις γραμμές του αρχείου και προσθέτουμε μόνο αυτές που δεν είναι το playlist προς διαγραφή
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.trim().equals(playlistName)) {
                        playlists.add(line.trim());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Γράφουμε ξανά το αρχείο με τις υπόλοιπες playlists
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (String playlist : playlists) {
                    writer.write(playlist);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}


// check ola ta popups gia testing UI