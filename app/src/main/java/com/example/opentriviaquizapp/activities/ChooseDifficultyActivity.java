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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.opentriviaquizapp.R;
import com.example.opentriviaquizapp.models.BooleanQuestion;
import com.example.opentriviaquizapp.system.SystemController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class ChooseDifficultyActivity extends AppCompatActivity {

    ListView difficultiesListView;
    ArrayList<BooleanQuestion> questionsAndAnswers;
    private RequestQueue mQueue;
    ProgressBar difficulty_loading_indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_difficulty);
        difficulty_loading_indicator = (ProgressBar) findViewById(R.id.difficulty_loading_indicator);
        difficulty_loading_indicator.setVisibility(View.VISIBLE);
        mQueue = Volley.newRequestQueue(this);
        String[] levels = {"Easy", "Medium", "Hard"};
        difficultiesListView = (ListView) findViewById(R.id.listDifficulties);
        ListAdapter myAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, levels);
        difficultiesListView.setAdapter(myAdapter);
        difficultiesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String difficulty = String.valueOf(parent.getItemAtPosition(position));
                SystemController.getINSTANCE().setDifficulty(difficulty.toLowerCase());
                jsonParse();

            }
        });
        difficulty_loading_indicator.setVisibility(View.INVISIBLE);
    }



    private void jsonParse() {
        difficulty_loading_indicator.setVisibility(View.VISIBLE);

        String url = "https://opentdb.com/api.php?amount=5&category=" +
                SystemController.getINSTANCE().getCategoryID() +
                "&difficulty=" +
                SystemController.getINSTANCE().getDifficulty() +
                "&type=boolean";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");
                            questionsAndAnswers = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String question = jsonObject.getString("question");
                                try {
                                    question = java.net.URLDecoder.decode(question, "UTF-8");//check
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                                boolean answer = jsonObject.getBoolean("correct_answer");
                                questionsAndAnswers.add(new BooleanQuestion(question, answer));
                            }
                            SystemController.getINSTANCE().setBooleanQuestions(questionsAndAnswers);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        initiateQuiz();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }


    private void initiateQuiz(){
//        if(SystemController.getINSTANCE().getBooleanQuestions()!=null) {
            Intent intent = new Intent(ChooseDifficultyActivity.this, TrueFalseQuizActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            difficulty_loading_indicator.setVisibility(View.INVISIBLE);
            startActivity(intent);
        }
//    }
}


