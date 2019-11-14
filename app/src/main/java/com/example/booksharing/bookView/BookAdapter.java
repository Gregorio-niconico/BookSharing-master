package com.example.booksharing.bookView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.booksharing.R;

import java.util.List;

/**
 * 为RecyclerView添加一个适配器
 */
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private Context mContext;
    private List<Book> mBook;
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
        return new ViewHolder(view);
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
