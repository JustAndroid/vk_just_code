package com.nik.smartnote.vk;

import android.app.Application;
import android.content.Context;

/**
 * Created by mytrofanenkova-io on 15.04.2016.
 */
public class MayApplication extends Application {

    public static Context AppContext;
    @Override
    public void onCreate() {
        super.onCreate();
        AppContext = getApplicationContext();
    }


}
