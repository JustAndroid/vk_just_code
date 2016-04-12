package com.nik.smartnote.vk;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.nik.smartnote.vk.adapter.BossBotAdapter;
import com.nik.smartnote.vk.util.PrissonManager;
import com.nik.smartnote.vk.util.XMLParser;

import java.sql.Time;
import java.util.logging.Handler;

/**
 * Created by Николай on 20.02.2016.
 */
public class BossBotActivity extends Activity {

    TextView textView;
    ListView bossList;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boss_bot);
        textView = (TextView) findViewById(R.id.status_text_bot);
//bossList = (ListView)findViewById(R.id.boss_bot_list);
        //      bossList.setAdapter(new BossBotAdapter(this));


    }

    public void Go(View v) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int[] boses = {1, 2, 3, 4, 5, 6, 7, 8};
                int timeBatl = 10000;

                for (int i = 0; i < boses.length; i++) {
                    boolean endBoss = false;
                    while (endBoss) {
                        int code = new PrissonManager().attackBoss(i, PrissonManager.MODS[0], BossBotActivity.this);

                        switch (code) {

                            case 4:
                                // "Бой уже начат";

                            case 0:

                                try {
                                    Thread.sleep(timeBatl * i);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            case 6:
                                // "Лимит нападений на этого босса исчерпан";
endBoss = true;
                            case 2:
                                //"Нет доступа к боссу, ключей ноль";

                        }

                    }
                }
            }
        }
            );




    }
}
