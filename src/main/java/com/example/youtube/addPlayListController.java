package com.example.youtube;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class addPlayListController {
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
