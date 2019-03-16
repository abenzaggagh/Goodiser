package com.goodiser.modal;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Database {

    private static FirebaseFirestore database = FirebaseFirestore.getInstance();

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
}
