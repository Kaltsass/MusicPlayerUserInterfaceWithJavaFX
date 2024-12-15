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

                    String jsonResponse = response.toString();
                    System.out.println("API Response: " + jsonResponse);  // Log the response
                    ObservableList<Hyperlink> newsArticles = parseNewsArticles(jsonResponse);

                    Platform.runLater(() -> newsListView.setItems(newsArticles));
                } else {
                    System.err.println("Error: Unable to fetch news (Response Code: " + responseCode + ")");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private ObservableList<Hyperlink> parseNewsArticles(String jsonResponse) {
        ObservableList<Hyperlink> newsArticles = FXCollections.observableArrayList();

        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray articles = jsonObject.getJSONArray("articles");

            for (int i = 0; i < articles.length(); i++) {
                JSONObject article = articles.getJSONObject(i);
                String title = article.getString("title");
                String url = article.getString("url");

                Hyperlink hyperlink = new Hyperlink(title);
                hyperlink.setOnAction(event -> openUrl(url));  // Attach the URL to open when clicked

                newsArticles.add(hyperlink);
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
