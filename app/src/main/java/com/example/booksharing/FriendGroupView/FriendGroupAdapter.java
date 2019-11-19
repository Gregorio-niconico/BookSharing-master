package com.example.booksharing.FriendGroupView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.booksharing.FriendView.Friend;
import com.example.booksharing.R;

import java.util.List;

public class FriendGroupAdapter extends ArrayAdapter<FriendGroup> {
    private int resourceId;
    public FriendGroupAdapter(Context context, int textViewRresourceId, List<FriendGroup> objects){
        super(context,textViewRresourceId,objects);
        resourceId=textViewRresourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //获取当前项的FriendGroup实例
        FriendGroup friendGroup=getItem(position);
        //View view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        View view;
        if(convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,
                    parent,false);
        }else{
            view=convertView;//convertView参数用于缓存加载好的布局以便于重用，提高运行效率
        }
        ImageView headPic=(ImageView)view.findViewById(R.id.img_name);
        TextView groupName=(TextView)view.findViewById(R.id.friendgroup_name);
        headPic.setImageResource(friendGroup.getHeadPic());
        groupName.setText(friendGroup.getFriendGroupName());
        return view;

    }
}