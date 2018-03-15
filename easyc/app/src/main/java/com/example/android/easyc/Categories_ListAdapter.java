package com.example.android.easyc;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by MAN CENTER on 15-Mar-18.
 */

public class Categories_ListAdapter extends ArrayAdapter<String>{

    public Categories_ListAdapter(Activity r, ArrayList<String> a) {

        super(r, 0, a);

    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {


       String  current = getItem(position);

        View k = convertView;
        if (k == null) {

            k = LayoutInflater.from(getContext()).inflate(R.layout.block, parent, false);
        }

        TextView t1 = (TextView) k.findViewById(R.id.simpleTextView1);
        TextView t2 = (TextView) k.findViewById(R.id.simpleTextView2);
        TextView t3 = (TextView) k.findViewById(R.id.simpleTextView3);

        t1.setText(current.s1);
        t2.setText(current.s2);
        t3.setText(current.s3);
        return k;


    }


}
