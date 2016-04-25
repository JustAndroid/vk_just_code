package com.nik.smartnote.vk;


import android.app.ActionBar;
import android.app.Activity;

import android.content.ClipboardManager;
import android.content.Context;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.nik.smartnote.vk.api.APIFunctions;

import com.nik.smartnote.vk.util.ParsDataHelper;
import com.nik.smartnote.vk.util.PrissonManager;
import com.nik.smartnote.vk.util.XMLParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.net.URLDecoder;


/**
 * Created by mytrofanenkova-io on 23.02.2016.
 */
public class AchievementsActivity extends Activity {

    EditText editTextURL;
ImageButton imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.achievements_layout);
        ActionBar actionBar = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            actionBar = getActionBar();

            assert actionBar != null;
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        editTextURL = (EditText) findViewById(R.id.editTextURL);
imageButton = (ImageButton)findViewById(R.id.serchfriend);

    }

    public void CheckFriend(View v) {

        if (editTextURL.getText().toString().equals("")) {
            Toast.makeText(this, "Адрес пуст", Toast.LENGTH_LONG).show();
        } else {
            CleanFriendsActivity cleanFriendsActivity = new CleanFriendsActivity();


            try {

              String id_name = ParsDataHelper.getUserId(editTextURL.getText().toString());
                if (id_name == null ) {
                    Toast.makeText(this, "Адрес некоректный", Toast.LENGTH_LONG).show();
                }else {
                    JSONObject jsonObject = new JSONObject(APIFunctions.Users.usersGet(id_name));

                    JSONArray response = jsonObject.getJSONArray("response");

                    String id = response.getJSONObject(0).getString("uid");
                    String info = new PrissonManager().getUserInfo(Integer.parseInt(id));
                    if (!cleanFriendsActivity.isPlaerNotFount(info)) {


                        TextView nameVK = (TextView) findViewById(R.id.textViewNameVK);
                        nameVK.setText("Ф.И. ВКонтакте: " + response.getJSONObject(0).getString("first_name") + " " + response.getJSONObject(0).getString("last_name"));
                        TextView name = (TextView) findViewById(R.id.textViewKlichka);
                        name.setText("Кличка в тюряге: " + URLDecoder.decode(new XMLParser().parsXMLTeg(info, "name"), "UTF-8"));
                        TextView avtoritet = (TextView) findViewById(R.id.textViewAvtoritet);
                        avtoritet.setText("Авторитет: " + new XMLParser().parsXMLTeg(info, "rating"));
                        TextView talants = (TextView) findViewById(R.id.textViewTalants);
                        talants.setText("Талантов: " + cleanFriendsActivity.getPlayerTalants(info));
                        TextView boroda = (TextView) findViewById(R.id.textViewBoroda);
                        boroda.setText("Борода: " + new XMLParser().parsXMLTeg(info, "beard"));
                        TextView maxUron = (TextView) findViewById(R.id.textViewMaxUron);
                        int maxUronValue = cleanFriendsActivity.getMaxUron(info);
                        switch (maxUronValue) {

                            case 0:
                                maxUron.setText("Максимальный урон: 0 целых, хрен дисятих + ");
                                break;
                            case 10:
                                maxUron.setText("Максимальный урон: 10к + ");
                                break;
                            case 30:
                                maxUron.setText("Максимальный урон: 30к + ");
                                break;
                            case 50:
                                maxUron.setText("Максимальный урон: 50к + ");
                                break;
                            case 100:
                                maxUron.setText("Максимальный урон: 100к + ");
                                break;
                            case 500:
                                maxUron.setText("Максимальный урон: 500к + ");
                                break;
                            case 1000:
                                maxUron.setText("Максимальный урон: 1кк + ");
                                break;


                        }
                        XmlPullParserFactory xmlPullParserFactory;

                        xmlPullParserFactory = XmlPullParserFactory.newInstance();
                        xmlPullParserFactory.setNamespaceAware(true);
                        XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
                        xmlPullParser.setInput(new StringReader(info));

                        TextView killBossKirpich = (TextView) findViewById(R.id.TextKirpich);
                        TextView killBossSyzyi = (TextView) findViewById(R.id.textSizyi);
                        TextView killBossMahno = (TextView) findViewById(R.id.textMahno);
                        TextView killBossLutyi = (TextView) findViewById(R.id.textLutyi);
                        TextView killBossShaiba = (TextView) findViewById(R.id.textShaiba);

                        while (xmlPullParser.getEventType() != XmlPullParser.END_DOCUMENT) {

                            if (xmlPullParser.getEventType() == XmlPullParser.START_TAG) {
                                if (xmlPullParser.getName().equals("boss")) {
                                    for (int i = 0; i < xmlPullParser.getAttributeCount(); i++) {
                                        if (xmlPullParser.getAttributeValue(i).equals("1")) {
                                            killBossKirpich.setText("Угробил босса \"Кирпич\" : " + xmlPullParser.nextText() + " раз");
                                            continue;
                                        }
                                        if (xmlPullParser.getAttributeValue(i).equals("2")) {
                                            killBossSyzyi.setText("Угробил босса \"Сизый\" : " + xmlPullParser.nextText() + " раз");
                                            continue;
                                        }
                                        if (xmlPullParser.getAttributeValue(i).equals("3")) {
                                            killBossMahno.setText("Угробил босса \"Махно\" : " + xmlPullParser.nextText() + " раз");
                                            continue;
                                        }
                                        if (xmlPullParser.getAttributeValue(i).equals("4")) {
                                            killBossLutyi.setText("Угробил босса \"Лютий\" : " + xmlPullParser.nextText() + " раз");
                                            continue;
                                        }
                                        if (xmlPullParser.getAttributeValue(i).equals("5")) {
                                            killBossShaiba.setText("Угробил босса \"Шайба\" : " + xmlPullParser.nextText() + " раз");

                                        }
                                    }

                                }
                            }
                            xmlPullParser.next();
                        }
                    }else{
                        Toast.makeText(this, "Не играет в тюрягу", Toast.LENGTH_LONG).show();
                    }
                }
            } catch (XmlPullParserException | JSONException | IOException e) {
                Toast.makeText(this, "Такого пользователя не существует!", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

        }

//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(imageButton.getWindowToken(),
//                InputMethodManager.HIDE_NOT_ALWAYS);
    }
    public void pasteText(View v) {
        int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) this
                    .getSystemService(CLIPBOARD_SERVICE);
if(clipboard.hasText()){
            editTextURL.setText(clipboard.getText());
}else{
    Toast.makeText(this, "Буфер обмена пуст", Toast.LENGTH_LONG).show();
    }
        } else {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

            if (clipboard.getPrimaryClip() != null) {
                editTextURL.setText(clipboard.getPrimaryClip().getItemAt(0).getText());
            } else {
                Toast.makeText(this, "Буфер обмена пуст", Toast.LENGTH_LONG).show();
            }
        }


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
                startActivity(new Intent(AchievementsActivity.this, DeveloperActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }


}