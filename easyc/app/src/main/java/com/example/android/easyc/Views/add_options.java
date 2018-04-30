package com.example.android.easyc.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android.easyc.R;

public class add_options extends AppCompatActivity {



    Button addCategory;
    Button addTopic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_options);
        addCategory = findViewById(R.id.add_cat);
        addTopic = findViewById(R.id.add_topic);

        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo(add_category.class);
            }
        });
        addTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo(add_topic.class);
            }
        });

    }

    void goTo(Class c) {
        Intent intent = new Intent(getApplicationContext(), c);
        startActivity(intent);
        // overridePendingTransition(R.anim.goup, R.anim.godown);
    }
}
