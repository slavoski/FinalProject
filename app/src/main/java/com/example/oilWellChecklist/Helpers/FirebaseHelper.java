package com.example.oilWellChecklist.Helpers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class FirebaseHelper {

    public FirebaseAuth mAuth;
    public FirebaseUser currentUser;
    public FirebaseDatabase database;

    public FirebaseFirestore fire_store;
    public FirebaseStorage storage;
    public FirebaseHelper()
    {
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        fire_store = FirebaseFirestore.getInstance();
    }

}
