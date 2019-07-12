package com.example.opentriviaquizapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ChooseDifficultyActivity extends AppCompatActivity {

    ListView difficultiesListView;
    Button beginQuizButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_difficulty);
        String[] levels = {"Easy", "Medium", "Hard"};
        difficultiesListView = (ListView) findViewById(R.id.listDifficulties);
        ListAdapter myAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, levels);
        difficultiesListView.setAdapter(myAdapter);
        difficultiesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String difficulty = String.valueOf(parent.getItemAtPosition(position));
                SystemController.getINSTANCE().setDifficulty(difficulty.toLowerCase());
                Toast.makeText(getApplicationContext(), "Difficulty: " + difficulty, Toast.LENGTH_SHORT).show();
            }
        });
        beginQuizButton = (Button) findViewById(R.id.buttonBeginQuiz);
    }
}


