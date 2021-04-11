package com.example.capstonedesign_tripplan;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.firestore.GeoPoint;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    RecyclerView_Adapter recyclerView_adapter;
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        /*test = view.findViewById(R.id.buttontest);
        test2 = view.findViewById(R.id.buttontest2);*/
        recyclerView = view.findViewById(R.id.recyclerview);
        List<String> str = new ArrayList<>();
        str.add("asdf.jpg");
        str.add("1.jpg");
        str.add("2.png");
        str.add("3.png");
        str.add("4.png");
        Trip trip = new Trip(1,"제목","박경신","eee@naver.com",new GeoPoint(10,10),"2021-01-01","안녕하세요",str);
        ArrayList<Trip> trips = new ArrayList<>();
        trips.add(trip);
        recyclerView_adapter = new RecyclerView_Adapter(trips, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerView_adapter);
        return view;
    }

    /*@Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        test.setOnClickListener(view -> Singletone.getInstance().InsertDate("User", new User(1, "박경신", "sex@gmail.com", "010-4444-5555", "경기도")));
        test2.setOnClickListener(view -> {
            Log.d("get","데이터불러오기호출");
            ArrayList<User> datas = Singletone.getInstance().SelectUserData();
            if(datas != null){
                for(User user: datas){
                    Log.d("get","data -> " + user.getName() + " / " + user.getEmail());
                }
            }
        });
    }*/
}