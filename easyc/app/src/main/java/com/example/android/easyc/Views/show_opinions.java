package com.example.android.easyc.Views;

/**
 * Created by KhALeD SaBrY on 15-Mar-18.
 */


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.android.easyc.Controllers.OpinionController;
import com.example.android.easyc.Interfaces.OnTaskListeners;
import com.example.android.easyc.R;

import java.util.ArrayList;

public class show_opinions extends AppCompatActivity {
    OpinionController opinionController;
    ArrayList<Integer> idArrayList;
    ArrayList<String> titleArrayList;
    ListView listView;
    public  static  final  String EXTRA_DATA_ID = "ID";
    public  static  final  String EXTRA_DATA_TITLE = "TITLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showopinions);
        titleArrayList = new ArrayList<String>();
        idArrayList = new ArrayList<Integer>();
        opinionController = new OpinionController();

        listView = (ListView) findViewById(R.id.listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              openOpinion(idArrayList.get(position),titleArrayList.get(position));
    }
});

        fillList();

    }


    void fillList()
    {
        opinionController.returnTitle(new OnTaskListeners.IdAndList() {
            @Override
            public void OnSuccess(ArrayList<Integer> id, ArrayList<Object> result) {
                idArrayList = id;
                titleArrayList = (ArrayList<String>) (Object) result;
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,titleArrayList);
        listView.setAdapter(adapter);
    }



    void openOpinion(int id,String title)
    {
        Intent i;
        i = new Intent(this, show_opinion.class);
        i.putExtra(show_opinions.EXTRA_DATA_TITLE,title);
        i.putExtra(show_opinions.EXTRA_DATA_ID,id);
        startActivity(i);
    }
}

