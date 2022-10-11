package com.example.w5_p4;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ScoreFragment extends Fragment {
    public ScoreFragment() {
        // Required empty public constructor
    }

    public void updateScore(String s){
        // update score display

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_score, container, false);
    }
}