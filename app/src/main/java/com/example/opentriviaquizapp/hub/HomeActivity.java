package com.example.opentriviaquizapp.hub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;


import com.example.opentriviaquizapp.R;
import com.example.opentriviaquizapp.quizconfig.ChooseCategoryActivity;
import com.example.opentriviaquizapp.userprofile.UserProfileActivity;


public class HomeActivity extends AppCompatActivity {
    Button beginQuiz;
    Button userProfile;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        beginQuiz=findViewById(R.id.beginQuiz);
        userProfile=findViewById(R.id.userProfile);
        progressBar=findViewById(R.id.progressBar2);


        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, UserProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        beginQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ChooseCategoryActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }


}



