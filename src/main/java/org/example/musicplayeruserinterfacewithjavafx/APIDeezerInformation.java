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
        // Κωδικοποίηση του ονόματος του καλλιτέχνη για ασφαλή χρήση στο URL
        String encodedArtistName = URLEncoder.encode(artistName, StandardCharsets.UTF_8);
        String url = API_URL + "?q=" + encodedArtistName;

        // Δημιουργία του HTTP Client
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        // Εκτέλεση του αιτήματος και λήψη της απάντησης
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("HTTP error code: " + response.code());
            }

            // Ανάλυση του JSON για να βρούμε το ID του καλλιτέχνη
            String jsonResponse = response.body().string();
            String artistId = parseArtistIdFromJson(jsonResponse);

            // Αν βρούμε το ID του καλλιτέχνη, ανακτούμε τους followers
            if (artistId != null) {
                return fetchFollowersByArtistId(artistId);
            } else {
                return "Δεν βρέθηκε ο καλλιτέχνης.";
            }
        }
    }

    // Μέθοδος για να ανακτήσει το ID του καλλιτέχνη από την απάντηση του Deezer API
    private static String parseArtistIdFromJson(String jsonResponse) {
        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
        JsonArray dataArray = jsonObject.getAsJsonArray("data");

        if (dataArray.size() > 0) {
            JsonObject artistObject = dataArray.get(0).getAsJsonObject();
            return artistObject.get("id").getAsString();
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
            return parseFollowersFromJson(jsonResponse);
        }
    }

    // Μέθοδος για να αναλύσει τον αριθμό των followers από το JSON της Deezer API
    private static String parseFollowersFromJson(String jsonResponse) {
        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
        if (jsonObject.has("nb_fan")) {
            return jsonObject.get("nb_fan").getAsString();
        }
        return "Δεν βρέθηκαν followers.";
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
