package Views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.android.easyc.R;

import Controllers.CourseController;
import Interfaces.OnTaskListeners;

public class topic_description extends AppCompatActivity {


    CourseController courseController;
    String description;
    String code;
    String output;
    int topicId;
    TextView description_textView;
    TextView code_textView;
    TextView output_textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_description);
        courseController = new CourseController();
        description_textView = findViewById(R.id.TV_description);
        code_textView = findViewById(R.id.TV_code);
        output_textView = findViewById(R.id.TV_output);

        topicId = getIntent().getIntExtra(topic.TOPIC_ID, 1);

        fillData();

    }

    //fill the topic data on the screen
    public void fillData() {

        courseController.getDescription(topicId, new OnTaskListeners.Word() {
            @Override
            public void onSuccess(String result) {
                description = result;
                description_textView.setText(description);
            }
        });

        courseController.getCode(topicId, new OnTaskListeners.Word() {
            @Override
            public void onSuccess(String result) {
                code = result;
                code_textView.setText(code);
            }
        });
        courseController.getOutput(topicId, new OnTaskListeners.Word() {
            @Override
            public void onSuccess(String result) {
                output = result;
                output_textView.setText(output);
            }
        });

    }
}
