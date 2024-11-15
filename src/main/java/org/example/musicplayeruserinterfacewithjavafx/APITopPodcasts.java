package org.example.musicplayeruserinterfacewithjavafx;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class APITopPodcasts {

    private static final String API_URL = "https://api.deezer.com/chart/0/podcasts?limit=50";

    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(API_URL)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.out.println("Σφάλμα κατά τη λήψη των podcasts: HTTP " + response.code());
                return;
            }

            String jsonResponse = response.body().string();

            // Parsing JSON response
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
            JsonArray podcastsArray = jsonObject.getAsJsonArray("data");

            // Εμφάνιση των top podcasts
            System.out.println("Top Podcasts List:");
            for (int i = 0; i < podcastsArray.size(); i++) {
                JsonObject podcast = podcastsArray.get(i).getAsJsonObject();
                String podcastName = podcast.get("title").getAsString();
                String podcastLink = podcast.get("link").getAsString();

                System.out.println((i + 1) + ". Podcast Name: " + podcastName);
                System.out.println("   Link: " + podcastLink);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
