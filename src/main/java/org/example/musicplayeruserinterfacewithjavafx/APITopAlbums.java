package org.example.musicplayeruserinterfacewithjavafx;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class APITopAlbums {

    private static final String API_URL = "https://api.deezer.com/chart/0/albums?limit=50";

    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(API_URL)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.out.println("Σφάλμα κατά τη λήψη των albums: HTTP " + response.code());
                return;
            }

            String jsonResponse = response.body().string();

            // Εμφάνιση ολόκληρης της απόκρισης για debugging
            System.out.println("Full JSON Response: " + jsonResponse);

            // Parsing JSON
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
            JsonArray albumsArray = jsonObject.getAsJsonArray("data");

            System.out.println("Number of albums received: " + albumsArray.size());

            // Εμφάνιση albums
            System.out.println("Albums List:");
            for (int i = 0; i < albumsArray.size(); i++) {
                JsonObject album = albumsArray.get(i).getAsJsonObject();
                String albumTitle = album.get("title").getAsString();
                String artistName = album.getAsJsonObject("artist").get("name").getAsString();

                System.out.println((i + 1) + ". Album: " + albumTitle + ", Artist: " + artistName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
