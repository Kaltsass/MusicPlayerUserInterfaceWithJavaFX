package org.example.musicplayeruserinterfacewithjavafx;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Scanner;

public class songPlayerTest {

    private static final String BASE_API_URL = "https://api.deezer.com/search/track?q=";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        OkHttpClient client = new OkHttpClient();

        System.out.print("Enter the artist or song to search for: ");
        String searchQuery = scanner.nextLine().trim();

        if (searchQuery.isEmpty()) {
            System.out.println("Search query cannot be empty.");
            return;
        }

        try {
            JsonArray songs = fetchSongs(client, searchQuery);

            if (songs.size() == 0) {
                System.out.println("No songs found for the query: " + searchQuery);
                return;
            }

            System.out.println("Number of songs received: " + songs.size());
            System.out.println("Songs List:");

            for (int i = 0; i < songs.size(); i++) {
                JsonObject track = songs.get(i).getAsJsonObject();
                String songTitle = track.get("title").getAsString();
                String artistName = track.getAsJsonObject("artist").get("name").getAsString();
                String albumTitle = track.getAsJsonObject("album").get("title").getAsString();
                int duration = track.get("duration").getAsInt();
                String songLink = track.get("link").getAsString();

                System.out.printf("%d. Song: %s, Artist: %s, Album: %s, Duration: %d seconds, Link: %s%n",
                        i + 1, songTitle, artistName, albumTitle, duration, songLink);
            }
        } catch (IOException e) {
            System.err.println("An error occurred while fetching songs: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static JsonArray fetchSongs(OkHttpClient client, String query) throws IOException {
        String apiUrl = BASE_API_URL + query.replace(" ", "%20"); // Handle spaces in the query.
        Request request = new Request.Builder().url(apiUrl).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("HTTP error code: " + response.code());
            }

            String jsonResponse = response.body().string();
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();

            if (!jsonObject.has("data")) {
                throw new IOException("No data found for the query.");
            }

            return jsonObject.getAsJsonArray("data");
        }
    }
}