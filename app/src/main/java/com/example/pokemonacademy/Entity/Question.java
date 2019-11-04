/**
 * The Question class consists
 * of the attributes and getter setter
 * function for a Question.
 *
 * @author  Lawrann
 * @version 1.0
 * @since   2019-10-07
 */

package com.example.pokemonacademy.Entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;


public class Question implements Parcelable {
    public boolean attempted = false;
    public int difficultyLevel;
    public String question;
    public int questionId;
    public String quizId;
    public String worldId;
    public ArrayList<QuestionChoice> questionChoice;

    public Question(){

    }

    public Question(String worldId, String quizId, int qnsId, int difficultyLevel, String qns){
        this.worldId = worldId;
        this.quizId = quizId;
        this.questionId = qnsId;
        this.difficultyLevel = difficultyLevel;
        this.question = qns;
    }


    protected Question(Parcel in) {
        attempted = in.readByte() != 0;
        difficultyLevel = in.readInt();
        question = in.readString();
        questionId = in.readInt();
        quizId = in.readString();
        worldId = in.readString();
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
        return choice.getRightChoice();
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

    public String getWorldId() {
        return worldId;
    }

    public void setWorldId(String worldId) {
        this.worldId = worldId;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public Boolean getAttempted() { return attempted; }

    public void setAttempted(Boolean attempted) { this.attempted = attempted; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (attempted ? 1 : 0));
        dest.writeInt(difficultyLevel);
        dest.writeString(question);
        dest.writeInt(questionId);
        dest.writeString(quizId);
        dest.writeString(worldId);
        dest.writeTypedList(questionChoice);
    }
}
