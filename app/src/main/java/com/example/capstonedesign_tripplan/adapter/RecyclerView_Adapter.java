package com.example.capstonedesign_tripplan.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.capstonedesign_tripplan.R;
import com.example.capstonedesign_tripplan.data.SharePlan;

import java.util.ArrayList;

public class RecyclerView_Adapter extends RecyclerView.Adapter<RecyclerView_Adapter.ViewHolderItem> {
    ArrayList<SharePlan> sharePlans;
    Context context;

    public RecyclerView_Adapter(ArrayList<SharePlan> sharePlans, Context context) {
        this.sharePlans = sharePlans;
        this.context = context;
    }

    public void updateList(ArrayList<SharePlan> sharePlans){
        Log.d("adapter","update");
        this.sharePlans.clear();
        this.sharePlans = sharePlans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolderItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolderItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderItem holder, int position) {
        holder.BindData(sharePlans.get(position));
    }

    @Override
    public int getItemCount() {
        return sharePlans.size();
    }

    class ViewHolderItem extends RecyclerView.ViewHolder {
        ViewPager2 viewPager2;
        ViewPager_Adapter viewPager_adapter;
        TextView title;
        TextView contents;
        SharePlan sharePlans;

        public ViewHolderItem(@NonNull View itemView) {
            super(itemView);
            viewPager2 = itemView.findViewById(R.id.viewpager);
            title = itemView.findViewById(R.id.recyclerview_title);
            contents = itemView.findViewById(R.id.recyclerview_contents);
        }
        public void BindData(SharePlan sharePlans) {
            this.sharePlans = sharePlans;
            viewPager_adapter = new ViewPager_Adapter(sharePlans.getImages(),context);
            title.setText(sharePlans.getTitle());
            contents.setText(sharePlans.getContents());
            viewPager2.setAdapter(viewPager_adapter);
        }
    }

}
