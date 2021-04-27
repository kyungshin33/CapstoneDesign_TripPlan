package com.example.capstonedesign_tripplan;

import android.util.Log;

import com.example.capstonedesign_tripplan.data.MyTrip;
import com.example.capstonedesign_tripplan.data.SharePlan;
import com.example.capstonedesign_tripplan.data.Trip;
import com.example.capstonedesign_tripplan.data.User;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;


public class Singletone{
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "DocSnippets";

    private Singletone() {

        List<Trip> tripList = new ArrayList<>();
        List<MyTrip> myTripList = new ArrayList<>();
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

    public Observable<ArrayList<SharePlan>> SelectSharePlan() {
        return Observable.create(item -> {
            ArrayList<SharePlan> result = new ArrayList<>();
            db.collection("Contents")
                    .orderBy("tm", Query.Direction.DESCENDING)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                result.add(document.toObject(SharePlan.class));
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        item.onNext(result);
                        item.onComplete();
                    });
        });
    }

    public void InsertDate(String ColPath, Object data) {
        db.collection(ColPath)
                .add(data)
                .addOnSuccessListener(documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId()))
                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
    }
}
