package org.example.musicplayeruserinterfacewithjavafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.Song;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Parent;
import javafx.scene.Scene;



import java.sql.*;


import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class HelloController implements Initializable {

    @FXML private ImageView albumCoverImage;
    @FXML private Label songTitleLabel;
    @FXML private HBox favoriteContainer;
    @FXML private HBox recentlyPlayedContainer;
    @FXML private TextField searchBar;
    @FXML private VBox searchResultsContainer;
    @FXML private Slider playbackSlider;
    @FXML private Button playPauseButton;
    @FXML private Button nextButton;
    @FXML private Button prevButton;
    @FXML private Button btnnewplaylist;
    @FXML
    private TextField tf_newUsername;

    @FXML
    private PasswordField tf_newPassword;
    private MediaPlayerManager mediaPlayerManager;
    @FXML
    private Button likedSong;  // Κουμπί για την καρδιά (like/unlike)

    private boolean isLiked = false;  // Σημειώνει αν το τραγούδι είναι liked ή όχι

    // Μέθοδος για την ενημέρωση του κουμπιού καρδιάς
    private void updateHeartButton() {
        if (isLiked) {
            likedSong.setText("❤");  // Αν είναι αγαπημένο, δείχνουμε την κόκκινη ή πράσινη καρδιά
            likedSong.getStyleClass().add("liked");  // Προσθήκη της κλάσης "liked" για πράσινη καρδιά
        } else {
            likedSong.setText("🤍");  // Αν δεν είναι αγαπημένο, δείχνουμε την άσπρη καρδιά
            likedSong.getStyleClass().remove("liked");  // Αφαίρεση της κλάσης "liked" για άσπρη καρδιά
        }
    }

    // Μέθοδος που καλείται όταν γίνεται κλικ στην καρδιά
    @FXML
    private void handleLikeButtonClick(MouseEvent event) {
        isLiked = !isLiked;  // Εναλλάσσουμε την κατάσταση του like
        updateHeartButton();  // Ενημερώνουμε το κουμπί της καρδιάς
        System.out.println(isLiked ? "Song added to favorites!" : "Song removed from favorites!");
    }
    @FXML
    private void handleSearch(ActionEvent event) {
        String query = searchBar.getText();
        if (query.isEmpty()) {
            return;
        }
        searchSongs(query);
    }


    @FXML private Button button_account; // Account button in the main window

    // Handle Account button click

    public void updateAccountButton(String username) {
        button_account.setText(username);  // Update the account button text to new username
    }
    @FXML
    private void handleAccountButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("account.fxml"));
            Parent root = loader.load();

            // Get the controller of the popup and pass the reference of the main controller
            AccountPopupController accountPopupController = loader.getController();
            accountPopupController.setMainController(this);

            // Create a new scene for the popup
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);

            // Optionally, set a title for the popup
            stage.setTitle("Account Details");

            // Show the popup window
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void openLogoutPopup() {
        // You can create a simple confirmation popup asking if they want to log out
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Log Out");
        alert.setHeaderText("Are you sure you want to log out?");
        alert.setContentText("Click OK to log out, or Cancel to stay logged in.");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                handleLogout();  // Call logout method if they confirm
            }
        });
    }
    @FXML
    private void handleLogout() {
        // 1. Reset the account button text to "Log In"
        button_account.setText("Log In");

        // 2. Optionally, clear any user session data (e.g., username, preferences, etc.)
        clearUserData();

        System.out.println("User logged out successfully.");
    }

    private void clearUserData() {
        // Example: Reset username or any other session data
        // username = null;
        // Clear any other global variables or session data if necessary
    }

    private List<Song> recentlyPlayed;
    private List<Song> favorites;
    private List<Song> allSongs;
    private boolean isPlaying = false;
    private int currentSongIndex = 0;
    private MediaPlayer mediaPlayer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allSongs = new ArrayList<>();
        recentlyPlayed = new ArrayList<>(getRecentlyPlayed());
        favorites = new ArrayList<>(getFavorites());

        // Combine lists into one for all songs
        allSongs.addAll(recentlyPlayed);
        allSongs.addAll(favorites);

        mediaPlayerManager = new MediaPlayerManager();

        playbackSlider.setMin(0);
        playbackSlider.setMax(100);
        playbackSlider.setValue(0);

        playbackSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println("Slider moved to: " + newVal.doubleValue());
        });

        loadSongs(recentlyPlayed, recentlyPlayedContainer);
        loadSongs(favorites, favoriteContainer);
    }


    private void loadSongs(List<Song> songList, HBox container) {
        try {

            for (Song song : songList) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("song.fxml"));
                VBox vBox = fxmlLoader.load();
                SongController songController = fxmlLoader.getController();
                songController.setData(song);

                Label songLabel = new Label(song.getName() + " - " + song.getArtist());
                songLabel.setStyle("-fx-text-fill: white;");

                // Προσθέτουμε το event listener για το κλικ στο τραγούδι
                vBox.setOnMouseClicked(event -> playSong(song));  // Όταν το τραγούδι πατηθεί, παίζει το τραγούδι

                container.getChildren().add(vBox);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


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

            searchResultsContainer.getChildren().clear();

            for (int i = 0; i < tracksArray.size(); i++) {
                JsonObject track = tracksArray.get(i).getAsJsonObject();
                String songTitle = track.get("title").getAsString();
                String artistName = track.getAsJsonObject("artist").get("name").getAsString();
                String previewUrl = track.get("preview").getAsString();
                String coverUrl = track.getAsJsonObject("album").get("cover_small").getAsString();

                Song song = new Song();
                song.setName(songTitle);
                song.setArtist(artistName);
                song.setPreviewUrl(previewUrl);
                song.setCover(coverUrl);

                Label resultLabel = new Label((i + 1) + ". " + songTitle + " by " + artistName);
                resultLabel.setStyle("-fx-text-fill: white;");

                if (coverUrl != null && !coverUrl.isEmpty()) {
                    try {
                        Image albumCoverImage = new Image(coverUrl);
                        ImageView imageView = new ImageView(albumCoverImage);
                        imageView.setFitWidth(50);
                        imageView.setFitHeight(50);
                        resultLabel.setGraphic(imageView);
                    } catch (Exception e) {
                        System.out.println("Error creating ImageView for song cover: " + e.getMessage());
                    }
                }

                resultLabel.setOnMouseClicked(mouseEvent -> playSong(song));
                searchResultsContainer.getChildren().add(resultLabel);
            }

            searchResultsContainer.setVisible(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @FXML
    private void toggleRecentlyPlayedVisibility(MouseEvent event) {
        boolean isVisible = recentlyPlayedContainer.isVisible();
        recentlyPlayedContainer.setVisible(!isVisible);
        System.out.println("Recently Played section is now " + (isVisible ? "hidden" : "visible"));
    }

    // Playback controls
    @FXML
    private void handlePlayPauseAction(MouseEvent event) {
        // Έλεγχος αν το MediaPlayer είναι null
        if (mediaPlayer == null) {
            return;  // Αν δεν υπάρχει mediaPlayer, κάνουμε επιστροφή (όταν δεν έχει φορτωθεί τραγούδι).
        }

        if (isPlaying) {
            // Αν το τραγούδι παίζει, το σταματάμε
            mediaPlayer.pause();
            playPauseButton.setText("▶ Play");  // Αλλάζουμε το κείμενο στο κουμπί σε Play
            isPlaying = false;
            System.out.println("Paused song.");
        } else {
            // Αν το τραγούδι είναι παγωμένο, το ξεκινάμε
            mediaPlayer.play();
            playPauseButton.setText("⏸ Pause");  // Αλλάζουμε το κείμενο στο κουμπί σε Pause
            isPlaying = true;
            System.out.println("Playing song...");
        }
    }


    @FXML
    private void handleLogout(ActionEvent event) {
        // Reset the account button text to "Log In"
        button_account.setText("Log In");

        // Clear any user session data
        clearUserData();

        System.out.println("User logged out successfully.");
    }

    @FXML
    private void handleNextAction(MouseEvent event) {
        if (allSongs != null && !allSongs.isEmpty()) {
            currentSongIndex = (currentSongIndex + 1) % allSongs.size();
            mediaPlayerManager.playSong(allSongs.get(currentSongIndex).getCover());  // Παίζουμε το επόμενο τραγούδι
            System.out.println("Playing next song: " + allSongs.get(currentSongIndex).getName());
        }
    }

    @FXML
    private void handlePreviousAction(MouseEvent event) {
        if (allSongs != null && !allSongs.isEmpty()) {
            currentSongIndex = (currentSongIndex - 1 + allSongs.size()) % allSongs.size();
            mediaPlayerManager.playSong(allSongs.get(currentSongIndex).getCover());  // Παίζουμε το προηγούμενο τραγούδι
            System.out.println("Playing previous song: " + allSongs.get(currentSongIndex).getName());
        }
    }


    private void playSong(Song song) {
        System.out.println("Playing song: " + song.getName() + " by " + song.getArtist());

        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        Media media = new Media(song.getPreviewUrl());
        mediaPlayer = new MediaPlayer(media);

        playbackSlider.setValue(0);
        mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            double progress = newValue.toMillis() / media.getDuration().toMillis() * 100;
            playbackSlider.setValue(progress);
        });

        mediaPlayer.play();
        isPlaying = true;
        playPauseButton.setText("⏸ Pause");

        if (song.getCover() != null && !song.getCover().isEmpty()) {
            Image albumCoverImage = new Image(song.getCover());
            this.albumCoverImage.setImage(albumCoverImage);
        }
        songTitleLabel.setText(song.getName());

        addToRecentlyPlayed(song);
    }


    private void addToRecentlyPlayed(Song song) {
        if (!recentlyPlayed.contains(song)) {
            recentlyPlayed.add(0, song);
        } else {
            recentlyPlayed.remove(song);
            recentlyPlayed.add(0, song);
        }

        recentlyPlayedContainer.getChildren().clear();
        loadSongs(recentlyPlayed, recentlyPlayedContainer);
    }

    public List<Song> getRecentlyPlayed() {
        List<Song> ls = new ArrayList<>();
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
    // Λειτουργία κουμπιού New Playlist
    public void OnButtonClick7() {
        System.out.println("Pressed");  //
        openPlaylistPopup();
    }

    private void openPlaylistPopup() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("new-playlist-popup.fxml"));
            Stage popupplaylist = new Stage();
            popupplaylist.initStyle(StageStyle.UNDECORATED);
            popupplaylist.setScene(new Scene(root, 600, 401));
            popupplaylist.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    private Song createSong(String name, String artist, String cover) {
        Song song = new Song();
        song.setName(name);
        song.setArtist(artist);
        song.setCover(cover);
        song.setPreviewUrl("https://www.deezer.com/preview"); // Προσωρινό preview URL για δοκιμές
        return song;
    }
}
