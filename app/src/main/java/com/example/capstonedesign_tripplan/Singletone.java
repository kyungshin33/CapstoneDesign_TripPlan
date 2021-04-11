package com.example.capstonedesign_tripplan;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Singletone{
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "DocSnippets";
    private List<Trip> TripList;
    private List<MyTrip> MyTripList;
    private Singletone() {

        TripList = new ArrayList<>();
        MyTripList = new ArrayList<>();
    }
    private static class SingletonHolder {
        public static final Singletone INSTANCE = new Singletone();
    }
    public static Singletone getInstance() {
        return SingletonHolder.INSTANCE;
    }
    public ArrayList<User> SelectUserData() {
        ArrayList<User> UserList = new ArrayList<>();
        db.collection("User")
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        QuerySnapshot querySnapshot = task.getResult();
                        for(QueryDocumentSnapshot documentSnapshot : querySnapshot){
                            Log.d("DATA","data -> " + documentSnapshot.getData());
                            User user = documentSnapshot.toObject(User.class);
                            UserList.add(user);
                        }
                    }else{
                        Log.w(TAG, "Error document", task.getException());
                    }
                });
        return UserList;
    }
    public void InsertDate(String ColPath, Object data) {
        db.collection(ColPath)
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }
}
