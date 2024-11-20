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

public class APITopArtists {

    // Η διεύθυνση URL του API για την λήψη των καλλιτεχνών
    private static final String API_URL = "https://api.deezer.com/chart/0/artists?limit=50";

    /**
     * Μέθοδος για να πάρουμε την λίστα με τους top 50 καλλιτέχνες
     *
     * @return Λίστα με τα ονόματα των top 50 καλλιτεχνών
     * @throws IOException Σε περίπτωση που συμβεί πρόβλημα κατά τη λήψη των δεδομένων
     */
    public static List<String> fetchTopArtists() throws IOException {
        List<String> topArtists = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();

        // Δημιουργία αιτήματος προς το API
        Request request = new Request.Builder()
                .url(API_URL)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Σφάλμα κατά τη λήψη των καλλιτεχνών: HTTP " + response.code());
            }

            String jsonResponse = response.body().string();

            // Ανάλυση του JSON που επιστρέφει το API
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
            JsonArray artistsArray = jsonObject.getAsJsonArray("data");

            // Ανάγνωση των ονομάτων των καλλιτεχνών από το JSON και προσθήκη τους στη λίστα
            for (int i = 0; i < artistsArray.size(); i++) {
                JsonObject artist = artistsArray.get(i).getAsJsonObject();
                String artistName = artist.get("name").getAsString();
                topArtists.add(artistName);
            }
        } catch (IOException e) {
            throw new IOException("Πρόβλημα με την λήψη των δεδομένων από το API", e);
        }

        return topArtists;
    }
}
