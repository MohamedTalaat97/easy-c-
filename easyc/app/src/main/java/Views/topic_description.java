package Views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.easyc.R;

public class topic_description extends AppCompatActivity {


    String description;
    String code;
    String output;
    int topicId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_description);
        topicId= getIntent().getIntExtra("topicId", 1);
        // select el 4r7 using topic id and put in the view
    }
}
