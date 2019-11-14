package com.example.booksharing.FriendView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.booksharing.R;

import java.util.List;

public class FriendAdapter extends ArrayAdapter<Friend> {
    private int resourceId;
    public FriendAdapter(Context context, int textViewRresourceId, List<Friend> objects){
        super(context,textViewRresourceId,objects);
        resourceId=textViewRresourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //获取当前项的Friend 实例
        Friend friends=getItem(position);
        //View view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        View view;
        if(convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,
                    parent,false);
        }else{
            view=convertView;//convertView参数用于缓存加载好的布局以便于重用，提高运行效率
        }
        ImageView bookImage=(ImageView)view.findViewById(R.id.img_name);
        TextView bookName=(TextView)view.findViewById(R.id.friend_name);
        bookImage.setImageResource(friends.getHeadPic());
        bookName.setText(friends.getName());
        return view;

    }
}