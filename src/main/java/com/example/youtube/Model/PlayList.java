package com.example.youtube.Model;

public class PlayList {
    private String description;
    private String name;
    private String ID;
    private String channelID;
    private String image;

    //videos???
    public PlayList(String name, String ID, String channelID, String description,String image){
        this.description=description;
        this.ID=ID;
        this.name=name;
        this.channelID=channelID;
        this.setImage(image);
    }

    public String getDescription() {
        return description;
    }

    public String getChannelID() {
        return channelID;
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return ID;
    }

    public void editDescription(String description) {
        this.description = description;
    }
    public void changeName(String name){
        this.name=name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
