package com.example.pokemonacademy.Entity;

import android.os.Parcel;
import android.os.Parcelable;

public class QuestionChoice implements Parcelable {
    public static int choice_id_counter = 0;
    public int qnsId;
    public int choiceId;
    public String choice;
    public boolean rightChoice;

    public QuestionChoice(){

    }

    public QuestionChoice(int qnsId, int choiceId, String choice, boolean rightChoice){
        this.qnsId = qnsId;
        this.choiceId = choiceId;
        this.choice = choice;
        this.rightChoice = rightChoice;
    }

    protected QuestionChoice(Parcel in) {
        qnsId = in.readInt();
        choiceId = in.readInt();
        choice = in.readString();
        rightChoice = in.readByte() != 0;
    }

    public static final Creator<QuestionChoice> CREATOR = new Creator<QuestionChoice>() {
        @Override
        public QuestionChoice createFromParcel(Parcel in) {
            return new QuestionChoice(in);
        }

        @Override
        public QuestionChoice[] newArray(int size) {
            return new QuestionChoice[size];
        }
    };

    public static QuestionChoice addChoice(int question_id, String choice, boolean is_right_choice){
        choice_id_counter++;
        int choice_id = choice_id_counter;
        QuestionChoice c = new QuestionChoice(question_id, choice_id, choice, is_right_choice);
        return c;
    }

    public boolean isCorrect(){
        return rightChoice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(qnsId);
        dest.writeInt(choiceId);
        dest.writeString(choice);
        dest.writeByte((byte) (rightChoice ? 1 : 0));
    }

    public int getQnsId(){
        return qnsId;
    }

    public void setQnsId(int qnsId){
        this.qnsId = qnsId;
    }

    public int getChoiceId(){
        return choiceId;
    }

    public void setChoiceId(int choiceId){
        this.choiceId = choiceId;
    }

    public String getChoice(){
        return choice;
    }

    public void setChoice(String choice){
        this.choice = choice;
    }

    public boolean getRightChoice(){
        return rightChoice;
    }

    public void setRightChoice(Boolean rightChoice){
        this.rightChoice = rightChoice;
    }
}
