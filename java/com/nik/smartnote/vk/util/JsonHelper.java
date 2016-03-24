package com.nik.smartnote.vk.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class JsonHelper
{
    private static JsonHelper mInstanse;

    public static JsonHelper getInstanse(){

        if(mInstanse == null){
            mInstanse = new JsonHelper();
        }
        return mInstanse;
    }

    public JSONArray parse(String strJson)
    {
        JSONArray jsonArray = null;
        System.out.println("Start work from Json");


        try {
            JSONObject jsonObject = null;
            System.out.println("пришла строка: " + strJson);

            strJson.replaceAll(",|\\[|]", "");

            System.out.println("пришла строка обработанная: " + strJson);

            jsonObject = new JSONObject(strJson);



 jsonArray = jsonObject.getJSONArray("response");


            System.out.println("Размер массива JSon"+jsonArray.length());


        } catch (JSONException e) {
            System.out.print("Ошибка" + e);
        }
        return jsonArray;

    }

}


