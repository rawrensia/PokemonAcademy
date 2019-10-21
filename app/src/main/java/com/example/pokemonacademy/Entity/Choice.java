package com.example.pokemonacademy.Entity;

public class Choice {
    public static int choice_id_counter = 0;
    public int question_id;
    public int choice_id;
    public String choice;
    public boolean is_right_choice;

    public Choice(int question_id, int choice_id, String choice, boolean is_right_choice){
        this.question_id = question_id;
        this.choice_id = choice_id;
        this.choice = choice;
        this.is_right_choice = is_right_choice;
    }

    public static Choice addChoice(int question_id, String choice, boolean is_right_choice){
        choice_id_counter++;
        int choice_id = choice_id_counter;
        Choice c = new Choice(question_id, choice_id, choice, is_right_choice);
        return c;
    }

    public boolean isCorrect(){
        return is_right_choice;
    }

}
