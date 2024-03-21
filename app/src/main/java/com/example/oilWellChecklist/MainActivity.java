package com.example.oilWellChecklist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.example.oilWellChecklist.SplashScreen.SplashFragment;
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
                setContentView(R.layout.activity_splash_screen);
                FragmentManager fragmentManager=getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.fragmentContainer,new SplashFragment());
                fragmentTransaction.commit();
                new CountDownTimer(2000, 1000)
                {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        startActivity(new Intent(MainActivity.this, HomePageActivity.class));
                        finish();
                    }
                }.start();
            }
            else
            {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        }
    }
}