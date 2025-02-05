package com.example.youtube.Server.API;

import com.example.youtube.DataBase.*;

import com.google.gson.Gson;
/***
 API codes in APIcodes.xlsx
 */
public class deleteApiService {
    private static final Gson gson = new Gson();
    public static String handleRequest(String request) {
        String[] parts = request.split("#", 2);
        String endpoint = parts[0];
        String body = parts.length > 1 ? parts[1] : "";

        switch (endpoint) {
            case "31":
                return deleteVideo(body); // video?
            case "32":
                return deletePalylist(body); // image?
            case "33":
                return deleteComment(body);
            case "34":
                return unfollow(body);
            case "35":
                return deleteVideoComments(body);
            default:
                return gson.toJson(new deleteApiService.ErrorResponse("Unknown endpoint"));
        }
    }
    //to delete a video
    private static String deleteVideo(String videoInfo) {  // video??
        try {
            String[] info=videoInfo.split("#",2);
            DataBaseManager.DE_Video(info[0],info[1]);
            //If the changes are applied successfully, return 1
            return "1";
        }catch (Exception e){
            System.out.println(e.getMessage());
            //if wasn't successfully, return 0
            return "0";
        }
    }

    //to delete playlist
    private static String deletePalylist(String playlistInfo) {    // image????
        try {
            DataBaseManager.deletePlayList(playlistInfo);
            //If the changes are applied successfully, return 1
            return "1";
        }catch (Exception e){
            System.out.println(e.getMessage());
            //if wasn't successfully, return 0
            return "0";
        }
    }

    // ?????????????????????????????/
    private static String deleteComment(String commentInfo) {
        try {
            String[] info=commentInfo.split("#",2);
            DataBaseManager.delete_Comment_writer(info[0],info[1]);
            //If the changes are applied successfully, return 1
            return "1";
        }catch (Exception e){
            System.out.println(e.getMessage());
            //if wasn't successfully, return 0
            return "0";
        }
    }
    // to unfollow
    private static String unfollow(String followInfo) {
        try {
            String[] info=followInfo.split("#",3);
            DataBaseManager.UnFollow(info[0],info[1],Integer.parseInt(info[2]));
            //If the changes are applied successfully, return 1
            return "1";
        }catch (Exception e){
            System.out.println(e.getMessage());
            //if wasn't successfully, return 0
            return "0";
        }
    }
    private static String deleteVideoComments(String videoInfo) {
        try {
            DataBaseManager.delete_Comment_ALL(videoInfo);
            //If the changes are applied successfully, return 1
            return "1";
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
