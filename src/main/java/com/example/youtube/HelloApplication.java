package com.example.youtube;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1190, 627);
        scene.getStylesheets().addAll(
                getClass().getResource("stylecss.css").toExternalForm(),
                getClass().getResource("DarkStyles.css").toExternalForm()
        );
        stage.setTitle("Youtube");
//        stage.getIcons().add(new Image("file:\"D:\\usb\\ap\\assignment\\YoutubeNotes\\FirstBranch(AmirAli\\YouTube\\src\\main\\resources\\com\\example\\youtube\\Images\\icon.jpeg\""));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}