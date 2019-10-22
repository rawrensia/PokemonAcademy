package com.example.pokemonacademy.Entity;

public class QuizzesCompleted {
    int userId;
    int worldId;
    int quizId;
    int timeTaken;
    int score;
    boolean completed;

    public QuizzesCompleted(int userId, int worldId, int quizId, boolean completed, int timeTaken, int score) {
        this.userId = userId;
        this.worldId = worldId;
        this.quizId = quizId;
        this.completed = completed;
        this.score = score;
        this.timeTaken = timeTaken;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getWorldId() {
        return worldId;
    }

    public void setWorldId(int worldId) {
        this.worldId = worldId;
    }

    public int getMiniQuizId() {
        return quizId;
    }

    public void setMiniQuizId(int quizId) {
        this.quizId = quizId;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
