import org.example.musicplayeruserinterfacewithjavafx.MediaPlayerManager;
import javafx.scene.media.MediaPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

public class TestStopSong {

    @InjectMocks
    private MediaPlayerManager mediaPlayerManager;

    @Mock
    private MediaPlayer mockMediaPlayer;

    @BeforeEach
    public void setUp() {
        mediaPlayerManager = new MediaPlayerManager();
    }

    @Test
    public void testStopSong() {
        // Act
        mediaPlayerManager.stopSong();

        // Assert
        verify(mockMediaPlayer, times(1)).stop();
    }
}
