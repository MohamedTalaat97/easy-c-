package com.example.android.easyc.Views;

/**
 * Created by KhALeD SaBrY on 15-Mar-18.
 */


import android.app.ActionBar;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;


import com.example.android.easyc.Controllers.OpinionController;
import com.example.android.easyc.Interfaces.OnTaskListeners;
import com.example.android.easyc.R;

import java.util.ArrayList;

public class show_opinions extends AppCompatActivity {
    OpinionController opinionController;
    ArrayList<Integer> idArrayList;
    ArrayList<String> titleArrayList;
    ArrayAdapter<CharSequence> arrayAdapter;
    String kind;
    ListView listView;
    Spinner spinner;
    public static final String EXTRA_DATA_ID = "ID";
    public static final String EXTRA_DATA_TITLE = "TITLE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showopinions);
        titleArrayList = new ArrayList<String>();
        idArrayList = new ArrayList<Integer>();
        opinionController = new OpinionController();

        spinner = findViewById(R.id.spinner);
        listView = findViewById(R.id.opinion_listview);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openOpinion(idArrayList.get(position), titleArrayList.get(position));
            }
        });

        initiateSpinner();
        fillList();

    }

    @Override
    protected void  onResume()
    {
        super.onResume();
        setSpinner();
    }

    void fillList() {
        opinionController.returnTitle(kind, new OnTaskListeners.IdAndList() {
            @Override
            public void onSuccess(ArrayList<Integer> id, ArrayList<Object> result) {
                idArrayList = id;
                titleArrayList = (ArrayList<String>) (Object) result;
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(show_opinions.this, android.R.layout.simple_selectable_list_item, titleArrayList);
                listView.setAdapter(adapter);
            }
        });


    }


    void initiateSpinner() {
        arrayAdapter = ArrayAdapter.createFromResource(this, R.array.kindOfOpinions, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                opinionController.toast(spinner.getSelectedItem().toString(),getApplication());
                setSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        kind = spinner.getSelectedItem().toString();
    }



    public void setSpinner() {
        kind = spinner.getSelectedItem().toString();
        fillList();
    }

    void openOpinion(int id, String title) {
        Intent i;
        i = new Intent(this, show_opinion.class);
        i.putExtra(show_opinions.EXTRA_DATA_TITLE, title);
        i.putExtra(show_opinions.EXTRA_DATA_ID, id);
        startActivity(i);
    }
}

