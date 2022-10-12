package com.example.w5_p4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements BoardFragment.BoardInterface, ScoreFragment.ScoreInterface {
    ScoreCalculator model;
    BoardFragment viewIn;
    ScoreFragment viewOut;

    // data IN function
    public void passData(String word){
        // determine value, display response toast, send value to score
        int wordVal = model.getChange(word);

        if(wordVal == 0){
            // neutral toast
        }
        else if(wordVal == -10){
            // bad toast
        }
        else{
            // good toast
        }
        displayData(wordVal);
    }

    // data OUT function
    public void displayData(int wordVal){
        String scoreString = viewOut.scoreBoard.getText().toString();
        int score = Integer.parseInt(scoreString);
        viewOut.scoreBoard.setText(String.valueOf(score + wordVal));
    }

    // game resetting function
    public void resetGame(){
        viewIn = new BoardFragment();
        viewOut = new ScoreFragment();
        model = new ScoreCalculator(this);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.board_frag, viewIn);
        transaction.replace(R.id.score_frag, viewOut);
        transaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.viewIn = new BoardFragment();
        this.viewOut = new ScoreFragment();
        this.model = new ScoreCalculator(this);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.board_frag, viewIn);
        transaction.add(R.id.score_frag, viewOut);
        transaction.commit();

    }

    // onClick function for grid buttons
    public void selectLetter(View view){
        Button selected = (Button) view;
        // only do something if button is active
        if(selected.isClickable()){
            String fullName = getResources().getResourceName(selected.getId());
            String name = fullName.substring(fullName.lastIndexOf("/") + 1);
            int index = Integer.parseInt(name.substring(1));

            // if not first letter clicked, verify input
            if(this.viewIn.mostRecentClickIndex != -1){
                // check that clicked button is nearby previous selection
                int lastIndex = this.viewIn.mostRecentClickIndex;
                int lastRow = lastIndex / this.viewIn.inputGrid.getColumnCount();
                int rowOffset = this.viewIn.inputGrid.getColumnCount();
                boolean valid = false;

                // valid indices around previous selection (rowOffset == 4)
                // lastIndex - 5   lastIndex - 4   lastIndex - 3
                // lastIndex - 1     lastIndex     lastIndex + 1
                // lastIndex + 3   lastIndex + 4   lastIndex + 5

                //Log.e("test", name + " " + String.valueOf(index) + " " +selected.getText().toString() + String.valueOf(lastIndex));

                for(int j = lastIndex - (rowOffset + 1); j <= lastIndex + (rowOffset + 1); j++){
                    if(
                            // handle same index, out of bounds, and index wrapping cases
                            j == lastIndex || j == lastIndex - (rowOffset - 2) || j == lastIndex + (rowOffset - 2)
                            || this.viewIn.inputGrid.getChildAt(j) == null
                            || ((j == lastIndex - (rowOffset + 1) || j == lastIndex + (rowOffset + 1))
                                    && (Math.abs(j / this.viewIn.inputGrid.getColumnCount() - lastRow)) > 1)
                            || ((j == lastIndex - (rowOffset - 1) || j == lastIndex + (rowOffset - 1))
                                    && (j / this.viewIn.inputGrid.getColumnCount() == lastRow))
                            || ((j == lastIndex - 1 || j == lastIndex + 1)
                                    && (j / this.viewIn.inputGrid.getColumnCount() != lastRow))
                    ){
                        // invalid index value
                        continue;
                    }
                    // valid index value. is this the index that was selected?
                    if(j == index){
                        // Log.e("test", "j = " + j);
                        // letter will be selected
                        valid = true;
                        break;
                    }
                }

                if(!valid){
                    // selected letter was not a valid option
                    return;
                }
            }

            // if here, select letter.
            this.viewIn.mostRecentClickIndex = index;
            selected.setClickable(false);
            selected.setBackgroundColor(getResources().getColor(R.color.purple_500));
            this.viewIn.updateWord(selected.getText().toString());
        }
    }

}