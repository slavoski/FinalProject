package com.example.oilWellChecklist.LeaseDetails;

import com.example.oilWellChecklist.Constants.EntityType;

public class NewTankModel extends NewEntityModel {

    public NewTankModel(String name, String description)
    {
        super(name, description, EntityType.TANK);
    }

}
