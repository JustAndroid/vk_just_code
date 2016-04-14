package com.nik.smartnote.vk.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by mytrofanenkova-io on 04.03.2016.
 */
public class User {

    final static String USER_ID = "user_id";
    final static String AUTH_KEY = "auth_key";
    final static String ACCESS_TOKEN = "access_token";
final static String NAME_USER_PREFENCES = "prisson.prefencess";


    private static String accessToken;
    private static String userId;
    private static String authKey;;



   private static User mInstance;

  public static User getInstance(){
       if(mInstance == null){
         mInstance =  new User();
       }
       return mInstance;
   }


    public  String getAccessToken(Context context) {
        if (accessToken == null) {

            accessToken =    context.getSharedPreferences(NAME_USER_PREFENCES, Context.MODE_PRIVATE)
                    .getString(ACCESS_TOKEN,"3232");

        }
        return accessToken;
    }

    public  void setAccessToken(String acessToken,Context context) {

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(ACCESS_TOKEN, acessToken);
        editor.commit();


//        context.getSharedPreferences(NAME_USER_PREFENCES, Context.MODE_PRIVATE)
//                .edit()
//                .putString(ACCESS_TOKEN, acessToken)
//                .commit();

        User.accessToken = acessToken;

    }


    public String getUser_id(Context context) {
        if (userId == null) {

            userId =  context.getSharedPreferences(NAME_USER_PREFENCES, Context.MODE_PRIVATE)
                    .getString(USER_ID, "32323");
        }
        return userId;
    }

    public void setUser_id(String userId, Context context) {
//        context.getSharedPreferences(NAME_USER_PREFENCES, Context.MODE_PRIVATE)
//                .edit()
//                .putString(USER_ID, userId)
//        .commit();

        SharedPreferences settings = context.getSharedPreferences(NAME_USER_PREFENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(USER_ID, userId);
        editor.commit();

        this.userId = userId;
    }

    public String getAuth_key(Context context) {

        if (authKey == null) {
System.out.println("Null, получаем с памяти.....");
            SharedPreferences sharedPreferences = context.getSharedPreferences(NAME_USER_PREFENCES, Context.MODE_PRIVATE);
             authKey =  sharedPreferences.getString(AUTH_KEY, "45566");
       }

        return authKey;
    }

    public void setAuth_key(String authKey, Context context) {
//        context.getSharedPreferences(NAME_USER_PREFENCES, Context.MODE_PRIVATE)
//                .edit()
//                .putString(AUTH_KEY, authKey)
//                .commit();

        SharedPreferences settings = context.getSharedPreferences(NAME_USER_PREFENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(AUTH_KEY, authKey);
        editor.commit();

        this.authKey = authKey;
    }

    public void clear(Context context){
        accessToken = null;
        userId=null;
        authKey=null;
        System.out.println(context.getSharedPreferences(NAME_USER_PREFENCES, Context.MODE_PRIVATE)
                .getString(AUTH_KEY, "ХУЙ"));

    }

}
