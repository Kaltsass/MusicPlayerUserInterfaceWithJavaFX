package org.example.musicplayeruserinterfacewithjavafx;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Song;

import java.io.IOException;
import java.util.List;

public class SongController {

    @FXML
    private Label artist;

    @FXML
    private ImageView img;  // Η εικόνα (cover της μουσικής)

    @FXML
    private Label songName;

    private Song song;

    // Μέθοδος για να ορίσετε τα δεδομένα του τραγουδιού
    public void setData(Song song) {
        this.song = song;
        Image image = new Image(getClass().getResourceAsStream(song.getCover()));
        img.setImage(image);
        songName.setText(song.getName());
        artist.setText(song.getArtist());

        // Προσθήκη ακροατή κλικ για την εικόνα
        img.setOnMouseClicked(this::handleImageClick);
    }

    // Μέθοδος που καλείται όταν γίνεται κλικ στην εικόνα
    private void handleImageClick(MouseEvent event) {
        if (song != null) {
            // Έλεγχος αν το τραγούδι είναι "Top 50 Artists"
            if (song.getName().equals("Top 50") && song.getArtist().equals("Artists")) {
                try {
                    // Κλήση της APITopArtists για να πάρουμε τους top 50 καλλιτέχνες
                    List<String> artists = APITopArtists.fetchTopArtists();

                    // Εμφάνιση των καλλιτεχνών στο UI (π.χ. σε ένα Alert)
                    if (artists != null && !artists.isEmpty()) {
                        StringBuilder artistsList = new StringBuilder();
                        for (String artistName : artists) {
                            artistsList.append(artistName).append("\n");
                        }

                        // Δημιουργία ενός Alert για την εμφάνιση των καλλιτεχνών
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Top 50 Artists");
                        alert.setHeaderText("Here are the Top 50 Artists:");
                        alert.setContentText(artistsList.toString());
                        alert.showAndWait();
                    } else {
                        showError("Δεν βρέθηκαν δεδομένα για τους Top 50 Καλλιτέχνες.");
                    }
                } catch (IOException e) {
                    showError("Πρόβλημα με την λήψη των δεδομένων από το API.");
                    e.printStackTrace();
                }
            }

            // Έλεγχος αν το τραγούδι είναι "Top 50 Charts" (αν υπάρχει στο UI)
            if (song.getName().equals("Top 50") && song.getArtist().equals("Global")) {
                try {
                    // Κλήση της RapidAPIExample για να πάρουμε τα top 50 charts
                    List<String> charts = RapidAPIExample.fetchTopCharts();  // Κλήση της static μεθόδου

                    // Εμφάνιση των charts στο UI (π.χ. σε ένα Alert)
                    if (charts != null && !charts.isEmpty()) {
                        StringBuilder chartsList = new StringBuilder();
                        for (String chart : charts) {
                            chartsList.append(chart).append("\n");
                        }

                        // Δημιουργία ενός Alert για την εμφάνιση των charts
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Top 50 Charts");
                        alert.setHeaderText("Here are the Top 50 Charts:");
                        alert.setContentText(chartsList.toString());
                        alert.showAndWait();
                    } else {
                        showError("Δεν βρέθηκαν δεδομένα για τα Top 50 Charts.");
                    }
                } catch (IOException e) {
                    showError("Πρόβλημα με την λήψη των δεδομένων από το API.");
                    e.printStackTrace();
                }
            }

            // Έλεγχος αν το τραγούδι είναι "Top 50 Podcasts"
            if (song.getName().equals("Top 50") && song.getArtist().equals("Podcast")) {
                try {
                    // Κλήση της fetchTopPodcasts για να πάρουμε τα top podcasts
                    List<String> podcasts = APITopPodcasts.fetchTopPodcasts();

                    // Εμφάνιση των podcasts στο UI (π.χ. σε ένα Alert)
                    if (podcasts != null && !podcasts.isEmpty()) {
                        StringBuilder podcastList = new StringBuilder();
                        for (String podcast : podcasts) {
                            podcastList.append(podcast).append("\n");
                        }

                        // Δημιουργία ενός Alert για την εμφάνιση των podcasts
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Top Podcasts");
                        alert.setHeaderText("Here are the Top 50 Podcasts:");
                        alert.setContentText(podcastList.toString());
                        alert.showAndWait();
                    } else {
                        showError("Δεν βρέθηκαν podcasts.");
                    }
                } catch (IOException e) {
                    showError("Πρόβλημα με την λήψη των podcasts.");
                    e.printStackTrace();
                }
            }

            // Έλεγχος αν το τραγούδι είναι "Top 50 Albums"
            if (song.getName().equals("Top ") && song.getArtist().equals("Albums")) {
                // Κλήση της APITopAlbums για να πάρουμε τα top albums
                List<String> albums = APITopAlbums.fetchTopAlbums();

                // Εμφάνιση των albums στο UI (π.χ. σε ένα Alert)
                if (albums != null && !albums.isEmpty()) {
                    StringBuilder albumsList = new StringBuilder();
                    for (String album : albums) {
                        albumsList.append(album).append("\n");
                    }

                    // Δημιουργία ενός Alert για την εμφάνιση των albums
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Top Albums");
                    alert.setHeaderText("Here are the Top Albums:");
                    alert.setContentText(albumsList.toString());
                    alert.showAndWait();
                } else {
                    showError("Δεν βρέθηκαν albums.");
                }
            }

            // Έλεγχος αν το τραγούδι είναι "Top 50 Playlists"
            if (song.getName().equals("Top 50") && song.getArtist().equals("Playlist")) {
                try {
                    // Κλήση της APITopPlaylist για να πάρουμε τις top playlists
                    List<String> playlists = APITopPlaylists.fetchTopPlaylists();

                    // Εμφάνιση των playlists στο UI (π.χ. σε ένα Alert)
                    if (playlists != null && !playlists.isEmpty()) {
                        StringBuilder playlistList = new StringBuilder();
                        for (String playlist : playlists) {
                            playlistList.append(playlist).append("\n");
                        }

                        // Δημιουργία ενός Alert για την εμφάνιση των playlists
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Top Playlists");
                        alert.setHeaderText("Here are the Top Playlists:");
                        alert.setContentText(playlistList.toString());
                        alert.showAndWait();
                    } else {
                        showError("Δεν βρέθηκαν playlists.");
                    }
                } catch (IOException e) {
                    showError("Πρόβλημα με την λήψη των playlists.");
                    e.printStackTrace();
                }
            }

        }
    }

    // Μέθοδος για να εμφανίσουμε ένα σφάλμα στο UI
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Something went wrong");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
