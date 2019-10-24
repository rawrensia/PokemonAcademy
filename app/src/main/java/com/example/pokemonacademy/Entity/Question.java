package com.example.pokemonacademy.Entity;


import android.os.Parcel;
import android.os.Parcelable;

import com.example.pokemonacademy.Entity.QuestionChoice;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Parcelable {
    public static int question_id_counter = 0;
    public int questionId;
    public String question;
    public String world; //not in database
    public int difficultyLevel;
    public ArrayList<QuestionChoice> choiceOptions; //not in database
    public boolean attempted;
    public String quiz_type;
    public int miniQuizId;
    int worldId;
    int quizId;

    public Question(){

    }

    public Question(int worldId, int quizId, int qnsId, int difficultyLevel, String qns){
        this.worldId = worldId;
        this.quizId = quizId;
        this.questionId = qnsId;
        this.difficultyLevel = difficultyLevel;
        this.question = qns;
    }

//    public Question(int questionId, String question, String world, int difficultyLevel, String quiz_type, int miniQuizId){
//        this.questionId = questionId;
//        this.question = question;
//        this.world = world;
//        this.difficultyLevel = difficultyLevel;
//        this.quiz_type = quiz_type;
//        //attempted = false;
//        this.miniQuizId = miniQuizId;
//    }

    public Question(int questionId, String question, String world, int difficultyLevel, String quiz_type, ArrayList<QuestionChoice> choiceOptions){
        this.questionId = questionId;
        this.question = question;
        this.world = world;
        this.difficultyLevel = difficultyLevel;
        this.quiz_type = quiz_type;
        this.choiceOptions = choiceOptions;
        //attempted = false;
    }

    protected Question(Parcel in) {
        questionId = in.readInt();
        question = in.readString();
        world = in.readString();
        difficultyLevel = in.readInt();
        attempted = in.readByte() != 0;
        quiz_type = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(questionId);
        dest.writeString(question);
        dest.writeString(world);
        dest.writeInt(difficultyLevel);
        dest.writeByte((byte) (attempted ? 1 : 0));
        dest.writeString(quiz_type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public static Question addQuestion(String question, String world, int difficultyLevel, String quiz_type){
        question_id_counter++;
        int questionId = question_id_counter;
        ArrayList<QuestionChoice> co = new ArrayList<QuestionChoice>();
        Question q = new Question(questionId, question, world, difficultyLevel,quiz_type, co);
        return q;
    }

    public void setChoiceOptions(ArrayList<QuestionChoice> choiceOptions){
        this.choiceOptions = choiceOptions;
    }

    public ArrayList<QuestionChoice> getChoices(){
        return choiceOptions;
    }

    public boolean verifyChoice(QuestionChoice choice) {
        return choice.isCorrect();
    }

    public void setAttempted(){
        attempted = true;

    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getDifficultyLevel(){ return difficultyLevel; }

    public void setDifficultyLevel(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public int getWorldId() {
        return worldId;
    }

    public void setWorldId(int worldId) {
        this.worldId = worldId;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public Boolean getAttempted() { return attempted; }

    public void setAttempted(Boolean attempted) { this.attempted = attempted; }

    public int getMiniQuizId() { return miniQuizId; }

    public void setMiniQuizId(int miniQuizId) { this.miniQuizId = miniQuizId; }

}
