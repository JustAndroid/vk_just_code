package com.nik.smartnote.vk.Model;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.nik.smartnote.vk.MayApplication;


public class User {

    final static String USER_ID = "user_id";
    final static String AUTH_KEY = "auth_key";
    final static String ACCESS_TOKEN = "access_token";
final static String NAME_USER_PREFENCES = "prisson.prefencess";


    private static String accessToken;
    private static String userId;
    private static String authKey;;

Context context;

   private static User mInstance;

  public static User getInstance(){
       if(mInstance == null){
         mInstance =  new User();
       }
       return mInstance;
   }


    public  String getAccessToken() {
        if (accessToken == null) {
            context = MayApplication.AppContext;
            accessToken  =  context.getSharedPreferences(NAME_USER_PREFENCES, Context.MODE_PRIVATE)
                    .getString(ACCESS_TOKEN,"");

        }
        return accessToken;
    }

    public  void setAccessToken(String accessToken) {
        context = MayApplication.AppContext;
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(ACCESS_TOKEN, accessToken);
        editor.commit();

        User.accessToken = accessToken;

    }


    public String getUser_id() {
        if (userId == null) {
            context = MayApplication.AppContext;
            userId =  context.getSharedPreferences(NAME_USER_PREFENCES, Context.MODE_PRIVATE)
                    .getString(USER_ID, "");
        }
        return userId;
    }

    public void setUser_id(String userId) {

        context = MayApplication.AppContext;
        SharedPreferences settings = context.getSharedPreferences(NAME_USER_PREFENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(USER_ID, userId);
        editor.commit();

        this.userId = userId;
    }

    public String getAuth_key() {
        context = MayApplication.AppContext;
        if (authKey == null) {

            SharedPreferences sharedPreferences = context.getSharedPreferences(NAME_USER_PREFENCES, Context.MODE_PRIVATE);
             authKey =  sharedPreferences.getString(AUTH_KEY, "");
       }

        return authKey;
    }

    public void setAuth_key(String authKey) {
        context = MayApplication.AppContext;
        SharedPreferences settings = context.getSharedPreferences(NAME_USER_PREFENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(AUTH_KEY, authKey);
        editor.commit();

        this.authKey = authKey;
    }
//тестовый метод, для проверки шарепрефенцес
    public void clear(){
        context = MayApplication.AppContext;
        accessToken = null;
        userId=null;
        authKey=null;
        System.out.println(context.getSharedPreferences(NAME_USER_PREFENCES, Context.MODE_PRIVATE)
                .getString(AUTH_KEY, ""));

    }

}
