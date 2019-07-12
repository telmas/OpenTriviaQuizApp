package com.example.opentriviaquizapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
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

import java.util.ArrayList;


public class ChooseCategoryActivity extends AppCompatActivity {

    private RequestQueue mQueue;
    ArrayList<String> categoryStringArrayList;
    ArrayList<Integer> categoryIntegerArrayList;
    ListView categoriesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_category);
        categoryStringArrayList = new ArrayList<>();
        categoryIntegerArrayList = new ArrayList<>();

        mQueue = Volley.newRequestQueue(this);
        jsonParse();
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
                                int categoryId = jsonObject.getInt("id");
                                String categoryName = jsonObject.getString("name");
                                categoryIntegerArrayList.add(categoryId);
                                categoryStringArrayList.add(categoryName);
                            }
                            setupListView();
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

    private void setupListView(){
        categoriesListView = (ListView) findViewById(R.id.listCategories);
        ListAdapter myAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, categoryStringArrayList);
        categoriesListView.setAdapter(myAdapter);
        categoriesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int categoryID = categoryIntegerArrayList.get(position);
                SystemController.getINSTANCE().setCategoryID(categoryID);
                Toast.makeText(getApplicationContext(), "ProjectID: " + categoryID, Toast.LENGTH_SHORT).show();
            }
        });
    }
}


