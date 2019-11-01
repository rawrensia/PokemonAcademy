package com.example.pokemonacademy.Control;

import com.example.pokemonacademy.Entity.QuestionChoice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import androidx.databinding.ObservableArrayList;

public class Shuffle {
    public static ArrayList<QuestionChoice> shuffleList(ArrayList<QuestionChoice> qc) {
        List<QuestionChoice> intList = qc.subList(0, 3);
        ArrayList<QuestionChoice> result = new ArrayList<>();

        Collections.shuffle(intList);

        for(int i=0; i<intList.size(); i++) {
            result.add(intList.get(i));
        }
        return result;
    }
}
