package com.nik.smartnote.vk.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.nik.smartnote.vk.api.APIFunctions;
import com.nik.smartnote.vk.util.HTTPHelper;
import com.nik.smartnote.vk.util.PrissonManager;

/**
 * Created by mytrofanenkova-io on 08.01.2016.
 */
public class BossAtakService extends Service {

    int bossId;
    final int BOSS_ID = 0;
    final int BOSS_MODE = 1;


    int[][] bossList = {
            {1,0},
            {2,0},
            {3,0},
            {4,0}};

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        int i = 0;
while( i == bossList.length){
//new PrissonManager().attackBoss();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
