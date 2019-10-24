package com.example.pokemonacademy.Entity;

public class QuizzesCompleted {
    int userId;
    int worldId;
    int miniQuizId;
    int timeTaken;
    int score;
    boolean completed;

    public QuizzesCompleted(int userId, int worldId, int miniQuizId, boolean completed, int timeTaken, int score) {
        this.userId = userId;
        this.worldId = worldId;
        this.miniQuizId = miniQuizId;
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
        return miniQuizId;
    }

    public void setMiniQuizId(int quizId) {
        this.miniQuizId = quizId;
    }

    public boolean getCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getTimeTaken(){ return timeTaken; }

    public void setTimeTaken(int timeTaken){ this.timeTaken = timeTaken; }

    public int getScore(){ return score; }

    public void setScore(int score){ this.score = score; }
}
