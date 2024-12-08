package org.example.musicplayeruserinterfacewithjavafx;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class APIArtistInformation {

    private static final String API_URL = "https://en.wikipedia.org/w/api.php";


    public static void main(String[] args) {
        String artistName = "Eleni Foureira"; // Να αλλάξει για να τραβάει το όνομα από το search bar


        try {
            String artistInfo = fetchArtistInfo(artistName.toString());
            System.out.println("Information about: \n" + artistName + "\n");
            System.out.println(artistInfo);
        } catch (IOException e) {
            System.out.println("Σφάλμα κατά την ανάκτηση πληροφοριών καλλιτέχνη: " + e.getMessage());
        }
    }

    private static String fetchArtistInfo(String artistName) throws IOException {
        // Construct the Wikipedia API URL
        String encodedArtistName = URLEncoder.encode(artistName, StandardCharsets.UTF_8);
        String url = API_URL +
                "?action=query" +
                "&prop=extracts" +
                "&format=json" +
                "&exintro=true" +
                "&explaintext=true" +
                "&titles=" + encodedArtistName;

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.out.println("Σφάλμα κατά τη λήψη των καλλιτεχνών: HTTP " + response.code());

            }
            String jsonResponse = response.body().string();

            // Parsing JSON
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
            JsonArray artistsArray = jsonObject.getAsJsonArray("data");

            return parseWikipediaResponse(jsonResponse, artistName);

        }
    }

    private static String parseWikipediaResponse(String jsonResponse, String artistName) {
        StringBuilder result = new StringBuilder();
        try {
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
            JsonObject query = jsonObject.getAsJsonObject("query");
            JsonObject pages = query.getAsJsonObject("pages");


            for (String key : pages.keySet()) {
                JsonObject page = pages.getAsJsonObject(key);

                if (page.has("extract")) {
                    String extract = page.get("extract").getAsString();
                    result.append(extract);
                } else {
                    result.append("Δεν υπάρχουν πληροφορίες για τον καλλιτέχνη: ").append(artistName);
                }
            }
        } catch (Exception e) {
            result.append("Error parsing response: ").append(e.getMessage());
        }
        return result.toString();
    }


}