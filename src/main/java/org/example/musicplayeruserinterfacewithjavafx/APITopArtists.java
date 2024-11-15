package org.example.musicplayeruserinterfacewithjavafx;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class APITopArtists {

    private static final String API_URL = "https://api.deezer.com/chart/0/artists?limit=50";

    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(API_URL)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.out.println("Σφάλμα κατά τη λήψη των καλλιτεχνών: HTTP " + response.code());
                return;
            }

            String jsonResponse = response.body().string();

            // Parsing JSON
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
            JsonArray artistsArray = jsonObject.getAsJsonArray("data");

            System.out.println("Top Artists List:");
            for (int i = 0; i < artistsArray.size(); i++) {
                JsonObject artist = artistsArray.get(i).getAsJsonObject();
                String artistName = artist.get("name").getAsString();

                System.out.println((i + 1) + ". Artist Name: " + artistName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
