package com.example.pokemonacademy.Entity;

public class UserCompletedQns {
    int userId;
    int qnsId;
    boolean completed;

    public UserCompletedQns(int userId, int qnsId, boolean completed) {
        this.userId = userId;
        this.qnsId = qnsId;
        this.completed = completed;
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

    public boolean getCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
