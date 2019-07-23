package com.example.opentriviaquizapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.opentriviaquizapp.R;
import com.example.opentriviaquizapp.system.SystemController;

import java.util.ArrayList;

public class ChooseCategoryActivity extends AppCompatActivity {

    ArrayList<String> categoryStringArrayList;
    ArrayList<Integer> categoryIntegerArrayList;
    ListView categoriesListView;

    ProgressBar category_loading_indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_category);

        category_loading_indicator = (ProgressBar) findViewById(R.id.category_loading_indicator);
        category_loading_indicator.setVisibility(View.VISIBLE);
        categoryStringArrayList = new ArrayList<>();
        categoryIntegerArrayList = new ArrayList<>();

        Intent intent = getIntent();

        categoryStringArrayList = SystemController.getINSTANCE().getCategoryStringArrayList();
        categoryIntegerArrayList = SystemController.getINSTANCE().getCategoryIntegerArrayList();

        setupListView();
    }

    private void setupListView(){
        categoriesListView = (ListView) findViewById(R.id.listCategories);
        ListAdapter myAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, categoryStringArrayList);
        categoriesListView.setAdapter(myAdapter);
        categoriesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int categoryID = categoryIntegerArrayList.get(position);
                SystemController.getINSTANCE().setCategoryID(categoryID);
                Intent intent = new Intent(ChooseCategoryActivity.this, ChooseDifficultyActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        category_loading_indicator.setVisibility(View.INVISIBLE);
    }
}
