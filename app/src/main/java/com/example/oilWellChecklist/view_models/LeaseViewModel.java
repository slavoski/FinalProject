package com.example.oilWellChecklist.view_models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class LeaseViewModel extends ViewModel {

    private LiveData<List<OilWell>> oilWells;

    private LiveData<List<TankBattery>> tankBatteries;

    private LiveData<List<DisposalWell>> disposalWell;


}
