package org.example.musicplayeruserinterfacewithjavafx;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class YoutubeAPI {
    private static final String YOUTUBE_API_KEY = "AIzaSyAfa-havR-5_ZRnuR82d7tJUMAzJOlDo5s";

    public static String getYouTubeVideoUrl(String songTitle, String artistName) {
        String query = songTitle + " " + artistName;
        String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&type=video&q=" + query + "&key=" + YOUTUBE_API_KEY;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.out.println("Error fetching YouTube data: HTTP " + response.code());
                return null;
            }

            String jsonResponse = response.body().string();
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
            JsonArray itemsArray = jsonObject.getAsJsonArray("items");

            if (itemsArray.size() > 0) {
                JsonObject firstItem = itemsArray.get(0).getAsJsonObject();
                JsonObject idObject = firstItem.getAsJsonObject("id");
                String videoId = idObject.get("videoId").getAsString();
                return "https://www.youtube.com/watch?v=" + videoId;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // Return null if no video is found
    }
}
