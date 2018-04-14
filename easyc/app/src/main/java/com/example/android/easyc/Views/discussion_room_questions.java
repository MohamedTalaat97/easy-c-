package com.example.android.easyc.Views;

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

import com.example.android.easyc.Controllers.DiscussionController;
import com.example.android.easyc.Interfaces.OnTaskListeners;
import com.example.android.easyc.R;

import java.util.ArrayList;

public class discussion_room_questions extends AppCompatActivity {
    public static String QUESTION_ID ="QUESTION_ID";
    public static String MY_QUESTION ="MY_QUESTION";


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
        isAsec = false;

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


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchInDatabase(newText);

                return false;
            }
        });
        initiateSpinners();
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
        limitSpinner.setAdapter(arrayAdapter2);

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
       // sortSpinner.setSelection(1);
     sortKind =   sortSpinner.getSelectedItem().toString();
     limitItems = limitSpinner.getSelectedItem().toString();

    }

    //refresh the view every time you change the parameters
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
                    updateListView(id,result);


                }
            });

        }
        else if (sortKind.matches("Date"))
        {
            discussionController.getQuestionsOrderByDate(myQuestions, isAnswered, isAsec, limitItems, new OnTaskListeners.IdAndList() {
                @Override
                public void onSuccess(ArrayList<Integer> id, ArrayList<Object> result) {
                    updateListView(id,result);


                }
            });
        }
    }


    //go to see specific question
   void goToQuestion(int id)
    {
        Intent intent  = new Intent(getApplicationContext(),put_opinion.class);
        intent.putExtra(QUESTION_ID,id);
        intent.putExtra(MY_QUESTION,myQuestions);
        startActivity(intent);

    }


    //search in database about specific topic questions
    void searchInDatabase(String searchTitle)
    {
        if(sortKind.matches("Name"))
        {
            discussionController.searchTitleOrderByName(searchTitle,myQuestions, isAnswered, isAsec, limitItems, new OnTaskListeners.IdAndList() {
                @Override
                public void onSuccess(ArrayList<Integer> id, ArrayList<Object> result) {
                    updateListView(id,result);


                }
            });

        }
        else if (sortKind.matches("Date"))
        {
            discussionController.searchTitleOrderByDate(searchTitle,myQuestions, isAnswered, isAsec, limitItems, new OnTaskListeners.IdAndList() {
                @Override
                public void onSuccess(ArrayList<Integer> id, ArrayList<Object> result) {
                    updateListView(id,result);

                }
            });
        }
    }




    @Override
    public void onBackPressed(){
        super.onBackPressed();
        //Add the OnBackPressed into Other activity when the BackPressed
        overridePendingTransition(R.anim.godown, R.anim.godown);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        refreshView();
    }

    //update the list view every time you change one of the parameters
    void updateListView(ArrayList<Integer> ids,ArrayList<Object> result)
    {
        if(ids == null || result == null)
            listView.setAdapter(null);
        else
        questionsId = ids;
        ArrayList<String> titles = (ArrayList<String>) (Object) result;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,titles);
        listView.setAdapter(adapter);
    }
}
