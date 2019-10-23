package com.example.booksharing.bookView;

public class Book {
    private String name;
    private  String imageId;
    public Book(String name,String imageId){
        this.name=name;
        this.imageId=imageId;
    }

    public String getName() {
        return name;
    }

    public String getImageId() {
        return imageId;
    }
}
