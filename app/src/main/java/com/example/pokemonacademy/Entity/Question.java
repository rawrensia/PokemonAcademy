package com.example.pokemonacademy.Entity;


import com.example.pokemonacademy.Entity.Choice;
import java.util.ArrayList;

public class Question {
    public static int question_id_counter = 0;
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

    public Question(int questionId, String question, String world, int difficultyLevel, String quiz_type, ArrayList<Choice> choiceOptions){
        this.questionId = questionId;
        this.question = question;
        this.world = world;
        this.difficultyLevel = difficultyLevel;
        this.quiz_type = quiz_type;
        this.choiceOptions = choiceOptions;
        attempted = false;
    }

    public static Question addQuestion(String question, String world, int difficultyLevel, String quiz_type){
        question_id_counter++;
        int questionId = question_id_counter;
        ArrayList<Choice> co = new ArrayList<Choice>();
        Question q = new Question(questionId, question, world, difficultyLevel,quiz_type, co);
        return q;
    }



    public int getDifficultyLevel(){
        return difficultyLevel;
    }

    public void setChoiceOptions(ArrayList<Choice> choiceOptions){
        this.choiceOptions = choiceOptions;
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
