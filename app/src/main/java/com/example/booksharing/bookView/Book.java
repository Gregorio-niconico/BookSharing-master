package com.example.booksharing.bookView;

public class Book {
    private String name;
    private  String imageId;
    public Book(String name,String imageId){
        this.imageId=imageId;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public String getImageId() {
        return imageId;
    }
}
