package org.example.musicplayeruserinterfacewithjavafx;

import javafx.scene.media.MediaPlayer;

public class MediaPlayerWrapper {
    private final MediaPlayer mediaPlayer;

    public MediaPlayerWrapper(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public void play() {
        mediaPlayer.play();
    }

    public void setOnError(Runnable onError) {
        mediaPlayer.setOnError(onError);
    }

    public void stop() {
        mediaPlayer.stop();
    }

    // Add other methods as needed
}
