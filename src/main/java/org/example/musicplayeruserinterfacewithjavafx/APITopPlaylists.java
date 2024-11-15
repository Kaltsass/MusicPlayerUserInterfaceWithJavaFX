package org.example.musicplayeruserinterfacewithjavafx;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class APITopPlaylists {

    private static final String API_URL = "https://api.deezer.com/chart/0/playlists?limit=50";

    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(API_URL)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.out.println("Σφάλμα κατά τη λήψη των playlists: HTTP " + response.code());
                return;
            }

            String jsonResponse = response.body().string();

            // Parsing JSON
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
            JsonArray playlistsArray = jsonObject.getAsJsonArray("data");

            System.out.println("Top Playlists List:");
            for (int i = 0; i < playlistsArray.size(); i++) {
                JsonObject playlist = playlistsArray.get(i).getAsJsonObject();
                String playlistName = playlist.get("title").getAsString();
                String playlistLink = playlist.get("link").getAsString();

                System.out.println((i + 1) + ". Playlist Name: " + playlistName);
                System.out.println("   Link: " + playlistLink);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
