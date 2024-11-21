package org.example.musicplayeruserinterfacewithjavafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;


import javafx.scene.layout.VBox;
import model.Song;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HelloController {

    @FXML
    private HBox favoriteContainer;

    @FXML
    private HBox recentlyPlayedContainer; // Reference to the recentlyPlayedContainer

    @FXML
    private TextField searchBar; // Search input field from FXML

    @FXML
    private VBox searchResultsContainer; // Container for search results

    private List<Song> recentlyPlayed;
    private List<Song> favorites;

    // Toggles visibility of recently played section

    @FXML
    private void toggleRecentlyPlayedVisibility(MouseEvent event) {
        boolean isVisible = recentlyPlayedContainer.isVisible();
        recentlyPlayedContainer.setVisible(!isVisible); // Toggle visibility
        System.out.println("Recently Played section is now " + (isVisible ? "hidden" : "visible"));
    }


    // Handle search action when the user clicks search button
    @FXML
    private void handleSearch(ActionEvent event) {
        String query = searchBar.getText(); // Get the search query

        if (query.isEmpty()) {
            return; // Don't search if the query is empty
        }

        // Perform the search and get the results from Deezer API
        searchSongs(query);
    }

    // Simulated search function (replace with your actual search logic)
    private List<String> performSearch(String query) {
        List<String> results = new ArrayList<>();
        results.add("Song 1 - " + query);
        results.add("Song 2 - " + query);
        results.add("Song 3 - " + query);
        return results;
    }
    @FXML
    private ListView<String> searchResultsListView;
    // Perform the song search using Deezer API
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

                // Create a new Label for each song and add it to the searchResultsContainer
                Label resultLabel = new Label((i + 1) + ". " + songTitle + " by " + artistName);
                resultLabel.setStyle("-fx-text-fill: white;"); // Style for the label

                // Add the result to the searchResultsContainer VBox
                searchResultsContainer.getChildren().add(resultLabel);
            }

            // Make the results container visible after adding the results
            searchResultsContainer.setVisible(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Initialize method for setting up initial data
    public void initialize() {
        recentlyPlayed = new ArrayList<>(getRecentlyPlayed());
        favorites = new ArrayList<>(getFavorites());

        // Load and display the recently played songs
        try {
            for (Song song : recentlyPlayed) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("song.fxml"));

                VBox vBox = fxmlLoader.load();
                SongController songController = fxmlLoader.getController();
                songController.setData(song);

                recentlyPlayedContainer.getChildren().add(vBox);
            }

            // Load and display the favorite songs
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
        song.setName("Top 50");
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