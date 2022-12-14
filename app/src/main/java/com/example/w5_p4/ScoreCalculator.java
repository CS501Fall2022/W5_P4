package com.example.w5_p4;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Locale;

public class ScoreCalculator {
    /*
    word requirements:
        >= 4 chars long
        >= 2 vowels
        in dictionary
        NOT used already (valid or otherwise)

    scoring specifics:
        +1 for each consonant
        +5 for each vowel
        value *= 2   if 'S' 'Z' 'P' 'X' 'Q' used > 1 time
        value = -10  if input meets word requirements but is not in dictionary
     */
    private final Context currContext;
    private final String special = "szpxq";
    private final String cons = "bcdfghjklmnrtvwy";
    private final String vows = "aeiou";
    private HashSet<String> usedValidWords = new HashSet<String>();
    private HashSet<String> usedInvalidWords = new HashSet<String>();


    public ScoreCalculator(Context context) {
        this.currContext = context;
    }

    // given input, determine change to score
    public int getChange(String input) {
        input = input.toLowerCase(Locale.ROOT);
        int change = calcValue(input);

        if(change != 0){
            // valid input
            if(!isWord(input)){
                // meets word requirements but is not a word
                change = -10;
            }
        }

        return change;
    }


    // is input a word?
    private boolean isWord(String word) {
        // word might've failed vowel check. verify validity
        if(usedInvalidWords.contains(word)){
            return false;
        }

        // is input in dictionary?
        try {
            InputStream inputStream = currContext.getResources().openRawResource(R.raw.words);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String entry = bufferedReader.readLine();
            while (entry != null) {
                if (word.equals(entry)) {
                    // input in dictionary
                    usedValidWords.add(word);
                    return true;
                }
                entry = bufferedReader.readLine();
            }
        } catch (Exception e) {
            Log.e("dictErr", e.toString());
        }
        // input not in dictionary
        usedInvalidWords.add(word);
        return false;
    }


    // what is the value of a given word?
    private int calcValue(String input) {
        // does input meet basic requirements?
//        Log.e("test", "input = " + input);
        if (input == null || input.length() < 4 || usedInvalidWords.contains(input) || usedValidWords.contains(input)) {
//            Log.e("test", "failed basic");
            // input is invalid
            return 0;
        }

        int vowCount = 0;
        int bonus = 0;
        int value = 0;
        char c;

        // calculate value of potential word
        for (int j = 0; j < input.length(); j++) {
            c = input.charAt(j);

//            Log.e("test", "checking " + String.valueOf(c) );
            if (this.special.indexOf(c) != -1) {
                // c in SZPXQ
//                Log.e("test", "special cons");
                bonus += 1;
                value += 1;
            } else if (this.cons.indexOf(c) != -1) {
                // c in BCDFGHJKLMNRTVWY
//                Log.e("test", "normal cons");
                value += 1;
            } else if (this.vows.indexOf(c) != -1) {
                // c in AEIOU
//                Log.e("test", "vowel");
                value += 5;
                vowCount++;
            } else {
                // something weird happened
                Log.e("scoreErr", "char not a letter");
            }
        }

        // at least 2 special consonants in word
        if(bonus > 1) {
//            Log.e("test", "special applied");
            value *= 2;
        }

        // not enough vowels, word is invalid
        if(vowCount < 2){
//            Log.e("test", "not enough vowels");
            this.usedInvalidWords.add(input);
            value = 0;
        }

        return value;
    }

}