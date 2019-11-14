package com.example.booksharing.FriendView;

public class Friend {
    private String name;
    private  int headPic;
    public Friend(String name,int headPic){
        this.name=name;
        this.headPic=headPic;
    }

    public String getName() {
        return name;
    }

    public int getHeadPic() {
        return headPic;
    }
}
