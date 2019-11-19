package com.example.booksharing.bookView;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.booksharing.BookInfoActivity;
import com.example.booksharing.MainActivity;
import com.example.booksharing.R;
import com.example.booksharing.ScanResultActivity;
import com.example.booksharing.database.book_info;

import org.litepal.LitePal;

import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * 为RecyclerView添加一个适配器
 */
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private Context mContext;
    private List<Book> mBook;
    private List<book_info> book_infoList;
    static  class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView bookImage;
        TextView bookName;
        public ViewHolder(View view){
            super(view);
            cardView=(CardView)view;
            bookImage=(ImageView)view.findViewById(R.id.book_picture);
            bookName=(TextView)view.findViewById(R.id.book_name);
        }
    }
    public BookAdapter(List<Book> bookList){
        mBook=bookList;
    }
    @NonNull
    @Override
    public BookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(mContext==null){
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.gridview_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                Book book=mBook.get(position);
//                LitePal.getDatabase();
//                book_infoList= LitePal.where("name=?",book.getName()).find(book_info.class);
//                    String bookname=book_infoList.get(0).getBookname();
//                    String ISBN=book_infoList.get(0).getIsbn();
//                    String author=book_infoList.get(0).getAuthor();
//                    String publishing=book_infoList.get(0).getPublishing();
//                    String price=book_infoList.get(0).getPrice();
//                    String photourl=book_infoList.get(0).getPictureurl();
                    Intent intent=new Intent(mContext, BookInfoActivity.class);
//                    intent.putExtra("ISBN",ISBN);
//                    intent.putExtra("author",author);
//                    intent.putExtra("publishing",publishing);
//                    intent.putExtra("price",price);
//                    intent.putExtra("name",bookname);
//                    intent.putExtra("photourl",photourl);
//                Log.d(TAG, "ISBN:"+ISBN);
//                Log.d(TAG, "name:"+bookname);
                    mContext.startActivity(intent);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.ViewHolder holder, int position) {
        Book book=mBook.get(position);
        holder.bookName.setText(book.getName());
        Glide.with(mContext).load(book.getImageId()).into(holder.bookImage);
    }

    @Override
    public int getItemCount() {
        return mBook.size();
    }


}
