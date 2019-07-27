package com.example.opentriviaquizapp.userprofile.previousscores;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.opentriviaquizapp.R;
import com.example.opentriviaquizapp.quiz.entities.ScoreRecord;
import com.example.opentriviaquizapp.system.DBHelper;
import com.example.opentriviaquizapp.system.SystemController;

import java.util.ArrayList;
import java.util.HashMap;

public class PreviousScoresFragment extends Fragment {

    ListView listPrevScores;
    TextView noResults;
    ProgressBar loader;
    DBHelper dataBase;

    ArrayList<ScoreRecord> dataList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.previous_scores_fragment, container, false);
    }

    @Override
    public void  onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dataList = new ArrayList<>();

        listPrevScores = (ListView) getView().findViewById(R.id.listPrevScoresID);
        loader = (ProgressBar) getView().findViewById(R.id.loader);
        listPrevScores.setEmptyView(loader);
        noResults = (TextView) getView().findViewById(R.id.noPreviousScoresID);

        dataBase = new DBHelper(getActivity());
    }

    public void populateData() {
        loader.setVisibility(View.VISIBLE);
        dataList = new ArrayList<>();
        Cursor prevUserScores = dataBase.getPrevUserScores(SystemController.getINSTANCE().getUserName());
        loadDataList(prevUserScores, dataList);


        if(dataList == null || dataList.size() == 0) {
            noResults.setVisibility(View.VISIBLE);
        } else {
            noResults.setVisibility(View.INVISIBLE);
        }
        loadListView(listPrevScores,dataList);
        loader.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        populateData();
    }

    public void loadDataList(Cursor cursor, ArrayList<ScoreRecord> dataList){
        if(cursor != null ) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int categoryId = cursor.getInt(0);
                int difficultyId = cursor.getInt(1);
                int score = cursor.getInt(2);
                HashMap<Integer, String> map = SystemController.getINSTANCE().getFetchedCategories();
                String category = map.get(categoryId);
                String difficulty;
                switch(difficultyId) {
                    case 0:
                        difficulty = "Easy";
                        break;
                    case 1:
                        difficulty = "Medium";
                        break;
                    case 2:
                        difficulty = "Hard";
                        break;
                    default:
                        difficulty = "No Difficulty";
                        break;
                }
                ScoreRecord scoreRecord = new ScoreRecord(category, difficulty, score);
                dataList.add(scoreRecord);
                cursor.moveToNext();
            }
        }
    }

    public void loadListView(ListView listView, ArrayList<ScoreRecord> dataList){
        PreviousScoresListAdapter previousScoresListAdapter = new PreviousScoresListAdapter(getActivity(), dataList);
        listView.setAdapter(previousScoresListAdapter);
    }
}
