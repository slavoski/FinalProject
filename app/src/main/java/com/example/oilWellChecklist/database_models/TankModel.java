package com.example.oilWellChecklist.database_models;

import com.example.oilWellChecklist.Constants.EntityType;

public class TankModel extends EntityModel {

    public String TotalVolume;
    public String Volume;
    public String Diameter;
    public String Circumference;

    public TankModel(String id, String name, String description, String leaseId, String url,
                     String totalVolume, String volume, String diameter, String circumference) {
        super(id, name, description, leaseId, url, EntityType.TANK);

        TotalVolume = totalVolume;
        Volume = volume;
        Diameter = diameter;
        Circumference = circumference;
    }
}
