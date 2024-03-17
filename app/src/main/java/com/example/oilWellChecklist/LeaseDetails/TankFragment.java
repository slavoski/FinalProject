package com.example.oilWellChecklist.LeaseDetails;

import androidx.fragment.app.Fragment;

import com.example.oilWellChecklist.database_models.TankModel;

public class TankFragment extends Fragment {

    TankModel _tankModel;
    public TankFragment(TankModel tankModel)
    {
        _tankModel = tankModel;
    }

}
