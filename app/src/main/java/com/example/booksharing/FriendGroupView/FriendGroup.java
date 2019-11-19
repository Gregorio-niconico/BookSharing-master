package com.example.booksharing.FriendGroupView;

public class FriendGroup {
    private String friendGroupName;
    private int headPic;
    public FriendGroup(String friendGroupName,int headPic){
        this.friendGroupName=friendGroupName;
        this.headPic=headPic;
    }

    public String getFriendGroupName() {
        return friendGroupName;
    }

    public int getHeadPic() {
        return headPic;
    }
}

