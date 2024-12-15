package org.example.musicplayeruserinterfacewithjavafx;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

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
    private ListView<NewsArticle> newsListView;

    public void initialize() {
        fetchNews();
    }

    private void fetchNews() {
        String apiKey = "62be30e711fc48ce94ae856c2650bb5b";
        String apiUrl = "https://newsapi.org/v2/top-headlines?country=us&apiKey=" + apiKey;

        new Thread(() -> {
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

                    // Log the full response to check if multiple articles exist
                    String jsonResponse = response.toString();
                    System.out.println("API Response: " + jsonResponse); // Log the full API response
                    ObservableList<NewsArticle> newsArticles = parseNewsArticles(jsonResponse);

                    // Debugging: Log number of news articles parsed
                    System.out.println("Parsed " + newsArticles.size() + " news articles.");

                    // Update UI on the JavaFX Application Thread
                    Platform.runLater(() -> newsListView.setItems(newsArticles));

                } else {
                    System.err.println("Error: Unable to fetch news (Response Code: " + responseCode + ")");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private ObservableList<NewsArticle> parseNewsArticles(String jsonResponse) {
        ObservableList<NewsArticle> newsArticles = FXCollections.observableArrayList();

        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray articles = jsonObject.getJSONArray("articles");

            // Loop through each article and get the title and URL
            for (int i = 0; i < articles.length(); i++) {
                JSONObject article = articles.getJSONObject(i);
                String title = article.getString("title");
                String url = article.getString("url");
                newsArticles.add(new NewsArticle(title, url));
                System.out.println("Parsed title: " + title); // Debug the parsed title
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newsArticles;
    }

    // Event handler to open the article link when a news item is clicked
    @FXML
    private void handleArticleClick(MouseEvent event) {
        NewsArticle selectedArticle = newsListView.getSelectionModel().getSelectedItem();
        if (selectedArticle != null) {
            String url = selectedArticle.getUrl();
            try {
                // Open the URL in the default web browser
                Desktop.getDesktop().browse(new URI(url));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
