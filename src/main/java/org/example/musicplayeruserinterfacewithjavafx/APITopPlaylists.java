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

public class APITopPlaylists {

    private static final String API_URL = "https://api.deezer.com/chart/0/playlists?limit=50";

    // Μέθοδος που επιστρέφει τη λίστα των top playlists
    public static List<String> fetchTopPlaylists() throws IOException {
        List<String> playlists = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(API_URL)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Σφάλμα κατά τη λήψη των playlists: HTTP " + response.code());
            }

            String jsonResponse = response.body().string();

            // Parsing JSON
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
            JsonArray playlistsArray = jsonObject.getAsJsonArray("data");

            // Προσθήκη των playlists στη λίστα
            for (int i = 0; i < playlistsArray.size(); i++) {
                JsonObject playlist = playlistsArray.get(i).getAsJsonObject();
                String playlistName = playlist.get("title").getAsString();
                playlists.add(playlistName);
            }
        }

        return playlists;
    }
}
