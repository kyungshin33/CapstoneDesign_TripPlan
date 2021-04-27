package com.example.capstonedesign_tripplan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.capstonedesign_tripplan.R;
import com.example.capstonedesign_tripplan.RecyclerViewDecoration;
import com.example.capstonedesign_tripplan.RetrofitService;
import com.example.capstonedesign_tripplan.adapter.Location_RecyclerView_Adapter;
import com.example.capstonedesign_tripplan.adapter.RecyclerView_Adapter;
import com.example.capstonedesign_tripplan.data.LocationPOJO;
import com.example.capstonedesign_tripplan.documents;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.uber.rxdogtag.RxDogTag;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocationActivity extends AppCompatActivity {
    ArrayList<documents> documents = new ArrayList<>();
    Location_RecyclerView_Adapter location_recyclerView_adapter;
    RecyclerView recyclerView_location;
    EditText editText;
    ImageButton imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        recyclerView_location = findViewById(R.id.recyclerview_location);
        editText = findViewById(R.id.searchEditText);
        imageButton = findViewById(R.id.imageButton);

        location_recyclerView_adapter = new Location_RecyclerView_Adapter(documents, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView_location.setLayoutManager(linearLayoutManager);
        recyclerView_location.setHasFixedSize(true);
        recyclerView_location.addItemDecoration(new RecyclerViewDecoration(10));
        recyclerView_location.setAdapter(location_recyclerView_adapter);
        imageButton.setOnClickListener(view -> {
            try {
                String queryString = URLEncoder.encode(editText.getText().toString(), "UTF-8");
                Log.d("queryString","queryString -> " + queryString);
                Retrofit client = new Retrofit.Builder()
                        .baseUrl("https://dapi.kakao.com/v2/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();

                RetrofitService service = client.create(RetrofitService.class);


                Observable<LocationPOJO> locations = service.searchLocation(editText.getText().toString(),2);
                RxDogTag.install();

                locations.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(data ->{
                            if(data != null){
                                location_recyclerView_adapter.updateList(data.getDocuments());
                            }
                        });

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });
    }
}