package com.example.dasha.profile;

/**
 * Created by dasha on 21/10/16.
 */
public class Profile {

    private String email;
    private String name;
    private String date;
    private String location;

    private static Profile instance;

    private Profile( ) {
        this.email = new String();
        this.name = new String();
        this.date = new String();
        this.location = new String();
    }

    public static Profile getInstance() {
        if (instance == null) {
            instance = new Profile();
        }
        return instance;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public void update(String email, String name, String date, String location){
        this.email = email;
        this.name = name;
        this.date = date;
        this.location = location;
    }

}
