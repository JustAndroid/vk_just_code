package com.nik.smartnote.vk;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.nik.smartnote.vk.adapter.BossBotAdapter;

/**
 * Created by Николай on 20.02.2016.
 */
public class BossBotActivity extends Activity {


ListView bossList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boss_bot);

bossList = (ListView)findViewById(R.id.boss_bot_list);
        bossList.setAdapter(new BossBotAdapter(this));


    }

public void botGo(View v){




}
}
