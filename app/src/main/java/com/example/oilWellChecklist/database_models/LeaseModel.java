package com.example.oilWellChecklist.database_models;

public class LeaseModel {

    public final String LeaseName;

    public final String Description;

    public final String Id;

    public final String UserId;
    public final String Url;

    public LeaseModel(String lease_name, String description, String id, String url, String userId )
    {
        LeaseName = lease_name;
        Description = description;
        Id = id;
        Url = url;
        UserId = userId;
    }



}
