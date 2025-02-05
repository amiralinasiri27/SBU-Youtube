package com.example.youtube.Server.API;
import com.example.youtube.DataBase.*;

import com.google.gson.Gson;
/***
 API codes in APIcodes.xlsx
 */
public class updateApiService {
    private static final Gson gson = new Gson();
    public static String handleRequest(String request) {
        String[] parts = request.split("#", 2);
        String endpoint = parts[0];
        String body = parts.length > 1 ? parts[1] : "";

        switch (endpoint) {
            case "41":
                return editPassword(body); // video?
            case "42":
                return editUsername(body); // image?
            case "43":
                return editNameOfPlayList(body);
            case "44":
                return updateLikeComment(body);
            case "45":
                return editTextComment(body); // video?
            case "46":
                return updateDeslikeComment(body); // image?
            case "47":
                return editChannelName(body);
            case "48":
                return editChannelDescription(body);
            case "49":
                return editChannelLinks(body);
            case "410":
                return updateVideoViews(body);
            case "411":
                return updateVideoLikes(body);
            case "412":
                return updateVideoDeslikes(body);
            // video?
            //               ####
            //               update image??????
            //               ####
            default:
                return gson.toJson(new updateApiService.ErrorResponse("Unknown endpoint"));
        }
    }
    //to change password
    private static String editPassword(String userInfo) {
        try {
            String[] info=userInfo.split("#",2);
            if(DataBaseManager.CH_PassWordUser(info[0],info[1])) {
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
    //to change username
    private static String editUsername(String userInfo) {
        try {
            String[] info=userInfo.split("#",2);
            if(DataBaseManager.CH_UserName(info[0],info[1])) {
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
    // edit name of playlist
    private static String editNameOfPlayList(String playlistInfo) {
        try {
            String[] info=playlistInfo.split("#",2);
            if(DataBaseManager.UP_Name_Playlist(info[0],info[1])) {
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
    // to like a comment
    private static String updateLikeComment(String commentInfo) {
        try {
            if(DataBaseManager.UP_Like_Comment(commentInfo)) {
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
    // to edit a comment text
    private static String editTextComment(String commentInfo) {
        try {
            String[] info=commentInfo.split("#",2);
            if(DataBaseManager.UP_Comment_Comment(info[0],info[1])) {
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
    // to deslike a comment
    private static String updateDeslikeComment(String commentInfo) {
        try {
            if(DataBaseManager.UP_DisLike_Comment(commentInfo)) {
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
    //To edit channel name
    private static String editChannelName(String channelInfo) {
        try {
            String[] info=channelInfo.split("#",2);
            if(DataBaseManager.UP_Name_Chanel(info[0],info[1])) {
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
    // To edit description of a channel
    private static String editChannelDescription(String channelInfo) {
        try {
            String[] info=channelInfo.split("#",2);
            if(DataBaseManager.UP_information_Chanel(info[0],info[1])) {
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
    private static String editChannelLinks(String channelInfo) {
        try {
            String[] info=channelInfo.split("#",2);
            if(DataBaseManager.UP_Link_Chanel(info[0],info[1])) {
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
    private static String updateVideoViews(String videoInfo) {
        try {
            String[] info=videoInfo.split("#",2);
            if(DataBaseManager.UP_view_vidoe(info[0],info[1])) {
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
    private static String updateVideoLikes(String videoInfo) {
        try {
            String[] info=videoInfo.split("#",2);
            if(DataBaseManager.UP_Like_Vidoe(info[0],info[1])) {
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
    private static String updateVideoDeslikes(String videoInfo) {
        try {
            String[] info=videoInfo.split("#",2);
            if(DataBaseManager.UP_DisLike_Vidoe(info[0],info[1])) {
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
    private static class ErrorResponse {
        private String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
