package com.example.youtube.Model;

public class Video extends Short{
    private String description;
    private String category;
    private String Block;
    public Video(String ID,String idchal,String name,String description,String uplaodTime,
                  Integer duration,Integer view,String Block,String category ) {
        super(ID,idchal,name,uplaodTime,duration,view);
        this.description = description;
        this.category=category;
        this.setBlock(Block);

    }
    public String getDescription() {
        return description;
    }


    public void editDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category= category;
    }


    public String getBlock() {
        return Block;
    }

    public void setBlock(String block) {
        Block = block;
    }
}
