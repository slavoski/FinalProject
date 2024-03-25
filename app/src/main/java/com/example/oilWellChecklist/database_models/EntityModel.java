package com.example.oilWellChecklist.database_models;

import com.example.oilWellChecklist.Constants.EntityType;

public class EntityModel {
    public final String Id;
    public final String Name;

    public final String Description;

    public final String LeaseId;

    public final String Url;

    public final EntityType TypeId;

    public EntityModel(String id, String name, String description, String leaseId, String url, EntityType typeId)
    {
        Id = id;
        Name = name;
        Description = description;
        LeaseId = leaseId;
        Url = url;
        TypeId = typeId;
    }

}
