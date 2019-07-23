package com.example.opentriviaquizapp.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.opentriviaquizapp.R;

public class ViewSolutionsActivity extends AppCompatActivity {

    Button button;
    ListView listNews;
    ProgressBar loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_solutions);


        listNews = (ListView) findViewById(R.id.listSolutions);
        loader = (ProgressBar) findViewById(R.id.solutions_loading_indicator);
        listNews.setEmptyView(loader);

        button = (Button) findViewById(R.id.buttonReturnHome);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewSolutionsActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        SolutionListAdapter adapter = new SolutionListAdapter(this);

        listNews.setAdapter(adapter);
    }
}
