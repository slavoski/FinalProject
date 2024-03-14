package com.example.oilWellChecklist.database_models;

public class LeaseModel {

    public String LeaseName;

    public String Description;

    public String Id;

    public String UserId;
    public String Url;

    public LeaseModel(String lease_name, String description, String id, String url, String userId )
    {
        LeaseName = lease_name;
        Description = description;
        Id = id;
        Url = url;
        UserId = userId;
    }



}
