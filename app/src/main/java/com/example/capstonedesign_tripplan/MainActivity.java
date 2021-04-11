package com.example.capstonedesign_tripplan;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private HomeFragment fragmentHome = new HomeFragment();
    private MapsFragment fragmentMaps = new MapsFragment();
    private MyTripFragment fragmentMyTrip = new MyTripFragment();
    private UserFragment fragmentUser = new UserFragment();
    private static final String TAG = "DocSnippets";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragmentHome).commitAllowingStateLoss();

        BottomNavigationView bottomNavigationView = findViewById(R.id.navBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                switch (item.getItemId()){
                    case R.id.Home:
                        transaction.replace(R.id.frameLayout, fragmentHome).commitAllowingStateLoss();
                        break;
                    case R.id.Map:
                        transaction.replace(R.id.frameLayout, fragmentMaps).commitAllowingStateLoss();
                        break;
                    case R.id.MyTrip:
                        transaction.replace(R.id.frameLayout, fragmentMyTrip).commitAllowingStateLoss();
                        break;
                    case R.id.User:
                        transaction.replace(R.id.frameLayout, fragmentUser).commitAllowingStateLoss();
                        break;

                }
                return true;
            }
        });

    }


}