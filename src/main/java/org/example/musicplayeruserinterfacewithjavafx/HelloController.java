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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Parent;
import javafx.scene.Scene;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.Desktop;
import java.net.URI;
import javafx.stage.Modality;
import javafx.scene.layout.AnchorPane;


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
    private MediaPlayerManager mediaPlayerManager;




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

        // Δημιουργία αντικειμένου MediaPlayerManager
        mediaPlayerManager = new MediaPlayerManager();

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


    private void loadSongs(List<Song> songList, HBox container) {
        try {
            for (Song song : songList) {
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

    @FXML
    private Button exploreButton;

    @FXML
    private void handleExploreButtonClick() {
        try {
            // Load the Explore page FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ExploreView.fxml"));
            AnchorPane explorePage = loader.load();

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


    public void handleUpgrade(ActionEvent event) {
        try {
            // Open the Spotify Premium URL
            Desktop.getDesktop().browse(new URI("https://www.spotify.com/gr/premium/"));
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions such as unsupported Desktop or invalid URI
        }
    }


    public void openMapLink() {
        try {
            Desktop.getDesktop().browse(new URI("https://www.bing.com/maps?q=ihu+serres&FORM=HDRSC6&cp=41.075631%7E23.553572&lvl=16.0"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openWebsite() {
        try {
            Desktop.getDesktop().browse(new URI("https://www.ihu.gr/"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void handleDotsClick() {
        // Create a new Stage for the modal window
        Stage modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL); // Blocks interaction with the main window
        modalStage.setTitle("Options");

        // Create buttons for the options
        Button termsButton = new Button("Terms of Service");
        Button privacyButton = new Button("Privacy Policy"); // New button for Privacy Policy


        termsButton.setOnAction(e -> {
            openTermsOfService(); // Call the method to open the Terms link
            modalStage.close(); // Optionally close the modal
        });

        privacyButton.setOnAction(e -> {
            openPrivacyPolicy(); // Call the method to open the Privacy Policy link
            modalStage.close(); // Optionally close the modal
        });

        // Layout for the modal
        VBox layout = new VBox(10); // Spacing of 10
        layout.getChildren().addAll(termsButton, privacyButton);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // Set the Scene for the modal
        Scene modalScene = new Scene(layout, 200, 200);
        modalStage.setScene(modalScene);

        // Show the modal and wait until it is closed
        modalStage.showAndWait();
    }

    // Method to open the Terms of Service link
    private void openTermsOfService() {
        try {
            URI termsUri = new URI("https://policies.google.com/terms?hl=en");
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(termsUri);
            } else {
                System.out.println("Desktop browsing not supported on this system.");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log any errors
        }
    }

    // Method to open the Privacy Policy link
    private void openPrivacyPolicy() {
        try {
            URI privacyUri = new URI("https://policies.google.com/privacy?hl=en-US");
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(privacyUri);
            } else {
                System.out.println("Desktop browsing not supported on this system.");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log any errors
        }
    }


    @FXML
    private void handleSearch(ActionEvent event) {
        String query = searchBar.getText();
        if (query.isEmpty()) {
            return;
        }
        searchSongs(query);
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

            // Clear previous search results
            searchResultsContainer.getChildren().clear();

            // Add search results to UI
            for (int i = 0; i < tracksArray.size(); i++) {
                JsonObject track = tracksArray.get(i).getAsJsonObject();
                String songTitle = track.get("title").getAsString();
                String artistName = track.getAsJsonObject("artist").get("name").getAsString();
                String previewUrl = track.get("preview").getAsString(); // Preview URL
                String coverUrl = track.getAsJsonObject("album").get("cover_small").getAsString(); // Album cover

                Song song = new Song();
                song.setName(songTitle);
                song.setArtist(artistName);
                song.setPreviewUrl(previewUrl);
                song.setCover(coverUrl); // Αποθήκευση της φωτογραφίας του εξωφύλλου

                // Δημιουργία του Label για το τραγούδι
                Label resultLabel = new Label((i + 1) + ". " + songTitle + " by " + artistName);
                resultLabel.setStyle("-fx-text-fill: white;");

                // Δημιουργία ImageView για την φωτογραφία του άλμπουμ
                if (coverUrl != null && !coverUrl.isEmpty()) {
                    try {
                        Image albumCoverImage = new Image(coverUrl);
                        if (albumCoverImage.isError()) {
                            System.out.println("Error loading image from URL: " + coverUrl);
                        } else {
                            ImageView imageView = new ImageView(albumCoverImage);
                            imageView.setFitWidth(50);  // Ρύθμιση του πλάτους
                            imageView.setFitHeight(50); // Ρύθμιση του ύψους
                            resultLabel.setGraphic(imageView); // Προσθήκη εικόνας στο Label
                        }
                    } catch (Exception e) {
                        System.out.println("Error creating ImageView for song cover: " + e.getMessage());
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Cover URL is empty or invalid for song: " + songTitle);
                }

                resultLabel.setOnMouseClicked(mouseEvent -> playSong(song)); // Παίζουμε το τραγούδι όταν το κλικάρουμε
                searchResultsContainer.getChildren().add(resultLabel);
            }

            // Κάνε το container των αποτελεσμάτων ορατό
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

        // Δημιουργία αντικειμένου MediaPlayer με το preview URL
        Media media = new Media(song.getPreviewUrl());
        mediaPlayer = new MediaPlayer(media);

        // Ορισμός του Slider για τον έλεγχο της προόδου
        playbackSlider.setValue(0);
        mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            double progress = newValue.toMillis() / media.getDuration().toMillis() * 100;
            playbackSlider.setValue(progress);
        });

        mediaPlayer.play();
        isPlaying = true;
        playPauseButton.setText("⏸ Pause");

        // Ενημέρωση του ImageView και του Label για το όνομα και την εικόνα του άλμπουμ
        if (song.getCover() != null && !song.getCover().isEmpty()) {
            Image albumCoverImage = new Image(song.getCover());
            this.albumCoverImage.setImage(albumCoverImage);
        }
        songTitleLabel.setText(song.getName());
    }

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
