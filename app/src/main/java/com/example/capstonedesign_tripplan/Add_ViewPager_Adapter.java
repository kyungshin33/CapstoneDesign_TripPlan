package com.example.capstonedesign_tripplan;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class Add_ViewPager_Adapter extends RecyclerView.Adapter<Add_ViewPager_Adapter.ViewPagerHolder>{
    ArrayList<String> imageList;
    Context context;
    public Add_ViewPager_Adapter(ArrayList<String> imageList, Context context) {
        this.imageList = imageList;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewPagerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_viewpager_item, parent, false);
        return new Add_ViewPager_Adapter.ViewPagerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPagerHolder holder, int position) {
        holder.BindData(imageList.get(position));
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    class ViewPagerHolder extends RecyclerView.ViewHolder {
        String uri;
        ImageView imageView;
        public ViewPagerHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.add_viewpager_item);
        }
        public void BindData(String uri) {
            this.uri = uri;
            Glide.with(context)
                    .load(uri)
                    .into(imageView);
            Log.d("BindData",imageList.toString());
        }
    }
}
