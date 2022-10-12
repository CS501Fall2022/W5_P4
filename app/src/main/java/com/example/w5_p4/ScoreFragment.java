package com.example.w5_p4;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.TextView;

public class ScoreFragment extends Fragment {
    Button newgame;
    TextView scoreBoard;
    public ScoreFragment() {
        // Required empty public constructor
    }

    interface ScoreInterface{
        void resetGame();
        void displayData(int i);
    }

    // updates scoreboard via function defined in activity
    public void updateScore(int change){
        ((ScoreInterface)getActivity()).displayData(change);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_score, container, false);

        this.newgame = v.findViewById(R.id.ng_button);
        this.scoreBoard = v.findViewById(R.id.score_display);

        // resets game via function defined in activity
        newgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ScoreInterface)getActivity()).resetGame();
            }
        });












        return v;
    }
}