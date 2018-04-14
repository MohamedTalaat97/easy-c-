package com.example.android.easyc.Views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.easyc.Controllers.DiscussionController;
import com.example.android.easyc.Interfaces.OnTaskListeners;
import com.example.android.easyc.R;

import java.util.ArrayList;

public class replies_on_questions extends AppCompatActivity {

    int question_id;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager manager;


    ArrayList<Integer> replyIds;
    ArrayList<String> userNames;
    ArrayList<String> contents;
    ArrayList<Integer> userIds;
    ArrayList<Boolean> bestAnswer;


  //  String content;
    String title;


    boolean isMyQuestion;

    ArrayList<ListItems> listItems;

    DiscussionController discussionController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replies_on_questions);
        discussionController = new DiscussionController();
        question_id = getIntent().getIntExtra(discussion_room_questions.QUESTION_ID, 0);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);


        listItems = new ArrayList<ListItems>();

        replyIds = new ArrayList<Integer>();
        userIds = new ArrayList<Integer>();
        userNames = new ArrayList<String>();
        contents = new ArrayList<String>();


fillList();
    }


    void fillList() {
        replyIds.clear();
        userIds.clear();
        userNames.clear();
        contents.clear();
        bestAnswer.clear();

        discussionController.getQuestion(question_id, new OnTaskListeners.Map() {
            @Override
            public void onSuccess(String key, ArrayList<Object> list) {
                if (key.matches(DiscussionController.USERID))
                    userIds.addAll((ArrayList<Integer>) (Object) list);
                else if (key.matches(DiscussionController.TITLE))
                    title = (String) list.get(0);
                else if (key.matches(DiscussionController.USERNAME))
                    userNames.addAll((ArrayList<String>) (Object) list);
                else if (key.matches(DiscussionController.CONTENT))
                    contents.addAll((ArrayList<String>) (Object) list);
                else if (key.matches(DiscussionController.BEST_ANSWER))
                    bestAnswer.addAll((ArrayList<Boolean>) (Object) list);
                else if (key.matches(DiscussionController.FINISHED)) {
                    replyIds.add(question_id);
                    discussionController.getReplies(question_id, new OnTaskListeners.Map() {
                        @Override
                        public void onSuccess(String key, ArrayList<Object> list) {
                            if (key.matches(DiscussionController.REPLYID))
                                replyIds.addAll((ArrayList<Integer>) (Object) list);
                            else if (key.matches(DiscussionController.USERID))
                                userIds.addAll((ArrayList<Integer>) (Object) list);
                            else if (key.matches(DiscussionController.USERNAME))
                                userNames.addAll((ArrayList<String>) (Object) list);
                            else if (key.matches(DiscussionController.CONTENT))
                                contents.addAll((ArrayList<String>) (Object) list);
                            else if (key.matches(DiscussionController.BEST_ANSWER))
                                bestAnswer.addAll((ArrayList<Boolean>) (Object) list);
                            else if (key.matches(DiscussionController.FINISHED))
                                loadAdapter();


                        }
                    });
                }


            }
        });

    }


    void loadAdapter() {
        for (int i = 0; i < replyIds.size(); i++) {
            ListItems listItem = new ListItems(userNames.get(i), contents.get(i), userIds.get(i), replyIds.get(i),bestAnswer.get(i));
            listItems.add(listItem);
        }

        adapter = new cardViewAdapter(listItems, getApplicationContext(), this, title);
        recyclerView.setAdapter(adapter);
    }



    public void putBestAnswer(int replyId)
    {
        discussionController.updateReplyBestAnswer(replyId, true, new OnTaskListeners.Bool() {
            @Override
            public void onSuccess(Boolean result) {
                if(result)
                {
                    discussionController.toast("successfully updated",getApplicationContext());
                    fillList();
                }
                else
                {
                    if(discussionController.checkConnection(getApplicationContext()))
                        return;

                    discussionController.toast("unsuccessfully updated",getApplicationContext());

                }
            }
        });
    }
}