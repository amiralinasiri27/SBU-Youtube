package com.example.youtube.Model;

import java.util.ArrayList;
import java.util.Arrays;

public class Channel {
    private String id;
    private String name;
    private String description;
    private String image_Chane=null;

    private String image_pro=null;

    private ArrayList<String> Link;
    private String country;


    private String username;

    public Channel(String id,String name,String description,String username,String image){
        this.id=id;
        this.description=description;
        this.name=name;
        this.username=username;
        this.setImage(image);
        Link=new ArrayList<>();
        this.image_pro=name+image+"PRO";
        this.image_Chane=name+image+"Chanel";
    }




    public Channel() {

    }

    public Channel(String idChanel, String name, String information, String imageChanel, String username, String imagePro, String link) {
        this.id=idChanel;
        this.name=name;
        this.description=information;
        this.image_Chane=imageChanel;
        this.username=username;
        this.image_pro=imagePro;
        String[] parts = link.split("#");
        this.Link = new ArrayList<>(Arrays.asList(parts));
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void changeDescription(String description) {
        this.description = description;
    }

    public void changeName(String name) {
        this.name = name;
        // you should know this is unique
        //add database method
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage() {
        return image_Chane;
    }

    public void setImage(String image) {
        this.image_Chane = image;
    }

    public String getImage_pro() {
        return image_pro;
    }

    public void setImage_pro(String image_pro) {
        this.image_pro = image_pro;
    }

    public ArrayList<String> getLink() {
        return Link;
    }
    public String get_Link() {
        String e = null;
        for (String x :Link){
            e=e+"#"+x;
        }
        return e;
    }

    public void setLink(String link) {
        Link.add(link);
    }
}
