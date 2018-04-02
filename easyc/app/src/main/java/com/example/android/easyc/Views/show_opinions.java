package com.example.android.easyc.Views;

/**
 * Created by KhALeD SaBrY on 15-Mar-18.
 */


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.android.easyc.R;

import java.util.ArrayList;

import com.example.android.easyc.Controllers.OpinionController;
import com.example.android.easyc.Interfaces.OnTaskListeners;

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
                goToOpinion(idArrayList.get(position), titleArrayList.get(position));
            }
        });

        initiateSpinner();
        setSpinner();

    }

    //when you come back fron the opinion make sure to update the list
    @Override
    protected void onResume() {
        super.onResume();
        setSpinner();
    }

    //fill the opinion list
    void fillList() {
        if (kind.matches("UnSeen"))
            getUnSeen();
        else if (kind.matches("UnRead"))
            getUnRead();
        else if (kind.matches("Favourite"))
            getFavourite();
        else
            getAll();
    }

    //get unreaded opinions
    void getUnRead() {
        opinionController.getUnReadOpinions(new OnTaskListeners.IdAndList() {
            @Override
            public void onSuccess(ArrayList<Integer> id, ArrayList<Object> result) {
                idArrayList = id;
                titleArrayList = (ArrayList<String>) (Object) result;
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(show_opinions.this, android.R.layout.simple_selectable_list_item, titleArrayList);
                listView.setAdapter(adapter);
            }
        });

    }

    //get unseen opinions
    void getUnSeen() {
        opinionController.getUnSeenOpinions(new OnTaskListeners.IdAndList() {
            @Override
            public void onSuccess(ArrayList<Integer> id, ArrayList<Object> result) {
                idArrayList = id;
                titleArrayList = (ArrayList<String>) (Object) result;
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(show_opinions.this, android.R.layout.simple_selectable_list_item, titleArrayList);
                listView.setAdapter(adapter);
            }
        });

    }

    //get favourite opinions
    void getFavourite() {
        opinionController.getFavouriteOpinions(new OnTaskListeners.IdAndList() {
            @Override
            public void onSuccess(ArrayList<Integer> id, ArrayList<Object> result) {
                idArrayList = id;
                titleArrayList = (ArrayList<String>) (Object) result;
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(show_opinions.this, android.R.layout.simple_selectable_list_item, titleArrayList);
                listView.setAdapter(adapter);
            }
        });

    }

    //get All opinions
    void getAll() {

        opinionController.getAllOpinions(new OnTaskListeners.IdAndList() {
            @Override
            public void onSuccess(ArrayList<Integer> id, ArrayList<Object> result) {
                idArrayList = id;
                titleArrayList = (ArrayList<String>) (Object) result;
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(show_opinions.this, android.R.layout.simple_selectable_list_item, titleArrayList);
                listView.setAdapter(adapter);
            }
        });

    }


    //setup the spinner
    void initiateSpinner() {
        arrayAdapter = ArrayAdapter.createFromResource(this, R.array.kindOfOpinions, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                opinionController.toast(spinner.getSelectedItem().toString(), getApplication());
                setSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    //update the list according to the spinner state
    public void setSpinner() {
        kind = spinner.getSelectedItem().toString();
        fillList();
    }

    //open the opinion
    void goToOpinion(int id, String title) {
        Intent i = new Intent(getApplicationContext(), show_opinion.class);
        i.putExtra(show_opinions.EXTRA_DATA_TITLE, title);
        i.putExtra(show_opinions.EXTRA_DATA_ID, id);
        startActivity(i);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        //Add the OnBackPressed into Other activity when the BackPressed
        overridePendingTransition(R.anim.godown, R.anim.godown);
    }
}

