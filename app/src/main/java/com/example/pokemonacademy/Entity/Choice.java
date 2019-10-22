package com.example.pokemonacademy.Entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Choice implements Parcelable {
    public static int choice_id_counter = 0;
    public int question_id;
    public int choice_id;
    public String choice;
    public boolean is_right_choice;

    public Choice(int question_id, int choice_id, String choice, boolean is_right_choice){
        this.question_id = question_id;
        this.choice_id = choice_id;
        this.choice = choice;
        this.is_right_choice = is_right_choice;
    }

    protected Choice(Parcel in) {
        question_id = in.readInt();
        choice_id = in.readInt();
        choice = in.readString();
        is_right_choice = in.readByte() != 0;
    }

    public static final Creator<Choice> CREATOR = new Creator<Choice>() {
        @Override
        public Choice createFromParcel(Parcel in) {
            return new Choice(in);
        }

        @Override
        public Choice[] newArray(int size) {
            return new Choice[size];
        }
    };

    public static Choice addChoice(int question_id, String choice, boolean is_right_choice){
        choice_id_counter++;
        int choice_id = choice_id_counter;
        Choice c = new Choice(question_id, choice_id, choice, is_right_choice);
        return c;
    }

    public boolean isCorrect(){
        return is_right_choice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(question_id);
        dest.writeInt(choice_id);
        dest.writeString(choice);
        dest.writeByte((byte) (is_right_choice ? 1 : 0));
    }
}
