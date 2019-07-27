package com.example.opentriviaquizapp.userprofile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.opentriviaquizapp.R;
import com.example.opentriviaquizapp.userprofile.previousscores.PreviousScoresFragment;
import com.example.opentriviaquizapp.userprofile.prizes.EarnedPrizesFragment;

public class UserProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        BottomNavigationView navigationView = findViewById(R.id.bottom_nav);

        final PreviousScoresFragment previousScoresFragment = new PreviousScoresFragment();
        final EarnedPrizesFragment earnedPrizesFragment = new EarnedPrizesFragment();

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if(id == R.id.previousScoresTabID){
                    setFragment(previousScoresFragment);
                    return true;
                } else if (id == R.id.prizesTabID){
                    setFragment(earnedPrizesFragment);
                    return true;
                }
                return false;
            }
        });
        navigationView.setSelectedItemId(R.id.previousScoresTabID);
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }
}
