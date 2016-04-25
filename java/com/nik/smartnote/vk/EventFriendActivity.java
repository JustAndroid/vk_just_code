package com.nik.smartnote.vk;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.nik.smartnote.vk.Model.User;
import com.nik.smartnote.vk.util.HTTPHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mytrofanenkova-io on 02.03.2016.
 */
public class EventFriendActivity extends Activity {

    public final String allFriendsPlayPrison = "http://109.234.156.251/prison/universal.php?user=" + User.getInstance().getUser_id() + "&method=getFriendRatings&key=" + User.getInstance().getAuth_key();
EditText editTextSum;
    TextView textViewAvtoritet;
    TextView textViewMoney;
    TextView textViewLogAttak;
    ImageButton attakButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_friend_layout);

        ActionBar actionBar;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            actionBar = getActionBar();

            actionBar.setDisplayHomeAsUpEnabled(true);
        }

editTextSum = (EditText)findViewById(R.id.editTextSum);
textViewAvtoritet = (TextView)findViewById(R.id.give_avtoritet);
        textViewMoney = (TextView)findViewById(R.id.prise_fight);
        textViewLogAttak = (TextView)findViewById(R.id.TextViewLogAttakFriend);
attakButton = (ImageButton)findViewById(R.id.AttakButton);
       calculateAvtoritet();


        editTextSum.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

              calculateAvtoritet();

                return false;
            }
        });
    editTextSum.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
           calculateAvtoritet();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    });
    }


    public void onAttak(View v){

        new AttakTask().execute(Integer.parseInt(editTextSum.getText().toString()));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    class AttakTask extends AsyncTask<Integer,String,Void> {



        @Override
        protected Void doInBackground(Integer... params) {


            try {

                JSONArray jsonArray = null;
                jsonArray = new JSONArray(new HTTPHelper().requestGet(allFriendsPlayPrison, null));
                JSONObject jsonObj;
                int j = 1;
                System.out.println(jsonArray.optJSONObject(1));
                for (int i = 0; i < params[0]; i++) {
                    jsonObj = jsonArray.getJSONObject(jsonArray.length()-5);
                    //берем 5 человека с конца для нападения

                    HTTPHelper.getInstance().requestGet("http://109.234.156.251/prison/universal.php?key="+User.getInstance().getAuth_key()+"&method=challengeToDuel&user="+User.getInstance().getUser_id()+"&enemy="+jsonObj.getString("uid"), null);
                    System.out.println(i);
                    publishProgress("\nЛупашим кореша: " + i + " из "+ params[0]);
                    j++;
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            publishProgress("\nВсё");

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            textViewLogAttak.append(values[0]);
        }
    }

    void calculateAvtoritet(){
        //просчет и проверка на валидность текста, сетим скок будет потрачено итп


        if(!editTextSum.getText().toString().equals("")&& !editTextSum.getText().toString().equals("")
                && Integer.parseInt(editTextSum.getText().toString()) > 0 && Integer.parseInt(editTextSum.getText().toString()) < 100000000  ) {
            int sigPotracheno = Integer.parseInt(editTextSum.getText().toString()) * 50;
            int avtoritetaGived = Integer.parseInt(editTextSum.getText().toString()) * 7;
            textViewMoney.setText("Будет потрачено: " + sigPotracheno + " сигарет");
            textViewAvtoritet.setText("Будет получено: " + avtoritetaGived + " авторитета");
            attakButton.setEnabled(true);
        }else{
            Toast.makeText(this,"Не корректное количество!", Toast.LENGTH_LONG).show();
            attakButton.setEnabled(false);
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
                startActivity(new Intent(EventFriendActivity.this, DeveloperActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}

