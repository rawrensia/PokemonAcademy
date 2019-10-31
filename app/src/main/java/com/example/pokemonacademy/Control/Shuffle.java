package com.example.pokemonacademy.Control;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Shuffle {
    public static Integer[] shuffleList(Integer[] intArray) {
        List<Integer> intList = Arrays.asList(intArray);

        Collections.shuffle(intList);

        return intList.toArray(intArray);
    }
}
