package com.example.oilWellChecklist.database_models;

import com.example.oilWellChecklist.Constants.EntityType;

/** @noinspection unused, unused, unused, unused */
public class TankModel extends EntityModel {

    public final String TotalVolume;
    public final String Volume;
    public final String Diameter;
    public final String Circumference;

    public TankModel(String id, String name, String description, String leaseId, String url,
                     String totalVolume, String volume, String diameter, String circumference) {
        super(id, name, description, leaseId, url, EntityType.TANK);

        TotalVolume = totalVolume;
        Volume = volume;
        Diameter = diameter;
        Circumference = circumference;
    }
}
