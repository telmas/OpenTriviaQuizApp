package com.example.opentriviaquizapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class WelcomeActivity extends AppCompatActivity {


    private RequestQueue mQueue;

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


        mQueue = Volley.newRequestQueue(this);

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

                if(!dataBase.userExists(name)){
                    dataBase.createUser(name);
                    Toast.makeText(getApplicationContext(), "User Created", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "User Exists", Toast.LENGTH_SHORT).show();

                }
                progressBar.setVisibility(View.GONE);
                jsonParse();
            }
        });
    }

    private void jsonParse() {

        String url = "https://opentdb.com/api_category.php";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("trivia_categories");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                int id = jsonObject.getInt("id");
                                String categoryName = jsonObject.getString("name");
                                Category category = new Category();
                                category.setId(id);
                                category.setName(categoryName);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }
}



