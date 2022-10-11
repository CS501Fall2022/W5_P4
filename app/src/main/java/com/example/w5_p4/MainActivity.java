package com.example.w5_p4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements BoardFragment.PassInterface {
    ScoreCalculator model;
    BoardFragment viewIn;
    ScoreFragment viewOut;
    int score;

    public void passData(String word){
        // given input, determine new score and send to score fragment for display
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewIn = new BoardFragment();
        viewOut = new ScoreFragment();
        model = new ScoreCalculator(this);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.board_frag, viewIn);
        transaction.add(R.id.score_frag, viewOut);
        transaction.commit();

    }

}