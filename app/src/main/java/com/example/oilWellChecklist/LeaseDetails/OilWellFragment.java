package com.example.oilWellChecklist.LeaseDetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.oilWellChecklist.R;
import com.example.oilWellChecklist.database_models.OilWellModel;
import com.google.android.material.textview.MaterialTextView;

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

        try {
            MaterialTextView _oilWellDescription = view.findViewById(R.id.oil_well_description);
            _oilWellDescription.setText(_oilWellModel.Description);

            MaterialTextView _oilWellName = view.findViewById(R.id.oil_well_name);
            _oilWellName.setText(_oilWellModel.Name);
        }
        catch (Exception ex)
        {
            Toast.makeText(this.getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }



        return view;
    }
}
