package com.example.android.easyc.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.android.easyc.R;

/**
 * Created by MAN CENTER on 13-Apr-18.
 */

public class GridAdapter extends BaseAdapter {

    Context context;
    final Integer[] images;
    View view;
    LayoutInflater layoutInflater;


    public GridAdapter(Context context, Integer[] images) {
        this.context = context;
        this.images = images;


    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {

            view = new View(context);
            view = layoutInflater.inflate(R.layout.single_item, null);
            ImageView i = (ImageView) view.findViewById(R.id.grid_image);
            i.setImageResource(images[position]);

        }
        return view;
    }
}
