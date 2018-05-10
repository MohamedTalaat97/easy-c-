package com.example.android.easyc.Views.DiscussionViews;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.android.easyc.Models.UserData;
import com.example.android.easyc.R;

import java.util.ArrayList;

public class repliesAdapter extends RecyclerView.Adapter<repliesAdapter.ViewHolder> {
    private ArrayList<ListItems> listItems;
    private Context context;
    private UserData userData;
    private boolean isMyQuestion;
    private replies_on_questions repliesOnQuestions;
    public String title;



    private Color bestAnswerColor;
    private Color myReplyColor;
    private Color othersReplyColor;
    private Color questionReplyColor;
    private Color usernameColor;

    public repliesAdapter(ArrayList<ListItems> listItems, Context context, replies_on_questions repliesOnQuestions, String title) {
        this.listItems = listItems;
        this.context = context;
        userData = UserData.getInstance();
        this.title = title;
        this.repliesOnQuestions = repliesOnQuestions;
        if (listItems.get(0).getUserId() == userData.getUserId())
            isMyQuestion = true;
        else
            isMyQuestion = false;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_card_view_replies, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        final ListItems listItem = listItems.get(position);
        //if this item is the question
        if (position ==0) {
            //remove best answer button from it
            viewHolder.bestAnswer.setVisibility(View.INVISIBLE);
            //change the background
            //viewHolder.userName.setBackgroundColor(R.drawable.o24);
            //change the direction of the layout
          //  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            //    viewHolder.cardView.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
          //  }

            //put the title of the question
        } else {
            //if the answer is mine
            //change background
            if (userData.getUserId() == listItem.getUserId()) {
                viewHolder.userName.setBackgroundColor(Color.BLUE);
                viewHolder.bestAnswer.setVisibility(View.INVISIBLE);

            }

            //if it is not question so remove the title
            viewHolder.title.setVisibility(View.INVISIBLE);
            //if its the best answer then change the background
            if(listItem.isBestAnswer()) {
                viewHolder.userName.setBackgroundColor(Color.RED);
                viewHolder.bestAnswer.setVisibility(View.INVISIBLE);
            }

            //remove the best answer button if it's not my question
            if (!isMyQuestion) {
                viewHolder.bestAnswer.setVisibility(View.INVISIBLE);
            }


        }



        viewHolder.title.setText(title);
        YoYo.with(Techniques.FadeIn).playOn(viewHolder.cardView);
        viewHolder.userName.setText(listItem.getUsername());
        viewHolder.content.setText(listItem.getContent());
        viewHolder.bestAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repliesOnQuestions.putBestAnswer(listItems.get(position).getReplyId());

            }
        });

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(context,position,Toast.LENGTH_SHORT).show();
                System.out.print(position);
            }
        });

        viewHolder.report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listItems.get(0) == listItems.get(position))
                {
                    repliesOnQuestions.makeReport(null,listItems.get(0).replyId);
                }
                else
                {
                    repliesOnQuestions.makeReport(listItems.get(position).replyId,listItems.get(0).replyId);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView userName;
        public TextView content;
        public Button bestAnswer;
      //  public RelativeLayout relativeLayout;
        public CardView cardView;
        public TextView title;
        public Button report;

        public ViewHolder(View itemView) {
            super(itemView);


            userName = itemView.findViewById(R.id.userNameID);
            content = itemView.findViewById(R.id.Description);
            bestAnswer = itemView.findViewById(R.id.bestAnswerID);
            cardView = itemView.findViewById(R.id.replayCardViewID);
            title = itemView.findViewById(R.id.titlequestionid);
            report = itemView.findViewById(R.id.reportID);
        }
    }
}