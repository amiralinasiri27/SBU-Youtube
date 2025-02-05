package com.example.youtube;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class notificationController {
    @FXML
    private TilePane content;

    // TODO : Pass videos here
    public void createVideoBox() {
        VBox vbox = new VBox();
        vbox.prefWidth(309.0);
        vbox.prefHeight(680.0);

        ImageView imageView = new ImageView();
        imageView.setFitHeight(191.0);
        imageView.setFitWidth(261.0);

        Label title = new Label();
        title.setAlignment(Pos.CENTER);
        title.setPrefHeight(45.0);
        title.setPrefWidth(264.0);
        title.setFont(Font.font(37.0));
        // text from server

        Label channelName = new Label();
        channelName.setAlignment(Pos.CENTER);
        channelName.setPrefHeight(45.0);
        channelName.setPrefWidth(266.0);
        channelName.setFont(Font.font(37.0));
        // text from server

        vbox.getChildren().addAll(imageView, title, channelName);

        content.getChildren().add(vbox);
    }

    // TODO : pass comment here
    public void createComment() {
        VBox vbox = new VBox();
        vbox.prefWidth(309.0);
        vbox.prefHeight(680.0);

        Label name = new Label();
        name.setAlignment(Pos.CENTER);
        name.setPrefHeight(45.0);
        name.setPrefWidth(264.0);
        name.setFont(Font.font(37.0));
        // text from server

        Label commentContent = new Label();
        commentContent.setAlignment(Pos.CENTER);
        commentContent.setPrefHeight(45.0);
        commentContent.setPrefWidth(266.0);
        commentContent.setFont(Font.font(37.0));
        // text from server

        vbox.getChildren().addAll(name, commentContent);

        content.getChildren().add(vbox);
    }
}
