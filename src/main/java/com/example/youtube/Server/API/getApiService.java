package com.example.youtube.Server.API;

import com.example.youtube.Model.*;
import com.example.youtube.DataBase.*;

import com.google.gson.Gson;

import java.util.ArrayList;
/***
 API codes in APIcodes.xlsx
 */
public class getApiService {
    private static final Gson gson = new Gson();

    public static String handleRequest(String request) {
        String[] parts = request.split("#", 2);
        String endpoint = parts[0];
        String body = parts.length > 1 ? parts[1] : "";
        switch (endpoint) {
            case "11":
                return getUser(body);
            case "12":
                return getChannel(body);
            case "13":
                return getVideoComments(body);
            case "14":
                return getChannelComments(body);
            case "15":
                return getUserComments(body);
            case "16":
                return getChannelPlaylists(body);
            case "17":
                return getChannelVideos(body);
            case "18":
                return getplaylistVideos(body);
            case "19":
                return getVideosByCategory(body);
            case "110":
                return getSavedVideos(body);
            case "111":
                return getSavedPlaylists(body);
            case "112":
                return getVideoByRandomCategory(body);
            case "113":
                return getVideoHistory(body);
            case "114":
                return getSearchedVideos(body);
            case "115":
                return getSearchedChannels(body);
            case "116":
                return getSearchedPlaylists(body);
            case "117":
                return countVideoLike(body);
            case "118":
                return countShortLike(body);
            default:
                System.out.println("unknown endpoint");
                return "0";
        }
    }

    private static String getUser(String userInfo) {
        try {
            System.out.println("Get user ");
            String[] info=userInfo.split("#",2);
            User user = DataBaseManager.get_User(info[0],info[1]);
            String response=gson.toJson(user);
            return response;
        }catch (Exception e){
            return ("0");
        }
    }
    //get Channel with  username or ID_chanel
    private static String getChannel(String channelInfo) {
        try {
            String[] info=channelInfo.split("#",2);
            Channel channel=DataBaseManager.get_Channel(info[0],Integer.parseInt(info[1]));
            String response=gson.toJson(channel);
            return response;
        }catch (Exception E){
            return ("0");
        }
    }
    // this is for get the comments of a video
    private static String getVideoComments(String videoInfo) {
        try {
            ArrayList<Comment> comments=DataBaseManager.getListComment(videoInfo);
            String response=gson.toJson(comments);
            return response;
        }catch (Exception e){
            return ("0");
        }
    }
    //comment that User get for his videos
    private static String getChannelComments(String channelInfo) {
        try {
            ArrayList<Comment> comments=DataBaseManager.getListComment_userGet(channelInfo);
            String response=gson.toJson(comments);
            return response;
        }catch (Exception e){
            return ("0");
        }
    }
    //get list of comment that the User send
    private static String getUserComments(String userInfo) {
        try {
            ArrayList<Comment> comments=DataBaseManager.getListCommentUser(userInfo);
            String response=gson.toJson(comments);
            return response;
        }catch (Exception e){
            return ("0");
        }
    }
    //get list of playlist in chanel
    private static String getChannelPlaylists(String channelInfo) {
        try {
            ArrayList<PlayList> playLists=DataBaseManager.getPlayList(channelInfo);
            String response=gson.toJson(playLists);
            return response;
        }catch (Exception e){
            return ("0");
        }
    }
    //get channel videos
    private static String getChannelVideos(String channelInfo) {
        try {
            ArrayList<Video> videos=DataBaseManager.getList_video(channelInfo);
            String response=gson.toJson(videos);
            return response;
        }catch (Exception e){
            return ("0");
        }
    }
    private static String getplaylistVideos(String playlistInfo) {
        try {
            ArrayList<Video> videos=DataBaseManager.getListVideoInPlayList(playlistInfo);
            String response=gson.toJson(videos);
            return response;
        }catch (Exception e){
            return ("0");
        }
    }
    private static String getVideosByCategory(String categoryInfo) {
        try {
            ArrayList<Video> videos=DataBaseManager.getListVideoByCategory(categoryInfo);
            String response=gson.toJson(videos);
            return response;
        }catch (Exception e){
            return ("0");
        }
    }
    private static String getSavedVideos(String userInfo) {
        try {
            ArrayList<Video> videos=DataBaseManager.getlistVideoSave(userInfo);
            String response=gson.toJson(videos);
            return response;
        }catch (Exception e){
            return ("0");
        }
    }
    private static String getSavedPlaylists(String userInfo) {
        try {
            ArrayList<PlayList> plylists=DataBaseManager.getListPlayListSave(userInfo);
            String response=gson.toJson(plylists);
            return response;
        }catch (Exception e){
            return ("0");
        }
    }
    private static String getVideoHistory(String userInfo) {
        try {
            ArrayList<Video> videos=DataBaseManager.getListVideoInHistory(userInfo);
            String response=gson.toJson(videos);
            return response;
        }catch (Exception e){
            return ("0");
        }
    }
    private static String getVideoByRandomCategory(String categoryInfo) {
        try {
            String[] info=categoryInfo.split("#",2);
            ArrayList<Video> videos=DataBaseManager.getListVideoByCategoryRandom(Integer.parseInt(info[0]),info[1]);
            String response=gson.toJson(videos);
            return response;
        }catch (Exception e){
            return ("0");
        }
    }private static String getVideoByRandomAll(String categoryInfo) {
        try {
            String[] info=categoryInfo.split("#",2);
            ArrayList<Video> videos=DataBaseManager.getListVideoByCategoryRandom(Integer.parseInt(info[0]),info[1]);
            String response=gson.toJson(videos);
            return response;
        }catch (Exception e){
            return ("0");
        }
    }
    private static String getSearchedVideos(String searchInfo) {
        try {
            String[] info=searchInfo.split("#",2);
            ArrayList<Video> videos=DataBaseManager.SE_video(info[0],info[1]);
            String response=gson.toJson(videos);
            return response;
        }catch (Exception e){
            return ("0");
        }
    }
    private static String getSearchedChannels(String searchInfo) {
        try {
            String[] info=searchInfo.split("#",2);
            ArrayList<Channel> channels=DataBaseManager.SE_Chanel(info[0],info[1]);
            String response=gson.toJson(channels);
            return response;
        }catch (Exception e){
            return ("0");
        }
    }
    private static String getSearchedPlaylists(String searchInfo) {
        try {
            String[] info=searchInfo.split("#",2);
            ArrayList<PlayList> playLists=DataBaseManager.SE_playLists(info[0],info[1]);
            String response=gson.toJson(playLists);
            return response;
        }catch (Exception e){
            return ("0");
        }
    }
    private static String countVideoLike(String videoInfo) {
        try {
            String[] info=videoInfo.split("#",2);
            int karma=DataBaseManager.countVideoLike(info[0],info[1]);
            String response=String.valueOf(karma);
            return response;
        }catch (Exception e){
            return ("0");
        }
    }
    private static String countShortLike(String shortInfo) {
        try {
            String[] info=shortInfo.split("#",2);
            int karma=DataBaseManager.countShortLike(info[0],info[1]);
            String response=String.valueOf(karma);
            return response;
        }catch (Exception e){
            return ("0");
        }
    }

    //TODO get following






    //TODO get followers







}
