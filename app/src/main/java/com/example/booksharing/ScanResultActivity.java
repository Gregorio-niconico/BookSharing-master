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

import com.bumptech.glide.Glide;

public class ScanResultActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ScanResultActivity";
    //    TextView textUrl;
    TextView ISBN;
    TextView Author;
    TextView Publishing;
    TextView Price;
    TextView BookName;
    MyImageView myImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_result);
        Button buttonUrl = (Button) findViewById(R.id.button_commit);
        BookName = (TextView) findViewById(R.id.text_bookname);
        ISBN = (TextView) findViewById(R.id.text_isbn);
        Author = (TextView) findViewById(R.id.text_author);
        Publishing = (TextView) findViewById(R.id.text_publishing);
        Price = (TextView) findViewById(R.id.text_price);
        myImageView = (MyImageView) findViewById(R.id.image);
//        imageView = (ImageView) findViewById(R.id.image);
        buttonUrl.setOnClickListener(this);
        showResult();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_commit:
                showResult();
                break;
            default:
                break;
        }
    }

    public void showResult() {
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
                //在这里做UI操作，将结果显示到界面上
                BookName.setText(bookname);
                ISBN.setText(mISBN);
                Author.setText(author);
                Price.setText(price);
                Publishing.setText(publishing);
                Log.d(TAG, "run: "+photourl);
                Glide.with(ScanResultActivity.this).load(photourl).into(myImageView);
                Log.d(TAG, "parseJSONWithJSONObject: " + mISBN);
                Log.d(TAG, "parseJSONWithJSONObject: " + author);
                Log.d(TAG, "parseJSONWithJSONObject: " + publishing);
                Log.d(TAG, "parseJSONWithJSONObject: " + price);
                Log.d(TAG, "run: " + photourl);
            }
        });
    }

}