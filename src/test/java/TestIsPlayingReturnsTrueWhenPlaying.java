import org.example.musicplayeruserinterfacewithjavafx.MediaPlayerManager;
import javafx.scene.media.MediaPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestIsPlayingReturnsTrueWhenPlaying {

    @InjectMocks
    private MediaPlayerManager mediaPlayerManager;

    @Mock
    private MediaPlayer mockMediaPlayer;

    @BeforeEach
    public void setUp() {
        mediaPlayerManager = new MediaPlayerManager();
    }

    @Test
    public void testIsPlayingReturnsTrueWhenPlaying() {
        // Arrange
        when(mockMediaPlayer.getStatus()).thenReturn(MediaPlayer.Status.PLAYING);

        // Act
        boolean isPlaying = mediaPlayerManager.isPlaying();

        // Assert
        assertTrue(isPlaying);
    }
}
