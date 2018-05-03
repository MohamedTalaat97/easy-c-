package com.example.android.easyc.Views.reportViews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.easyc.Controllers.ReportController;
import com.example.android.easyc.Interfaces.OnTaskListeners;
import com.example.android.easyc.R;
import com.example.android.easyc.Views.DiscussionViews.replies_on_questions;

import java.util.ArrayList;

public class report_on extends AppCompatActivity {

    Spinner reportType;
    TextView reportComment;
    Button reportButton;
    ReportController reportController;
    Integer replyId;
    Integer questionId;

    public static final String misLeadingQuestion = "misLeading question";
    public static final String misLeadingAnswer = "misLeading answer";

    public static final String unApropiateAnswer = "UnAppropriate Answer";

    public static final String wrongAnswer = "Wrong Answer";



    public static final ArrayList<String> reportTypes  = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_on_question);
        reportController = new ReportController();
        reportType = findViewById(R.id.reportTypeId);
        reportComment = findViewById(R.id.reportdiscription);
        reportButton = findViewById(R.id.makereportid);

reportTypes.add(wrongAnswer);

        ArrayList<String> list = new ArrayList<>();
        list.add(misLeadingQuestion);
        list.add(misLeadingAnswer);
        list.add(unApropiateAnswer);
        list.add(wrongAnswer);

        ArrayAdapter<String> adapterList = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,list);
        reportType.setAdapter(adapterList);

        getDataFromIntent();

        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putReport();
            }
        });


    }

    void putReport() {
        if (!check())
            return;

        reportController.putReport(questionId, replyId, reportComment.getText().toString(), reportType.getSelectedItem().toString(), new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {
                if(result)
                {
                    reportController.toast("successfully reported",getApplicationContext());
                    finish();
                }
                else
                {
                    if(!reportController.checkConnection(getApplicationContext()))
                        return;
                    reportController.toast("unsuccessfully reported",getApplicationContext());

                }
            }
        });

    }

    boolean check() {
        if (reportComment.getText().toString().length() <= 0) {
            reportController.toast("you didn't enter any discription about this report", getApplicationContext());
            return false;
        }
        return true;
    }

    void getDataFromIntent()
    {
        questionId = getIntent().getIntExtra(replies_on_questions.QUESTIONID,0);
        replyId = getIntent().getIntExtra(replies_on_questions.REPLYID,0);

    }



    public static   ArrayList<String> reportsKinds()
    {
        ArrayList<String> kinds = new ArrayList<String>();
        kinds.add(report_on.misLeadingAnswer);
        kinds.add(report_on.misLeadingQuestion);
        kinds.add(report_on.unApropiateAnswer);
        kinds.add(report_on.wrongAnswer);
        return  kinds;

    }
}
