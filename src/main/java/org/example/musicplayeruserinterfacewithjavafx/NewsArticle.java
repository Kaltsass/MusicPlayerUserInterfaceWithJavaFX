package org.example.musicplayeruserinterfacewithjavafx;

public class NewsArticle {
    private String title;
    private String url;

    // Constructor to initialize title and url
    public NewsArticle(String title, String url) {
        this.title = title;
        this.url = url;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    // Override the toString method to display the title in the ListView
    @Override
    public String toString() {
        return title;
    }
}


