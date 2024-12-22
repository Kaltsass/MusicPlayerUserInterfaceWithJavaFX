package org.example.musicplayeruserinterfacewithjavafx;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RadioController {

    private final List<Song> radioPlaylist = new ArrayList<>();
    private int currentSongIndex = 0;
    private MediaPlayer mediaPlayer;

    // Bind UI components with @FXML annotation
    @FXML
    private Label nowPlayingLabel;

    @FXML
    private ImageView albumCoverImageView;

    @FXML
    private Button nextStationButton;

    public void initialize() {
        // Set up the action for the "Next Station" button
        nextStationButton.setOnAction(event -> playNextSong());
    }

    public void openRadioWindow() {
        try {
            // Load the Radio window and initialize UI components
            fetchRadioPlaylist();
        } catch (IOException e) {
            // This will catch network issues or I/O problems.
            e.printStackTrace();
        }
    }

    private void fetchRadioPlaylist() throws IOException {  // Declare IOException is thrown here
        // Jamendo API URL with client key
        String apiUrl = "https://api.jamendo.com/v3.0/tracks/?client_id=d0b648cc&limit=10";

        // Create the OkHttpClient and request
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(apiUrl).build();

        // Using try-with-resources to ensure the response is closed after use
        try (Response response = client.newCall(request).execute()) {

            // Check if the response was successful
            if (!response.isSuccessful()) {
                System.out.println("Error fetching radio playlist: HTTP " + response.code());
                return;
            }

            // Read the response body
            String jsonResponse = response.body().string();
            System.out.println("Response: " + jsonResponse); // Debugging print statement

            // Parse the response JSON
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
            JsonArray tracksArray = jsonObject.getAsJsonArray("results");

            // Check if tracks are available
            if (tracksArray == null || tracksArray.size() == 0) {
                System.out.println("No tracks found in the response.");
                return;
            }

            // Clear the existing playlist
            radioPlaylist.clear();

            // Loop through tracks and extract information
            for (int i = 0; i < tracksArray.size(); i++) {
                JsonObject track = tracksArray.get(i).getAsJsonObject();
                String songTitle = track.get("name").getAsString();
                String artistName = track.getAsJsonObject("artist").get("name").getAsString();
                String previewUrl = track.get("url").getAsString(); // Preview URL
                String coverUrl = track.getAsJsonObject("album").get("image").getAsJsonArray().get(3).getAsString(); // Album cover

                // Add the song to the playlist
                radioPlaylist.add(new Song(songTitle, artistName, previewUrl, coverUrl));
            }

            // If there are songs in the playlist, start playing
            if (!radioPlaylist.isEmpty()) {
                playNextSong();
            } else {
                updateNowPlayingUI("No songs available", null);
            }

        } catch (IOException e) {
            // Handle IOException in case of network issues
            throw e; // Re-throw to be handled in the calling method
        }
    }


    private void playNextSong() {
        if (currentSongIndex >= radioPlaylist.size()) {
            currentSongIndex = 0; // Loop back to the first song
        }

        Song currentSong = radioPlaylist.get(currentSongIndex);

        // Stop current media player if playing
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        // Create a new media player for the next song
        Media media = new Media(currentSong.getPreviewUrl());
        mediaPlayer = new MediaPlayer(media);

        // Update the UI with the current song info
        updateNowPlayingUI(currentSong.getName() + " by " + currentSong.getArtist(), currentSong.getCover());

        // Set up event to play the next song when the current one finishes
        mediaPlayer.setOnEndOfMedia(() -> {
            currentSongIndex++;
            playNextSong();
        });

        // Play the song
        mediaPlayer.play();
    }

    private void updateNowPlayingUI(String nowPlayingText, String coverUrl) {
        Platform.runLater(() -> {
            if (nowPlayingLabel != null) {
                nowPlayingLabel.setText(nowPlayingText);
            }
            if (albumCoverImageView != null && coverUrl != null && !coverUrl.isEmpty()) {
                albumCoverImageView.setImage(new Image(coverUrl));
            }
        });
    }

    public void stopRadio() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }
}
