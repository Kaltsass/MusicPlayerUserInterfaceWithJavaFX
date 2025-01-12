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
import model.Song;

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
    private Song selectedSong;
    private String name;
    private String artist;
    private String previewUrl;
    private String cover;
    private final PlaylistManager playlistManager = new PlaylistManager();

    // Σύνδεση με τον HelloController
    public void setMainController(HelloController mainController) {
        this.mainController = mainController;
    }

    public void setSelectedSong(Song selectedSong) {
        this.selectedSong = selectedSong; // Αρχικοποίηση του τραγουδιού
        System.out.println("Selected song: " + selectedSong.getName());
    }

    public void setSongDetails(String name, String artist, String previewUrl, String cover) {
        this.name = name;
        this.artist = artist;
        this.previewUrl = previewUrl;
        this.cover = cover;
        System.out.println("Song Details: " + name + ", " + artist + ", " + previewUrl + ", " + cover);
    }


    @FXML
    public void initialize() {

        playlistItems = FXCollections.observableArrayList(loadPlaylistsFromFolder()); // Φορτώνουμε τα playlists από το αρχείο σε ObservableList
        playlistListView.setItems(playlistItems); // Σύνδεση της ObservableList με το ListView

        // Για την επιλογή ενός playlist από το ListView
        playlistListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println("Selected playlist: " + newValue);
            }
        });

    }



    private List<String> loadPlaylistsFromFolder() {
        List<String> playlists = new ArrayList<>();
        File playlistsFolder = new File("playlists");

        // Δημιουργία φακέλου αν δεν υπάρχει
        if (!playlistsFolder.exists()) {
            playlistsFolder.mkdir();
        }

        // Φόρτωση αρχείων .txt από το φάκελο
        File[] playlistFiles = playlistsFolder.listFiles((dir, name) -> name.endsWith(".txt"));
        if (playlistFiles != null) {
            for (File file : playlistFiles) {
                String fileName = file.getName();
                // Αφαίρεση της κατάληξης .txt
                String playlistName = fileName.substring(0, fileName.lastIndexOf('.'));
                playlists.add(playlistName);
            }
        }

        return playlists;
    }

    public void OnBtnClickAddToPlaylist(){
        System.out.println("Pressed"); // Βγάζει μήνυμα στο τερματικό ότι πατήθηκε το κουμπί - να το σβήσουμε μετά
        // Να βάζει το τραγούδι στη λίστα με τα liked songs

        // Λήψη του επιλεγμένου playlist από το ListView
        String selectedPlaylist = playlistListView.getSelectionModel().getSelectedItem();
        if (selectedPlaylist == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No Playlist Selected");
            alert.setContentText("Please select a playlist to add the song.");
            alert.showAndWait();
            return;
        }

        // Δημιουργία του αντικειμένου Song από τα πεδία
        if (selectedSong == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No Song Selected");
            alert.setContentText("No song data is available to add to the playlist.");
            alert.showAndWait();
            return;
        }

        // Ορισμός του ονόματος του αρχείου playlist
        String playlistFilePath = "playlists/" + selectedPlaylist + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(playlistFilePath, true))) {
            // Γράφουμε τα δεδομένα του τραγουδιού στο αρχείο
            writer.write(selectedSong.getName() + " - " + selectedSong.getArtist() + " - " + selectedSong.getPreviewUrl() + " - " + selectedSong.getCover());
            writer.newLine();
            System.out.println("Song added to playlist: " + selectedPlaylist);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("File Write Error");
            alert.setContentText("An error occurred while writing to the playlist file.");
            alert.showAndWait();
            return;
        }

        // Εμφάνιση μηνύματος επιτυχίας
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Song Added");
        alert.setContentText("The song '" + selectedSong.getName() + "' was added to playlist '" + selectedPlaylist + "'.");
        alert.showAndWait();
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
            // Ανανέωση του ListView
            refreshPlaylists();

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
        playlistItems.clear();
        playlistItems.addAll(loadPlaylistsFromFolder());
        playlistListView.setItems(playlistItems);
        /*
        if (popupController != null) {
            List<String> playlists = popupController.getPlaylists();
            playlistListView.getItems().clear();
            playlistListView.getItems().addAll(playlists);
            playlistListView.setItems(playlistItems);
        } else {
            System.out.println("PopupController not initialized.");
        }*/
    }

    public void OnBtnClickClose() {
        System.out.println("Pressed"); // Βγάζει μήνυμα στο τερματικό ότι πατήθηκε το κουμπί - να το σβήσουμε μετά
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onDeletePlaylistButtonClick() {
        String selectedPlaylist = playlistListView.getSelectionModel().getSelectedItem();

        if (selectedPlaylist == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Playlist Selected");
            alert.setHeaderText("Please select a playlist to delete.");
            alert.setContentText("You need to select a playlist from the list to proceed.");
            alert.showAndWait();
            return;
        }

        // Ορισμός διαδρομής του αρχείου
        File playlistFile = new File("playlists/" + selectedPlaylist + ".txt");

        if (!playlistFile.exists()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Playlist File Not Found");
            alert.setContentText("The playlist file does not exist.");
            alert.showAndWait();
            return;
        }

        try {
            // Έλεγχος αν το αρχείο είναι κενό
            if (playlistFile.length() == 0) {
                // Διαγραφή κενής λίστας
                boolean deleted = playlistFile.delete();
                if (deleted) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText("Playlist Deleted");
                    alert.setContentText("The playlist '" + selectedPlaylist + "' was successfully deleted.");
                    alert.showAndWait();

                    // Ανανέωση του ListView
                    refreshPlaylists();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Deletion Failed");
                    alert.setContentText("The playlist could not be deleted.");
                    alert.showAndWait();
                }
            } else {
                // Το αρχείο δεν είναι κενό - ερώτηση για επιβεβαίωση
                Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
                confirmDialog.setTitle("Delete Confirmation");
                confirmDialog.setHeaderText("Playlist Not Empty");
                confirmDialog.setContentText("The playlist '" + selectedPlaylist + "' is not empty. Do you want to delete it?");

                // Περιμένουμε την απάντηση του χρήστη
                confirmDialog.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        // Διαγραφή του αρχείου
                        boolean deleted = playlistFile.delete();
                        if (deleted) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Success");
                            alert.setHeaderText("Playlist Deleted");
                            alert.setContentText("The playlist '" + selectedPlaylist + "' was successfully deleted.");
                            alert.showAndWait();

                            // Ανανέωση του ListView
                            refreshPlaylists();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("Deletion Failed");
                            alert.setContentText("The playlist could not be deleted.");
                            alert.showAndWait();
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error occurred");
            alert.setContentText("Could not delete the playlist due to an error.");
            alert.showAndWait();
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