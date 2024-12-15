package org.example.musicplayeruserinterfacewithjavafx;

public class NewsArticle {
    private String title;
    private String url;

    public NewsArticle(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return title; // This will display only the title in the ListView
    }
}



