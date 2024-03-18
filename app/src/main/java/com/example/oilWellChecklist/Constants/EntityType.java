package com.example.oilWellChecklist.Constants;

public enum EntityType {
    OIL_WELL("Oil Well"),
    TANK("Tank"),
    OTHER("Other");

    private final String _description;

    EntityType(String description)
    {
        _description = description;
    }

    public String getDescription()
    {
        return _description;
    }

}
