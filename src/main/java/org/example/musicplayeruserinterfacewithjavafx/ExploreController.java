package org.example.musicplayeruserinterfacewithjavafx;  // <-- This line should be added

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ExploreController {
    @FXML
    private ListView<String> newsListView;

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
                    ObservableList<String> newsTitles = parseNewsTitles(jsonResponse);

                    // Debugging: Log number of news articles parsed
                    System.out.println("Parsed " + newsTitles.size() + " news articles.");

                    // Update UI on the JavaFX Application Thread
                    Platform.runLater(() -> newsListView.setItems(newsTitles));

                } else {
                    System.err.println("Error: Unable to fetch news (Response Code: " + responseCode + ")");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private ObservableList<String> parseNewsTitles(String jsonResponse) {
        ObservableList<String> newsTitles = FXCollections.observableArrayList();

        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray articles = jsonObject.getJSONArray("articles");

            // Loop through each article and get the title
            for (int i = 0; i < articles.length(); i++) {
                JSONObject article = articles.getJSONObject(i);
                String title = article.getString("title");
                newsTitles.add(title);
                System.out.println("Parsed title: " + title); // Debug the parsed title
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newsTitles;
    }
}
