package com.nik.smartnote.vk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nik.smartnote.vk.R;
import com.nik.smartnote.vk.util.PrissonManager;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by mytrofanenkova-io on 31.03.2016.
 */
public class BossBotAdapter extends ArrayAdapter<String> {

    ArrayList<Boolean> checked = new ArrayList<Boolean>();

    public BossBotAdapter(Context context) {
        super(context, R.layout.boss_bot);
    }

    @Override
    public int getCount() {
        return PrissonManager.BOSSES.length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View root =  LayoutInflater.from(getContext()).inflate(R.layout.boss_bot_menu_item, parent, false);
      ImageView imageView = (ImageView)root.findViewById(R.id.boss_bot_image_view);
imageView.setImageResource(PrissonManager.BOSSES[position][2]);
        TextView textViewname = (TextView)root.findViewById(R.id.boss_bot_name_text);
        textViewname.setText(PrissonManager.BOSSES[position][1]);
        TextView textViewhp = (TextView)root.findViewById(R.id.text_boss_bot_hp);
        textViewhp.setText(PrissonManager.BOSSES[position][1]);
        textViewhp.setText("Здоровья: " + root.getResources().getString(PrissonManager.BOSSES[position][3]));
        checked.add(false);

        return root;
    }


}
