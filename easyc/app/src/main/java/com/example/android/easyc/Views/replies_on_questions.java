package com.example.android.easyc.Views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.easyc.Controllers.DiscussionController;
import com.example.android.easyc.Interfaces.OnTaskListeners;
import com.example.android.easyc.R;

import java.util.ArrayList;

public class replies_on_questions extends AppCompatActivity {

    int question_id;
    int user_id;
    ArrayList<Integer> replyIds;
    ArrayList<String> replies;



    ListView listView;
    RelativeLayout.LayoutParams userNameLayout ;
    RelativeLayout.LayoutParams replyLayout;
    ListView.LayoutParams viewLayout;
    ArrayList<String> userNames;

    DiscussionController discussionController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replies_on_questions);
        discussionController = new DiscussionController();
        question_id = getIntent().getIntExtra(discussion_room_questions.QUESTION_ID,0);

         userNameLayout = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
         replyLayout= new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
         viewLayout = new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT, ListView.LayoutParams.WRAP_CONTENT);




        adjustView();
        fillList();
    }


    void fillList()
    {
        discussionController.getRepliesIdUserNameContent(question_id, new OnTaskListeners.ThreeList() {
            @Override
            public void onSuccess(ArrayList<Object> list1, ArrayList<Object> list2, ArrayList<Object> list3) {
                if(list1.isEmpty())
                    return;
                replyIds = (ArrayList<Integer>) (Object) list1;
                userNames = (ArrayList<String>)(Object) list2;
                replies = (ArrayList<String>) (Object) list3;
                addReplies();
            }
        });

    }

    void adjustView()
    {
        userNameLayout.addRule(RelativeLayout.ALIGN_LEFT);
        replyLayout.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

        replyLayout.setMargins(66,200,66,0);


    }


    void addReplies()
    {
        for(int i = 0; i < replyIds.size();i++)
        {
            RelativeLayout relativeLayout = new RelativeLayout(getApplicationContext());
            TextView textView1 = new TextView(getApplicationContext());
            TextView textView2 = new TextView(getApplicationContext());
            textView1.setLayoutParams(userNameLayout);
            textView2.setLayoutParams(replyLayout);
            relativeLayout.setLayoutParams(viewLayout);

            textView1.setText(userNames.get(i).toString());
            textView2.setText(replies.get(i).toString());

            relativeLayout.addView(textView1);
            relativeLayout.addView(textView2);
            listView.addView(relativeLayout,i+1);
        }
    }


}
