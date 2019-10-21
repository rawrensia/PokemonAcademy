package com.example.pokemonacademy.Entity;


public class QuestionChoice {
    int choiceId; //MAY NOT NEED THIS
    int qnsId; //MAY NOT NEED THIS
    int isRightChoice;
    String choice;

    public QuestionChoice(int choiceId, int qnsId, int isRightChoice, String choice) {
        this.choiceId = choiceId;
        this.qnsId = qnsId;
        this.isRightChoice = isRightChoice;
        this.choice = choice;
    }

    public int getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(int choiceId) {
        this.choiceId = choiceId;
    }

    public int getQnsId() {
        return qnsId;
    }

    public void setQnsId(int qnsId) {
        this.qnsId = qnsId;
    }

    public int isRightChoice() {
        return isRightChoice;
    }

    public void setRightChoice(int rightChoice) {
        isRightChoice = rightChoice;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }
}
