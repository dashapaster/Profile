package com.example.dasha.profile;

import android.app.Application;
import android.content.SharedPreferences;

import com.parse.Parse;

/**
 * Created by dasha on 21/10/16.
 */
public class ProfileApp extends Application {
    private static final String APPLICATION_ID = "W76esufikBqRxocz72XHGwpsbLNGBgZZwb7XK8C8" ;

    private static final String CLIENT_KEY = "0OukH9nE65lBZhyDyY2MjJmYxi1erN5ffcRHCv6s";
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, APPLICATION_ID, CLIENT_KEY);


    }
}
