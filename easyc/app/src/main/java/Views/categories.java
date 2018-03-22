package Views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import Controllers.CourseController;
import Interfaces.OnTaskListeners;
import com.example.android.easyc.R;

import java.util.ArrayList;

public class categories extends AppCompatActivity {

    CourseController course_controller;
    ListView categoriesList;
    ArrayList<String>  categories;
    int cat_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        categories = new ArrayList<String>();
        categoriesList = (ListView) findViewById(R.id.categories_ListView);
        course_controller =new CourseController();

        categoriesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                openTopics(categories.get(i));
            }}
        );
        fillList();
    }

    void fillList()
    {
        course_controller.getCategories(new OnTaskListeners.List() {
            @Override
            public void onSuccess(ArrayList<Object> result) {

                categories = (ArrayList<String>)(Object)result;
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(categories.this, android.R.layout.simple_selectable_list_item, categories);
                categoriesList.setAdapter(adapter);
            }
        });
    }


public void openTopics(String tilte)
{
    course_controller.getCatagoryId(tilte, new OnTaskListeners.Number() {
    @Override
    public void onSuccess(int result) {
        cat_id=result;
    }
//  get cat id and send it to topic, make list view for topics and get it from database by the at id
        //then display in topics which has arraylist<String>
});











}












}


