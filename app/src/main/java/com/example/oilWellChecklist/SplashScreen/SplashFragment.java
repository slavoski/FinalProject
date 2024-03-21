package com.example.oilWellChecklist.SplashScreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.oilWellChecklist.R;

    public class SplashFragment extends Fragment {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.splash_layout, container, false);
            Glide.with(getActivity())
                    .load(R.drawable.oil_well)
                    .into((ImageView)v.findViewById(R.id.logo));
            return v;
        }
    }

