import org.example.musicplayeruserinterfacewithjavafx.MediaPlayerManager;
import javafx.scene.media.MediaPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestPlaySongSetsOnReadyListener {

    @InjectMocks
    private MediaPlayerManager mediaPlayerManager;

    @Mock
    private MediaPlayer mockMediaPlayer;

    @Mock
    private ArgumentCaptor<Runnable> runnableCaptor;

    @BeforeEach
    public void setUp() {
        mediaPlayerManager = new MediaPlayerManager();
    }

    @Test
    public void testPlaySongSetsOnReadyListener() {
        // Arrange
        String previewUrl = "https://example.com/song.mp3";

        // Act
        mediaPlayerManager.playSong(previewUrl);

        // Capture and verify the Runnable passed to `setOnReady`
        verify(mockMediaPlayer).setOnReady(runnableCaptor.capture());

        // Trigger the captured Runnable to simulate readiness
        runnableCaptor.getValue().run();

        // Assert that the song started playing after being ready
        verify(mockMediaPlayer).play();
    }
}
