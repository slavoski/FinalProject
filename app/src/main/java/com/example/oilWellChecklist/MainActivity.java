package com.example.oilWellChecklist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth _authorizer;
    private FirebaseUser _authorizedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _authorizer = FirebaseAuth.getInstance();
        _authorizedUser = _authorizer.getCurrentUser();

        if(_authorizedUser == null)
        {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
        else
        {
            if(_authorizedUser.isEmailVerified())
            {
                //start main activity
            }
            else
            {
                Toast.makeText(MainActivity.this, R.string.verify_email, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        }
    }
}