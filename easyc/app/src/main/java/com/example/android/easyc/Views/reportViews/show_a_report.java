package com.example.android.easyc.Views.reportViews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.easyc.Controllers.ReportController;
import com.example.android.easyc.Interfaces.OnTaskListeners;
import com.example.android.easyc.R;

import java.util.ArrayList;

public class show_a_report extends AppCompatActivity {

    Integer replyId;
    Integer questionId;
    Integer reportId;
    String type;
    ReportController reportController;
    String discription;
    TextView discriptionTextView;
    TextView problemTextView;

    ArrayList<String> list;
    private static String state = "delete question";
    private static String answer1 = "Delete Answer";
    Spinner decisions;
    Button takeAction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_a_report);
        reportController = new ReportController();
        list = new ArrayList<String>();
        decisions = findViewById(R.id.decisionspinner);
        discriptionTextView = findViewById(R.id.descriptionText);
        problemTextView = findViewById(R.id.problemText);
        takeAction = findViewById(R.id.takeActionButton);

        decisions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                takeAction.setText(decisions.getSelectedItem().toString());
                loadData();
            }
        });

        takeAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeAction();
            }
        });

        getDataFromIntent();
        getReport();

    }


    void getReport() {
        reportController.getReport(reportId, new OnTaskListeners.Map() {
            @Override
            public void onSuccess(String key, ArrayList<Object> list) {
                if (key == ReportController.COMMENTID)
                    questionId = (Integer) reportController.arrayToValue(list);
                else if (key == ReportController.DISCRIPTION)
                    discriptionTextView.setText(reportController.arrayToValue(list).toString());
                else if (key == ReportController.REPLYID)
                    replyId = (Integer) reportController.arrayToValue(list);
                else if (key == ReportController.Fininshed) {
                    loadData();
                }
            }
        });

    }


    //load data
    void loadData() {
        list.clear();
        if (type.matches(report_on.misLeadingQuestion)) {
            loadQuestion();
            list.add(state);
            //adding the decision that you will take

        }

        if (type.matches(report_on.misLeadingAnswer)) {
            loadAnswer();
            //adding the decision that you will take
        }

        if (type.matches(report_on.unApropiateAnswer)) {
            loadAnswer();
            list.add(answer1);
        }
        loadAdapter();
    }

    // load to the decision spinner
    private void loadAdapter() {
        ArrayAdapter<String> adapterList = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, list);
        decisions.setAdapter(adapterList);
    }

    //get question text
    private void loadQuestion() {
        reportController.getquestion(questionId, new OnTaskListeners.Word() {
            @Override
            public void onSuccess(String result) {
                problemTextView.setText(result);
            }
        });


    }

    //get answer text
    private void loadAnswer() {
        reportController.getAnswer(replyId, new OnTaskListeners.Word() {
            @Override
            public void onSuccess(String result) {
                problemTextView.setText(result);
            }
        });

    }


    //get data from intent
    //get type and id
    void getDataFromIntent() {
        reportId = getIntent().getIntExtra(show_reports.REPORTID, 0);
        type = getIntent().getStringExtra(show_reports.TYPE);
    }


    void takeAction() {
        if (decisions.getSelectedItem().toString().matches(state)) {
            reportController.deleteComment(questionId, new OnTaskListeners.Bool() {
                @Override
                public void onSuccess(Boolean result) {
                    finishActivity(result);
                }
            });
        } else if (decisions.getSelectedItem().toString().matches(answer1)) {
            reportController.deleteReply(replyId, new OnTaskListeners.Bool() {
                @Override
                public void onSuccess(Boolean result) {

                    finishActivity(result);
                }

            });
        }
        //   else

    }

    void finishActivity(boolean result) {
        if (result) {
            reportController.toast("successfull task", getApplicationContext());
            finish();
        } else {
            if (!reportController.checkConnection(getApplicationContext()))
                return;
            reportController.toast("unsuccessful task", getApplicationContext());

        }

    }


}
