package com.example.oilWellChecklist.LeaseDetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.oilWellChecklist.R;
import com.example.oilWellChecklist.database_models.TankModel;
import com.google.android.material.textview.MaterialTextView;

public class TankFragment extends Fragment {

    TankModel _tankModel;

    public TankFragment(TankModel tankModel)
    {
        _tankModel = tankModel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tank_details_view, container, false);

        MaterialTextView _tankName = view.findViewById(R.id.tank_name);
        _tankName.setText(_tankModel.Name);

        MaterialTextView _tankDescription = view.findViewById(R.id.tank_description);
        _tankDescription.setText(_tankModel.Description);


        return view;
    }

}
