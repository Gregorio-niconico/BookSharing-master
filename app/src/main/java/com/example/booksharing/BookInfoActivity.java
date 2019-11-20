package com.example.booksharing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.booksharing.database.book_info;
import java.util.List;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class BookInfoActivity extends AppCompatActivity {
    private static final String TAG = "BookInfoActivity";
    TextView ISBN;
    TextView Author;
    TextView Publishing;
    TextView Price;
    TextView BookName;
    MyImageView myImageView;
    private List<book_info> book_infoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);
//        LitePal.getDatabase();
        BookName = (TextView) findViewById(R.id.text_bookname);
        ISBN = (TextView) findViewById(R.id.text_isbn);
        Author = (TextView) findViewById(R.id.text_author);
        Publishing = (TextView) findViewById(R.id.text_publishing);
        Price = (TextView) findViewById(R.id.text_price);
        myImageView = (MyImageView) findViewById(R.id.image);
        showResult();

    }
    //显示书籍详情结果
    public void showResult() {
        //开启子线程做UI操作
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = getIntent();
                String mISBN = intent.getStringExtra("ISBN");
                String author = intent.getStringExtra("author");
                String publishing = intent.getStringExtra("publishing");
                String price = intent.getStringExtra("price");
                String bookname = intent.getStringExtra("name");
                String photourl = intent.getStringExtra("photourl");
                BookName.setText(bookname);
                ISBN.setText(mISBN);
                Author.setText(author);
                Price.setText(price);
                Publishing.setText(publishing);
                Log.d(TAG, "ISBN:"+ISBN);
                Log.d(TAG, "name:"+bookname);

                //显示图片
                Glide.with(BookInfoActivity.this).load(photourl).into(myImageView);
            }
        });
    }
}
