package com.nik.smartnote.vk.Model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.nik.smartnote.vk.MainActivity;

/**
 * Created by mytrofanenkova-io on 04.03.2016.
 */
public class User {

    final static String USER_ID = "user_id";
    final static String AUTH_KEY = "auth_key";
    final static String ACCESS_TOKEN = "access_token";



    private static String accessToken;
    private static String userId;
    private static String authKey;;

SharedPreferences sPref;

   private static User mInstance;

  public static User getInstance(){
       if(mInstance == null){
         mInstance =  new User();
       }
       return mInstance;
   }


    public  String getAccessToken(Context context) {
        if (accessToken == null) {

            accessToken =   PreferenceManager.getDefaultSharedPreferences(context)
                    .getString(ACCESS_TOKEN,"");

        }
        return accessToken;
    }

    public  void setAccessToken(String acessToken,Context context) {

        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(ACCESS_TOKEN, acessToken)
                .commit();

        User.accessToken = acessToken;

    }


    public String getUser_id(Context context) {
        if (userId == null) {

            userId = PreferenceManager.getDefaultSharedPreferences(context)
                    .getString(USER_ID, "");
        }
        return userId;
    }

    public void setUser_id(String userId, Context context) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(USER_ID, userId)
        .commit();
System.out.println("Ложим эту хуйню!!!" + PreferenceManager.getDefaultSharedPreferences(context)
        .getString(USER_ID, ""));

        this.userId = userId;
    }

    public String getAuth_key(Context context) {

        if (authKey == null) {
System.out.println("Null, получаем с памяти.....");
             authKey = PreferenceManager.getDefaultSharedPreferences(context)
                    .getString(AUTH_KEY, "");
       }

        return authKey;
    }

    public void setAuth_key(String authKey, Context context) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(AUTH_KEY, authKey)
                .commit();
        this.authKey = authKey;
    }

    public void clear(){
        accessToken = null;
        userId=null;
        authKey=null;;
    }

//    void saveText(Context context, String key, String data ) {
//
//        sPref = context.getPreferences(MODE_PRIVATE);
//        SharedPreferences.Editor ed = sPref.edit();
//        ed.putString(key, data);
//
//
//        ed.commit();
//        Toast.makeText(this, "Комментарий сохраненный!", Toast.LENGTH_SHORT).show();
//    }
//
//    void loadText() {
//
//        sPref = getPreferences(MODE_PRIVATE);
//        String savedText = sPref.getString(SAVED_TEXT, "");
//        String savedText1 = sPref.getString(SAVED_TEXT1, "");
//        String savedText3 = sPref.getString(SAVED_TEXT2, "");
//
//        // editTextComment.setText(savedText);
//        // editTextIdPost.setText(savedText1);
//        // editTextIdPage.setText(savedText3);
//
//    }
}
