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
    TextView description;
    TextView problemTextView;

    ArrayList<String> list;
    private static String deleteQuestion = "Delete Question";
    private static String deleteAnswer = "Delete Answer";
    private static String removeBestAnswer = "Remove Best Answer";
    private static String suspendUser = "Suspend User";
    Spinner decisions;
    Button takeAction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_a_report);
        reportController = new ReportController();
        list = new ArrayList<String>();
        decisions = findViewById(R.id.decisionspinner);
        description = findViewById(R.id.descriptionText);
        problemTextView = findViewById(R.id.problemText);
        takeAction = findViewById(R.id.takeActionButton);

        getDataFromIntent();

        decisions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                takeAction.setText(decisions.getSelectedItem().toString());
                loadData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /*
        decisions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
           //     takeAction.setText(decisions.getSelectedItem().toString());
            //    loadData();
            }
        });
*/
        takeAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeAction();
            }
        });

        getReport();

    }


    void getReport() {
        reportController.getReport(reportId, new OnTaskListeners.Map() {
            @Override
            public void onSuccess(String key, ArrayList<Object> list) {
                if (key == ReportController.COMMENTID)
                    questionId = (Integer) reportController.arrayToValue(list);
                else if (key == ReportController.DISCRIPTION)
                    description.setText(reportController.arrayToValue(list).toString());
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
            list.add(deleteQuestion);
            list.add(suspendUser);
            //adding the decision that you will take

        }

        else if (type.matches(report_on.misLeadingAnswer)) {
            loadAnswer();
            list.add(deleteAnswer);
            list.add(removeBestAnswer);
            list.add(suspendUser);
            //adding the decision that you will take
        }

        else if (type.matches(report_on.unApropiateAnswer)) {
            loadAnswer();
            list.add(deleteAnswer);
            list.add(suspendUser);
        }

        else if(type.matches(report_on.wrongAnswer))
        {
            loadAnswer();
            list.add(deleteAnswer);
            list.add(removeBestAnswer);
            list.add(suspendUser);

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
        if (decisions.getSelectedItem().toString().matches(deleteQuestion)) {
            reportController.deleteComment(questionId, new OnTaskListeners.Bool() {
                @Override
                public void onSuccess(Boolean result) {
                    finishActivity(result);
                }
            });
        }
        else if (decisions.getSelectedItem().toString().matches(deleteAnswer)) {
            reportController.deleteReply(replyId, new OnTaskListeners.Bool() {
                @Override
                public void onSuccess(Boolean result) {

                    finishActivity(result);
                }

            });
        }

        else if(decisions.getSelectedItem().toString().matches(removeBestAnswer))
        {
            reportController.changeBestAnswer(replyId, new OnTaskListeners.Bool() {
                @Override
                public void onSuccess(Boolean result) {
                    finishActivity(result);
                }
            });
        }

        else if(decisions.getSelectedItem().toString().matches(suspendUser))
        {
            reportController.suspendUser(replyId, new OnTaskListeners.Bool() {
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
            reportController.solveReport(reportId, new OnTaskListeners.Bool() {
                @Override
                public void onSuccess(Boolean result) {
                    reportController.toast("successfull task", getApplicationContext());
                    finish();
                }
            });

        } else {
            if (!reportController.checkConnection(getApplicationContext()))
                return;
            reportController.toast("unsuccessful task", getApplicationContext());

        }

    }


}
