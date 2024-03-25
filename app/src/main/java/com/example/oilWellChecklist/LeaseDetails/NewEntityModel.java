package com.example.oilWellChecklist.LeaseDetails;

import com.example.oilWellChecklist.Constants.EntityType;

public class NewEntityModel {
    final String Name;
    final String Description;
    final EntityType Type;

    public NewEntityModel(String name, String description, EntityType entityType)
    {
        Name = name;
        Description = description;
        Type = entityType;
    }

}
