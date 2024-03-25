package com.example.oilWellChecklist.database_models;

/** @noinspection unused*/
public class User {
    public final String DisplayName;
    public final String Email;
    public final String UserName;
    public final String Phone;

    public User(String displayName, String email, String phone)
    {
        DisplayName = displayName;
        Email = email;
        UserName = email;
        Phone = phone;
    }
}
