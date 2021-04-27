package com.example.capstonedesign_tripplan.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.capstonedesign_tripplan.R;
import com.example.capstonedesign_tripplan.fragment.HomeFragment;
import com.example.capstonedesign_tripplan.fragment.MyTripFragment;
import com.example.capstonedesign_tripplan.fragment.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_PERMISSION_CODE = 0;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private HomeFragment fragmentHome = new HomeFragment();
    private MyTripFragment fragmentMyTrip = new MyTripFragment();
    private UserFragment fragmentUser = new UserFragment();
    private static final String TAG = "DocSnippets";
    private final int PERMISSIONS_REQUEST_RESULT = 1;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)) {
                //권한을 거절하면 재 요청을 하는 함수
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_RESULT);
            }
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragmentHome).commitAllowingStateLoss();
        BottomNavigationView bottomNavigationView = findViewById(R.id.navBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            FragmentTransaction transaction1 = fragmentManager.beginTransaction();

            switch (item.getItemId()){
                case R.id.Home:
                    transaction1.replace(R.id.frameLayout, fragmentHome).commitAllowingStateLoss();
                    break;
                case R.id.MyTrip:
                    transaction1.replace(R.id.frameLayout, fragmentMyTrip).commitAllowingStateLoss();
                    break;
                case R.id.User:
                    transaction1.replace(R.id.frameLayout, fragmentUser).commitAllowingStateLoss();
                    break;

            }
            return true;
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (PERMISSIONS_REQUEST_RESULT == requestCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "권한 요청이 됐습니다.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "권한 요청을 해주세요.", Toast.LENGTH_SHORT).show();
                finish();
            }
            return;
        }
    }
    /*@Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch (requestCode){
            case REQUEST_PERMISSION_CODE:
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                //Toast toast=Toast.makeText(this,"기능 사용을 위한 권한 동의가 필요합니다.", Toast.LENGTH_SHORT);
                Log.d("권한","권한 ->");
            } else {
            //거부했을 경우
            Toast toast=Toast.makeText(this,"기능 사용을 위한 권한 동의가 필요합니다.", Toast.LENGTH_SHORT);
            toast.show();
        } break;
        }
    }*/
    /*private void getHashKey(){
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }*/


}