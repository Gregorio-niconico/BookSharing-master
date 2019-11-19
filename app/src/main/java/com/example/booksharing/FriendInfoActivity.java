package com.example.booksharing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.booksharing.bookView.Book;
import com.example.booksharing.bookView.BookAdapter;
import com.example.booksharing.database.book_info;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class FriendInfoActivity extends AppCompatActivity {
    private List<book_info> book_infoList;
    private List<book_info> book_List;
    private List<Book> bookList=new ArrayList<>();
    private BookAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_info);
        //查找书籍
        book_List= LitePal.select("pictureurl","bookname").find(book_info.class);
        int nums=book_List.size();
        //添加书籍列表
        for(int i=0;i<nums;i++) {
            bookList.add(new Book(book_List.get(i).getBookname().toString(),
                    book_List.get(i).getPictureurl().toString()));
        }
        //RecyclerView用户显示列表中的书名以及图片地址
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.fg_recycler_view);
        GridLayoutManager layoutManager=new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new BookAdapter(bookList);
        recyclerView.setAdapter(adapter);
    }
}
