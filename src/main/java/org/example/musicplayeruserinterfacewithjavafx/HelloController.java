package org.example.musicplayeruserinterfacewithjavafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Song;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class HelloController implements Initializable {
    @FXML
    private HBox favoriteContainer;
    List<Song> recentlyPlayed;
    List<Song> favorites;



    @FXML
    private HBox recentlyPlayedContainer;

    @FXML
    private Slider playbackSlider;

    @FXML
    private Button playPauseButton;

    @FXML
    private Button nextButton;

    @FXML
    private Button prevButton;

    private boolean isPlaying = false; // Κατάσταση αναπαραγωγής (true=παίζει, false=παύση)
    private int currentSongIndex = 0; // Δείκτης για το τρέχον τραγούδι

    private List<Song> allSongs; // Λίστα όλων των τραγουδιών για αναπαραγωγή

    @FXML
    private void refreshRecentlyPlayed(MouseEvent event) {
        System.out.println("Label clicked! Refreshing the recently played songs.");
        // Λογική για να ανανεώσεις το πρόσφατα παιγμένο περιεχόμενο
    }

    @FXML
    private void handlePlayPauseAction(MouseEvent event) {
        isPlaying = !isPlaying;
        playPauseButton.setText(isPlaying ? "⏸ Pause" : "▶ Play");
        System.out.println(isPlaying ? "Playing song..." : "Paused song.");
        // Προσθήκη λογικής για την αναπαραγωγή/παύση του τραγουδιού
    }

    @FXML
    private void handleNextAction(MouseEvent event) {
        if (allSongs != null && !allSongs.isEmpty()) {
            currentSongIndex = (currentSongIndex + 1) % allSongs.size();
            playSong(allSongs.get(currentSongIndex));
        }
    }

    @FXML
    private void handlePreviousAction(MouseEvent event) {
        if (allSongs != null && !allSongs.isEmpty()) {
            currentSongIndex = (currentSongIndex - 1 + allSongs.size()) % allSongs.size();
            playSong(allSongs.get(currentSongIndex));
        }
    }

    private void playSong(Song song) {
        System.out.println("Playing: " + song.getName() + " by " + song.getArtist());
        // Λογική αναπαραγωγής τραγουδιού
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allSongs = new ArrayList<>();
        recentlyPlayed = new ArrayList<>(getRecentlyPlayed());
        favorites = new ArrayList<>(getFavorites());

        // Συνδυασμός λιστών σε μία λίστα (π.χ., όλα τα τραγούδια)
        allSongs.addAll(recentlyPlayed);
        allSongs.addAll(favorites);

        try {
            for (Song song : recentlyPlayed) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("song.fxml"));

                VBox vBox = fxmlLoader.load();
                SongController songController = fxmlLoader.getController();
                songController.setData(song);

                recentlyPlayedContainer.getChildren().add(vBox);
            }
            for (Song song : favorites) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("song.fxml"));

                VBox vBox = fxmlLoader.load();
                SongController songController = fxmlLoader.getController();
                songController.setData(song);

                favoriteContainer.getChildren().add(vBox);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Αρχική ρύθμιση του slider
        playbackSlider.setMin(0);
        playbackSlider.setMax(100); // Δείκτης χρόνου τραγουδιού (π.χ., δευτερόλεπτα)
        playbackSlider.setValue(0);

        playbackSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println("Slider moved to: " + newVal.doubleValue());
            // Προσθήκη λογικής για την αλλαγή θέσης στο τραγούδι
        });
    }

    private List<Song> getRecentlyPlayed() {
        List<Song> ls = new ArrayList<>();

        Song song = new Song();
        song.setName("I Hronia Mou");
        song.setArtist("Mpelafon");
        song.setCover("/img/Mpelafon.png");
        ls.add(song);

        song = new Song();
        song.setName("Pellegrino");
        song.setArtist("Mikros Kleftis,Dof Twogee");
        song.setCover("/img/mk_pelegrino.png");
        ls.add(song);

        song = new Song();
        song.setName("20/20");
        song.setArtist("Mikros Kleftis,LEX");
        song.setCover("/img/mk_20-20.png");
        ls.add(song);

        song = new Song();
        song.setName("Smooth Kai Hardcorila");
        song.setArtist("Thitis,Sadomas,ΔΠΘ,Buzz,MadnessKey");
        song.setCover("/img/mk_20-20.png");
        ls.add(song);

        song = new Song();
        song.setName("Top Boys");
        song.setArtist("Sadam,LEX,Dof Twogee");
        song.setCover("/img/sadam.png");
        ls.add(song);

        song = new Song();
        song.setName("Gioconda");
        song.setArtist("Immune");
        song.setCover("/img/Gioconda-Immune.png");
        ls.add(song);

        return ls;
    }

    public List<Song> getFavorites() {
        List<Song> ls = new ArrayList<>();
        Song song = new Song();
        song.setName("Top 50");
        song.setArtist("Global");
        song.setCover("/img/Top50Charts.png");
        ls.add(song);

        song = new Song();
        song.setName("Top 50");
        song.setArtist("Podcast");
        song.setCover("/img/Podcast.png");
        ls.add(song);

        song = new Song();
        song.setName("Top ");
        song.setArtist("Albums");
        song.setCover("/img/TopAlbums.png");
        ls.add(song);

        song = new Song();
        song.setName("Top 50");
        song.setArtist("Artists");
        song.setCover("/img/TopArtists1.png");
        ls.add(song);
        song = new Song();
        song.setName("Top 50");
        song.setArtist("Playlist");
        song.setCover("/img/TopPlaylist.png");
        ls.add(song);

        return ls;
    }
}
