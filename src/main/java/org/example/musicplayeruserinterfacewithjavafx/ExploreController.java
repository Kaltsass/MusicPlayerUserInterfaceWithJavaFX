package org.example.musicplayeruserinterfacewithjavafx;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Hyperlink;
import javafx.event.ActionEvent;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.awt.Desktop;
import java.net.URI;

public class ExploreController {
    @FXML
    private ListView<Hyperlink> newsListView;

    public void initialize() {
        fetchMusicNews();
    }

    private void fetchMusicNews() {
        String apiKey = "62be30e711fc48ce94ae856c2650bb5b";

        // Enhanced queries for music-related news
        String[] queries = {
                "music",
                "singer",
                "artist",
                "album",
                "concert",
                "song",
                "band",
                "festival",
                "recording",
                "lyrics"
        };

        new Thread(() -> {
            ObservableList<Hyperlink> allMusicNews = FXCollections.observableArrayList();

            for (String query : queries) {
                String musicNewsUrl = "https://newsapi.org/v2/everything?q=" + query + "&apiKey=" + apiKey;

                try {
                    ObservableList<Hyperlink> musicNews = fetchArticlesFromApi(musicNewsUrl);
                    allMusicNews.addAll(musicNews); // Add results from each query to the combined list
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // Update the UI
            Platform.runLater(() -> newsListView.setItems(allMusicNews));
        }).start();
    }

    private ObservableList<Hyperlink> fetchArticlesFromApi(String apiUrl) {
        ObservableList<Hyperlink> articles = FXCollections.observableArrayList();

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) { // Success
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                in.close();

                String jsonResponse = response.toString();
                System.out.println("API Response for URL " + apiUrl + ": " + jsonResponse);

                articles.addAll(parseNewsArticles(jsonResponse));
            } else {
                System.err.println("Error: Unable to fetch news from " + apiUrl + " (Response Code: " + responseCode + ")");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return articles;
    }

    private ObservableList<Hyperlink> parseNewsArticles(String jsonResponse) {
        ObservableList<Hyperlink> newsArticles = FXCollections.observableArrayList();

        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray articles = jsonObject.getJSONArray("articles");

            for (int i = 0; i < articles.length(); i++) {
                JSONObject article = articles.getJSONObject(i);
                String title = article.optString("title", "Untitled");
                String description = article.optString("description", "");
                String content = article.optString("content", "");
                String url = article.optString("url", "");

                // Apply stricter filtering
                if ((title.toLowerCase().contains("music") || description.toLowerCase().contains("music") || content.toLowerCase().contains("music")) && !url.isEmpty()) {
                    Hyperlink hyperlink = new Hyperlink(title);
                    hyperlink.setOnAction(event -> openUrl(url)); // Attach the URL to open when clicked

                    newsArticles.add(hyperlink);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newsArticles;
    }

    private void openUrl(String url) {
        try {
            // Open the URL in the default browser
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

