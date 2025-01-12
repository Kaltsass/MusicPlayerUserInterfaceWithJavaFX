package org.example.musicplayeruserinterfacewithjavafx;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RadioController {

    @FXML
    private Label nowPlayingLabel;

    @FXML
    private ImageView albumCoverImageView;

    @FXML
    private Button nextStationButton;

    @FXML
    private Button stopButton; // New stop button

    private MediaPlayer mediaPlayer;

    private List<JsonObject> trackData = new ArrayList<>();

    private int currentTrackIndex = 0;

    private final OkHttpClient client = new OkHttpClient();

    /**
     * Initialize the radio functionality.
     */
    public void initialize() {
        nowPlayingLabel.setText("Now Playing: Nothing");
        albumCoverImageView.setImage(null);

        // Set up button actions
        nextStationButton.setOnAction(event -> playNextTrack());
        stopButton.setOnAction(event -> stopPlayback()); // Assign stop button action

        // Fetch radio tracks when the UI loads
        fetchRadioTracks();
    }

    /**
     * Fetch tracks for the radio using the Deezer API.
     */
    private void fetchRadioTracks() {
        String apiUrl = "https://api.deezer.com/search/track?q=pop"; // Replace "pop" with your preferred query

        Request request = new Request.Builder().url(apiUrl).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.out.println("Error fetching radio tracks: HTTP " + response.code());
                nowPlayingLabel.setText("Error loading radio tracks.");
                return;
            }

            String jsonResponse = response.body().string();
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
            JsonArray tracksArray = jsonObject.getAsJsonArray("data");

            trackData.clear(); // Clear existing track data

            for (int i = 0; i < tracksArray.size(); i++) {
                JsonObject track = tracksArray.get(i).getAsJsonObject();
                if (track.has("preview") && !track.get("preview").getAsString().isEmpty()) {
                    trackData.add(track);
                }
            }

            if (trackData.isEmpty()) {
                nowPlayingLabel.setText("No tracks available.");
            } else {
                playNextTrack(); // Start playback
            }

        } catch (IOException e) {
            e.printStackTrace();
            nowPlayingLabel.setText("Failed to load radio tracks.");
        }
    }

    /**
     * Play the next track in the radio playlist.
     */
    private void playNextTrack() {
        stopPlayback(); // Stop current playback before playing next track

        if (!trackData.isEmpty()) {
            currentTrackIndex = (currentTrackIndex + 1) % trackData.size();
            JsonObject track = trackData.get(currentTrackIndex);

            String trackTitle = track.get("title").getAsString();
            String artistName = track.getAsJsonObject("artist").get("name").getAsString();
            String previewUrl = track.get("preview").getAsString();
            String albumCoverUrl = track.getAsJsonObject("album").get("cover_big").getAsString();

            nowPlayingLabel.setText("Now Playing: " + trackTitle + " by " + artistName);
            albumCoverImageView.setImage(new Image(albumCoverUrl));

            Media media = new Media(previewUrl);
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setOnEndOfMedia(this::playNextTrack);
            mediaPlayer.play();
        } else {
            nowPlayingLabel.setText("No tracks to play.");
        }
    }

    /**
     * Stop theradio.
     */
    private void stopPlayback() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = null;
        }
        nowPlayingLabel.setText("Radio Stopped");
    }
}
