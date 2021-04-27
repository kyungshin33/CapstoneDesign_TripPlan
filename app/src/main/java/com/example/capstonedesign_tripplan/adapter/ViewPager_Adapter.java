package com.example.capstonedesign_tripplan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.capstonedesign_tripplan.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class ViewPager_Adapter extends RecyclerView.Adapter<ViewPager_Adapter.ViewPagerHolder>{
    List<String> images;
    Context context;
    FirebaseStorage storage;
    public ViewPager_Adapter(List<String> images,Context context) {
        this.images = images;
        this.context = context;
        storage = FirebaseStorage.getInstance();
    }
    @NonNull
    @Override
    public ViewPagerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewpager_item, parent, false);
        return new ViewPagerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPagerHolder holder, int position) {
        holder.BindData(images.get(position));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    class ViewPagerHolder extends RecyclerView.ViewHolder {
        String imagePath;
        ImageView imageView;
        public ViewPagerHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.viewpagerItem);
        }
        public void BindData(String imagePath) {
            this.imagePath = imagePath;
            StorageReference storageReference = storage.getReference().child("photo/" + imagePath);
            Glide.with(context)
                    .load(storageReference)
                    .into(imageView);
        }
    }
}
