package com.example.w5_p4;

import android.app.Fragment;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.Random;

public class BoardFragment extends Fragment {
    Button clear;
    Button check;
    TextView currWord;
    GridLayout inputGrid;
    int mostRecentClickIndex;

    public BoardFragment() {
        // Required empty public constructor
    }

    interface BoardInterface{
        void passData(String s);
    }

    // place given letter into currWord
    // called inside onClick function for inputGrid buttons
    public void updateWord(String s){
        currWord.setText(currWord.getText() + s);
    }

    // reset selections
    // onClick function for clear button, also used in check button
    public void clearSelectedLetters(){
        currWord.setText("");
        mostRecentClickIndex = -1;
        Button b;
        for(int j = 0; j < inputGrid.getChildCount(); j++){
            b = (Button) inputGrid.getChildAt(j);
            b.setClickable(true);
            b.setBackgroundColor(getResources().getColor(R.color.purple_200));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_board, container, false);

        this.clear = v.findViewById(R.id.clear_button);
        this.check = v.findViewById(R.id.check_button);
        this.currWord = v.findViewById(R.id.created_word);
        this.inputGrid = v.findViewById(R.id.choice_grid);
        this.mostRecentClickIndex = -1;

        String alpha = "ETAOINSRHLDCUMFPGWYBVKXJQZ";
        Random rand = new Random();

        Button b;
        for(int j = 0; j < inputGrid.getChildCount(); j++){
            b = (Button) inputGrid.getChildAt(j);
            b.setText(String.valueOf(alpha.charAt(rand.nextInt(26))));
//            Log.e("test", b.getText().toString() +" " + String.valueOf(j));
        }

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearSelectedLetters();
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((BoardInterface)getActivity()).passData(currWord.getText().toString());
                clearSelectedLetters();
            }
        });

        return v;
    }




}