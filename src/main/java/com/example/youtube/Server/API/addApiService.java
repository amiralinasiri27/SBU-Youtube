package com.example.youtube.Server.API;

import com.example.youtube.Model.*;
import com.example.youtube.DataBase.*;

import com.google.gson.Gson;
/***
  API codes in APIcodes.xlsx
 */

public class addApiService {
    private static final Gson gson = new Gson();

    public static String handleRequest(String request) {
        String[] parts = request.split("#", 2);
        String endpoint = parts[0];
        String body = parts.length > 1 ? parts[1] : "";

        switch (endpoint) {
            case "21":
                return addUser(body);
            case "22":
                return addChannel(body); // image?
            case "23":
                return addComment(body);
            case "24":
                return addPlayList(body); // image?
            case "25":
                return addVideo(body);  // video?
            case "26":
                return addVideoToHistory(body);
            case "27":
                return addFollowerOrFollowing(body);
            case "28":
                return addKarma(body);
            case "29":
                return addVideoToSaved(body);
            case "210":
                return addPlaylistToSaved(body);
            default:
                System.out.println("unknown endpoint");
                return "0";
        }
    }
    //to add a new user
    private static String addUser(String userJson) {
        try {
            System.out.println("[ADD USER]");
            User user = gson.fromJson(userJson, User.class);
            System.out.println(user.getID());
            if(DataBaseManager.Cr_User(user)) {
                //If the changes are applied successfully, return 1
                return "1";
            }
            return "0";
        }catch (Exception e){
            System.out.println(e.getMessage());
            //if wasn't successfully, return 0
            return "0";
        }
    }
    //to add a new channel
    private static String addChannel(String channelJson) {
        try {
            System.out.println("[START ADD CHANNEL ]");
            Channel channel = gson.fromJson(channelJson, Channel.class);
            System.out.println(channel.getName());
            if(DataBaseManager.Cr_Chanel(channel)) {
                //If the changes are applied successfully, return 1
                System.out.println("[ADD CHANNEL ]");
                return "1";
            }
            return "0";
        }catch (Exception e){
            System.out.println(e.getMessage());
            //if wasn't successfully, return 0
            return "0";
        }
        //                            ###
        //                            prifile and image
        //                            ###
    }
    //to add a new comment
    private static String addComment(String commentJson) {
        try {
            Comment comment = gson.fromJson(commentJson, Comment.class);
            if(DataBaseManager.Cr_comment(comment)) {
                //If the changes are applied successfully, return 1
                return "1";
            }
            return "0";
        }catch (Exception e){
            System.out.println(e.getMessage());
            //if wasn't successfully, return 0
            return "0";
        }
    }
    //to add a new playlist
    private static String addPlayList(String playlistJson) {
        try {
            PlayList playList= gson.fromJson(playlistJson, PlayList.class);
            if(DataBaseManager.Cr_PlayList(playList)) {
                //If the changes are applied successfully, return 1
                return "1";
            }
            return "0";
        }catch (Exception e){
            System.out.println(e.getMessage());
            //if wasn't successfully, return 0
            return "0";
        }
        //                             ###
        //                             image?
        //                             ###
    }
    //to add a new comment
    private static String addVideo(String videoJson) {
        try {
            System.out.println("[ADD Video ]");
            Video video= gson.fromJson(videoJson, Video.class);
            if(DataBaseManager.Cr_Video(video)) {
                //If the changes are applied successfully, return 1
                return "1";
            }
            return "0";
        }catch (Exception e){
            System.out.println(e.getMessage());
            //if wasn't successfully, return 0
            return "0";
        }
        //                             ###
        //                             video?
        //
    }
    // to add a video to a user history
    private static String addVideoToHistory(String videoInfo) {
        try {
            String[] info=videoInfo.split("#",2);
            if(DataBaseManager.ADD_video_history(info[0],info[1])) {
                //If the changes are applied successfully, return 1
                return "1";
            }
            return "0";
        }catch (Exception e){
            System.out.println(e.getMessage());
            //if wasn't successfully, return 0
            return "0";
        }
    }
    // to add a follower or following
    private static String addFollowerOrFollowing(String followInfo) {
        try {
            String[] info=followInfo.split("#",3);
            if(DataBaseManager.ADD_follower_following(info[0],info[1], Integer.parseInt(info[2]))) {
                //If the changes are applied successfully, return 1
                return "1";
            }
            return "0";
        }catch (Exception e){
            System.out.println(e.getMessage());
            //if wasn't successfully, return 0
            return "0";
        }
    }
    private static String addKarma(String karmaInfo) {
        try {
            String[] info=karmaInfo.split("#",3);
            if(DataBaseManager.Karam(Integer.parseInt(info[0]),info[1],info[2])) {
                //If the changes are applied successfully, return 1
                return "1";
            }
            return "0";
        }catch (Exception e){
            System.out.println(e.getMessage());
            //if wasn't successfully, return 0
            return "0";
        }
    }
    private static String addVideoToSaved(String saveInfo) {
        try {
            String[] info=saveInfo.split("#",2);
            if(DataBaseManager.SA_Video(info[0],info[1])) {
                //If the changes are applied successfully, return 1
                return "1";
            }
            return "0";
        }catch (Exception e){
            System.out.println(e.getMessage());
            //if wasn't successfully, return 0
            return "0";
        }
    }
    private static String addPlaylistToSaved(String saveInfo) {
        try {
            String[] info=saveInfo.split("#",2);
            if(DataBaseManager.SA_playlist(info[0],info[1])) {
                //If the changes are applied successfully, return 1
                return "1";
            }
            return "0";
        }catch (Exception e){
            System.out.println(e.getMessage());
            //if wasn't successfully, return 0
            return "0";
        }
    }
}
