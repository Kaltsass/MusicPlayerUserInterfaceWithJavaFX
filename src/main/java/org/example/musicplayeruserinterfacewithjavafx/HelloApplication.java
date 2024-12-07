package org.example.musicplayeruserinterfacewithjavafx;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1600, 700);
        Objects.requireNonNull(getClass().getClassLoader().getResource("css/style.css")).toExternalForm();

        // Get the controller from the FXMLLoader
        HelloController controller = fxmlLoader.getController();

        // Pass HostServices to the controller
        controller.setHostServices(getHostServices());

        stage.setTitle("Music Player");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
