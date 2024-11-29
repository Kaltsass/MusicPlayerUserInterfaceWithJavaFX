

        package org.example.musicplayeruserinterfacewithjavafx;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class APITopPodcasts {

    private static final String API_URL = "https://api.deezer.com/chart/0/podcasts?limit=50";

    // Μέθοδος για να ανακτήσουμε τα top podcasts
    public static List<String> fetchTopPodcasts() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(API_URL)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.out.println("Σφάλμα κατά τη λήψη των podcasts: HTTP " + response.code());
                return null;  // Επιστροφή null σε περίπτωση σφάλματος
            }

            String jsonResponse = response.body().string();

            // Parsing JSON response
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
            JsonArray podcastsArray = jsonObject.getAsJsonArray("data");

            // Δημιουργία μιας λίστας με τα ονόματα και τα links των podcasts
            List<String> podcastsList = new ArrayList<>();
            for (int i = 0; i < podcastsArray.size(); i++) {
                JsonObject podcast = podcastsArray.get(i).getAsJsonObject();
                String podcastName = podcast.get("title").getAsString();
                String podcastLink = podcast.get("link").getAsString();

                podcastsList.add((i + 1) + ". Podcast Name: " + podcastName + " | Link: " + podcastLink);
            }

            return podcastsList;  // Επιστροφή της λίστας με τα top podcasts
        }
    }
}
