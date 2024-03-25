package com.example.oilWellChecklist.Helpers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class FirebaseHelper {

    public final FirebaseAuth mAuth;
    public final FirebaseUser currentUser;
    public final FirebaseDatabase database;

    public final FirebaseFirestore fire_store;
    public final FirebaseStorage storage;
    public FirebaseHelper()
    {
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        fire_store = FirebaseFirestore.getInstance();
    }

}
