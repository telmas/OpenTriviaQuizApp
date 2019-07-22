package com.example.opentriviaquizapp.activities;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.opentriviaquizapp.R;

public class ViewSolutionsActivity extends AppCompatActivity {

    ListView listNews;
    ProgressBar loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_solutions);


        listNews = (ListView) findViewById(R.id.listSolutions);
        loader = (ProgressBar) findViewById(R.id.solutions_loading_indicator);
        listNews.setEmptyView(loader);

        SolutionListAdapter adapter = new SolutionListAdapter(this);

        listNews.setAdapter(adapter);
    }
}
