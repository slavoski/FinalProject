package com.example.oilWellChecklist.database_models;

import com.example.oilWellChecklist.Constants.EntityType;

public class EntityModel {
    public String Id;
    public String Name;

    public String Description;

    public String LeaseId;

    public String Url;

    public EntityType TypeId;

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
