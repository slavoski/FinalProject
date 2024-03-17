package com.example.oilWellChecklist.LeaseDetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.oilWellChecklist.R;
import com.example.oilWellChecklist.database_models.OilWellModel;

public class OilWellFragment extends Fragment {

    OilWellModel _oilWellModel;
    public  OilWellFragment(OilWellModel oilWellModel)
    {
        _oilWellModel = oilWellModel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.oil_well_details_view, container, false);



        return super.onCreateView(inflater, container, savedInstanceState);
    }
}