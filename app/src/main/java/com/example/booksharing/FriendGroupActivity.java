package com.example.booksharing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.booksharing.FriendGroupView.FriendGroup;
import com.example.booksharing.FriendGroupView.FriendGroupAdapter;
import com.example.booksharing.FriendView.Friend;
import com.example.booksharing.FriendView.FriendAdapter;

import java.util.ArrayList;
import java.util.List;

public class FriendGroupActivity extends AppCompatActivity {
    private List<FriendGroup> friendGroups=new ArrayList<FriendGroup>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendgroup);
        initFriendGroup();
        FriendGroupAdapter adapter=new FriendGroupAdapter(this,
                R.layout.friendgroup_item,friendGroups);
        ListView listView=(ListView)findViewById(R.id.friendgroup_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FriendGroup friendGroup=friendGroups.get(position);
                Toast.makeText(FriendGroupActivity.this,
                        friendGroup.getFriendGroupName(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initFriendGroup(){
        for(int i=0;i<10;i++){
            FriendGroup fg=new FriendGroup("圈子"+i,R.mipmap.gift);
            friendGroups.add(fg);
        }
    }
}
