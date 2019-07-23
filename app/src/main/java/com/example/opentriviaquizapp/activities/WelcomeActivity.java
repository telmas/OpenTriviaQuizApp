package com.example.opentriviaquizapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.opentriviaquizapp.system.DBHelper;
import com.example.opentriviaquizapp.R;
import com.example.opentriviaquizapp.system.SystemController;

public class WelcomeActivity extends AppCompatActivity {

    Button proceedButton;
    EditText nameEditText;
    ProgressBar progressBar;

    DBHelper dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        dataBase = new DBHelper(this);

        proceedButton = (Button) findViewById(R.id.proceedButton);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String name = nameEditText.getText().toString().trim().toUpperCase();

                if (name.isEmpty()) {
                    nameEditText.setError("Name is required");
                    nameEditText.requestFocus();
                    progressBar.setVisibility(View.GONE);
                    return;
                }


                if(!dataBase.userExists(name)) {
                    dataBase.createUser(name);
                }
                Intent intent = new Intent(WelcomeActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                SystemController.getINSTANCE().setUserName(name);

                progressBar.setVisibility(View.GONE);
            }
        });
    }
}



