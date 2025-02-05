package com.example.youtube;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class videoViewController implements Initializable {
    MediaPlayer mediaPlayer;

    @FXML
    private MediaView mediaView;
    @FXML
    private Slider timeSlider;
    @FXML
    private ImageView pauseImg;
    @FXML
    private ImageView platImg;
    @FXML
    private ImageView notLikedImg;
    @FXML
    private ImageView likedImg;
    @FXML
    private ImageView notDisLikedImg;
    @FXML
    private ImageView disLikedImg;
    @FXML
    private TextArea description;
    @FXML
    private TilePane content;
    private String path;
    private Stage stage = new Stage();
    @FXML
    public void openSongMedia() {
        try {
            File chooser = new File(getPath());
            System.out.println(getPath());

            Media media = new Media(chooser.toURI().toURL().toString());
            mediaPlayer = new MediaPlayer(media);

            mediaView.setMediaPlayer(mediaPlayer);

            // slider
            mediaPlayer.setOnReady(() -> {
                timeSlider.setMin(0);
                timeSlider.setMax(mediaPlayer.getMedia().getDuration().toSeconds());
                timeSlider.setValue(0);
            });

            mediaPlayer.currentTimeProperty().addListener((observableValue, duration, t1) -> {
                Duration d = mediaPlayer.getCurrentTime();
                timeSlider.setValue(d.toSeconds());
            });

            timeSlider.valueProperty().addListener((observableValue, number, t1) -> {
                if (timeSlider.isPressed()) {
                    double value = timeSlider.getValue();
                    mediaPlayer.seek(new Duration(value * 1000));
                }
            });
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    public void play() {
        MediaPlayer.Status status = mediaPlayer.getStatus();

        try {
            if (status == MediaPlayer.Status.PLAYING) {
                mediaPlayer.pause();
                pauseImg.setVisible(false);
                platImg.setVisible(true);
            }
            else {
                mediaPlayer.play();
                pauseImg.setVisible(true);
                platImg.setVisible(false);
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    public void nextBtnClick() {
        double d = mediaPlayer.getCurrentTime().toSeconds();

        d += 15;

        mediaPlayer.seek(new Duration(d * 1000));
    }

    @FXML
    public void preBtnClick() {
        double d = mediaPlayer.getCurrentTime().toSeconds();

        d -= 10;

        mediaPlayer.seek(new Duration(d * 1000));
    }

    @FXML
    public void mediaViewClickToHidePlayBtn() {
        if (mediaPlayer != null) {
            MediaPlayer.Status status = mediaPlayer.getStatus();

            if (platImg.isVisible() || pauseImg.isVisible()) {
                platImg.setVisible(false);
                pauseImg.setVisible(false);
            } else {
                if (status == MediaPlayer.Status.PLAYING) {
                    pauseImg.setVisible(true);
                } else {
                    platImg.setVisible(true);
                }
            }
        }
    }
    @FXML
    public void likeUnlikeClick() {
        if (likedImg.isVisible()) {
            likedImg.setVisible(false);
            notLikedImg.setVisible(true);
        }
        else {
            likedImg.setVisible(true);
            notLikedImg.setVisible(false);
            disLikedImg.setVisible(false);
            notDisLikedImg.setVisible(true);
        }
    }

    @FXML
    public void disLikeUnDisLikeClick() {
        if (disLikedImg.isVisible()) {
            disLikedImg.setVisible(false);
            notDisLikedImg.setVisible(true);
        }
        else {
            disLikedImg.setVisible(true);
            notDisLikedImg.setVisible(false);
            likedImg.setVisible(false);
            notLikedImg.setVisible(true);
        }
    }


    // profile Image - comment content Pass here
    public void addCommentInBox() {
        HBox hBox = new HBox();
        hBox.setPrefHeight(130.0);
        hBox.setPrefWidth(133.0);

        ImageView profileImg = new ImageView();
        profileImg.setFitHeight(150.0);
        profileImg.setFitWidth(133.0);

        Label commentContent = new Label();
        commentContent.setPrefWidth(274.0);
        commentContent.setPrefHeight(150.0);

        hBox.getChildren().addAll(profileImg, commentContent);
        content.getChildren().add(hBox);
    }

    // pass description of video here
    public void setDescription(String descriptionTxt) {
        description.setText(descriptionTxt);
    }

    @FXML
    public void exitBtnClick() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
        Stage stage = (Stage) timeSlider.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (mediaView != null) {
            openSongMedia();
        }
    }

    public String getPath() {
        return path;
    }
    public void  setPath(String path) {
        this. path =path;
    }

    @FXML
    public void addToWatchLater() {
        // TODO : add current video to current user's watchLater List
    }

}
