package com.example.pokemonacademy.Entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.pokemonacademy.Entity.QuestionChoice;

import java.io.Serializable;
import java.util.ArrayList;

//Question
// - question_id: int
// - world_id: int (from 0 to 5)
// - quiz_id: int (from 0 to 2. [0 : mini_quiz_1] [1 : mini_quiz_2] [3: final_quiz])
// - difficulty_level: int (from 1 to 3)

public class Question implements Parcelable {
    public boolean attempted;
    public int difficultyLevel;
    public int miniQuizId;
    public String question;
    public int questionId;
    public int quizId;
    public int worldId;
    public ArrayList<QuestionChoice> questionChoice;

    public Question(){

    }

    public Question(int worldId, int quizId, int qnsId, int difficultyLevel, String qns){
        this.worldId = worldId;
        this.quizId = quizId;
        this.questionId = qnsId;
        this.difficultyLevel = difficultyLevel;
        this.question = qns;
    }


    protected Question(Parcel in) {
        attempted = in.readByte() != 0;
        difficultyLevel = in.readInt();
        miniQuizId = in.readInt();
        question = in.readString();
        questionId = in.readInt();
        quizId = in.readInt();
        worldId = in.readInt();
        questionChoice = in.createTypedArrayList(QuestionChoice.CREATOR);
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

    public boolean verifyChoice(QuestionChoice choice) {
        return choice.isCorrect();
    }

    public void setQuestionChoice(ArrayList<QuestionChoice> questionChoice){ this.questionChoice = questionChoice; }

    public ArrayList<QuestionChoice> getQuestionChoice() {return this.questionChoice; }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (attempted ? 1 : 0));
        dest.writeInt(difficultyLevel);
        dest.writeInt(miniQuizId);
        dest.writeString(question);
        dest.writeInt(questionId);
        dest.writeInt(quizId);
        dest.writeInt(worldId);
        dest.writeTypedList(questionChoice);
    }
}
