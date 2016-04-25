package com.nik.smartnote.vk.util;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.nik.smartnote.vk.DeveloperActivity;
import com.nik.smartnote.vk.MainActivity;
import com.nik.smartnote.vk.Model.User;
import com.nik.smartnote.vk.R;

import com.nik.smartnote.vk.SettingsActivity;
import com.nik.smartnote.vk.service.PostService;


public class WorkActivity extends Activity {

    //todo-------------------------------------------


    SharedPreferences sPref;
    final String SAVED_TEXT = "saved_text";
    final String SAVED_TEXT1 = "saved_text1";
    final String SAVED_TEXT2 = "saved_text2";
    EditText editTextIdPost;
    EditText editTextComment;
    EditText editTextIdPage;
    EditText editTextTimeSleep;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work_layout);
        ActionBar actionBar = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            actionBar = getActionBar();

            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        editTextComment = (EditText) findViewById(R.id.editTextComment);
        editTextIdPost = (EditText) findViewById(R.id.editTextIdPost);
        editTextIdPage = (EditText) findViewById(R.id.editTextIdPage);
        editTextTimeSleep = (EditText) findViewById(R.id.editTextTimeSleep);
//загружаем комментарий
        loadText();
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    }

    public void onClStopService(View v) {
        stopService(new Intent(WorkActivity.this, PostService.class));


        Intent intent = new Intent(WorkActivity.this, ServiceStartReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(WorkActivity.this, 0, intent, 0);

        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();

    }

    public void onClServiceGo(View v) {
        if (!editTextIdPage.getText().toString().matches("")) {
            if (!editTextComment.getText().toString().matches("")) {

                if (!editTextTimeSleep.getText().toString().matches("")) {
                    int a = Integer.parseInt(editTextTimeSleep.getText().toString());
                    if (a == 0) {
                        Toast.makeText(WorkActivity.this, "Периодичность не может быть ноль!", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(WorkActivity.this, ServiceStartReceiver.class);
                    intent.putExtra("access_token", User.getInstance().getAccessToken());

                    intent.putExtra("id_post", editTextIdPost.getText().toString());
                    intent.putExtra("id_page", editTextIdPage.getText().toString());
                    System.out.println(a);

                    intent.putExtra("comment", editTextComment.getText().toString());

                    pendingIntent = PendingIntent.getBroadcast(WorkActivity.this, 0, intent, 0);

//				//сохраняем комментарий
                    saveText();
//				//запускаем сервис
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, 50, a * 60000, pendingIntent);

                } else {
                    Toast.makeText(WorkActivity.this, "Вы не ввели время периодичности!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(WorkActivity.this, "Вы не ввели текст комментария!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(WorkActivity.this, "Вы не ввели ID группы!", Toast.LENGTH_SHORT).show();
        }
    }


    void saveText() {

        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SAVED_TEXT, editTextComment.getText().toString());
        ed.putString(SAVED_TEXT1, editTextIdPost.getText().toString());
        ed.putString(SAVED_TEXT2, editTextIdPage.getText().toString());

        ed.commit();
        Toast.makeText(this, "Комментарий сохраненный!", Toast.LENGTH_SHORT).show();
    }

    void loadText() {

        sPref = getPreferences(MODE_PRIVATE);
        String savedText = sPref.getString(SAVED_TEXT, "");
        String savedText1 = sPref.getString(SAVED_TEXT1, "");
        String savedText3 = sPref.getString(SAVED_TEXT2, "");

        editTextComment.setText(savedText);
        editTextIdPost.setText(savedText1);
        editTextIdPage.setText(savedText3);
        Toast.makeText(this, "Ваш прежний комментарий загружен!", Toast.LENGTH_SHORT).show();
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
        }
        return super.onOptionsItemSelected(item);
    }






}



