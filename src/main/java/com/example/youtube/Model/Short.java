package com.example.youtube.Model;

public class Short {
    private String ID;
    private String uploadTime;
    private Integer view;
    private Integer duration; //??
    private String name;
    private String path;
    private String IDChanel;
    public Short(String ID,String idchal,String name,String uplaodTime,
                 Integer duration,Integer view) {
        this.name = name;
        this.view = view;
        this.uploadTime = uplaodTime;
        this.duration=duration;
        this.ID=ID;
        this.IDChanel=idchal;
        this.setPath(ID);


    }

    public String getID() {
        return ID;
    }
    public String getName() {
        return name;
    }
    public int  getView() {
        return view;
    }

    public int  getDuration() {
        return duration;
    }

    public String getUploadTime() {
        return uploadTime;
    }


    public void editName(String name) {
        this.name = name;
    }

    public String getIDChanel() {
        return IDChanel;
    }

    public void setIDChanel(String IDChanel) {
        this.IDChanel = IDChanel;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
