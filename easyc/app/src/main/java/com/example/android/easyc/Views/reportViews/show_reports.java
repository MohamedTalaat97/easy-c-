package com.example.android.easyc.Views.reportViews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.android.easyc.Controllers.ReportController;
import com.example.android.easyc.Interfaces.OnTaskListeners;
import com.example.android.easyc.R;

import java.util.ArrayList;

public class show_reports extends AppCompatActivity {

    public static String REPORTID = "REPORTID";
    public static String TYPE = "TYPES";
    ArrayList<Integer> idArrayList;
    ArrayList<String> titleArrayList;
    ListView listView;
    ReportController reportController;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_reports);
        titleArrayList = new ArrayList<String>();
        idArrayList = new ArrayList<Integer>();
        spinner = findViewById(R.id.reportsTypes);
        reportController = new ReportController();
        listView = findViewById(R.id.reports_listview);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goToReport(idArrayList.get(position));
            }
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                reportController.toast(spinner.getSelectedItem().toString(), getApplication());
                fillList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        initiateSpinner();
       // fillList();
    }


    //fill the report list
    void fillList() {
        listView.setAdapter(null);
        reportController.getReports(spinner.getSelectedItem().toString(), new OnTaskListeners.Map() {
            @Override
            public void onSuccess(String key, ArrayList<Object> list) {
                if (key.matches(ReportController.DISCRIPTION)) {
                    titleArrayList = (ArrayList<String>) (Object) list;
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_selectable_list_item, titleArrayList);
                    listView.setAdapter(adapter);
                } else if (key.matches(ReportController.REPORTID)) {
                    idArrayList = (ArrayList<Integer>) (Object) list;
                }
            }
        });

    }

    //setup the spinner
    void initiateSpinner() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, report_on.reportsKinds());

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

    }


    //open the report
    void goToReport(int id) {
        Intent i = new Intent(getApplicationContext(), show_a_report.class);
        i.putExtra(show_reports.REPORTID, id);
        i.putExtra(show_reports.TYPE,spinner.getSelectedItem().toString());
        startActivity(i);
    }


    @Override
    protected void onResume() {
        super.onResume();
        fillList();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Add the OnBackPressed into Other activity when the BackPressed
        overridePendingTransition(R.anim.godown, R.anim.godown);
    }
}
