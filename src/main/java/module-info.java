module org.example.musicplayeruserinterfacewithjavafx {

    exports org.example.musicplayeruserinterfacewithjavafx;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.sql;
    requires com.google.gson;

    requires javafx.web;
    requires com.fasterxml.jackson.databind;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires okhttp3;
    requires java.net.http;
    requires javafx.media;
    requires java.desktop;


    // Open package to javafx.fxml for reflection access
    opens org.example.musicplayeruserinterfacewithjavafx to javafx.fxml;

    opens model to javafx.base;
}
