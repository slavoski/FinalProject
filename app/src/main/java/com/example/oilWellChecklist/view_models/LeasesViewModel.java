package com.example.oilWellChecklist.view_models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class LeasesViewModel extends ViewModel {
    private final LiveData<List<LeaseViewModel>> leasesList;

    public LeasesViewModel(LiveData<List<LeaseViewModel>> leasesList) {
        this.leasesList = leasesList;
    }

    public LiveData<List<LeaseViewModel>> getLeases()
    {
        return leasesList;
    }
}
