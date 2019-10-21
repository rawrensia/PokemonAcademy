package com.example.pokemonacademy.Entity;


public class Question {
    int qnsId;
    String qns;
    String world;
    String topic;
    String quizType;
    int diffLevel;

    public Question(int qnsId, String qns, String world, String topic, String quizType, int diffLevel){
        this.qnsId = qnsId;
        this.qns = qns;
        this.world = world;
        this.topic = topic;
        this.quizType = quizType;
        this.diffLevel = diffLevel;
    }

    public int getQnsId() {
        return qnsId;
    }

    public void setQnsId(int qnsId) {
        this.qnsId = qnsId;
    }

    public String getQns() {
        return qns;
    }

    public void setQns(String qns) {
        this.qns = qns;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getQuizType() {
        return quizType;
    }

    public void setQuizType(String quizType) {
        this.quizType = quizType;
    }

    public int getDiffLevel() {
        return diffLevel;
    }

    public void setDiffLevel(int diffLevel) {
        this.diffLevel = diffLevel;
    }
}
