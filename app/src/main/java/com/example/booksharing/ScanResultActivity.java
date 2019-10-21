package com.example.booksharing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.booksharing.database.book_info;

import org.litepal.LitePal;

import java.util.List;

public class ScanResultActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ScanResultActivity";
    //    TextView textUrl;
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
        setContentView(R.layout.activity_scan_result);
        LitePal.getDatabase();
        Button buttonUrl = (Button) findViewById(R.id.button_commit);
        BookName = (TextView) findViewById(R.id.text_bookname);
        ISBN = (TextView) findViewById(R.id.text_isbn);
        Author = (TextView) findViewById(R.id.text_author);
        Publishing = (TextView) findViewById(R.id.text_publishing);
        Price = (TextView) findViewById(R.id.text_price);
        myImageView = (MyImageView) findViewById(R.id.image);
        buttonUrl.setOnClickListener(this);
        showResult();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_commit:
                Intent intent = getIntent();
                String mISBN = intent.getStringExtra("ISBN");
                String author = intent.getStringExtra("author");
                String publishing = intent.getStringExtra("publishing");
                String price = intent.getStringExtra("price");
                String bookname = intent.getStringExtra("name");
                String photourl = intent.getStringExtra("photourl");
                book_infoList=LitePal.where("isbn=?",mISBN).find(book_info.class);
                if(book_infoList.isEmpty()) {
                    book_info bookInfo = new book_info();
                    bookInfo.setBookname(bookname);
                    bookInfo.setIsbn(mISBN);
                    bookInfo.setAuthor(author);
                    bookInfo.setPublishing(publishing);
                    bookInfo.setPrice(price);
                    bookInfo.setPictureurl(photourl);
                    bookInfo.save();
                    Log.d(TAG, "成功储存！");
                    Toast.makeText(this, "图书添加成功！", Toast.LENGTH_SHORT).show();
                }else{
                    Log.d(TAG, "书籍已存在~");
                    Toast.makeText(this, "图书已存在~", Toast.LENGTH_SHORT).show();
                }
                Intent mitent=new Intent(ScanResultActivity.this,MainActivity.class);
                startActivity(mitent);
                break;
            default:
                break;
        }
    }

    //显示扫描结果
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
                Log.d(TAG, "run: "+photourl);
                //显示图片
                Glide.with(ScanResultActivity.this).load(photourl).into(myImageView);
            }
        });
    }


}