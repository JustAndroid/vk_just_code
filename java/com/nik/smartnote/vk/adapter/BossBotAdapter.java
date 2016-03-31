package com.nik.smartnote.vk.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;

/**
 * Created by mytrofanenkova-io on 31.03.2016.
 */
public class BossBotAdapter extends ArrayAdapter<String> {
    public BossBotAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getPosition(String item) {
        return super.getPosition(item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}
