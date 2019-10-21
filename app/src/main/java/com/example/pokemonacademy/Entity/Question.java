package com.example.pokemonacademy.Entity;


import com.example.pokemonacademy.Entity.Choice;
import java.util.ArrayList;

public class Question {
    public int questionId;
    public String question;
    public String world;
    public int difficultyLevel;
    public ArrayList<Choice> choiceOptions;
    public boolean attempted;
    public String quiz_type;

    public Question(int questionId, String question, String world, int difficultyLevel, String quiz_type){
        this.questionId = questionId;
        this.question = question;
        this.world = world;
        this.difficultyLevel = difficultyLevel;
        this.quiz_type = quiz_type;
        attempted = false;
    }

    public int getDifficultyLevel(){
        return difficultyLevel;
    }

    public void setChoiceOptions(){
        // iterate through list of choices,
        // get those this.question_id == choice.question_id
        // append those choices into ArrayList<Choice> CO
        // this.choiceOptions = CO
    }

    public ArrayList<Choice> getChoices(){
        return choiceOptions;
    }
    public boolean verifyChoice(Choice choice) {
        return choice.isCorrect();
    }
    public void setAttempted(){
        attempted = true;

    }
}
