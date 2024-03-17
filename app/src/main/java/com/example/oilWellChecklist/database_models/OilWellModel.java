package com.example.oilWellChecklist.database_models;

import com.example.oilWellChecklist.Constants.EntityType;

public class OilWellModel extends EntityModel {

    public boolean IsCapped;


    public OilWellModel(String id, String name, String description, String leaseId, String url, boolean isCapped) {
        super(id, name, description, leaseId, url, EntityType.OIL_WELL);

        IsCapped = isCapped;
    }
}
