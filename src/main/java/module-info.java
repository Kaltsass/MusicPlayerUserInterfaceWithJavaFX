module org.example.musicplayeruserinterfacewithjavafx {
    requires com.google.gson;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.desktop;


    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires okhttp3;
    requires java.net.http;

    opens org.example.musicplayeruserinterfacewithjavafx to javafx.fxml;
    exports org.example.musicplayeruserinterfacewithjavafx;
}