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
import java.util.ArrayList;
import java.util.List;

public class APIDeezerInformation {

    private static final String API_URL = "https://api.deezer.com/search";
    private static final String ARTIST_API_URL = "https://api.deezer.com/artist/";

    // Μέθοδος για να ανακτήσει τους followers του καλλιτέχνη
    public static String fetchArtistFollowers(String artistName) throws IOException {
        String encodedArtistName = URLEncoder.encode(artistName, StandardCharsets.UTF_8);
        String url = API_URL + "?q=" + encodedArtistName;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("HTTP error code: " + response.code());
            }

            // Ανάκτηση JSON απόκρισης
            String jsonResponse = response.body().string();
            System.out.println("Search JSON: " + jsonResponse); // Debugging

            // Parse του ID του καλλιτέχνη
            String artistId = parseArtistIdFromJson(jsonResponse);
            if (artistId != null) {
                return fetchFollowersByArtistId(artistId);
            } else {
                return "Δεν βρέθηκε ο καλλιτέχνης.";
            }
        }
    }


    // Μέθοδος για να ανακτήσει το ID του καλλιτέχνη από την απάντηση του Deezer API
    private static String parseArtistIdFromJson(String jsonResponse) {
        try {
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
            JsonArray dataArray = jsonObject.getAsJsonArray("data");

            if (dataArray.size() > 0) {
                JsonObject artistObject = dataArray.get(0).getAsJsonObject();
                String artistId = artistObject.get("id").getAsString();
                System.out.println("Parsed Artist ID: " + artistId); // Debugging
                return artistId;
            }
        } catch (Exception e) {
            System.out.println("Error parsing artist ID: " + e.getMessage());
        }
        return null; // Επιστρέφει null αν δεν βρεθεί ο καλλιτέχνης
    }

    // Μέθοδος για να ανακτήσει τους followers του καλλιτέχνη χρησιμοποιώντας το ID του
    private static String fetchFollowersByArtistId(String artistId) throws IOException {
        String url = ARTIST_API_URL + artistId;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("HTTP error code: " + response.code());
            }

            // Επεξεργασία του JSON για να ανακτήσουμε τον αριθμό των followers
            String jsonResponse = response.body().string();
            System.out.println("Artist JSON Response: " + jsonResponse); // Debugging

            return parseFollowersFromJson(jsonResponse);
        }
    }

    // Μέθοδος για να αναλύσει τον αριθμό των followers από το JSON της Deezer API
    private static String parseFollowersFromJson(String jsonResponse) {
        try {
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
            if (jsonObject.has("nb_fan")) {
                String followers = jsonObject.get("nb_fan").getAsString();
                System.out.println("Followers count: " + followers); // Debugging
                return followers;
            } else {
                System.out.println("Followers field not found in JSON response.");
                return "Followers not found in JSON.";
            }
        } catch (Exception e) {
            System.out.println("Error parsing followers JSON: " + e.getMessage());
            return "Error parsing followers JSON: " + e.getMessage();
        }

    }

    // Επεξεργασία του JSON για τα τραγούδια
    public static List<String> fetchArtistSongs(String artistName) throws IOException {
        String encodedArtistName = URLEncoder.encode(artistName, StandardCharsets.UTF_8);
        String url = API_URL + "?q=" + encodedArtistName;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("HTTP error code: " + response.code());
            }
            String jsonResponse = response.body().string();
            return parseSongsFromJson(jsonResponse);
        }
    }
    // Επεξεργασία του JSON για τα τραγούδια
    public static JsonArray fetchArtistTracks(String artistName) throws IOException {
        String encodedArtistName = URLEncoder.encode(artistName, StandardCharsets.UTF_8);
        String url = API_URL + "?q=" + encodedArtistName;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("HTTP error code: " + response.code());
            }
            String jsonResponse = response.body().string();
            return parseTracksFromJson(jsonResponse);
        }
    }

    // Επεξεργασία του JSON για τα τραγούδια και επιστροφή JsonArray
    private static JsonArray parseTracksFromJson(String jsonResponse) {
        try {
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
            return jsonObject.getAsJsonArray("data"); // Επιστρέφουμε το "data" που περιέχει τα τραγούδια
        } catch (Exception e) {
            System.out.println("Error parsing tracks JSON: " + e.getMessage());
            return new JsonArray(); // Επιστρέφουμε άδειο JsonArray σε περίπτωση σφάλματος
        }
    }


    // Επεξεργασία του JSON για τα τραγούδια
    private static List<String> parseSongsFromJson(String jsonResponse) {
        List<String> songs = new ArrayList<>();
        try {
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
            JsonArray dataArray = jsonObject.getAsJsonArray("data");

            for (int i = 0; i < dataArray.size(); i++) {
                JsonObject songObject = dataArray.get(i).getAsJsonObject();
                String songTitle = songObject.get("title").getAsString();
                songs.add(songTitle);
            }
        } catch (Exception e) {
            songs.add("Error parsing song data.");
        }
        return songs;
    }
}
