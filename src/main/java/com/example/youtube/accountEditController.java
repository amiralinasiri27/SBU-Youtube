package com.example.youtube;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class accountEditController {





    // TODO : set the first value of this components at inisialize method
    @FXML
    private ImageView profilePic;
    @FXML
    private TextField name;
    @FXML
    private TextField describe;
    Stage primaryStage = new Stage();

    public void aplodeImage() {
        try {
            FileChooser chooser = new FileChooser();
            File file = chooser.showOpenDialog(null);

            Image image = new Image(file.toURI().toString());
            profilePic.setImage(image);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}
