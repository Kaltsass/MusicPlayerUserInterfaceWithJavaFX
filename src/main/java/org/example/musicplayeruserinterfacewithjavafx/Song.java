package org.example.musicplayeruserinterfacewithjavafx;

public class Song {

    private String name;
    private String artist;
    private String previewUrl;
    private String cover;

    // Constructor
    public Song(String name, String artist, String previewUrl, String cover) {
        this.name = name;
        this.artist = artist;
        this.previewUrl = previewUrl;
        this.cover = cover;
    }

    // Getters and Setters
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
