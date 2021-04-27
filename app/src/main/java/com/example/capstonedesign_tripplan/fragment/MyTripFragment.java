package com.example.capstonedesign_tripplan.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.capstonedesign_tripplan.documents;
import com.example.capstonedesign_tripplan.R;
import com.example.capstonedesign_tripplan.RetrofitService;
import com.example.capstonedesign_tripplan.data.LocationPOJO;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.uber.rxdogtag.RxDogTag;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyTripFragment extends Fragment {
    private final String TAG = "java.StorageActivity";
    Button test;
    EditText textViewl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_trip, container, false);
        textViewl = v.findViewById(R.id.text2);
        test = v.findViewById(R.id.button2);
        test.setOnClickListener(view -> {
            try {
                String queryString = URLEncoder.encode(textViewl.getText().toString(), "UTF-8");
                Log.d("queryString","queryString -> " + queryString);
                Retrofit client = new Retrofit.Builder()
                        .baseUrl("https://dapi.kakao.com/v2/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();

                RetrofitService service = client.create(RetrofitService.class);


                Observable<LocationPOJO> locations = service.searchLocation(textViewl.getText().toString(),2);
                RxDogTag.install();

                locations.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(data ->{
                            if(data != null){
                                Log.d("데이터","데이터있음");
                                for(documents documents : data.getDocuments()){
                                    Log.d("data", "data -> " + documents.getPlace_name() + "/" + documents.getAddress_name());
                                }
                            }
                        });
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        });


        return v;
    }

}