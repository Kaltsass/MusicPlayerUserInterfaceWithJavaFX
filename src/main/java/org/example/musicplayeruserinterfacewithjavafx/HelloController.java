package org.example.musicplayeruserinterfacewithjavafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Slider;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.geometry.Point2D;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import java.sql.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.Desktop;
import java.net.URI;
import javafx.stage.Modality;
import org.example.musicplayeruserinterfacewithjavafx.YoutubeAPI;
import javafx.application.HostServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.stream.Collectors;


public class HelloController implements Initializable {



    @FXML public HBox likedSongsContainer;
    @FXML public ScrollPane mainScrollPane;
    @FXML HBox madeForYouContainer;
    @FXML private HBox artistLabelAndButtonContainer;
    @FXML private HBox madeForYouLabelAndButtonContainer;
    @FXML private Button artistActionButton;
    @FXML private HBox savedArtistsHBox;
    @FXML private ImageView albumCoverImage;
    @FXML private Label songTitleLabel;
    @FXML private HBox favoriteContainer;
    @FXML private HBox recentlyPlayedContainer;
    @FXML private HBox albumsContainer;
    @FXML private TextField searchBar;
    @FXML private VBox searchResultsContainer;
    @FXML private Slider playbackSlider;
    @FXML private Button playPauseButton;
    @FXML private Button madeForYouButton ;
    @FXML private Button nextButton;
    @FXML private Button prevButton;
    @FXML private Button btnnewplaylist;
    @FXML private VBox mainVBox;
    @FXML private ScrollPane scrollPaneMadeForYou;
    @FXML private ScrollPane scrollPaneRecentlyPlayed;
    @FXML private ScrollPane scrollPaneLikedSongs;
    @FXML private ScrollPane scrollPaneTopCharts;
    @FXML private ScrollPane scrollPaneArtists;
    @FXML private ScrollPane scrollPaneAlbums;
    @FXML public HBox playlistSongsContainer;
    @FXML private Label labelMadeForYou;
    @FXML private Label labelRecentlyPlayed;
    @FXML private Label labelLikedSongs;
    @FXML private Label labelTopCharts;
    @FXML private Label labelArtists;
    @FXML private Label labelAlbums;

    @FXML
    private Label labelPlaylist;

    @FXML
    private ScrollPane scrollPanelPlaylist;



    private MediaPlayerManager mediaPlayerManager;
    @FXML
    private ListView<String> playlistListView;
    @FXML
    private Label artistInfomLabel;
    @FXML
    private ArtistInformationController artistInformationController;

    private ObservableList<String> playlistItems;



    @FXML
    private void handleSearch(ActionEvent event) {
        String query = searchBar.getText();
        if (query.isEmpty()) {
            return;
        }
        searchSongs(query);
    }

    @FXML
    private TextField tf_newUsername;
    @FXML
    private PasswordField tf_newPassword;
    @FXML
    private VBox likedContainer;// Ο VBox που περιέχει τα αγαπημένα τραγούδια
    @FXML
    private Button button_account; // Account button in the main window

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

    @FXML
    private void handleLogout(ActionEvent event) {
        // Reset the account button text to "Log In"
        button_account.setText("Log In");
        // Clear any user session data
        clearUserData();

        System.out.println("User logged out successfully.");
    }

    @FXML
    private Button exploreButton;

    @FXML
    private void handleExploreButtonClick() {
        try {
            // Load the ExploreView.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ExploreView.fxml"));
            AnchorPane explorePane = loader.load();

            // Create a new stage (popup window)
            Stage popupStage = new Stage();
            popupStage.setTitle("Explore");
            popupStage.initModality(Modality.APPLICATION_MODAL); // Blocks interaction with the main window
            popupStage.setResizable(false);

            // Set the scene for the new stage
            Scene popupScene = new Scene(explorePane);
            popupStage.setScene(popupScene);

            // Show the popup window
            popupStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private Button radioButton;

    @FXML
    private void handleRadioButtonClick() {
        try {
            // Load the RadioView.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RadioView.fxml"));
            AnchorPane radioPane = loader.load();

            // Create a new stage (popup window)
            Stage popupStage = new Stage();
            popupStage.setTitle("Radio");
            popupStage.initModality(Modality.APPLICATION_MODAL); // Blocks interaction with the main window
            popupStage.setResizable(false);

            // Set the scene for the new stage
            Scene popupScene = new Scene(radioPane);
            popupStage.setScene(popupScene);

            // Show the popup window
            popupStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void handleLeftButtonClick(ActionEvent event) {
        // Λαμβάνουμε το κουμπί που πατήθηκε
        Button clickedButton = (Button) event.getSource();

        // Αντιστοιχίζουμε τα κουμπιά με τα αντίστοιχα Label και ScrollPane
        // Αλλαγή του target Label σε Node για να μπορεί να τραβήξει και το κουμπί
        Node targetLabel = null;
        ScrollPane targetScrollPane = null;

        // Ανάλογα με το κείμενο του κουμπιού, επιλέγουμε το αντίστοιχο Label και ScrollPane
        switch (clickedButton.getText()) {
            case "Made For You":
                targetLabel = madeForYouLabelAndButtonContainer;           // Το Label για το "Made For You"
                targetScrollPane = scrollPaneMadeForYou; // Το ScrollPane για το "Made For You"
                break;
            case "Recently Played":
                targetLabel = labelRecentlyPlayed;         // Το Label για τα "Recently Played"
                targetScrollPane = scrollPaneRecentlyPlayed; // Το ScrollPane για τα "Recently Played"
                break;
            case "Liked Songs":
                targetLabel = labelLikedSongs;             // Το Label για τα "Liked Songs"
                targetScrollPane = scrollPaneLikedSongs;   // Το ScrollPane για τα "Liked Songs"
                break;
            case "Albums":
                targetLabel = labelAlbums;                 // Το Label για τα "Albums"
                targetScrollPane = scrollPaneAlbums;       // Το ScrollPane για τα "Albums"
                break;
            case "Artists":
                targetLabel = artistLabelAndButtonContainer;            // Το Label για τους "Artists"
                targetScrollPane = scrollPaneArtists;      // Το ScrollPane για τους "Artists"
                break;
            case "Top Charts":
                targetLabel = labelTopCharts;              // Το Label για τα "Top Charts"
                targetScrollPane = scrollPaneTopCharts;    // Το ScrollPane για τα "Top Charts"
                break;
            case "Playlists":
                targetLabel = labelPlaylist; // Το Label με το σωστό fx:id
                targetScrollPane = scrollPanelPlaylist; // Το ScrollPane με το σωστό fx:id
                break;
        }

        // Εάν έχουν επιλεγεί το Label και το ScrollPane, προχωράμε με την αφαίρεση και την προσθήκη τους
        if (targetLabel != null && targetScrollPane != null) {
            // Αφαιρούμε το Label και το ScrollPane από τις τρέχουσες θέσεις τους στο VBox
            mainVBox.getChildren().remove(targetLabel);
            mainVBox.getChildren().remove(targetScrollPane);

            // Προσθέτουμε το ScrollPane και το Label στην κορυφή του VBox
            mainVBox.getChildren().add(0, targetScrollPane); // Πρώτα προσθέτουμε το ScrollPane
            mainVBox.getChildren().add(0, targetLabel);      // Στη συνέχεια προσθέτουμε το Label

            // Scroll the mainScrollPane to the top
            mainScrollPane.setVvalue(0);  // This will scroll to the top of the ScrollPane
        }
    }


    @FXML
    private ListView<Hyperlink> newsListView;

    @FXML
    private void handleArticleClick(MouseEvent event) {
        // Ensure you're using newsListView from ExploreController here
        Hyperlink selectedArticle = newsListView.getSelectionModel().getSelectedItem();
        if (selectedArticle != null) {
            String url = selectedArticle.getText(); // URL should be in the text
            System.out.println("Opening URL: " + url);  // Debugging log
            try {
                // Open the URL in the default web browser
                Desktop.getDesktop().browse(new URI(url));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No article selected.");
        }
    }


    @FXML
    private Button backButton;  // Ensure you have a reference to the Back button in your controller

    @FXML
    private void handleBackButtonClick() {
        try {
            // Load the HelloView.fxml file (the main page)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            AnchorPane helloPage = loader.load();

            // Create a new Scene for the Hello page
            Scene helloScene = new Scene(helloPage);

            // Get the current stage (the window) where the Explore scene is being shown
            Stage stage = (Stage) backButton.getScene().getWindow();  // Using the Back button here

            // Set the new scene to the current stage (back to the Hello page)
            stage.setScene(helloScene);
            stage.show();

        } catch (IOException e) {
            System.out.println(getClass().getResource("hello-view.fxml"));
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

    private List<Song> favorites = new ArrayList<>();

    public void onLikedSongClicked(Song song) {
        if (!favorites.contains(song)) {
            favorites.add(song);  // Αν το τραγούδι δεν υπάρχει, το προσθέτουμε
            addSongToLikedContainer(song);  // Προσθήκη στο UI (likedContainer)
        }
    }

    // Μέθοδος για την εμφάνιση του τραγουδιού στο likedContainer
    private void addSongToLikedContainer(Song song) {
        try {
            // Δημιουργία ενός VBox για το τραγούδι
            VBox vBox = new VBox();
            vBox.setSpacing(10);

            // Δημιουργία του Label για το όνομα του τραγουδιού
            Label songLabel = new Label(song.getName() + " - " + song.getArtist());
            songLabel.setStyle("-fx-text-fill: white;");

            // Δημιουργία του ImageView για το εξώφυλλο του τραγουδιού
            if (song.getCover() != null && !song.getCover().isEmpty()) {
                Image albumCoverImage = new Image(song.getCover());
                ImageView imageView = new ImageView(albumCoverImage);
                imageView.setFitWidth(50);
                imageView.setFitHeight(50);
                vBox.getChildren().add(imageView);  // Προσθήκη του εξώφυλλου
            }

            // Προσθήκη του Label με το όνομα του τραγουδιού
            vBox.getChildren().add(songLabel);

            // Προσθήκη του VBox στο likedContainer
            likedContainer.getChildren().add(vBox);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static HelloController instance;

    public static HelloController getInstance() {
        if (instance == null) {
            instance = new HelloController();  // Δημιουργία του instance
        }
        return instance;
    }

    private List<Song> recentlyPlayed;
    private List<Song> allSongs;
    private List<Song> likedSongs;
    private boolean isPlaying = false;
    private int currentSongIndex = 0;
    private MediaPlayer mediaPlayer;
    private List<Song> playlistSongs;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allSongs = new ArrayList<>();
        recentlyPlayed = new ArrayList<>(getRecentlyPlayed());
        favorites = new ArrayList<>(getFavorites());
        likedSongs = new ArrayList<>();
        playlistSongs = new ArrayList<>();

        // Initialize the set to avoid NullPointerException
        addedArtistNames = new HashSet<>();

        // Combine lists into one for all songs
        allSongs.addAll(recentlyPlayed);
        allSongs.addAll(favorites);
        allSongs.addAll(likedSongs);
        allSongs.addAll(playlistSongs);

        mediaPlayerManager = new MediaPlayerManager();

        playbackSlider.setMin(0);
        playbackSlider.setMax(100);
        playbackSlider.setValue(0);

        playbackSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println("Slider moved to: " + newVal.doubleValue());
        });

        loadSongs(recentlyPlayed, recentlyPlayedContainer);
        loadSongs(favorites, favoriteContainer);
        loadSongs(likedSongs, likedSongsContainer);
        loadSongs(playlistSongs, playlistSongsContainer);

        playlistListView.setItems(FXCollections.observableArrayList()); // Initialize list view
        playlistItems = FXCollections.observableArrayList();
        playlistListView.setItems(playlistItems);

        playlistItems = FXCollections.observableArrayList();
        playlistListView.setItems(playlistItems);
        //loadPlaylistsFromFile(); // Φορτώνουμε τα playlists από το αρχείο κατά την εκκίνηση


        // Φορτώνουμε τα playlists από το φάκελο κατά την εκκίνηση
        loadPlaylistsFromFolder();

        artistActionButton.setOnMouseClicked(event -> openArtistInformationWindow(artistActionButton.getText()));
        if (artistInformationController != null) {
            artistInformationController.setHelloController(this); // Περάστε το instance του HelloController
        }
       // Fetch top albums and their details
        List<String> albumsList = APITopAlbums.fetchTopAlbums(); // Fetch albums from API

        // Fetch album IDs for retrieving songs
        List<String> albumIds = APITopAlbums.getAlbumIds(); // A new method to fetch album IDs

        // Fetch songs for the top 10 albums
        Map<String, List<String>> albumSongsMap = APITopAlbums.fetchSongsFromTopAlbums(albumIds);

        // Load albums into the UI with click listeners to show songs
        loadAlbums(albumsList, albumSongsMap);
        // Made For You
        madeForYouButton.setOnAction(event -> handleMadeForYou());

        // Attach scroll event forwarding to all child ScrollPanes
        setupScrollForwarding();

        // Add a scroll event filter to the mainScrollPane
        mainScrollPane.addEventFilter(ScrollEvent.SCROLL, event -> {
            if (!event.isConsumed()) {
                // Amount scrolled
                double deltaY = event.getDeltaY();
                // Adjust this factor to slow down scrolling (e.g., 0.5 for half speed)
                double scrollSpeedFactor = 0.5;
                // Current scroll position
                double currentValue = mainScrollPane.getVvalue();
                mainScrollPane.setVvalue(currentValue - (deltaY / mainScrollPane.getHeight()) * scrollSpeedFactor);
                // Consume the event to prevent propagation
                event.consume();
            }
        });

    }




    @FXML
    private void handleAboutUsButtonClick(ActionEvent event) {
        try {
            // Load the AboutUsView.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/musicplayeruserinterfacewithjavafx/AboutUsView.fxml"));
            Parent root = loader.load();

            // Set up the modal stage
            Stage stage = new Stage();
            stage.setTitle("About Us");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // Ensures it blocks interaction with the main window
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void loadSongs(List<Song> songList, HBox container) {
        try {

            for (Song song : songList) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("song.fxml"));
                VBox vBox = fxmlLoader.load();
                SongController songController = fxmlLoader.getController();
                songController.setData(song);
                songController.setHelloController(this);

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

    public void addToLikedSongs(Song song) {
        if (likedSongs.contains(song)) {  // If the song is already in the liked songs list
            // Show confirmation dialog before removing the song
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remove Song");
            alert.setHeaderText("This song is already liked.");
            alert.setContentText("Do you want to remove it from liked songs?");

            // Add "Yes" and "No" buttons
            ButtonType yesButton = new ButtonType("Yes");
            ButtonType noButton = new ButtonType("No");

            alert.getButtonTypes().setAll(yesButton, noButton);

            // Wait for the user to respond
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == yesButton) {
                // Remove the song from the liked songs list if user clicks "Yes"
                likedSongs.remove(song);

                likedSongsContainer.getChildren().clear();  // Clear the current container to refresh the UI
                loadSongs(likedSongs, likedSongsContainer);  // Reload the liked songs into the container
            }
        } else {
            likedSongs.add(0, song);  // Add to the top (like recently played)
            likedSongsContainer.getChildren().clear();  // Clear the current container to refresh the UI
            loadSongs(likedSongs, likedSongsContainer);  // Reload the liked songs into the container
        }
    }



    private HostServices hostServices; // Field to store HostServices

    // Setter method to receive the HostServices instance from HelloApplication
    public void setHostServices(HostServices hostServices) {
        this.hostServices = hostServices;
    }

    // Method to open a URL using HostServices
    public void openYouTubeLink(String url) {
        if (hostServices != null) {
            hostServices.showDocument(url); // Opens the URL in the default browser
        } else {
            System.out.println("HostServices not available");
        }
    }

    // Method to handle song search and display YouTube link
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

                // Fetch YouTube video URL
                String youtubeUrl = YoutubeAPI.getYouTubeVideoUrl(songTitle, artistName);
                song.setYoutubeUrl(youtubeUrl);

                // Create the song title label
                Label resultLabel = new Label((i + 1) + ". " + songTitle + " by " + artistName);
                resultLabel.setStyle("-fx-text-fill: white;");

                // Create the album cover ImageView
                ImageView imageView = null;
                if (coverUrl != null && !coverUrl.isEmpty()) {
                    try {
                        Image albumCoverImage = new Image(coverUrl);
                        imageView = new ImageView(albumCoverImage);
                        imageView.setFitWidth(50);  // Set the width of the album cover
                        imageView.setFitHeight(50); // Set the height of the album cover
                    } catch (Exception e) {
                        System.out.println("Error creating ImageView for song cover: " + e.getMessage());
                    }
                }

                // Create a VBox for song info (title + YouTube link)
                VBox infoContainer = new VBox(5); // 5px spacing between elements vertically
                infoContainer.getChildren().add(resultLabel); // Add song title

                // Add YouTube link if available
                if (youtubeUrl != null) {
                    Hyperlink youtubeLink = new Hyperlink("Watch on YouTube");
                    youtubeLink.setStyle("-fx-text-fill: blue;");
                    youtubeLink.setOnAction(e -> openYouTubeLink(youtubeUrl)); // Open the YouTube link
                    infoContainer.getChildren().add(youtubeLink); // Add YouTube link below the title
                }

                // Create an HBox to hold both the album cover and the song info (on the right)
                HBox songContainer = new HBox(10); // 10px spacing between album cover and song info

                // Add album cover to HBox (left side)
                if (imageView != null) {
                    imageView.setOnMouseClicked(mouseEvent -> playSong(song)); // Play song when cover is clicked
                    songContainer.getChildren().add(imageView); // Add album cover on the left
                }

                // Add song info (title + YouTube link) to HBox (right side)
                songContainer.getChildren().add(infoContainer);

                // Add the HBox to the results container
                resultLabel.setOnMouseClicked(mouseEvent -> playSong(song)); // Play song when title is clicked
                searchResultsContainer.getChildren().add(songContainer);
            }

            searchResultsContainer.setVisible(true);


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


    public void playSong(Song song) {
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
            //Parent root = FXMLLoader.load(getClass().getResource("new-playlist-popup.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("new-playlist-popup.fxml"));
            Parent root = loader.load();

            // Get the PopupController instance
            PopupController popupController = loader.getController();

            // Pass HelloController to PopupController so it can call addPlaylist
            popupController.setMainController(this);
            Stage popupplaylist = new Stage();
            popupplaylist.initStyle(StageStyle.UNDECORATED);
            popupplaylist.setScene(new Scene(root, 600, 401));
            popupplaylist.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    // Μέθοδος για να ελέγξουμε αν το playlist υπάρχει ήδη
    public boolean isPlaylistExist(String playlistName) {
        return playlistItems.contains(playlistName);
    }

    // Μέθοδος για να προσθέσουμε νέο playlist
    public void addPlaylist(String playlistName) {
        if (playlistName == null || playlistName.trim().isEmpty()) {
            // Εμφάνιση alert αν το playlist υπάρχει ήδη
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Το Playlist Name είναι κενό!");
            alert.setContentText("Παρακαλούμε εισάγετε ένα έγκυρο όνομα για το playlist σας.");
            alert.showAndWait();
            return;
        }
        File playlistFile = new File("playlists/" + playlistName + ".txt");
        if (playlistFile.exists()) {
            // Εμφάνιση alert αν το playlist υπάρχει ήδη
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Duplicate Playlist Name");
            alert.setContentText("Αυτό το όνομα playlist υπάρχει ήδη. Παρακαλούμε εισάγετε άλλο όνομα για το Playlist σας.");
            alert.showAndWait();
        } else {
            try {
                if (playlistFile.createNewFile()) {
                    System.out.println("Playlist created successfully: " + playlistFile.getName());
                    loadPlaylistsFromFolder(); // Ενημέρωση της λίστας στο UI
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        /*
        // Αν το playlist δεν υπάρχει ήδη, το προσθέτουμε
        if (!isPlaylistExist(playlistName)) {
            playlistItems.add(playlistName);
            savePlaylistsToFile(); // Αποθηκεύουμε τη νέα λίστα
            refreshPlaylists();
        } else {
            // Εμφάνιση alert αν το playlist υπάρχει ήδη
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Duplicate Playlist Name");
            alert.setContentText("Αυτό το όνομα playlist υπάρχει ήδη. Παρακαλούμε εισάγετε άλλο όνομα για το Playlist σας.");
            alert.showAndWait();
        }*/
    }

    // Μέθοδος για να φορτώσουμε τα playlist από το αρχείο
    private void loadPlaylistsFromFile() {
        File file = new File("playlists.txt"); // Ο φάκελος και το όνομα του αρχείου
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.trim().isEmpty()) {
                        playlistItems.add(line); // Προσθήκη του playlist στο ObservableList
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    // Μέθοδος για να φορτώσουμε τα playlist από το φάκελο playlists
    private void loadPlaylistsFromFolder() {
        File playlistsFolder = new File("playlists");
        if (!playlistsFolder.exists() || !playlistsFolder.isDirectory()) {
            System.out.println("Playlists folder does not exist or is not a directory.");
            return;
        }

        File[] playlistFiles = playlistsFolder.listFiles((dir, name) -> name.endsWith(".txt"));
        if (playlistFiles != null) {
            playlistItems.clear();
            for (File file : playlistFiles) {
                String fileName = file.getName();
                String playlistName = fileName.substring(0, fileName.lastIndexOf('.')); // Αφαιρεί την κατάληξη .txt
                playlistItems.add(playlistName);
            }
            playlistListView.setItems(playlistItems);
        }
    }

    @FXML
    private void handlePlaylistSelection(MouseEvent event) {

        // Get the selected playlist from the ListView
        String selectedPlaylist = playlistListView.getSelectionModel().getSelectedItem();
        if (selectedPlaylist != null) {
            try {
                // Load the FXML file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("playlist-view.fxml"));
                Parent root = loader.load();

                // Get the controller of the loaded FXML
                PlaylistViewController controller = loader.getController();

                // Pass the selected playlist name to the controller
                controller.setPlaylistName(selectedPlaylist);

                // Create a new stage for the playlist view
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
                List<Song> playlistSongs = loadSongsFromPlaylist(selectedPlaylist);
                playlistSongsContainer.getChildren().clear();
                loadSongs(playlistSongs, playlistSongsContainer);
                System.out.println("Loaded songs for playlist: " + selectedPlaylist);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No playlist selected.");
        }

    }
    private List<Song> loadSongsFromPlaylist(String playlistName) {
        List<Song> songs = new ArrayList<>();
        File playlistFile = new File("playlists/" + playlistName + ".txt");
        if (playlistFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(playlistFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("Reading line: " + line);
                    String[] parts = line.split(" - ");
                    if (parts.length == 4) {
                        Song song = new Song();
                        song.setName(parts[0]);
                        song.setArtist(parts[1]);
                        song.setPreviewUrl(parts[2]);
                        song.setCover(parts[3]);
                        System.out.println("Added song: " + song.getName() + " by " + song.getArtist());

                        songs.add(song);
                    } else {
                        System.out.println("Invalid line format: " + line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Playlist file not found: " + playlistFile.getAbsolutePath());
        }
        return songs;
    }

    private void loadPlaylistsIntoListView() {
        File playlistsFolder = new File("playlists");
        if (playlistsFolder.exists() && playlistsFolder.isDirectory()) {
            List<String> playlistNames = List.of(playlistsFolder.list((dir, name) -> name.endsWith(".txt")))
                    .stream()
                    .map(name -> name.substring(0, name.lastIndexOf('.')))
                    .collect(Collectors.toList());
            playlistListView.getItems().addAll(playlistNames);
        } else {
            System.out.println("Playlists folder does not exist or is not a directory.");
        }
    }

    // Μέθοδος για συγχρονισμό του ListView
    private void refreshPlaylists() {

        playlistListView.getItems().clear();
        playlistListView.getItems().addAll(playlistItems);
    }

    // Μέθοδος για να αποθηκεύσουμε τα playlist στο αρχείο
    public void savePlaylistsToFile() {
        File file = new File("playlists.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String playlist : playlistItems) {
                writer.write(playlist);
                writer.newLine(); // Καταχώρηση κάθε playlist σε νέα γραμμή
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openArtistInformationWindow(String artistName) {
        try {
            // Φόρτωση του FXML αρχείου
            FXMLLoader loader = new FXMLLoader(getClass().getResource("artist-information.fxml"));
            Parent root = loader.load();


            // Get the ArtistInformationController
            ArtistInformationController artistInfoController = loader.getController();

            // Pass the reference of HelloController to ArtistInformationController
            artistInfoController.setHelloController(this);


            // Δημιουργία νέου Stage
            Stage stage = new Stage();
            stage.setTitle("Artist Information");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
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


    //Artists Button

    private Set<String> addedArtistNames;  // Set to track added artist names


    public void addArtistToScrollPane(String artistName, Image artistImage) {
        if (savedArtistsHBox != null) {
            // Check if the artist has already been added
            if (addedArtistNames != null && addedArtistNames.contains(artistName)) {
                return;  // Skip if artist has already been added
            }

            // Create a VBox to hold the image and label
            VBox artistVBox = new VBox();
            artistVBox.setSpacing(10);  // Space between image and label
            artistVBox.setStyle("-fx-alignment: center;");

            // Create an ImageView for the artist's image
            ImageView artistImageView = new ImageView(artistImage);
            artistImageView.setFitWidth(200);
            artistImageView.setFitHeight(200);
            artistImageView.setPreserveRatio(true);

            // Create a Label for the artist's name
            Label artistLabel = new Label(artistName);
            artistLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");

            // Add the ImageView and Label to the VBox
            artistVBox.getChildren().addAll(artistImageView, artistLabel);

            // Add the VBox to the HBox
            savedArtistsHBox.getChildren().add(artistVBox);

            // Track the artist to avoid duplicates
            addedArtistNames.add(artistName);
        } else {
            System.out.println("savedArtistsHBox is null, cannot add artist.");
        }
    }

    //Albums Button

    public void loadAlbums(List<String> albumsList, Map<String, List<String>> albumSongsMap) {
        try {
            for (String albumInfo : albumsList) {
                String[] parts = albumInfo.split(", ");
                String title = parts[0].split(": ")[1];
                String artist = parts[1].split(": ")[1];
                String coverImageUrl = parts[2].split(": ")[1];

                VBox albumItem = new VBox(10);
                albumItem.setStyle("-fx-alignment: center; -fx-cursor: hand;");

                Image coverImage = new Image(coverImageUrl, 200, 200, true, true);
                ImageView imageView = new ImageView(coverImage);
                imageView.setFitWidth(200);
                imageView.setFitHeight(200);
                imageView.setPreserveRatio(true);

                Label albumLabel = new Label(artist + " - " + title);
                albumLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10;");

                albumItem.getChildren().addAll(imageView, albumLabel);

                // Add click event listener
                albumItem.setOnMouseClicked(event -> {
                    List<String> songs = albumSongsMap.get(title);
                    if (songs != null) {
                        displaySongs(artist, title, songs);
                    } else {
                        System.out.println("No songs found for album: " + title);
                    }
                });

                albumsContainer.getChildren().add(albumItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading albums into the UI: " + e.getMessage());
        }
    }



    private void displaySongs(String artist, String albumTitle, List<String> songs) {
        Stage songStage = new Stage();
        songStage.setTitle("Songs of " + albumTitle + " by " + artist);

        VBox songListContainer = new VBox(10);
        songListContainer.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: #2b2b2b;");

        Label titleLabel = new Label("Songs from " + albumTitle);
        titleLabel.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-padding: 10;");
        songListContainer.getChildren().add(titleLabel);

        for (String song : songs) {
            Label songLabel = new Label(song);
            songLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
            songListContainer.getChildren().add(songLabel);
        }

        ScrollPane scrollPane = new ScrollPane(songListContainer);
        scrollPane.setStyle("-fx-background: transparent; -fx-padding: 10;");
        scrollPane.setFitToWidth(true);

        Scene scene = new Scene(scrollPane, 400, 600);
        songStage.setScene(scene);
        songStage.show();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleMadeForYou() {
        if (likedSongs.isEmpty()) {
            showError("No liked songs available to generate recommendations.");
            return;
        }

        Set<String> artistNames = likedSongs.stream()
                .map(Song::getArtist)
                .collect(Collectors.toSet());

        List<Song> recommendedSongs = new ArrayList<>();
        for (String artist : artistNames) {
            try {
                JsonArray artistTracks = APIDeezerInformation.fetchArtistTracks(artist);
                // Limit to 4 tracks per artist
                int trackLimit = 4;
                for (int i = 0; i < artistTracks.size() && i < trackLimit; i++) {
                    JsonObject trackObject = artistTracks.get(i).getAsJsonObject();
                    Song song = new Song();
                    song.setName(trackObject.get("title").getAsString());
                    song.setArtist(trackObject.get("artist").getAsJsonObject().get("name").getAsString());
                    song.setCover(trackObject.get("album").getAsJsonObject().get("cover").getAsString());
                    song.setPreviewUrl(trackObject.get("preview").getAsString());
                    recommendedSongs.add(song);
                }
            } catch (IOException e) {
                System.err.println("Error fetching songs for artist: " + artist);
                e.printStackTrace();
            }
        }

        // Display only the limited recommended songs
        loadSongs(recommendedSongs, madeForYouContainer);
    }

    private void setupScrollForwarding() {
        for (Node child : mainVBox.getChildren()) {
            if (child instanceof ScrollPane childScrollPane) {
                childScrollPane.addEventFilter(ScrollEvent.SCROLL, event -> {
                    boolean isEmpty = isChildScrollPaneEmpty(childScrollPane);
                    double deltaY = event.getDeltaY();
                    boolean atTop = childScrollPane.getVvalue() <= 0;
                    boolean atBottom = childScrollPane.getVvalue() >= 1;

                    // Forward the scroll event to the mainScrollPane if:
                    // - The child ScrollPane is empty, OR
                    // - The scroll is upwards (deltaY > 0) and the child is at the top, OR
                    // - The scroll is downwards (deltaY < 0) and the child is at the bottom
                    if (isEmpty || (deltaY > 0 && atTop) || (deltaY < 0 && atBottom)) {
                        event.consume(); // Consume the event in the child ScrollPane
                        mainScrollPane.fireEvent(event); // Forward the event to the mainScrollPane
                    }
                });
            }
        }
    }

    private boolean isChildScrollPaneEmpty(ScrollPane scrollPane) {
        Node content = scrollPane.getContent();
        if (content instanceof VBox vbox) {
            return vbox.getChildren().isEmpty(); // Check if the VBox has no children
        }
        return content == null; // If no content is set, it's empty
    }





}