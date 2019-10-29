package com.example.pokemonacademy.Entity;

public class UserCompletedQns {
    String userId;
    int qnsId;
    boolean completed;

    public UserCompletedQns(){

    }

    public UserCompletedQns(String userId, int qnsId, boolean completed) {
        this.userId = userId;
        this.qnsId = qnsId;
        this.completed = completed;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getQnsId() {
        return qnsId;
    }

    public void setQnsId(int qnsId) {
        this.qnsId = qnsId;
    }

    public boolean getCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
