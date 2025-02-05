package com.example.youtube;

import com.example.youtube.Model.Channel;
import com.example.youtube.Model.PlayList;
import com.example.youtube.Server.Client;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class addVideoController {

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
    private ChoiceBox playLists;
    @FXML
    private CheckBox blockCheck;
    @FXML
    private ImageView videoPic;
    Stage stage = new Stage();

    //---------------------------------------------
    public Client client;

    public Channel channel;
    public File file;




    //------------------------------------------------

    public boolean isDarkModeOn = HelloController.isDarkModeOn;
    public String darkTheme = HelloApplication.class.getResource("DarkStyles.css").toExternalForm();
    public String lightTheme = HelloApplication.class.getResource("stylecss.css").toExternalForm();


    @FXML
    public void openSongMedia(ActionEvent event) {
        System.out.println("Open Song");

        try {
            FileChooser chooser = new FileChooser();
            File file = chooser.showOpenDialog(null);

            Media media = new Media(file.toURI().toURL().toString());
            mediaPlayer = new MediaPlayer(media);
            client.sendVideoBytes(file.toURI().toURL().toString());
            mediaView.setMediaPlayer(mediaPlayer);

            // slider

            mediaPlayer.setOnReady(()->{
                timeSlider.setMin(0);
                timeSlider.setMax(mediaPlayer.getMedia().getDuration().toSeconds());
                System.out.println(mediaPlayer.getMedia().getDuration().toSeconds());
                timeSlider.setValue(0);
            });

            mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration t1) {
                    Duration d = mediaPlayer.getCurrentTime();
                    timeSlider.setValue(d.toSeconds());
                }
            });

            timeSlider.valueProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                    if (timeSlider.isPressed()) {
                        double value = timeSlider.getValue();
                        mediaPlayer.seek(new Duration(value * 1000));
                    }
                }
            });
        }
        catch (Exception e) {
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
        MediaPlayer.Status status = mediaPlayer.getStatus();

        if (platImg.isVisible() || pauseImg.isVisible()) {
            platImg.setVisible(false);
            pauseImg.setVisible(false);
        }
        else {
            if (status == MediaPlayer.Status.PLAYING) {
                pauseImg.setVisible(true);
            }
            else {
                platImg.setVisible(true);
            }
        }
    }

    @FXML
    public void addToPlayList() {
        String playListName = playLists.getValue().toString();
        try {
            ArrayList<PlayList>playLists1=new ArrayList<>(client.getChannelPlaylistsRequest(channel.getId()));
            for(PlayList x: playLists1)
            {
                if(x.getName().equals(playListName)){
                    client.getPlaylistVideosRequest(x.getID());
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //this is for add video in playlist
//        mediaPlayer.getMedia()
        // add video to playList
    }

    // pass List of Play Lists Here
    public void initialize() {


        playLists.getItems().add("Your Channel");
        playLists.getSelectionModel().select("Your Channel");



    }
    @FXML
    public void refreshclick(){
        try {
            ArrayList<PlayList>playLists1=new ArrayList<>(client.getChannelPlaylistsRequest(channel.getId()));
            for(PlayList x:playLists1) {
                playLists.getItems().addAll(x.getName());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void openImage() {
        try {
            FileChooser chooser = new FileChooser();
            file = chooser.showOpenDialog(null);

            Image image = new Image(file.toURI().toString());
            videoPic.setImage(image);

        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    public void nextFunc() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("addinformationvideo.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 805, 530);
        if (isDarkModeOn) {
            scene.getStylesheets().add(getClass().getResource("DarkStyles.css").toExternalForm());
        }
        else {
            scene.getStylesheets().add(getClass().getResource("stylecss.css").toExternalForm());
        }
        Stage stage1 = (Stage) platImg.getScene().getWindow();
        stage1.close();
        stage.setTitle("---AddVideo---");
        stage.setScene(scene);
        stage.show();
    }
}
