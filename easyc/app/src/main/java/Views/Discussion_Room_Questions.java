package Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;

import com.example.android.easyc.R;

import java.util.ArrayList;

import Controllers.DiscussionController;
import Interfaces.OnTaskListeners;

public class Discussion_Room_Questions extends AppCompatActivity {
    public static String QUESTION_ID ="QUESTION_ID";
    ListView listView;
    Spinner sortSpinner;
    Spinner limitSpinner;
    Button answerButton;
    Button mineButton;
    Button asecButton;

    SearchView searchView;
    DiscussionController discussionController;
    String sortKind;
    boolean myQuestions;
    boolean isAnswered;
    boolean isAsec;
    String limitItems;
    ArrayList<Integer> questionsId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion__room__questions);
        searchView = findViewById(R.id.searchid);
        listView = findViewById(R.id.listViewid);
        sortSpinner = findViewById(R.id.sortid);
        limitSpinner = findViewById(R.id.limitId);
        answerButton = findViewById(R.id.answerid);
        mineButton = findViewById(R.id.questionid);
        asecButton = findViewById(R.id.asecid);
        questionsId = new ArrayList<Integer>();
        discussionController = new DiscussionController();
        myQuestions = false;
        isAnswered = false;
        isAsec = true;

        mineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myQuestions = !myQuestions;
                refreshView();

            }
        });

        answerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAnswered = !isAnswered;
                refreshView();
            }
        });

        asecButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAsec = !isAsec;
                refreshView();
            }
        });


        listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                goToQuestion(questionsId.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    //setup the spinner
    void initiateSpinners() {
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.sort, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(arrayAdapter);

        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sortKind = sortSpinner.getSelectedItem().toString();
                refreshView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        ArrayAdapter<CharSequence> arrayAdapter2 = ArrayAdapter.createFromResource(this, R.array.LIMIT_ITEMS, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        limitSpinner.setAdapter(arrayAdapter);

        limitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                limitItems = limitSpinner.getSelectedItem().toString();
                refreshView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    void refreshView() {
        if (myQuestions)
            mineButton.setText("My Questions");
        else
            mineButton.setText("Others Questions");

        if(isAnswered)
            answerButton.setText("Answered");
        else
            answerButton.setText("Not Answered");

        if(isAsec)
            asecButton.setText("Asec");
        else
            asecButton.setText("Desc");

        if(sortKind.matches("Name"))
        {
            discussionController.getQuestionsOrderByName(myQuestions, isAnswered, isAsec, limitItems, new OnTaskListeners.IdAndList() {
                @Override
                public void onSuccess(ArrayList<Integer> id, ArrayList<Object> result) {
                    questionsId = id;
                    ArrayList<String> titles = (ArrayList<String>) (Object) result;
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,titles);
                    listView.setAdapter(adapter);

                }
            });

        }
        else if (sortKind.matches("Date"))
        {
            discussionController.getQuestionsOrderByDate(myQuestions, isAnswered, isAsec, limitItems, new OnTaskListeners.IdAndList() {
                @Override
                public void onSuccess(ArrayList<Integer> id, ArrayList<Object> result) {
                    questionsId = id;
                    ArrayList<String> titles = (ArrayList<String>) (Object) result;
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,titles);
                    listView.setAdapter(adapter);

                }
            });
        }
    }


   void goToQuestion(int id)
    {
        Intent intent  = new Intent(getApplicationContext(),put_opinion.class);
        intent.putExtra(QUESTION_ID,id);
        startActivity(intent);

    }




    @Override
    protected void onResume()
    {
        super.onResume();
        refreshView();
    }
}
