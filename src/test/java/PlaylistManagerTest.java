

import org.example.musicplayeruserinterfacewithjavafx.PlaylistManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlaylistManagerTest {

    @Test
    public void testAddNewPlaylist() {
        PlaylistManager manager = new PlaylistManager();
        String playlistName = "Rock";

        manager.addPlaylist(playlistName);
        assertTrue(manager.getPlaylists().contains(playlistName),
                "The playlist 'Rock' should be added to the list");
    }

}
