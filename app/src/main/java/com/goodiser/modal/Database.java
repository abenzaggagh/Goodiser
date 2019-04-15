package com.goodiser.modal;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Database {

    private static FirebaseFirestore database = FirebaseFirestore.getInstance();

    private static CollectionReference users = database.collection("Users");

    public static Map<String, Object> mapUser(User user) {

        Map<String, Object> userMap = new HashMap<>();

        userMap.put("UID", user.getUID());
        userMap.put("Name", user.getName());
        userMap.put("Phone", user.getPhone());
        userMap.put("Email", user.getEmail());

        return userMap;

    }
    public static Boolean insertUser(User user) {

        Map<String, Object> userMap = mapUser(user);

        database.collection("Users").add(userMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d("info", "DocumentSnapshot added with ID: " + documentReference.getId());
            }
        });

        return true;
    }
    public static User getUser(String UID) {

        Query query = users.whereEqualTo("UID", UID);
        /*
        for (QueryDocumentSnapshot document : query.get().getResult()) {
            Log.d("User", document.getId() + " => " + document.getData());
        }


        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("User", document.getId() + " => " + document.getData());
                    }

                } else {
                    Log.d("User", "Error getting documents: ", task.getException());
                }


            }
        });*/

        return null;
    }

    private static CollectionReference products = database.collection("Products");

    public static ArrayList<Product> getProducts() {

        return null;
    }



}
