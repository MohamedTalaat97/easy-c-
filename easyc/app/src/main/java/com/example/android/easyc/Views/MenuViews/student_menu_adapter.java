//package com.example.android.easyc.Views;
//
//import android.content.Context;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//
//import com.example.android.easyc.R;
//
//
//public class student_menu_adapter extends BaseAdapter {
//    private Context mContext;
//
//
//    public student_menu_adapter(Context c) {
//        mContext = c;
//    }
//
//    public int getCount() {
//        return mThumbIds.length;
//    }
//
//    public Object getItem(int position) {
//        return mThumbIds[position];
//    }
//
//    public long getItemId(int position) {
//        return position;
//    }
//
//    // create a new ImageView for each item referenced by the Adapter
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ImageView imageView;
//        if (convertView == null) {
//            // if it's not recycled, initialize some attributes
//            imageView = new ImageView(mContext);
//            imageView.setLayoutParams(new ViewGroup.LayoutParams(100, 80));
//            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
//            imageView.setPadding(8, 8, 8, 8);
//        } else {
//            imageView = (ImageView) convertView;
//        }
//
//        imageView.setImageResource(mThumbIds[position]);
//        return imageView;
//    }
//
//    // references to menu images
//
//}
