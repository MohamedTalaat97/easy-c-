package com.example.android.easyc.Views.ExamViews;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.android.easyc.R;
import java.util.ArrayList;


public class quiz_adapter extends ArrayAdapter<quiz> {


    public quiz_adapter(Activity r, ArrayList<quiz> a) {

        super(r, 0, a);


    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {


        quiz current = getItem(position);

        View k = convertView;
        if (k == null) {

            k = LayoutInflater.from(getContext()).inflate(R.layout.quiz, parent, false);
        }

        TextView t1 = (TextView) k.findViewById(R.id.quizQuestion);


        t1.setText(current.getQuestion());

        return k;


    }

}
