package com.example.oilWellChecklist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseAuth _authorizer = FirebaseAuth.getInstance();
        FirebaseUser _authorizedUser = _authorizer.getCurrentUser();

        if(_authorizedUser == null)
        {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
        else
        {
            if(_authorizedUser.isEmailVerified())
            {
                startActivity(new Intent(MainActivity.this, HomePageActivity.class));
                finish();
            }
            else
            {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        }
    }
}