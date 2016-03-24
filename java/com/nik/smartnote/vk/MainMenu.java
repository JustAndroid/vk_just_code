package com.nik.smartnote.vk;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nik.smartnote.vk.Model.User;
import com.nik.smartnote.vk.adapter.MainMenuAdapter;
import com.nik.smartnote.vk.util.HTTPHelper;
import com.nik.smartnote.vk.util.PrissonManager;
import com.nik.smartnote.vk.util.WorkActivity;
import com.nik.smartnote.vk.util.XMLParser;

/**
 * Created by Николай on 10.02.2016.
 */
public class MainMenu extends Activity {
    ActionBar actionBar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            actionBar = getActionBar();

            actionBar = getActionBar();
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#B8860B")));

            actionBar.setDisplayHomeAsUpEnabled(true);

        }

        ListView listView = (ListView) findViewById(R.id.main_list_view);
        MainMenuAdapter mainMenuAdapter = new MainMenuAdapter(this, getResources().getStringArray(R.array.main_meny_texts));

        listView.setAdapter(mainMenuAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {

                    case 0:
                        ProgressDialog progressDialog = new ProgressDialog(MainMenu.this, ProgressDialog.STYLE_SPINNER);
                        progressDialog.setTitle("Загрузка");
                        progressDialog.setMessage("Отправка запроса...");
                        progressDialog.show();

                        String xmlRezult = HTTPHelper.getInstance().requestGet(new SettingsActivity().BOSS_INFO, null);
                        progressDialog.dismiss();
                        XMLParser xmlParser = new XMLParser();
                        String status = xmlParser.parsXMLTeg(xmlRezult, "status");

                        if (status != null) {
                            switch (status) {
                                case "win":
                                    AlertDialog.Builder alertDialogB = new AlertDialog.Builder(MainMenu.this).setTitle("Победа");

                                    alertDialogB.setMessage("Помянем " + xmlParser.parsXMLTeg(xmlRezult, "name"));
                                    alertDialogB.setPositiveButton("К списку", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            startActivity(new Intent(MainMenu.this, BossMenuActivity.class));
                                        }
                                    });
                                    AlertDialog alertDialog = alertDialogB.create();
                                    alertDialog.show();

                                    break;
                                default:


                                    //  statusTextView.setText("Проигрыш, либо бой не начат!!!");
                                    break;
                            }
                        } else {
                            String helFull = xmlParser.parsXMLTeg(xmlRezult, "h_full");

                            if(helFull != null){
                                String nowHel = xmlParser.parsXMLTeg(xmlRezult, "h_now");
                                int  idBoss =  new PrissonManager().getBoss(Integer.parseInt(xmlParser.parsXMLTeg(xmlRezult, "id")));
                                System.out.println(idBoss);
Intent intent = new Intent(MainMenu.this, FightActivity.class);
                                intent.putExtra("helFull", helFull);
                                intent.putExtra("nowHel", nowHel);
                                intent.putExtra("idBoss", Integer.toString(idBoss));

                                        startActivity(intent);
                            }else {
                                startActivity(new Intent(MainMenu.this, BossMenuActivity.class));

                            }


                        }


                        break;
                    case 2:
                        startActivity(new Intent(MainMenu.this, SettingsActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(MainMenu.this, CleanFriendsActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(MainMenu.this, WorkActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(MainMenu.this, AchievementsActivity.class));
                        break;
                    case 6:
                        startActivity(new Intent(MainMenu.this,AttakFriendActivity.class));
                        break;

                }
            }
        });
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
                startActivity(new Intent(MainMenu.this, DeveloperActivity.class));
                new User().clear();
        }
        return super.onOptionsItemSelected(item);
    }
}
