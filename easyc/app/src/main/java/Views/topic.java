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
    public static String TOPIC_ID = "TOPICID";


    //initialization
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        CatId = getIntent().getIntExtra(categories.CAT_ID, 1);
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

    //fill the list of topics related to the category specified
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


    //open the selected topic
    public void openTopicDescription(String title) {
        courseController.getTopicId(title, new OnTaskListeners.Number() {
            @Override
            public void onSuccess(int result) {
                topicId = result;
                Intent i = new Intent(getApplicationContext(), topic_description.class);
                i.putExtra(TOPIC_ID, topicId);
                startActivity(i);
            }

        });

    }
}

