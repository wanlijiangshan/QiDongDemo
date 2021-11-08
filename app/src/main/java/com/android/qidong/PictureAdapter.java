package com.android.qidong;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;

public class PictureAdapter extends RecyclerView.Adapter {

    private Context mContext;
    public List<PictureBean> mData;

    public PictureAdapter(Context Context) {
        this.mContext = Context;
        this.mData = new ArrayList<>();
    }


    public void setData(List<PictureBean> data) {
        this.mData.clear();
        if (data != null)
            this.mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_picture, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        Glide.with(mContext).load(mData.get(position).imagePath).into(myViewHolder.imageView);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_picture_imageView);
        }
    }


}
