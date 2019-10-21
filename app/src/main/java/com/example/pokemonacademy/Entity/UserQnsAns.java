package com.example.pokemonacademy.Entity;


public class UserQnsAns {

    int userId;
    int qnsId;
    int choiceId;
    int isRight;

    public UserQnsAns(int userId, int qnsId, int choiceId, int isRight) {
        this.userId = userId;
        this.qnsId = qnsId;
        this.choiceId = choiceId;
        this.isRight = isRight;
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

    public int getIsRight() {
        return isRight;
    }

    public void setIsRight(int isRight) {
        this.isRight = isRight;
    }
}
