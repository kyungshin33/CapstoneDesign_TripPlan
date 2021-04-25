package com.example.capstonedesign_tripplan;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Observable;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeFragment extends Fragment {
    private static final int REQUEST_TAKE_ALBUM = 1;
    RecyclerView_Adapter recyclerView_adapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    FloatingActionButton button;
    Context context;
    ArrayList<SharePlan> sharePlans;
    Disposable backgroundtask;
    private FirebaseFirestore store = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);
        button = view.findViewById(R.id.btn);
        swipeRefreshLayout = view.findViewById(R.id.srl_commodities);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sharePlans = new ArrayList<>();

        swipeRefreshLayout.setOnRefreshListener(() -> {
            loadSharePlans();
        });

        recyclerView_adapter = new RecyclerView_Adapter(sharePlans, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new RecyclerViewDecoration(30));
        recyclerView.setAdapter(recyclerView_adapter);
        loadSharePlans();
        // 앨범접근
        button.setOnClickListener(view1 -> {
            getAlbum();

        });
    }

    public void loadSharePlans(){
        Singletone.getInstance().SelectSharePlan()
                .subscribeOn(Schedulers.io())
                .subscribe(result -> {
                    recyclerView_adapter.updateList(result);
                    swipeRefreshLayout.setRefreshing(false);
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void getAlbum() {
        // 앨범 호출
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(Intent.createChooser(intent, "다중 선택은 '포토'를 선택하세요."), REQUEST_TAKE_ALBUM);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("onActivityResult", "CALL");
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("result", String.valueOf(resultCode));
        if (resultCode == Activity.RESULT_OK) {
            ArrayList imageList = new ArrayList<>();
            if (data.getClipData() == null) {
                Log.i("1. single choice", String.valueOf(data.getData()));
                imageList.add(String.valueOf(data.getData()));
            } else {
                ClipData clipData = data.getClipData();
                Log.i("clipdata", String.valueOf(clipData.getItemCount()));
                if (clipData.getItemCount() > 10){
                    Toast.makeText(getActivity(), "사진은 10개까지 선택가능 합니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 멀티 선택에서 하나만 선택했을 경우
                else if (clipData.getItemCount() == 1) {
                    String dataStr = String.valueOf(clipData.getItemAt(0).getUri());
                    Log.i("2. clipdata choice", String.valueOf(clipData.getItemAt(0).getUri()));
                    Log.i("2. single choice", clipData.getItemAt(0).getUri().getPath());
                    imageList.add(dataStr);

                } else if (clipData.getItemCount() > 1 && clipData.getItemCount() < 10) {
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        Log.i("3. single choice", String.valueOf(clipData.getItemAt(i).getUri()));
                        imageList.add(String.valueOf(clipData.getItemAt(i).getUri()));
                    }
                }
            }
            Intent intent = new Intent(getActivity(), Add_Contents_Activity.class);
            intent.putStringArrayListExtra("list", imageList);
            startActivity(intent);
        } else {
            Toast.makeText(getActivity(), "사진 선택을 취소하였습니다.", Toast.LENGTH_SHORT).show();
        }

    }


}