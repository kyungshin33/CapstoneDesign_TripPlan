package com.example.capstonedesign_tripplan.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.capstonedesign_tripplan.activity.Add_Contents_Activity;
import com.example.capstonedesign_tripplan.R;
import com.example.capstonedesign_tripplan.RecyclerViewDecoration;
import com.example.capstonedesign_tripplan.activity.MainActivity;
import com.example.capstonedesign_tripplan.adapter.RecyclerView_Adapter;
import com.example.capstonedesign_tripplan.Singletone;
import com.example.capstonedesign_tripplan.data.SharePlan;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class HomeFragment extends Fragment {
    private static final int GALLEY_CODE = 1;
    private static final int ALL_PERMISSION_OK = 100;
    RecyclerView_Adapter recyclerView_adapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    FloatingActionButton button;
    Context context;
    ArrayList<SharePlan> sharePlans;
    Disposable backgroundtask;
    ArrayList<String> imagePath;
    private FirebaseFirestore store = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);
        //permissionCheck = new PermissionCheck(MainActivity.this);
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

    //@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void getAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, GALLEY_CODE);

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*if (data == null) {
          Log.d("데이터", "data = null");
        } else {
            ClipData clipData = data.getClipData();

            if (clipData.getItemCount() > 9) {
                Toast.makeText(context,"사진은 9장까지 선택 가능",Toast.LENGTH_LONG).show();
            } else if (clipData.getItemCount() == 1) {
                imagePath = getActivity().getPath
            }
        }*/
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
            getActivity().finish();
        } else {
            Toast.makeText(getActivity(), "사진 선택을 취소하였습니다.", Toast.LENGTH_SHORT).show();
        }

    }
}