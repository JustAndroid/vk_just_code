package com.nik.smartnote.vk;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.nik.smartnote.vk.Model.User;
import com.nik.smartnote.vk.api.APIFunctions;
import com.nik.smartnote.vk.util.HTTPHelper;

import com.nik.smartnote.vk.util.JsonHelper;
import com.nik.smartnote.vk.util.ParsDataHelper;
import com.nik.smartnote.vk.util.PrissonManager;
import com.nik.smartnote.vk.util.XMLParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class SettingsActivity extends Activity {
    //писал на быструю руку, исправить и рефлакторить
    TextView statusTextView;
   public final String BOSS_INFO = "http://109.234.156.253/prison/universal.php?key=" + User.getInstance().getAuth_key(SettingsActivity.this) + "&method=getBoss&user=" + User.getInstance().getUser_id(SettingsActivity.this);
   public final String PRIBIL = "http://109.234.156.252/prison/universal.php?key=" + User.getInstance().getAuth_key(SettingsActivity.this) + "&method=getAllBuildingsRewards&user=" + User.getInstance().getUser_id(SettingsActivity.this);
   public final String SIGI = "http://109.234.156.252/prison/universal.php?key=" + User.getInstance().getAuth_key(SettingsActivity.this) + "&getidea=1&method=office&user=" + User.getInstance().getUser_id(SettingsActivity.this);
   public final String TUALET = "http://109.234.156.252/prison/universal.php?key=" + User.getInstance().getAuth_key(SettingsActivity.this) + "&method=collectToiletPaper&user=" + User.getInstance().getUser_id(SettingsActivity.this);
   public final String allFriendsPlayPrison = "http://109.234.156.251/prison/universal.php?user=" + User.getInstance().getUser_id(SettingsActivity.this) + "&method=getFriendRatings&key=" + User.getInstance().getAuth_key(SettingsActivity.this);

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);

        ActionBar actionBar = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {

            actionBar = getActionBar();

            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ProgressDialog mProgressDialog = new ProgressDialog(SettingsActivity.this);
        mProgressDialog.setMessage("Загрузка");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);


        statusTextView = (TextView) findViewById(R.id.status_text_view);
        String[] menuList = getResources().getStringArray(R.array.menu_list);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this, R.layout.menu_item, menuList);
        listView = (ListView) findViewById(R.id.list_view);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        TaskManager taskManager;
                        switch (position) {

                            case 0:
                                statusTextView.setText(HTTPHelper.getInstance().requestGet(PRIBIL, null));
                                break;
                            case 1:
                                statusTextView.setText(HTTPHelper.getInstance().requestGet(SIGI, null));
                                break;
                            case 2:
                                statusTextView.setText(HTTPHelper.getInstance().requestGet(TUALET, null));
                                break;
                            case 3:
                                taskManager = new TaskManager();
                                taskManager.execute(TaskManager.FRIEND_BAKED);
                                break;
                            case 4:
                                taskManager = new TaskManager();
                                taskManager.execute(TaskManager.ALL_FRIEND_BAKED);
                                break;
                            case 5:
                                taskManager = new TaskManager();
                                taskManager.execute(TaskManager.DELETE_ALL_SEND_REQUESTS);
                                break;
                            case 6:
                                Intent intent = new Intent(SettingsActivity.this, CleanFriendsActivity.class);
                                startActivity(intent);
                                break;
                            case 7:
                                taskManager = new TaskManager();
                                taskManager.execute(TaskManager.ADD_ALL_FRIEND);

                                break;
                            case 8:
                                statusTextView.setText(HTTPHelper.getInstance().requestGet("http://109.234.156.251/prison/universal.php?user=" + User.getInstance().getUser_id(SettingsActivity.this) + "&key=" + User.getInstance().getAuth_key(SettingsActivity.this) + "&method=cardGamePlay", null));


                                statusTextView.append("\n" + HTTPHelper.getInstance().requestGet("http://109.234.156.251/prison/universal.php?user=" + User.getInstance().getUser_id(SettingsActivity.this) + "&key=" + User.getInstance().getAuth_key(SettingsActivity.this) + "&method=cardGameFinish", null));
                                break;
                            case 9:

                                taskManager = new TaskManager();
                                taskManager.execute(TaskManager.PARASHA_DROGGI);
                                break;
                            case 10:
                                HTTPHelper.getInstance().requestGet(BOSS_INFO, null);
                              statusTextView.setText(HTTPHelper.getInstance().requestGet(
                                      "http://109.234.156.250/prison/universal.php?key=" + User.getInstance().getAuth_key(SettingsActivity.this)
                                              + "&buff=0&method=startBattle&user=" + User.getInstance().getUser_id(SettingsActivity.this) + "&boss%5Fid="
                                              + PrissonManager.BOSSES[0][0] + "&type=boss&guild%5Fmode=0&choice=p&mode=simple"
                                      , null
                              ));
                                break;
                            case 11:
                                taskManager = new TaskManager();
                                taskManager.execute(TaskManager.HARCHKI);
                                break;
                            case 12:
                                taskManager = new TaskManager();
                                taskManager.execute(TaskManager.ATTAK);
                                break;
                            case 13:
                                statusTextView.setText(HTTPHelper.getInstance().requestGet(
                                        "http://109.234.156.250/prison/universal.php?key=" + User.getInstance().getAuth_key(SettingsActivity.this)
                                                + "&buff=0&method=startBattle&user=" + User.getInstance().getUser_id(SettingsActivity.this) + "&boss%5Fid="
                                                + PrissonManager.BOSSES[0][0] + "&type=boss&guild%5Fmode=0&choice=p&mode=epic"
                                        , null
                                ));
                                break;
                            case 14 :
                                taskManager = new TaskManager();
                                taskManager.execute(TaskManager.CHISTKA);
                        }

                    }
                }
        );
    }

    public class TaskManager extends AsyncTask<Integer, String, Void> {

        final public static int DELETE_ALL_SEND_REQUESTS = 0;
        final public static int ADD_ALL_FRIEND = 1;
        final public static int FRIEND_BAKED = 2;
        final public static int ALL_FRIEND_BAKED = 3;
        final public static int PARASHA_DROGGI = 4;
        final public static int HARCHKI = 5;
        final public static int ATTAK = 6;
        final public static int CHISTKA = 7;

        JSONArray jsonArray;


        @Override
        protected Void doInBackground(Integer... id) {


            switch (id[0]) {
                case DELETE_ALL_SEND_REQUESTS:

                    String strJson = APIFunctions.Friends.getRequests(100, 1,SettingsActivity.this);
                    JSONArray jsonArray = JsonHelper.getInstanse().parse(strJson);
                    for (int i = 0; i < jsonArray.length(); i++) {


                        try {
                            APIFunctions.Friends.delete(Integer.parseInt(jsonArray.getString(i)),SettingsActivity.this);
                            String text = jsonArray.getString(i);
                            publishProgress("Пытаемся удалить: " + jsonArray.getInt(i) + "\nНомер: " + (i+1) + " из " + jsonArray.length());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    break;

                case ADD_ALL_FRIEND:

                    JSONArray allFriendsArray = JsonHelper.getInstanse().parse(APIFunctions.Friends.getRequests(100, 0,SettingsActivity.this));

                    for (int i = 0; i < allFriendsArray.length(); i++) {

                        try {
                            System.out.println(allFriendsArray.getInt(i));
                            APIFunctions.Friends.add(Integer.parseInt(allFriendsArray.getString(i)),SettingsActivity.this);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    break;
                case FRIEND_BAKED:
                    try {
                        String urlListId = "http://109.234.156.252/prison/universal.php?key=" + User.getInstance().getAuth_key(SettingsActivity.this) + "&user=" + User.getInstance().getUser_id(SettingsActivity.this) + "&method=votes.getLog";

                        jsonArray = new JSONArray(HTTPHelper.getInstance().requestGet(urlListId, null));

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            System.out.println(jsonObject.getString("uid"));
                            HTTPHelper.getInstance().requestGet(
                                    "http://109.234.156.252/prison/universal.php?friend_uid=" + jsonObject.getString("uid")
                                            + "&user=" + User.getInstance().getUser_id(SettingsActivity.this) + "&username=%D0%94%D0%BC%D0%B8%D1%82%D1%80%D0%B8%D0%B9%20%D0%9C" +
                                            "%D0%B0%D1%85%D0%B0%D0%B5%D0%B2&method=voteForFriend&sex=0&vote=5&key=" + User.getInstance().getAuth_key(SettingsActivity.this) + "&model_id=1"
                                    , null
                            );
                            publishProgress("Качаем номеру: " + i + " из " + jsonArray.length());

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    publishProgress("Все довольны");
                    break;

                case ALL_FRIEND_BAKED:
                    try {
                        String urlListId = "http://109.234.156.252/prison/universal.php?key=" + User.getInstance().getAuth_key(SettingsActivity.this) + "&user=" + User.getInstance().getUser_id(SettingsActivity.this) + "&method=votes.getLog";


                        JSONObject jsonObject;

                        jsonArray = new JSONArray(HTTPHelper.getInstance().requestGet(allFriendsPlayPrison, null));


                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObject = jsonArray.getJSONObject(i);


                            HTTPHelper.getInstance().requestGet(
                                    "http://109.234.156.252/prison/universal.php?friend_uid=" + jsonObject.getString("uid")
                                            + "&user=" + User.getInstance().getUser_id(SettingsActivity.this)
                                            + "&username=%D0%94%D0%BC%D0%B8%D1%82%D1%80%D0%B8%D0%B9%20%D0%9C" +
                                            "%D0%B0%D1%85%D0%B0%D0%B5%D0%B2&method=voteForFriend&sex=0&vote=5&key=" + User.getInstance().getAuth_key(SettingsActivity.this)
                                            + "&model_id=1", null);
                            publishProgress("Номер " + i + " из " + jsonArray.length());
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                    publishProgress("Все довольны");
                    break;

                case PARASHA_DROGGI:


                    try {
                        String getInfo = "http://109.234.156.253/prison/universal.php?key=" + User.getInstance().getAuth_key(SettingsActivity.this) + "&method=getInfo&user=" + User.getInstance().getUser_id(SettingsActivity.this);
                        String namePlayer = new XMLParser().parsXMLTeg(new HTTPHelper().requestGet(getInfo, null), "name");

                        jsonArray = null;
                        jsonArray = new JSONArray(new HTTPHelper().requestGet(allFriendsPlayPrison, null));
                        JSONObject jsonObj;
                        int j = 1;
                        System.out.println(jsonArray.optJSONObject(1));
                        for (int i = jsonArray.length() - 120; i < jsonArray.length(); i++) {
                            jsonObj = jsonArray.getJSONObject(i);

                            HTTPHelper.getInstance().requestGet("http://109.234.156.253/prison/universal.php?key=" + User.getInstance().getAuth_key(SettingsActivity.this) + "&vote=1&friend%5Fuid=" + jsonObj.getString("uid")
                                    + "&model%5Fid=1&method=voteForFriend&sex=0&username=" + URLEncoder.encode(namePlayer, "UTF-8") + "&user=" + User.getInstance().getUser_id(SettingsActivity.this), null);
                            System.out.println(i);
                            publishProgress("Бросаем дрожжи другу номер: " + j + " из 120ти");
                            j++;
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    publishProgress("Всё");
                    break;
                case HARCHKI:



                    try {
                        String getInfo = "http://109.234.156.253/prison/universal.php?key=" + User.getInstance().getAuth_key(SettingsActivity.this) + "&method=getInfo&user=" + User.getInstance().getUser_id(SettingsActivity.this);
                        String namePlayer = new XMLParser().parsXMLTeg(new HTTPHelper().requestGet(getInfo, null), "name");

                        jsonArray = null;
                        jsonArray = new JSONArray(new HTTPHelper().requestGet(allFriendsPlayPrison, null));
                        JSONObject jsonObj;
                        int j = 1;
                        System.out.println(jsonArray.optJSONObject(1));
                        for (int i = jsonArray.length() - 120; i < jsonArray.length(); i++) {
                            jsonObj = jsonArray.getJSONObject(i);

                            HTTPHelper.getInstance().requestGet("http://109.234.156.253/prison/universal.php?key=" + User.getInstance().getAuth_key(SettingsActivity.this) + "&vote=2&friend%5Fuid=" + jsonObj.getString("uid")
                                    + "&model%5Fid=1&method=voteForFriend&sex=0&username=" + URLEncoder.encode(namePlayer, "UTF-8") + "&user=" + User.getInstance().getUser_id(SettingsActivity.this), null);
                            System.out.println(i);
                            publishProgress("Плюем другу номер: " + j + " из 120ти");
                            j++;
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    publishProgress("Всё");
                    break;

                case ATTAK:




                case CHISTKA:



                    try {


                        jsonArray = null;
                        jsonArray = new JSONArray(new HTTPHelper().requestGet(allFriendsPlayPrison, null));
                        JSONObject jsonObj;
                        int j = 1;
                        System.out.println(jsonArray.optJSONObject(1));
                        for (int i = jsonArray.length()-1; i > 1; i--) {
                            jsonObj = jsonArray.getJSONObject(i);
                            int rating =  jsonObj.getInt("rating");
                            if(rating < 50000) {
System.out.println("Удаляем" + jsonObj.getInt("uid") +"---"+ rating);
                               APIFunctions.Friends.delete(jsonObj.getInt("uid"),SettingsActivity.this);
                            }
                            System.out.println(i);
                            publishProgress("Плюем другу номер: " + j + " из 120ти");
                            j++;
                        }


                    } catch (JSONException e) {

                        e.printStackTrace();
                    }
                    publishProgress("Всё");
                    break;
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            statusTextView.setText(values[0]);

        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.about:
                startActivity(new Intent(SettingsActivity.this,DeveloperActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }



}



