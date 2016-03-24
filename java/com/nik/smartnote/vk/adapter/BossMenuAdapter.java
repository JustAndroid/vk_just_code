package com.nik.smartnote.vk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nik.smartnote.vk.R;
import com.nik.smartnote.vk.util.PrissonManager;

/**
 * Created by mytrofanenkova-io on 17.02.2016.
 */


public class BossMenuAdapter extends ArrayAdapter<String> {

    public BossMenuAdapter(Context context) {
        super(context, R.layout.boss_list_item);


        }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View root =  LayoutInflater.from(getContext()).inflate(R.layout.boss_list_item,parent,false);
        ImageView imageView = (ImageView)root.findViewById(R.id.boss_image_view);
        imageView.setImageResource(PrissonManager.BOSSES[position][2]);
        TextView textViewNameBoss = (TextView)root.findViewById(R.id.boss_name_text);
        textViewNameBoss.setText(PrissonManager.BOSSES[position][1]);
        TextView textViewBossXP = (TextView)root.findViewById(R.id.text_boss_hp);
        textViewBossXP.setText("Здоровья: " + root.getResources().getString(PrissonManager.BOSSES[position][3]));
        return root;
    }

    @Override
    public int getCount() {
        return PrissonManager.BOSSES.length;
    }
}
