package com.nik.smartnote.vk;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.nik.smartnote.vk.Model.User;
import com.nik.smartnote.vk.api.APIFunctions;
import com.nik.smartnote.vk.util.HTTPHelper;
import com.nik.smartnote.vk.util.JsonHelper;
import com.nik.smartnote.vk.util.XMLParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;


/**
 * Created by Николай on 15.01.2016.
 */
public class CleanFriendsActivity extends Activity {

    PowerManager.WakeLock wakeLock;

    Spinner spinner;
    Switch prissonSwitch;
    Switch talantSwitch;
    Switch avtoritetSwitch;
    Switch uronSwitch;
    Switch deleteFriendsSwitch;
    TextView statusText;
    TextView talantsTextView;
    TextView avtoritetTextView;
    ScrollView scrollView;

    ProgressDialog progressDialog;
    ProgressDialog taskProgressDialog;

    ArrayAdapter<CharSequence> adapter;


    int IsPlayPrisson;
    int avtoritet;
    int talants;
    int baned;
    int maxUron;

    final int ALL_FRIENDS = 1; //друзья
    final int FOLLOWERS = 0; //подписчики

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clean_friends);
        ActionBar actionBar = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            actionBar = getActionBar();

            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        prissonSwitch = (Switch) findViewById(R.id.prissonSwitch);
        talantSwitch = (Switch) findViewById(R.id.talantsSwitch);
        avtoritetSwitch = (Switch) findViewById(R.id.avtoritetSwitch);
        uronSwitch = (Switch) findViewById(R.id.uronSwitch);
        deleteFriendsSwitch = (Switch) findViewById(R.id.deleteFriendsSwitch);
        statusText = (TextView) findViewById(R.id.status_text);
        talantsTextView = (TextView) findViewById(R.id.talantsTextView);
        avtoritetTextView = (TextView) findViewById(R.id.avtoritetTextView);
        scrollView = (ScrollView) findViewById(R.id.scroll_view);
        //выпадающий список
        spinner = (Spinner) findViewById(R.id.spinnerMaxUron);
        adapter = ArrayAdapter.createFromResource(this, R.array.max_uron, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(onItemSelectedListener);

        prissonSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (prissonSwitch.isChecked()) {
                    talantSwitch.setEnabled(true);
                    avtoritetSwitch.setEnabled(true);
                    uronSwitch.setEnabled(true);

                    spinner.setVisibility(Spinner.VISIBLE);
                    if (avtoritetSwitch.isChecked()) {
                        avtoritetTextView.setEnabled(true);
                    }
                    if (talantSwitch.isChecked()) {
                        talantsTextView.setEnabled(true);
                    }
                } else {
                    talantSwitch.setEnabled(false);
                    avtoritetSwitch.setEnabled(false);
                    uronSwitch.setEnabled(false);

                    avtoritetTextView.setEnabled(false);
                    talantsTextView.setEnabled(false);
                    spinner.setVisibility(Spinner.INVISIBLE);
                }
            }
        });
        avtoritetSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (avtoritetSwitch.isChecked()) {
                    avtoritetTextView.setEnabled(true);
                } else {
                    avtoritetTextView.setEnabled(false);
                }
            }
        });
        talantSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (talantSwitch.isChecked()) {
                    talantsTextView.setEnabled(true);
                } else {
                    talantsTextView.setEnabled(false);
                }
            }
        });

    }


    public void onCheckFriends(View v) {

        progressDialog = new ProgressDialog(CleanFriendsActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Подождите...");
        progressDialog.setMessage("Получение списка пользователей.");
        statusText.setText("Начинаем проверку...");
        final CheckUsersTask checkFriendsTask;
        checkFriendsTask = new CheckUsersTask();

        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                checkFriendsTask.cancel(true);
            }
        });
        if (prissonSwitch.isChecked()) {
            IsPlayPrisson = 1;
        } else {
            IsPlayPrisson = 0;
        }
        if (avtoritetSwitch.isChecked()) {
            avtoritet = Integer.parseInt(talantsTextView.getText().toString());

        } else {
            avtoritet = 0;
        }
        if (talantSwitch.isChecked()) {
            talants = Integer.parseInt(talantsTextView.getText().toString());
        } else {
            talants = 0;
        }
        if (uronSwitch.isChecked()) {
            int position = spinner.getSelectedItemPosition();

            switch (position) {
                case 0:
                    maxUron = 10;
                    break;
                case 1:
                    maxUron = 30;
                    break;
                case 2:
                    maxUron = 50;
                    break;
                case 3:
                    maxUron = 100;
                    break;
                case 4:
                    maxUron = 500;
                    break;
                case 5:
                    maxUron = 1000;

                    break;

            }

        } else {
            maxUron = 0;
        }
        if (deleteFriendsSwitch.isChecked()) {
            baned = 1;
        } else {
            baned = 0;
        }


        checkFriendsTask.execute(ALL_FRIENDS, IsPlayPrisson, avtoritet, talants, maxUron, baned);

    }


    public void onCheckSubscriber(View v) {
        final CheckUsersTask checkSubscriberTask;
        checkSubscriberTask = new CheckUsersTask();

        progressDialog = new ProgressDialog(CleanFriendsActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Подождите...");
        progressDialog.setMessage("Получение списка пользователей.");

        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                checkSubscriberTask.cancel(true);
            }
        });

        statusText.setText("Начинаем проверку...");


        if (prissonSwitch.isChecked()) {
            IsPlayPrisson = 1;
        } else {
            IsPlayPrisson = 0;
        }
        if (avtoritetSwitch.isChecked()) {
            avtoritet = Integer.parseInt(avtoritetTextView.getText().toString());

        } else {
            avtoritet = 0;
        }
        if (talantSwitch.isChecked()) {
            talants = Integer.parseInt(talantsTextView.getText().toString());
        } else {
            talants = 0;
        }
        if (uronSwitch.isChecked()) {
            int position = spinner.getSelectedItemPosition();

            switch (position) {
                case 0:
                    maxUron = 10;
                    break;
                case 1:
                    maxUron = 30;
                    break;
                case 2:
                    maxUron = 50;
                    break;
                case 3:
                    maxUron = 100;
                    break;
                case 4:
                    maxUron = 500;
                    break;
                case 5:
                    maxUron = 1000;
                    break;


            }

        } else {
            maxUron = 0;
        }
        if (deleteFriendsSwitch.isChecked()) {
            baned = 1;
        } else {
            baned = 0;
        }
        if (IsPlayPrisson == 0 && avtoritet == 0 && talants == 0 && maxUron == 0 && baned == 0) {

            Toast.makeText(this, "Не выбрано параметров для отсеивания!", Toast.LENGTH_LONG).show();

        } else {


            checkSubscriberTask.execute(FOLLOWERS, IsPlayPrisson, avtoritet, talants, maxUron, baned);
        }
    }


    AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    private class CheckUsersTask extends AsyncTask<Integer, String, Void> {

        final int USERS_COUNT = 0;
        final int PROGRESS = 1;
        final int REZULT = 2;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
         wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"Wake lock for AsyncTAsk");
            wakeLock.acquire();
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Integer... array) {


            if (array[0] == ALL_FRIENDS) {
                Log.w("Лог Асунг Таска", "Кореша");

                JSONArray allFriendsVK = JsonHelper.getInstanse().parse(APIFunctions.Friends.getFriends(0));
                checkFriends(allFriendsVK, array);

            } else if (array[0] == FOLLOWERS) {


                  checkFollowers(APIFunctions.Users.getFollowers(), array);

            }


            return null;
        }

        int checkFriends(JSONArray jsonArray, Integer[] array) {

//ALL_FRIENDS, IsPlayPrisson, avtoritet, talants, maxUron, baned
            String userInfo;

            int usersDelete = 0;
            int userID;
            publishProgress(Integer.toString(jsonArray.length()), null, null);
            for (int i = 0; i < jsonArray.length() && !isCancelled(); i++) {
userInfo = null;
                publishProgress(null, Integer.toString(i + 1), null);
                try {
                    userID = Integer.parseInt(jsonArray.getString(i));


                    if (array[1] != 0) {
                        if(userInfo == null){
                            userInfo = HTTPHelper.getInstance().requestGet("http://109.234.156.250/prison/universal.php?key="
                                    + User.getInstance().getAuth_key() + "&with_guild=1&user="
                                    + User.getInstance().getUser_id() + "&method=getFriendModels&friend_uid=" + Integer.toString(userID), null);}
                        if (isPlaerNotFount(userInfo)) {
                            publishProgress(null, null, "Удалил: не играет ***");
                            APIFunctions.Friends.delete(userID);
                            continue;
                        }
                    }



                    if (array[2] != 0) {

                        if(userInfo == null){
                            userInfo = HTTPHelper.getInstance().requestGet("http://109.234.156.250/prison/universal.php?key="
                                    + User.getInstance().getAuth_key() + "&with_guild=1&user="
                                    + User.getInstance().getUser_id() + "&method=getFriendModels&friend_uid=" + Integer.toString(userID), null);}
                        String parserRezult = new XMLParser().parsXMLTeg(userInfo, "rating");

                        int plaerAvtoritet;
                        if (parserRezult != null) {
                            plaerAvtoritet = Integer.parseInt(parserRezult);
                        } else {
                            plaerAvtoritet = 0;
                            //авторитет
                        }

                        if (array[2] >= plaerAvtoritet) {
                            publishProgress(null, null, "Удалил: *** Авторитет - " + plaerAvtoritet + " *** ТАланты - " + getPlayerTalants(userInfo) + "*** Урон -  " + getMaxUron(userInfo));

                            APIFunctions.Friends.delete(userID);
                            continue;
                        }
                    }

                    if (array[3] != 0) {
                        if(userInfo == null){
                            userInfo = HTTPHelper.getInstance().requestGet("http://109.234.156.250/prison/universal.php?key="
                                    + User.getInstance().getAuth_key() + "&with_guild=1&user="
                                    + User.getInstance().getUser_id() + "&method=getFriendModels&friend_uid=" + Integer.toString(userID), null);}

                        if (array[3] >= getPlayerTalants(userInfo)) {
                            publishProgress(null, null, "Удалил: *** ТАланты - " + getPlayerTalants(userInfo));
                            APIFunctions.Friends.delete(userID);
                            continue;
                        }
                    }

                    if (array[4] != 0) {
                        if(userInfo == null){
                            userInfo = HTTPHelper.getInstance().requestGet("http://109.234.156.250/prison/universal.php?key="
                                    + User.getInstance().getAuth_key() + "&with_guild=1&user="
                                    + User.getInstance().getUser_id() + "&method=getFriendModels&friend_uid=" + Integer.toString(userID), null);}

                        if (array[4] > getMaxUron(userInfo)) {
                            publishProgress(null, null,"Удалил: *** ТАланты - " + getPlayerTalants(userInfo));
                            APIFunctions.Friends.delete(userID);
                            continue;
                        }
                    }
                    if (array[5] != 0) {

                        if (isUserBanedOrDelete(userID)) {
                            publishProgress(null, null, i + ") Удалил: \n" +
                                    "Забанен *** https://vk.com/id" + jsonArray.getString(i));

                            APIFunctions.Friends.delete(userID);
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return usersDelete;
        }

        int checkFollowers(ArrayList<Integer> followers, Integer[] array) {

//ALL_FRIENDS, IsPlayPrisson, avtoritet, talants, maxUron, baned
            int userAdds = 0;
            int userID;
            String userInfo;

            publishProgress(Integer.toString(followers.size()), null, null);
            for (int i = 0; i < followers.size() && !isCancelled(); i++) {
                userInfo = null;
                publishProgress(null, Integer.toString(i + 1), null);

                    userID = followers.get(i);



                    if (array[1] != 0) {
                        if(userInfo == null){
                            userInfo = HTTPHelper.getInstance().requestGet("http://109.234.156.250/prison/universal.php?key="
                                    + User.getInstance().getAuth_key() + "&with_guild=1&user="
                                    + User.getInstance().getUser_id() + "&method=getFriendModels&friend_uid=" + Integer.toString(userID), null);}
                        if (isPlaerNotFount(userInfo)) {

                            continue;
                        }
                    }

                    if (array[2] != 0) {
                        if(userInfo == null){
                            userInfo = HTTPHelper.getInstance().requestGet("http://109.234.156.250/prison/universal.php?key="
                                    + User.getInstance().getAuth_key() + "&with_guild=1&user="
                                    + User.getInstance().getUser_id() + "&method=getFriendModels&friend_uid=" + Integer.toString(userID), null);}

                        String parserRezult = new XMLParser().parsXMLTeg(userInfo, "rating");
                        int plaerAvtoritet;
                        if (parserRezult != null) {
                            plaerAvtoritet = Integer.parseInt(parserRezult);
                        } else {
                            plaerAvtoritet = 0;
                            //авторитет
                        }
                        if (array[2] > plaerAvtoritet) {
                            continue;
                        }
                    }

                    if (array[3] != 0) {
                        if(userInfo == null){
                            userInfo = HTTPHelper.getInstance().requestGet("http://109.234.156.250/prison/universal.php?key="
                                    + User.getInstance().getAuth_key() + "&with_guild=1&user="
                                    + User.getInstance().getUser_id() + "&method=getFriendModels&friend_uid=" + Integer.toString(userID), null);}
                        if (array[3] > getPlayerTalants(userInfo)) {
                            continue;
                        }
                    }

                    if (array[4] != 0) {
                        if(userInfo == null){
                            userInfo = HTTPHelper.getInstance().requestGet("http://109.234.156.250/prison/universal.php?key="
                                    + User.getInstance().getAuth_key() + "&with_guild=1&user="
                                    + User.getInstance().getUser_id() + "&method=getFriendModels&friend_uid=" + Integer.toString(userID), null);}
                        if (array[4] > getMaxUron(userInfo)) {
                            continue;
                        }
                    }
                    if (array[5] != 0) {

                        if (isUserBanedOrDelete(followers.get(i))) {
                            continue;
                        }
                    }
                    publishProgress(null, null,"Добавлен");
                    Log.w("Добавление", "User = " + followers.get(i));

                    APIFunctions.Friends.add(followers.get(i));

            }
            return userAdds;

        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            if (values[USERS_COUNT] != null) {
                progressDialog.hide();


                taskProgressDialog = new ProgressDialog(CleanFriendsActivity.this);

                taskProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                taskProgressDialog.setMax(Integer.parseInt(values[USERS_COUNT]));
                taskProgressDialog.setTitle("Проверка пользователей");
                taskProgressDialog.setMessage("Проверено: ");
                taskProgressDialog.show();
                taskProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        cancel(true);
                    }
                });
            }
            if (values[PROGRESS] != null) {
                taskProgressDialog.setProgress(Integer.parseInt(values[PROGRESS]));
            }

            if (values[REZULT] != null) {

                statusText.append("\n" + values[REZULT]);
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            wakeLock.release();
           taskProgressDialog.hide();
            progressDialog.hide();
        }


    }


    boolean isPlaerNotFount(String userInfo) {

        XmlPullParserFactory factory;
        try {
            factory = XmlPullParserFactory.newInstance();

            factory.setNamespaceAware(true);
            XmlPullParser xmlPullParser = factory.newPullParser();

            xmlPullParser.setInput(new StringReader(userInfo));
            while (xmlPullParser.getEventType() != XmlPullParser.END_DOCUMENT) {

                if (xmlPullParser.getEventType() == XmlPullParser.TEXT) {
                    if (xmlPullParser.getText().equals("friend player not found")) {
                       Log.d("isPlaerNotFount", "Not fount");
                        return true;
                    }
                }

                try {
                    xmlPullParser.next();
                } catch (IOException e) {
                    Log.d("isPlaerNotFount", "XML Error");
                    e.printStackTrace();

                }
            }
        } catch (XmlPullParserException e) {
            Log.d("isPlaerNotFount", "XML Error");
            e.printStackTrace();

        }
        return false;

    }

   public  int getMaxUron(String userInfo) {

        try {
            XmlPullParserFactory xmlPullParserFactory;

            xmlPullParserFactory = XmlPullParserFactory.newInstance();
            xmlPullParserFactory.setNamespaceAware(true);
            XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
            xmlPullParser.setInput(new StringReader(userInfo));

            while (xmlPullParser.getEventType() != XmlPullParser.END_DOCUMENT) {

                if (xmlPullParser.getEventType() == XmlPullParser.START_TAG) {
                    if (xmlPullParser.getName().equals("achiev")) {
                        for (int i = 0; i < xmlPullParser.getAttributeCount(); i++) {
                            if (xmlPullParser.getAttributeValue(i).equals("1")) {
                                switch (xmlPullParser.nextText()) {
                                    case "0":
                                        return 0;

                                    case "1":
                                        return 10;
                                    case "2":
                                        return 30;
                                    case "3":
                                        return 50;
                                    case "4":
                                        return 100;
                                    case "5":
                                        return 500;
                                    case "6":
                                        return 1000;
                                    case "7":
                                        return 1500;

                                    default:
                                        return 10000;

                                }


                            }
                        }

                    }
                }
                xmlPullParser.next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }


        return 0;
    }

  public  int getPlayerTalants(String userInfo) {
        int talants = 0;

        try {
            XmlPullParserFactory xmlPullParserFactory;

            xmlPullParserFactory = XmlPullParserFactory.newInstance();
            xmlPullParserFactory.setNamespaceAware(true);
            XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
            xmlPullParser.setInput(new StringReader(userInfo));

            while (xmlPullParser.getEventType() != XmlPullParser.END_DOCUMENT) {

                if (xmlPullParser.getEventType() == XmlPullParser.START_TAG) {
                    if (xmlPullParser.getName().equals("talent")) {
                        for (int i = 0; i < xmlPullParser.getAttributeCount(); i++) {
                            talants += Integer.parseInt(xmlPullParser.nextText());
                        }

                    }
                }
                xmlPullParser.next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

        return talants;
    }

    boolean isUserBanedOrDelete(int id) {

        JSONArray jsonArray = JsonHelper.getInstanse().parse(APIFunctions.Users.usersGet(Integer.toString(id)));
        String status = null;
        try {
            JSONObject user = jsonArray.getJSONObject(0);
            if (user.has("deactivated")) {
                status = user.getString("deactivated");
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return status != null;

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
                startActivity(new Intent(CleanFriendsActivity.this, DeveloperActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }


}