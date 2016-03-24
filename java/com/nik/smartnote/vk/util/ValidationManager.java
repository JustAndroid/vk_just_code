package com.nik.smartnote.vk.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.nik.smartnote.vk.MainActivity;
import com.nik.smartnote.vk.MainMenu;

/**
 * Created by mytrofanenkova-io on 03.03.2016.
 */
public class ValidationManager {

    void chekAnswer(String xml, Context context) {
 if(Integer.parseInt(new XMLParser().parsXMLTeg(xml, "rezult")) == 0){
     Intent intent = new Intent(context, MainActivity.class);

     intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

     new Activity().startActivity(intent);

 }


    }



}
