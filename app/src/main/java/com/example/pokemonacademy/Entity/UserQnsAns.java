package com.example.pokemonacademy.Entity;


public class UserQnsAns {

    int userId;
    int qnsId;
    int choiceId;
    boolean isRight;
    boolean isSelected;

    public UserQnsAns(int userId, int qnsId, int choiceId, boolean isRight, boolean isSelected) {
        this.userId = userId;
        this.qnsId = qnsId;
        this.choiceId = choiceId;
        this.isRight = isRight;
        this.isSelected = isSelected;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getQnsId() {
        return qnsId;
    }

    public void setQnsId(int qnsId) {
        this.qnsId = qnsId;
    }

    public int getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(int choiceId) {
        this.choiceId = choiceId;
    }

    public boolean getIsRight() {
        return isRight;
    }

    public void setIsRight(boolean isRight) {
        this.isRight = isRight;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}