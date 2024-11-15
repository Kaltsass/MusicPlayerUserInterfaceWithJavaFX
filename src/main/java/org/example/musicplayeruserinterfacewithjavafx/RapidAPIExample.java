package org.example.musicplayeruserinterfacewithjavafx;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;

public class RapidAPIExample {

    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient();

        // Ενημέρωση URL για να ζητήσουμε τα Top 50 τραγούδια
        Request request = new Request.Builder()
                .url("https://api.deezer.com/chart?limit=50")  // Εδώ ζητάμε 50 τραγούδια από το Deezer API
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            // Ανάγνωση της απόκρισης σε μορφή JSON
            String jsonResponse = response.body().string();

            // Ανάλυση του JSON με Gson
            Gson gson = new Gson();

            // Ανάλυση της απόκρισης στο αντικείμενο ResponseData
            ResponseData responseData = gson.fromJson(jsonResponse, ResponseData.class);

            // Εκτύπωση των τραγουδιών και των καλλιτεχνών χωρίς την πλήρη απόκριση
            for (Track track : responseData.tracks.data) {
                System.out.println("Song Name: " + track.title + ", Artist: " + track.artist.name);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Κλάση που περιέχει τα δεδομένα του Deezer response για το chart
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
