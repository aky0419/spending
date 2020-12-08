package com.example.spense;

import android.app.Application;

import com.example.spense.DB.DBManager;

//全局应用的类

public class UniteApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DBManager.initDB(getApplicationContext());
    }
}
