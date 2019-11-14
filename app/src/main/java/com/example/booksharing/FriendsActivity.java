package com.example.booksharing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.booksharing.FriendView.Friend;
import com.example.booksharing.FriendView.FriendAdapter;

import java.util.ArrayList;
import java.util.List;

public class FriendsActivity extends AppCompatActivity {
    private List<Friend> friendList=new ArrayList<Friend>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        initFriends();
        FriendAdapter adapter=new FriendAdapter(this,
                R.layout.friend_item,friendList);
        ListView listView=(ListView)findViewById(R.id.friend_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Friend friend=friendList.get(position);
                Toast.makeText(FriendsActivity.this,
                        friend.getName(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initFriends(){
        for(int i=0;i<10;i++){
            Friend me=new Friend("Gregorio",R.mipmap.header);
            friendList.add(me);
        }
    }
}
