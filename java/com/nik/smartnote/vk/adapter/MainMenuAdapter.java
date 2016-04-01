package com.nik.smartnote.vk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nik.smartnote.vk.R;

/**
 * Created by Николай on 16.02.2016.
 */
public class MainMenuAdapter extends ArrayAdapter<String> {

    int[] image = {
            R.drawable.ic_action_warning,
            R.drawable.ic_action_dropbox,
            R.drawable.ic_action_emo_tongue,
            R.drawable.ic_action_users,
            R.drawable.ic_action_coffee,
            R.drawable.ic_action_document,
            R.drawable.ic_action_achievement,
            R.drawable.ic_action_gear
    };


    public MainMenuAdapter(Context context, String[] array) {
        super(context, R.layout.main_menu_item, array);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View root;

        root = LayoutInflater.from(getContext()).inflate(R.layout.main_menu_item, parent, false);

        TextView textView = (TextView) root.findViewById(R.id.text_list);
        textView.setText(getItem(position));
        ImageView imageView = (ImageView) root.findViewById(R.id.image_list);
        imageView.setImageResource(image[position]);

        return root;
    }
}
