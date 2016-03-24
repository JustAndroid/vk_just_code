package com.nik.smartnote.vk;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.nik.smartnote.vk.adapter.BossMenuAdapter;
import com.nik.smartnote.vk.util.PrissonManager;

/**
 * Created by mytrofanenkova-io on 17.02.2016.
 */
public class BossMenuActivity extends Activity {
    AlertDialog alertDialog;
    int mode;
    String[] items = {"Пацанский", "Блатной", "Авторитетный"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            actionBar = getActionBar();

            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setContentView(R.layout.boss_atak_layout);
        ListView listView = (ListView) findViewById(R.id.boss_list);



            BossMenuAdapter bossMenuAdapter = new BossMenuAdapter(this);

            listView.setAdapter(bossMenuAdapter);

listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        showDialog(getResources().getString(PrissonManager.BOSSES[position][1]),PrissonManager.BOSSES[position][0]);
    }
});


    }

    void showDialog(String name, final int bossId) {


        AlertDialog.Builder alertDialogB = new AlertDialog.Builder(BossMenuActivity.this);
        alertDialogB.setTitle("Нападаем на " + name);
        alertDialogB.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {

                        ProgressDialog progressDialog = new ProgressDialog(BossMenuActivity.this, ProgressDialog.STYLE_SPINNER);
                        progressDialog.setTitle("Загрузка");
                        progressDialog.setMessage("Отправка запроса...");
                        progressDialog.show();

 int id = new PrissonManager().attackBoss(bossId, PrissonManager.MODS[item],BossMenuActivity.this);
                        progressDialog.dismiss();
                       switch (id) {

                           case 4:
                               alertDialog.dismiss();
                               startActivity(new Intent(BossMenuActivity.this, FightActivity.class));
                               //"Бой уже начат";
break;
                           case 0:
                             // "Напали на босса";
                               alertDialog.dismiss();
                               startActivity(new Intent(BossMenuActivity.this, FightActivity.class));
                               break;
                           case 6:
                               //"Лимит нападений на этого босса исчерпан";
                               Toast.makeText(BossMenuActivity.this, "Лимит на сегодня исчерпан", Toast.LENGTH_LONG).show();

                               alertDialog.dismiss();
                               break;
                           case 2:
                               Toast.makeText(BossMenuActivity.this, "Нет доступа, или ключей!", Toast.LENGTH_LONG).show();
                               alertDialog.dismiss();
                               // "Нет доступа к боссу, ключей ноль";
                               break;
                       }
                       //    HTTPHelper.getInstance().requestGet(BOSS_INFO, null);


                    }
                }
        );
        alertDialog = alertDialogB.create();
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Отмена",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });

        alertDialog.show();

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
                startActivity(new Intent(BossMenuActivity.this, DeveloperActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

}
