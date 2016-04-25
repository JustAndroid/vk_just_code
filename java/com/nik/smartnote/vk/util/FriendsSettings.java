package com.nik.smartnote.vk.util;

import android.content.Context;
import android.util.Log;

import com.nik.smartnote.vk.MainActivity;
import com.nik.smartnote.vk.SettingsActivity;
import com.nik.smartnote.vk.api.APIFunctions;

import org.json.JSONArray;
import org.json.JSONException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Николай on 07.01.2016.
 */
public class FriendsSettings {





    public String deleteAllFriends() {


        String strJson = APIFunctions.Friends.getRequests(100, 1);
        System.out.println("Вернулась строка Jsone " + strJson);
        JSONArray jsonArray = JsonHelper.getInstanse().parse(strJson);
        for (int i = 0; i < jsonArray.length(); i++) {

            String reply = null;
            try {
                APIFunctions.Friends.delete(Integer.parseInt(jsonArray.getString(i)));
                return "ПЫТАЕМСЯ УДАЛИТЬ" + jsonArray.getInt(i);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return "Всех удалил";
    }



}
