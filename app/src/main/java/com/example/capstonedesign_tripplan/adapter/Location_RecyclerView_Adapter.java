package com.example.capstonedesign_tripplan.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstonedesign_tripplan.R;
import com.example.capstonedesign_tripplan.activity.Add_Contents_Activity;
import com.example.capstonedesign_tripplan.activity.MainActivity;
import com.example.capstonedesign_tripplan.documents;

import java.util.ArrayList;
import java.util.List;

public class Location_RecyclerView_Adapter extends RecyclerView.Adapter<Location_RecyclerView_Adapter.ViewHolderItem>{
    List<documents> locationData;
    Context context;

    public Location_RecyclerView_Adapter(ArrayList<documents> locationData, Context context) {
        this.locationData = locationData;
        this.context = context;
    }

    public void updateList(List<documents> locationData){
        //Log.d("adapter","update");
        this.locationData.clear();
        this.locationData = locationData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Location_RecyclerView_Adapter.ViewHolderItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_recyclerview_item, parent, false);
        return new Location_RecyclerView_Adapter.ViewHolderItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderItem holder, int position) {
        holder.BindData(locationData.get(position));
    }

    @Override
    public int getItemCount() {
        return locationData.size();
    }

    public class ViewHolderItem extends RecyclerView.ViewHolder {
        TextView searchLocationText;
        public ViewHolderItem(@NonNull View itemView) {
            super(itemView);
            this.searchLocationText = itemView.findViewById(R.id.searchText_loca);
        }
        public void BindData(documents locationData) {
            searchLocationText.setText(locationData.getPlace_name());
        }
    }
}
