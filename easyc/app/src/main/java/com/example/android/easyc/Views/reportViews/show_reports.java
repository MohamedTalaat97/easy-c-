package com.example.android.easyc.Views.reportViews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Spinner;

import com.example.android.easyc.R;

public class show_reports extends AppCompatActivity {

    public static String REPLYID = "REPLYSID";
    public static String TYPE = "TYPES";

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_reports);
    }
}
