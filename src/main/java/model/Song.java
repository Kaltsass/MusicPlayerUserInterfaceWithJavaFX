package model;

import javafx.scene.image.Image;

public class Song {
    private String cover;
    private String name;
    private String artist;
    private String previewUrl; // URL for song preview
    private String youtubeUrl; // URL for YouTube video

    // Getter and Setter for cover
    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    // Getter and Setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for artist
    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    // Getter and Setter for previewUrl
    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    // Getter and Setter for youtubeUrl
    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }

    public Image getCoverImage() {
        if (cover != null && !cover.isEmpty()) {
            try {
                return new Image(cover, true); // Load image in background
            } catch (Exception e) {
                System.err.println("Error loading image from URL: " + cover);
                return getDefaultCoverImage();
            }
        }
        return getDefaultCoverImage();
    }

    // Default cover image if no cover is available
    private Image getDefaultCoverImage() {
        return new Image(getClass().getResourceAsStream("/img/default_cover.png"));
    }

}
