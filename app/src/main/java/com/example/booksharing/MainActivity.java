package com.example.booksharing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.booksharing.Http.HttpCallbackListener;
import com.example.booksharing.Http.HttpRequest;
import com.example.booksharing.bookView.Book;
import com.example.booksharing.bookView.BookAdapter;
import com.example.booksharing.database.book_GSON;
import com.example.booksharing.database.book_info;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class MainActivity extends AppCompatActivity  {
    private static final String TAG="MainActivity";
    private DrawerLayout mDrawerLayout;
    private List<book_info> book_infoList;
    private List<book_info> book_List;
//    private ArrayList<Book> bookArrayList;
    private List<Book> bookList=new ArrayList<>();
    private BookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取数据库
        LitePal.getDatabase();
        //加载toolbar
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        NavigationView navigationView=(NavigationView)findViewById(R.id.nav_view);
        ActionBar actionBar=getSupportActionBar();
        navigationView.setCheckedItem(R.id.nav_home);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                if(menuItem.getItemId()==R.id.nav_decode){
//                    onScanBarcode(menuItem);
//                }
                switch (menuItem.getItemId()) {
                    case R.id.nav_decode:
                        onScanBarcode(menuItem);
                        break;
                    case R.id.nav_friend:
                        Intent intent2=new Intent(MainActivity.this,FriendsActivity.class);
                        startActivity(intent2);
                        Log.d(TAG, "onNavigationItemSelected: "+"friend");
                        break;
                }
                return true;

            }
        });
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.users);
        }
        book_List=LitePal.select("pictureurl","bookname").find(book_info.class);
        int nums=book_List.size();
        //添加书籍列表
        for(int i=0;i<nums;i++) {
            bookList.add(new Book(book_List.get(i).getBookname().toString(),
                    book_List.get(i).getPictureurl().toString()));
        }
        //RecyclerView用户显示列表中的书名以及图片地址
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new BookAdapter(bookList);
        recyclerView.setAdapter(adapter);
    }


    //扫码返回
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "扫码取消！", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "扫描成功，条码值: " + result.getContents()
                        , Toast.LENGTH_LONG).show();
                //查询ISBN
                queryBook(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    //匹配数据库是否有已有书籍
    private void queryBook(String isbn){
        book_infoList=LitePal.where("isbn=?",isbn).find(book_info.class);
        //若数据库已存在数据，直接调用
        if(!book_infoList.isEmpty()){
            String bookname=book_infoList.get(0).getBookname();
            String ISBN=book_infoList.get(0).getIsbn();
            String author=book_infoList.get(0).getAuthor();
            String publishing=book_infoList.get(0).getPublishing();
            String price=book_infoList.get(0).getPrice();
            String photourl=book_infoList.get(0).getPictureurl();
            Intent intent=new Intent(MainActivity.this,ScanResultActivity.class);
            intent.putExtra("ISBN",ISBN);
            intent.putExtra("author",author);
            intent.putExtra("publishing",publishing);
            intent.putExtra("price",price);
            intent.putExtra("name",bookname);
            intent.putExtra("photourl",photourl);
            Log.d(TAG, "query: "+ISBN);
            Log.d(TAG, "query: "+author);
            Log.d(TAG, "query: "+publishing);
            Log.d(TAG, "query: "+price);
            startActivity(intent);
        }else {
            //若不存在去接口查询
            final  String url="https://isbn.szmesoft.com/isbn/query?isbn="+isbn;
            HttpRequest httpRequest=new HttpRequest();
            httpRequest.sendHttpRequest(url, new HttpCallbackListener() {
                @Override
                public void onFinish(String response) {
                    Log.d(TAG, "url"+response);
                    parseJSONWithJSONObject(response);
                }
                @Override
                public void onError(Exception e) {
                    Log.d(TAG, "onError: "+e);
                }
            });
        }
    }

    //Json数据筛选
    private void parseJSONWithJSONObject(String jsonData){
        JSONObject jsonObject= null;
        try {
            jsonObject = new JSONObject(jsonData);
            String ISBN=jsonObject.getString("ISBN");
            String author=jsonObject.getString("Author");
            String publishing=jsonObject.getString("Publishing");
            String price=jsonObject.getString("Price");
            String bookname=jsonObject.getString("BookName");
            String photourl="https://isbn.szmesoft.com/ISBN/GetBookPhoto?ID="
                    +jsonObject.getString("PhotoUrl");
            Intent intent=new Intent(MainActivity.this,ScanResultActivity.class);
            intent.putExtra("ISBN",ISBN);
            intent.putExtra("author",author);
            intent.putExtra("publishing",publishing);
            intent.putExtra("price",price);
            intent.putExtra("name",bookname);
            intent.putExtra("photourl",photourl);
            Log.d(TAG, "parseJSONWithJSONObject: "+ISBN);
            Log.d(TAG, "parseJSONWithJSONObject: "+author);
            Log.d(TAG, "parseJSONWithJSONObject: "+publishing);
            Log.d(TAG, "parseJSONWithJSONObject: "+price);
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //条形码扫描
    public void onScanBarcode(MenuItem menuItem){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setPrompt("扫描条形码");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.initiateScan();
    }

    //加载toolbar.xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    //处理标题栏按钮的点击事件
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.homeIcon:
                Toast.makeText(this,"This is Home Button",Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this,"This is Home Button",Toast.LENGTH_SHORT).show();
                break;
            //HomeAsUp按钮默认值都是android.R.id.home
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }

}
