package com.example.opentriviaquizapp.quizconfig;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
import com.example.opentriviaquizapp.quiz.TrueFalseQuizActivity;
import com.example.opentriviaquizapp.userprofile.entities.BooleanQuestion;
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
                "&type=boolean&encode=url3986";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int responseCode = response.getInt("response_code");
                            if(responseCode != 1) {
                                JSONArray jsonArray = response.getJSONArray("results");
                                questionsAndAnswers = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String question = null;
                                    try {
                                        question = java.net.URLDecoder.decode(jsonObject.getString("question"), "UTF-8");
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }
                                    boolean answer = jsonObject.getBoolean("correct_answer");
                                    questionsAndAnswers.add(new BooleanQuestion(question, answer));
                                }
                                SystemController.getINSTANCE().setBooleanQuestions(questionsAndAnswers);
                                initiateQuiz();
                            } else {
                                AlertDialog alertDialog = new AlertDialog.Builder(ChooseDifficultyActivity.this).create();
                                alertDialog.setTitle("API Failure!");
                                alertDialog.setMessage("Cannot find quiz from selected category of this difficulty at the moment. Try again.");
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                Intent intent = new Intent(ChooseDifficultyActivity.this, ChooseCategoryActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                difficulty_loading_indicator.setVisibility(View.INVISIBLE);
                                                startActivity(intent);
                                            }
                                        });
                                alertDialog.show();
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

    private void initiateQuiz(){
            Intent intent = new Intent(ChooseDifficultyActivity.this, TrueFalseQuizActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            difficulty_loading_indicator.setVisibility(View.INVISIBLE);
            startActivity(intent);
        }
}


