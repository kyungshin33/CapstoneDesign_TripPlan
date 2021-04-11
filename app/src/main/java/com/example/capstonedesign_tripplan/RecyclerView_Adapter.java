package com.example.capstonedesign_tripplan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;

public class RecyclerView_Adapter extends RecyclerView.Adapter<RecyclerView_Adapter.ViewHolderItem> {
    ArrayList<Trip> TripList;
    Context context;
    public RecyclerView_Adapter(ArrayList<Trip> tripList, Context context) {
        this.TripList = tripList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolderItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderItem holder, int position) {
        holder.BindData(TripList.get(position));
    }

    @Override
    public int getItemCount() {
        return TripList.size();
    }

    class ViewHolderItem extends RecyclerView.ViewHolder {
        Trip trip;
        ViewPager2 viewPager2;
        ViewPager_Adapter viewPager_adapter;
        public ViewHolderItem(@NonNull View itemView) {
            super(itemView);
            viewPager2 = itemView.findViewById(R.id.veiwpager);
        }
        public void BindData(Trip trip) {
            this.trip = trip;
            viewPager_adapter = new ViewPager_Adapter(trip.getImages(),context);
            viewPager2.setAdapter(viewPager_adapter);
        }
    }
}
