package com.example.capstonedesign_tripplan;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Add_Contents_Activity extends AppCompatActivity {
    ArrayList<String> priorList = new ArrayList<>();
    ArrayList<String> fileName = new ArrayList<>();
    ViewPager2 pager;
    Add_ViewPager_Adapter pagerAdapter;
    Button btnUpload;
    EditText add_contents;
    EditText add_title;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private FirebaseFirestore store = FirebaseFirestore.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://capstonedesign-trip.appspot.com/photo");
    StorageReference userStorageRef;
    Uri file;
    Context context;
    Disposable backgroundtask;
    boolean result;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contents);
        init();
        Intent getInt = getIntent();
        priorList = getInt.getStringArrayListExtra("list");
        pagerAdapter = new Add_ViewPager_Adapter(priorList, this);
        pager.setAdapter(pagerAdapter);
        btnUpload.setOnClickListener(view -> {
            upLoadTask();
            Log.d("title",add_title.getText().toString());
        });
    }
    public void init() {
        pager = findViewById(R.id.add_viewpager);
        btnUpload = findViewById(R.id.btnUpload);
        add_contents = findViewById(R.id.addphoto_explain);
        add_title = findViewById(R.id.add_title);
    }
    public void upLoadTask(){
        backgroundtask = Observable.fromCallable(() ->{
            for (int i=0; i<priorList.size(); i++){
                file = Uri.parse(priorList.get(i));
                fileName.add(file.getLastPathSegment());
                userStorageRef = storageRef.child(file.getLastPathSegment());
                userStorageRef.putFile(file)
                        .addOnSuccessListener(taskSnapshot -> {
                            Log.d("이미지 성공여부","성공");
                            result = true;
                        })
                        .addOnFailureListener(exception -> {
                            Log.d("이미지 성공여부","실패");
                            result = false;
                        });
                Log.d("storage_child",userStorageRef.toString());
                Log.d("storage_child",file.toString());
            }
            SharePlan sharePlan = new SharePlan(add_title.getText().toString(),add_contents.getText().toString(),fileName, Timestamp.now());
            Singletone.getInstance().InsertDate("Contents",sharePlan);
            return result;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((result)->{
                    backgroundtask.dispose();
                    Intent changeView = new Intent(this,MainActivity.class);
                    startActivity(changeView);
                });
    }
}