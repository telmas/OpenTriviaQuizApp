package com.example.opentriviaquizapp.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.opentriviaquizapp.R;
import com.example.opentriviaquizapp.activities.adapters.PrizesListAdapter;
import com.example.opentriviaquizapp.models.Prize;
import com.example.opentriviaquizapp.system.DBHelper;
import com.example.opentriviaquizapp.system.SystemController;

import java.util.ArrayList;
import java.util.Objects;

public class EarnedPrizesFragment extends Fragment {

    ListView listPrizes;
    ProgressBar loader;
    DBHelper dataBase;

    ArrayList<Prize> dataList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.prizes_fragment, container, false);
    }

    @Override
    public void  onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dataList = new ArrayList<>();

        listPrizes = (ListView) Objects.requireNonNull(getView()).findViewById(R.id.listPrizesID);
        loader = (ProgressBar) getView().findViewById(R.id.loader);
        listPrizes.setEmptyView(loader);

        dataBase = new DBHelper(getActivity());
    }

    public void populateData() {
        loader.setVisibility(View.VISIBLE);

        Cursor earnedPrizes = dataBase.getUserEarnedPrizes(SystemController.getINSTANCE().getUserName());
        loadDataList(earnedPrizes, dataList);


        loadListView(listPrizes,dataList);
        loader.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        populateData();
    }

    public void loadDataList(Cursor cursor, ArrayList<Prize> dataList){
        if(cursor != null ) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int prizeId = cursor.getInt(0);
                String prizeName = cursor.getString(1);

                String prizeDescription = cursor.getString(2);

                Prize prize = new Prize(prizeId, prizeName, prizeDescription);
                dataList.add(prize);
                cursor.moveToNext();
            }
        }
    }

    public void loadListView(ListView listView, ArrayList<Prize> dataList){
        PrizesListAdapter prizesListAdapter = new PrizesListAdapter(getActivity(), dataList);
        listView.setAdapter(prizesListAdapter);
    }
}
