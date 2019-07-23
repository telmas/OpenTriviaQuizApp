package com.example.opentriviaquizapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.opentriviaquizapp.system.DBHelper;
import com.example.opentriviaquizapp.R;
import com.example.opentriviaquizapp.system.SystemController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class WelcomeActivity extends AppCompatActivity {

    private RequestQueue mQueue;
    ArrayList<String> categoryStringArrayList;
    ArrayList<Integer> categoryIntegerArrayList;

    Button proceedButton;
    EditText nameEditText;
    ProgressBar progressBar;

    DBHelper dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        categoryStringArrayList = new ArrayList<>();
        categoryIntegerArrayList = new ArrayList<>();

        mQueue = Volley.newRequestQueue(this);
        jsonParse();
    }

    private void setup(){

        dataBase = new DBHelper(this);

        proceedButton = (Button) findViewById(R.id.proceedButton);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

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

                SystemController.getINSTANCE().setUserName(name);
                progressBar.setVisibility(View.GONE);
                SystemController.getINSTANCE().setCategoryStringArrayList(categoryStringArrayList);
                SystemController.getINSTANCE().setCategoryIntegerArrayList(categoryIntegerArrayList);

                Intent intent = new Intent(WelcomeActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void jsonParse() {
        String url = "https://opentdb.com/api_category.php";
        final HashMap<Integer, String> map = new HashMap<>();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("trivia_categories");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                int categoryId = jsonObject.getInt("id");
                                String categoryName = jsonObject.getString("name");
                                categoryIntegerArrayList.add(categoryId);
                                categoryStringArrayList.add(categoryName);
                                map.put(categoryId, categoryName);
                            }
                            setup();
                            SystemController.getINSTANCE().setFetchedCategories(map);
                            progressBar.setVisibility(View.INVISIBLE);
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



