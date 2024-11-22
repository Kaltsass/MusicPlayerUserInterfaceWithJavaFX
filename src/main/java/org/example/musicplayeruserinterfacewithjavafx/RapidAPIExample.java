package org.example.musicplayeruserinterfacewithjavafx;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RapidAPIExample {

    // Κάνουμε τη μέθοδο static για να την καλέσουμε χωρίς να δημιουργήσουμε αντικείμενο
    public static List<String> fetchTopCharts() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.deezer.com/chart?limit=50")  // Deezer API για τα top 50 charts
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            String jsonResponse = response.body().string();
            Gson gson = new Gson();
            ResponseData responseData = gson.fromJson(jsonResponse, ResponseData.class);

            List<String> topCharts = new ArrayList<>();
            for (Track track : responseData.tracks.data) {
                topCharts.add("Song: " + track.title + " | Artist: " + track.artist.name);
            }

            return topCharts;
        }
    }

    // Κλάση που περιέχει τα δεδομένα της απόκρισης του API
    public static class ResponseData {
        public Tracks tracks; // Τα τραγούδια του chart
    }

    // Κλάση που περιέχει τη λίστα των τραγουδιών του chart
    public static class Tracks {
        public List<Track> data;  // Λίστα των τραγουδιών
    }

    // Κλάση που αντιπροσωπεύει το κάθε τραγούδι
    public static class Track {
        public String title;  // Τίτλος τραγουδιού
        public Artist artist; // Καλλιτέχνης του τραγουδιού
    }

    // Κλάση για τον καλλιτέχνη του τραγουδιού
    public static class Artist {
        public String name;  // Όνομα καλλιτέχνη
    }
}
