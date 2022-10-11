package com.example.w5_p4;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;

public class ScoreCalculator {
    /*
    word requirements:
        >= 4 chars long
        >= 2 vowels
        in dictionary
        NOT used already

    scoring specifics:
        +1 for each consonant
        +5 for each vowel
        value *= 2   if 'S' 'Z' 'P' 'X' 'Q' used > 1 time
        value = -10  if fail to meet word requirements
     */
    private final Context currContext;
    private final String special = "SZPXQ";
    private final String cons = "BCDFGHJKLMNRTVWY";
    private final String vows = "AEIOU";
    private HashSet<String> usedWords = new HashSet<String>();

    public ScoreCalculator(Context context) {
        this.currContext = context;
    }

    // given input, determine change to score
    public int getChange(String input) {
        int change = -10;

        if(isWord(input)){
            usedWords.add(input);
            change = calcValue(input);
        }

        return change;
    }


    // is input a word?
    private boolean isWord(String input) {
        // does input meet basic requirements?
        // checking for used words here avoids checking dict and calculating score
        if (input == null || input.length() < 4 || usedWords.contains(input)) {
            return false;
        }

        // is input in dictionary?
        try {
            InputStream inputStream = currContext.getResources().openRawResource(R.raw.words);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String entry = bufferedReader.readLine();

            while (entry != null) {
                if (input.equals(entry)) {
                    return true;
                } else {
                    entry = bufferedReader.readLine();
                }
            }
        } catch (Exception e) {
            Log.e("dictErr", e.toString());
        }
        return false;
    }


    // what is the value of a given word?
    private int calcValue(String word) {
        int vowCount = 0;
        int bonus = 0;
        int value = 0;
        char c;

        for (int j = 0; j < word.length(); j++) {
            c = word.charAt(j);

            if (special.indexOf(c) != -1) {
                // c in SZPXQ
                bonus += 1;
                value += 1;
            } else if (cons.indexOf(c) != -1) {
                // c in BCDFGHJKLMNRTVWY
                value += 1;
            } else if (vows.indexOf(c) != -1) {
                // c in AEIOU
                value += 5;
                vowCount++;
            } else {
                // something weird happened
                Log.e("scoreErr", "char not a letter");
            }
        }

        // at least 2 special consonants in word
        if(bonus > 1) {
            value *= 2;
        }

        // not enough vowels, word is invalid
        if(vowCount < 2){
            value = -10;
        }

        return value;
    }

}