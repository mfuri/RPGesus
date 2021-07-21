package com.cop4656.rpgesus;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.lang.*;

public class ListAdapter extends ArrayAdapter<Character> {

    private int resourceLayout;
    private Context mContext;

    public ListAdapter(Context context, int resource, List<Character> characters) {
        super(context, resource, characters);
        this.resourceLayout = resource;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }

        Character p = getItem(position);

        if (p != null) {
            TextView charName = (TextView) v.findViewById(R.id.charName);
            TextView charLevel = (TextView) v.findViewById(R.id.charLevel);
            TextView charRace = (TextView) v.findViewById(R.id.charRace);
            TextView charSkills = (TextView) v.findViewById(R.id.charSkills);
            ImageView avatar = (ImageView) v.findViewById(R.id.charAvatar);

            if (charName != null) {
                charName.setText(p.getName().trim());
            }
            if (charRace != null) {
                charRace.setText(p.getRace().trim());
            }
            if (charLevel != null) {
                charLevel.setText(String.valueOf(p.getLevel()).trim());
            }
            if (charSkills != null){
                charSkills.setText("S:" + p.getStrength() + " C:" + p.getCharisma() + " I:" + p.getIntelligence() + " L:" + p.getLuck() + " V:" + p.getVitality());
            }
            if (avatar != null){
                avatar.setImageBitmap(StringToBitMap(p.getAvatar()));
            }
        }

        return v;
    }

    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

}