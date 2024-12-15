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

public class APITopAlbums {

    private static final String API_URL = "https://api.deezer.com/chart/0/albums?limit=50";
    private static OkHttpClient client = new OkHttpClient();  // Κοινός OkHttpClient για βελτιωμένη απόδοση

    // Κλήση του API για να πάρουμε τα Top 50 Albums και επιστροφή λίστας με τα albums
    public static List<String> fetchTopAlbums() {
        Request request = new Request.Builder()
                .url(API_URL)
                .build();

        List<String> albumsList = new ArrayList<>();  // Λίστα για τα albums

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.out.println("Σφάλμα κατά τη λήψη των albums: HTTP " + response.code());
                return albumsList;  // Επιστροφή κενής λίστας σε περίπτωση αποτυχίας
            }

            String jsonResponse = response.body().string();

            // Εμφάνιση ολόκληρης της απόκρισης για debugging
            System.out.println("Full JSON Response: " + jsonResponse);

            // Parsing της JSON απόκρισης
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
            JsonArray albumsArray = jsonObject.getAsJsonArray("data");

            System.out.println("Number of albums received: " + albumsArray.size());

            // Επεξεργασία των albums και προσθήκη τους στη λίστα
            for (int i = 0; i < albumsArray.size(); i++) {
                JsonObject album = albumsArray.get(i).getAsJsonObject();
                String albumTitle = album.get("title").getAsString();
                String artistName = album.getAsJsonObject("artist").get("name").getAsString();
                // Get the URL of the album cover
                String coverImageUrl = album.get("cover_big").getAsString();

                // Προσθήκη του album στη λίστα
                albumsList.add("Album: " + albumTitle + ", Artist: " + artistName + ", Cover: " + coverImageUrl);
            }
        } catch (IOException e) {
            // Εκτύπωση στοίβας σφαλμάτων για περισσότερες πληροφορίες
            e.printStackTrace();  // Εκτύπωση της στοίβας σφαλμάτων
            System.out.println("Σφάλμα στην επικοινωνία με το API: " + e.getMessage());
        }

        return albumsList;  // Επιστροφή της λίστας με τα albums
    }
    //Για να δω αν τραβάει φωτογραφίες
    public static void main(String[] args) {
        List<String> albums = fetchTopAlbums();
        System.out.println("Fetched Albums: ");
        for (String album : albums) {
            System.out.println(album);
        }
    }

}
