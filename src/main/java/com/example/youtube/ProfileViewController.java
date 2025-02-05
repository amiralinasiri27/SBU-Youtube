package com.example.youtube;

import com.example.youtube.Model.Channel;
import com.example.youtube.Model.User;
import com.example.youtube.Server.Client;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileViewController implements Initializable {

    // handle subKeys in inisialize Function (sub or no / this is his page or no)

    public TilePane content;

    public Channel channel;

    public User user;
    public boolean check=true;
    public Client client;
    @FXML
    private ImageView profilePic;
    @FXML
    private ImageView longPic;
    @FXML
    private Label pageName;
    private Label Numvideos;
    private Label Numsubs;
    @FXML
    private AnchorPane moreInfo;
    @FXML
    private Button moreBtn;
    @FXML
    private Label describe;
    @FXML
    private Button unSubKey;
    @FXML
    private Button subKey;
    @FXML
    private Label playListL;
    @FXML
    private Label videosL;
    @FXML
    private Button getinformation;

    @FXML
    private ImageView imaeg4;

    @FXML
    private ImageView image1;

    @FXML
    private ImageView image2;

    @FXML
    private ImageView image3;

    @FXML
    private ImageView image5;

    @FXML
    private Button imageB1;

    @FXML
    private Button imageB2;

    @FXML
    private Button imageB3;

    @FXML
    private Button imageB4;

    @FXML
    private Button imageB5;
    @FXML
    private Button PlaylistM;
    @FXML
    private Button vidoemy;

    private boolean isDarkModeOn = HelloController.isDarkModeOn;

    public void createVideoBox() {
        VBox vbox = new VBox();
        vbox.prefWidth(309.0);
        vbox.prefHeight(680.0);

        ImageView imageView = new ImageView();
//        imageView.setImage(new Image(// path));
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

//    @FXML
//    public void moreBtnClick() {
//        if (moreInfo.isVisible()) {
//            moreBtn.setText("More...");
//            moreInfo.setVisible(false);
//
//        }
//        else {
//            moreBtn.setText("Less");
//            moreInfo.setVisible(true);
//
//            describe.setText(channel.getDescription());
//
//        }
//
//    }

    @FXML
    public void subFunc() {
        subKey.setVisible(false);
        unSubKey.setVisible(true);
    }
    @FXML
    public void unSubFunc() {
        subKey.setVisible(true);
        unSubKey.setVisible(false);
    }

    @FXML
    AnchorPane ShowInformation=new AnchorPane();

    @FXML
    Label   Email=new Label();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        HBox hBox=new HBox();
        Label information =new Label("information: ");
        information.setFont(Font.font("Californian FB"));
        Label informationSet=new Label();
        informationSet.setFont(Font.font("Californian FB"));
        hBox.getChildren().addAll(information,informationSet);
        hBox.setVisible(false);

        getinformation.setOnAction(event -> {
            informationSet.setText(channel.getDescription());
            //get numebr of video and Subscriber
            //better request to server and count the number







            //--------------------------------
            pageName.setText(channel.getName());
        });

        vidoemy.setOnAction(event -> {
            //get information form client
            //get 5 image from

            //
        });
        PlaylistM.setOnAction(event -> {
            //get information form client
            //get 5 image from


            //
        });

//        System.out.println(check);
        moreBtn.setOnAction(e->{
            hBox.setVisible(true);
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(3), event -> {
                        ShowInformation.setVisible(false);
                    })
            );
            timeline.play();
        });
        if (check){
            unSubKey.setVisible(false);
            subKey.setVisible(false);
//
        }else{
            unSubKey.setVisible(true);
            subKey.setVisible(true);
        }
        //  chanel information

//        Numsubs.setText();
//        Numvideos.setText();


        Email.setText(user.getEmail());
        //set date


    }

    private void Setinformation() {

    }

    @FXML
    public void videosLHover() {
        if (isDarkModeOn) {
            videosL.setStyle("-fx-border-color: #FFF");
        }
        else {
            videosL.setStyle("-fx-border-color: #000");
        }
    }
    @FXML
    public void playListHover() {
        if (isDarkModeOn) {
            playListL.setStyle("-fx-border-color: #FFF");
        }
        else {
            playListL.setStyle("-fx-border-color: #000");
        }
    }





}
