package org.example.musicplayeruserinterfacewithjavafx;

public class Song extends model.Song {
    private String name;
    private String artist;
    private String previewUrl; // URL for a 30-second preview
    private String cover;      // Album cover image URL

    // Constructor
    public Song(String name, String artist, String previewUrl, String cover) {
        this.name = name;
        this.artist = artist;
        this.previewUrl = previewUrl;
        this.cover = cover;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
