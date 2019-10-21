package com.example.pokemonacademy.Entity;

public class Choice {
    public int question_id;
    public int choice_id;
    public String choice;
    public boolean is_right_choice;

    Choice(int question_id, int choice_id, String choice, boolean is_right_choice){
        this.question_id = question_id;
        this.choice_id = choice_id;
        this.choice = choice;
        this.is_right_choice = is_right_choice;
    }

    public boolean isCorrect(){
        return is_right_choice;
    }

}
