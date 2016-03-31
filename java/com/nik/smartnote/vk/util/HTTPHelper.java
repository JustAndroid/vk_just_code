package com.nik.smartnote.vk.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;


public class HTTPHelper implements Runnable {
    private String requestURL;
    String result;
    String cookie;
int error;




    private static HTTPHelper mInstance;

    public static HTTPHelper getInstance() {

        if (mInstance == null) {
            mInstance = new HTTPHelper();
        }
        return mInstance;
    }


    Thread thread;

    public String requestGet(String requestURL, String cookie) {
        // Создаём новый второй поток


        this.cookie = cookie;
        this.requestURL = requestURL;
        thread = new Thread(this, "Поток для примера");
error = 0;
        thread.start(); // Запускаем поток

        try {
            thread.join();


        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result;

    }


    @Override
    public void run() {

        try {

            URL url = null;

            System.out.println(requestURL);
            url = new URL(requestURL);


            URLConnection urlConnection = url.openConnection();
            urlConnection.setRequestProperty("Cookie", cookie);
            urlConnection.setRequestProperty("content-type", "charset=utf-8");
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0");
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));

            StringBuffer buffer = new StringBuffer();
            int ch;

            while ((ch = in.read()) > -1) {
                buffer.append((char) ch);
            }

            result = buffer.toString();

            Log.d("Result HttpHelper: ", result);


        } catch (Exception e) {
            System.out.println("Ошибка запроса" + e.toString());
            e.printStackTrace();
            if(error < 2) {
                error++;
                requestGet(requestURL, cookie);

            }

        }
    }


}
