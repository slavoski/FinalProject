package com.example.oilWellChecklist.LeaseDetails;

import com.example.oilWellChecklist.Constants.EntityType;

public class NewOilWellModel extends NewEntityModel {

    final Boolean IsCapped;

    public NewOilWellModel(String name, String description, Boolean isCapped)
    {
        super(name, description, EntityType.OIL_WELL);
        IsCapped = isCapped;
    }

}
