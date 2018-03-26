package Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.android.easyc.R;

import java.util.ArrayList;

import Controllers.CourseController;
import Interfaces.OnTaskListeners;

public class topic extends AppCompatActivity {

    int CatId;
    int topicId;
    CourseController courseController;
    ListView topicsListView;
    ArrayList<String> topicsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        CatId = getIntent().getIntExtra("catId", 1);
        courseController = new CourseController();
        topicsListView = findViewById(R.id.topics_ListView);
        topicsList = new ArrayList<String>();
        topicsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                openTopicDescription(topicsList.get(i));
            }}
        );

        fillList();
    }

    void fillList() {
        courseController.getTopics(CatId, new OnTaskListeners.List() {
            @Override
            public void onSuccess(ArrayList<Object> result) {

                topicsList = (ArrayList<String>) (Object) result;
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(topic.this, android.R.layout.simple_selectable_list_item, topicsList);
                topicsListView.setAdapter(adapter);
            }
        });
    }


    public void openTopicDescription(String tilte) {
        courseController.getTopicId(tilte, new OnTaskListeners.Number() {
            @Override
            public void onSuccess(int result) {
                topicId = result;
                Intent i = new Intent(topic.this, topic_description.class);
                i.putExtra("topicId", topicId);
                startActivity(i);
            }

        });

    }
}

