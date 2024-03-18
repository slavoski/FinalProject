package com.example.oilWellChecklist.LeaseDetails;

import com.example.oilWellChecklist.Constants.EntityType;

public class NewEntityModel {
    String Name;
    String Description;
    EntityType Type;

    public NewEntityModel(String name, String description, EntityType entityType)
    {
        Name = name;
        Description = description;
        Type = entityType;
    }

}
