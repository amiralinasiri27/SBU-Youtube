package com.example.youtube.Server.API;

import com.example.youtube.Model.*;
import com.example.youtube.DataBase.*;

import com.google.gson.Gson;
public class checkApiService {
    private static final Gson gson = new Gson();

    public static String handleRequest(String request) {
        String[] parts = request.split("#", 2);
        String endpoint = parts[0];
        String body = parts.length > 1 ? parts[1] : "";

        switch (endpoint) {
            case "51":
                return checkEmail(body);
            case "52":
                return checkUsername(body);
            case "53":
                return checkUserExists(body);
            case "54":
                return checkVideoSaving(body);
            case "55":
                return checkPlaylistSaving(body);
            default:
                System.out.println("Unknown endpoint");
                return "0";
        }
    }

    //This function checks if an account has been created with such an email
    private static String checkEmail(String email) {
        try {
            if(DataBaseManager.CheckEmailUser(email)){
                //Returns 1 if present
                return "1";
            }
            //Returns 0 if not present
            return "0";
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ("0");
        }
    }
    //This function checks if an account has been created with such a username
    private static String checkUsername(String username) {
        try {
            if(DataBaseManager.CheckUserName(username)){
                //Returns 1 if present
                return "1";
            }
            //Returns 0 if not present
            return "0";
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ("0");
        }
    }
    //This function checks if a user exists
    private static String checkUserExists(String userInfo) {
        try {
            String[] info=userInfo.split("#",2);
            if(DataBaseManager.CheckUserPass_Email(info[0],info[1])){
                //Returns 1 if present
                return "1";
            }
            //Returns 0 if not present
            return "0";
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ("0");
        }
    }
    //This function checks if a video saved
    private static String checkVideoSaving(String videoInfo) {
        try {
            String[] info=videoInfo.split("#",2);
            if(DataBaseManager.ChecksaveVideo(info[0],info[1])){
                //Returns 1 if present
                return "1";
            }
            //Returns 0 if not present
            return "0";
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ("0");
        }
    }
    //This function checks if a playlist saved
    private static String checkPlaylistSaving(String videoInfo) {
        try {
            String[] info=videoInfo.split("#",2);
            if(DataBaseManager.ChecksavePlayllist(info[0],info[1])){
                //Returns 1 if present
                return "1";
            }
            //Returns 0 if not present
            return "0";
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ("0");
        }
    }
}
