package org.example.musicplayeruserinterfacewithjavafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;

import javafx.scene.image.ImageView;

import javafx.scene.layout.AnchorPane;

import javafx.stage.Modality;

import javafx.stage.Stage;

import javafx.scene.media.Media;

import javafx.scene.media.MediaPlayer;

import java.io.IOException;

import java.util.ArrayList;

import java.util.List;

public class RadioController {

    @FXML
    private Label nowPlayingLabel;
    @FXML
    private ImageView albumCoverImageView;
    @FXML
    private ProgressBar songProgressBar;

    private List<Song> radioPlaylist = new ArrayList<>();
    private int currentSongIndex = 0;
    private MediaPlayer mediaPlayer;

    public void openRadioWindow() {
        try {
            // Load the Radio window FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RadioView.fxml"));
            AnchorPane radioPane = loader.load();

            // Attach this controller to the FXML
            loader.setController(this);

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

            // Initialize the radio functionality
            fetchRadioPlaylist();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fetchRadioPlaylist() {
        // Simulate fetching a playlist (replace with Deezer API if needed)
        radioPlaylist.add(new Song("Song 1", "Artist 1", "https://preview-url-1.mp3", "https://cover-url-1.jpg"));
        radioPlaylist.add(new Song("Song 2", "Artist 2", "https://preview-url-2.mp3", "https://cover-url-2.jpg"));
        radioPlaylist.add(new Song("Song 3", "Artist 3", "https://preview-url-3.mp3", "https://cover-url-3.jpg"));
    }

    @FXML
    private void startRadio() {
        if (radioPlaylist.isEmpty()) {
            nowPlayingLabel.setText("No songs available.");
            return;
        }

        playSong();
    }

    @FXML
    private void stopRadio() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        nowPlayingLabel.setText("Radio stopped.");
        songProgressBar.setProgress(0);
    }

    private void playSong() {
        Song currentSong = radioPlaylist.get(currentSongIndex);
        nowPlayingLabel.setText("Now Playing: " + currentSong.getName() + " by " + currentSong.getArtist());

        // Update album cover
        Image albumCover = new Image(currentSong.getCover());
        albumCoverImageView.setImage(albumCover);

        // Play song
        Media media = new Media(currentSong.getPreviewUrl());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();

        // Update progress bar
        mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            if (mediaPlayer.getTotalDuration() != null) {
                double progress = newValue.toSeconds() / mediaPlayer.getTotalDuration().toSeconds();
                songProgressBar.setProgress(progress);
            }
        });

        // Set up next song
        mediaPlayer.setOnEndOfMedia(() -> {
            currentSongIndex = (currentSongIndex + 1) % radioPlaylist.size();
            playSong();
        });
    }
}
