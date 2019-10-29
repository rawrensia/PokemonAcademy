package com.example.pokemonacademy.Entity;


import android.os.Parcel;
import android.os.Parcelable;

public class QuestionChoice implements Parcelable {
    public String choice;
    public int choiceId;
    public int qnsId;
    public boolean rightChoice;
    public boolean correct;

    public QuestionChoice(){

    }

    public QuestionChoice(int qnsId, int choiceId, String choice, boolean rightChoice){
        this.qnsId = qnsId;
        this.choiceId = choiceId;
        this.choice = choice;
        this.rightChoice = rightChoice;
    }

    //    public static QuestionChoice addChoice(int question_id, String choice, boolean is_right_choice){
//        choice_id_counter++;
//        int choice_id = choice_id_counter;
//        QuestionChoice c = new QuestionChoice(question_id, choice_id, choice, is_right_choice);
//        return c;
//    }

    protected QuestionChoice(Parcel in) {
        choice = in.readString();
        choiceId = in.readInt();
        qnsId = in.readInt();
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

    public boolean isCorrect(){
        return rightChoice;
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

    public void setCorrect(Boolean correct) { this.correct = correct; }
    
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(choice);
        dest.writeInt(choiceId);
        dest.writeInt(qnsId);
        dest.writeByte((byte) (rightChoice ? 1 : 0));
    }
}
