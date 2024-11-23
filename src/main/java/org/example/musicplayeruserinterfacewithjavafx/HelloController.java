package org.example.musicplayeruserinterfacewithjavafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Song;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javafx.fxml.Initializable;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML private HBox favoriteContainer;
    @FXML private HBox recentlyPlayedContainer;
    @FXML private TextField searchBar;
    @FXML private VBox searchResultsContainer;
    @FXML private Slider playbackSlider;
    @FXML private Button playPauseButton;
    @FXML private Button nextButton;
    @FXML private Button prevButton;
    @FXML private Button exploreButton;
    @FXML private Button radioButton;
    @FXML private Button backButton;

    private List<Song> recentlyPlayed;
    private List<Song> favorites;
    private List<Song> allSongs;
    private boolean isPlaying = false;
    private int currentSongIndex = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allSongs = new ArrayList<>();
        recentlyPlayed = new ArrayList<>(getRecentlyPlayed());
        favorites = new ArrayList<>(getFavorites());

        // Combine lists into one for all songs
        allSongs.addAll(recentlyPlayed);
        allSongs.addAll(favorites);

        // Initialize the playback slider
        playbackSlider.setMin(0);
        playbackSlider.setMax(100);
        playbackSlider.setValue(0);

        // Add listener to the playback slider
        playbackSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println("Slider moved to: " + newVal.doubleValue());
        });

        // Load and display the recently played songs
        loadSongs(recentlyPlayed, recentlyPlayedContainer);
        // Load and display the favorite songs
        loadSongs(favorites, favoriteContainer);
    }

    // Load songs into a container (e.g., recently played or favorites)
    private void loadSongs(List<Song> songList, HBox container) {
        try {
            for (Song song : songList) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("song.fxml"));
                VBox vBox = fxmlLoader.load();
                SongController songController = fxmlLoader.getController();
                songController.setData(song);
                container.getChildren().add(vBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Toggle visibility of the recently played container
    @FXML
    private void toggleRecentlyPlayedVisibility(MouseEvent event) {
        Platform.runLater(() -> {
            boolean isVisible = recentlyPlayedContainer.isVisible();
            recentlyPlayedContainer.setVisible(!isVisible);
            System.out.println("Recently Played section is now " + (isVisible ? "hidden" : "visible"));
        });
    }

    @FXML
    private void handleExploreButtonClick() {
        try {
            // Load the Explore page FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ExploreView.fxml"));
            Parent explorePage = loader.load();

            // Create a new Scene for the Explore page
            Scene exploreScene = new Scene(explorePage);

            // Get the current stage (the window)
            Stage stage = (Stage) exploreButton.getScene().getWindow();

            // Set the new scene to the current stage
            stage.setScene(exploreScene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRadioButtonClick() {
        try {
            // Load the radio page FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RadioView.fxml"));
            Parent radioPage = loader.load();

            // Create a new Scene for the radio page
            Scene radioScene = new Scene(radioPage);

            // Get the current stage (the window)
            Stage stage = (Stage) radioButton.getScene().getWindow();

            // Set the new scene to the current stage
            stage.setScene(radioScene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBackButtonClick() {
        try {
            // Load the HelloView.fxml file (the main page)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent helloPage = loader.load();

            // Create a new Scene for the Hello page
            Scene helloScene = new Scene(helloPage);

            // Get the current stage (the window) where the Explore scene is being shown
            Stage stage = (Stage) backButton.getScene().getWindow();

            // Set the new scene to the current stage (back to the Hello page)
            stage.setScene(helloScene);
            stage.show();

        } catch (IOException e) {
            System.out.println(getClass().getResource("hello-view.fxml"));
            e.printStackTrace();
        }
    }

    // Search for songs when the search button is pressed
    @FXML
    private void handleSearch(ActionEvent event) {
        String query = searchBar.getText();
        if (query.isEmpty()) {
            return;
        }
        searchSongs(query);
    }

    // Make a request to Deezer API to search for songs
    private void searchSongs(String query) {
        OkHttpClient client = new OkHttpClient();
        String apiUrl = "https://api.deezer.com/search/track?q=" + query;

        Request request = new Request.Builder().url(apiUrl).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.out.println("Error fetching songs: HTTP " + response.code());
                return;
            }

            String jsonResponse = response.body().string();
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
            JsonArray tracksArray = jsonObject.getAsJsonArray("data");

            // Clear previous search results
            searchResultsContainer.getChildren().clear();

            // Populate the UI with new search results
            for (int i = 0; i < tracksArray.size(); i++) {
                JsonObject track = tracksArray.get(i).getAsJsonObject();
                String songTitle = track.get("title").getAsString();
                String artistName = track.getAsJsonObject("artist").get("name").getAsString();

                Label resultLabel = new Label((i + 1) + ". " + songTitle + " by " + artistName);
                resultLabel.setStyle("-fx-text-fill: white;");
                searchResultsContainer.getChildren().add(resultLabel);
            }

            // Make the results container visible
            searchResultsContainer.setVisible(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Refresh the recently played list
    @FXML
    private void refreshRecentlyPlayed(MouseEvent event) {
        System.out.println("Refreshing Recently Played Songs");
        recentlyPlayedContainer.getChildren().clear(); // Clear the container
        try {
            for (Song song : getRecentlyPlayed()) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("song.fxml"));
                VBox vBox = fxmlLoader.load();
                SongController songController = fxmlLoader.getController();
                songController.setData(song);
                recentlyPlayedContainer.getChildren().add(vBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Play/pause song
    @FXML
    private void handlePlayPauseAction(MouseEvent event) {
        isPlaying = !isPlaying;
        playPauseButton.setText(isPlaying ? "⏸ Pause" : "▶ Play");
        System.out.println(isPlaying ? "Playing song..." : "Paused song.");
    }

    // Play the next song
    @FXML
    private void handleNextAction(MouseEvent event) {
        if (allSongs != null && !allSongs.isEmpty()) {
            currentSongIndex = (currentSongIndex + 1


public class HelloController implements Initializable {

    @FXML private HBox favoriteContainer;
    @FXML private HBox recentlyPlayedContainer;
    @FXML private TextField searchBar;
    @FXML private VBox searchResultsContainer;
    @FXML private Slider playbackSlider;
    @FXML private Button playPauseButton;
    @FXML private Button nextButton;
    @FXML private Button prevButton;

    private List<Song> recentlyPlayed;
    private List<Song> favorites;
    private List<Song> allSongs;
    private boolean isPlaying = false;
    private int currentSongIndex = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allSongs = new ArrayList<>();
        recentlyPlayed = new ArrayList<>(getRecentlyPlayed());
        favorites = new ArrayList<>(getFavorites());

        // Combine lists into one for all songs
        allSongs.addAll(recentlyPlayed);
        allSongs.addAll(favorites);

        // Initialize the playback slider
        playbackSlider.setMin(0);
        playbackSlider.setMax(100);
        playbackSlider.setValue(0);

        // Add listener to the playback slider
        playbackSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println("Slider moved to: " + newVal.doubleValue());
        });

        // Load and display the recently played songs
        loadSongs(recentlyPlayed, recentlyPlayedContainer);
        // Load and display the favorite songs
        loadSongs(favorites, favoriteContainer);
    }

    // Toggle visibility of the recently played container
    @FXML
    private void toggleRecentlyPlayedVisibility(MouseEvent event) {
        Platform.runLater(() -> {
            boolean isVisible = recentlyPlayedContainer.isVisible();
            recentlyPlayedContainer.setVisible(!isVisible);
            System.out.println("Recently Played section is now " + (isVisible ? "hidden" : "visible"));
        });
    }

 // Handle Explore button click to change scene
    @FXML
    private Button exploreButton;

    @FXML
    private void handleExploreButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ExploreView.fxml"));
            AnchorPane explorePage = loader.load();
            Scene exploreScene = new Scene(explorePage);
            Stage stage = (Stage) exploreButton.getScene().getWindow();
            stage.setScene(exploreScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Handle Radio button click to change scene
    @FXML
    private Button radioButton;

    @FXML
    private void handleRadioButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RadioView.fxml"));
            AnchorPane radioPage = loader.load();
            Scene radioScene = new Scene(radioPage);
            Stage stage = (Stage) radioButton.getScene().getWindow();
            stage.setScene(radioScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Handle Back button click to return to the main page
    @FXML
    private Button backButton;

    @FXML
    private void handleBackButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            AnchorPane helloPage = loader.load();
            Scene helloScene = new Scene(helloPage);
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(helloScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Search for songs when the search button is pressed
    @FXML
    private void handleSearch(ActionEvent event) {
        String query = searchBar.getText();
        if (query.isEmpty()) {
            return;
        }
        searchSongs(query);
    }

    // Make a request to Deezer API to search for songs
    private void searchSongs(String query) {
        OkHttpClient client = new OkHttpClient();
        String apiUrl = "https://api.deezer.com/search/track?q=" + query;

            String jsonResponse = response.body().string();
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
            JsonArray tracksArray = jsonObject.getAsJsonArray("data");

            // Clear previous search results
            searchResultsContainer.getChildren().clear();

            // Populate the UI with new search results
            for (int i = 0; i < tracksArray.size(); i++) {
                JsonObject track = tracksArray.get(i).getAsJsonObject();
                String songTitle = track.get("title").getAsString();
                String artistName = track.getAsJsonObject("artist").get("name").getAsString();

                Label resultLabel = new Label((i + 1) + ". " + songTitle + " by " + artistName);
                resultLabel.setStyle("-fx-text-fill: white;");
                searchResultsContainer.getChildren().add(resultLabel);
            }

            // Make the results container visible
            searchResultsContainer.setVisible(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Refresh the recently played list
    @FXML
    private void refreshRecentlyPlayed(MouseEvent event) {
        System.out.println("Refreshing Recently Played Songs");
        recentlyPlayedContainer.getChildren().clear(); // Αδειάζει το container
        try {
            for (Song song : getRecentlyPlayed()) { // Επαναλαμβάνει τη φόρτωση
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("song.fxml"));
                VBox vBox = fxmlLoader.load();
                SongController songController = fxmlLoader.getController();
                songController.setData(song);
                recentlyPlayedContainer.getChildren().add(vBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void populateContainer(HBox container, List<Song> songs) {
        container.getChildren().clear();
        try {
            for (Song song : songs) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("song.fxml"));
                VBox vBox = fxmlLoader.load();
                SongController songController = fxmlLoader.getController();
                songController.setData(song);
                container.getChildren().add(vBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Play/pause song
    @FXML
    private void handlePlayPauseAction(MouseEvent event) {
        isPlaying = !isPlaying;
        playPauseButton.setText(isPlaying ? "⏸ Pause" : "▶ Play");
        System.out.println(isPlaying ? "Playing song..." : "Paused song.");
    }

    // Play the next song
    @FXML
    private void handleNextAction(MouseEvent event) {
        if (allSongs != null && !allSongs.isEmpty()) {
            currentSongIndex = (currentSongIndex + 1) % allSongs.size();
            playSong(allSongs.get(currentSongIndex));
        }
    }

    // Play the previous song
    @FXML
    private void handlePreviousAction(MouseEvent event) {
        if (allSongs != null && !allSongs.isEmpty()) {
            currentSongIndex = (currentSongIndex - 1 + allSongs.size()) % allSongs.size();
            playSong(allSongs.get(currentSongIndex));
        }
    }

    // Function to simulate playing a song
    private void playSong(Song song) {
        System.out.println("Playing song: " + song.getName() + " by " + song.getArtist());
        // Implement actual song playing logic here
    }

    // Method to return a list of recently played songs (dummy data for now)
    public List<Song> getRecentlyPlayed() {
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
        song.setCover("/img/thitis_sado.png");
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




    // Method to return a list of favorite songs (dummy data for now)
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
