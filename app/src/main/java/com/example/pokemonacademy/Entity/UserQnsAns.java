package com.example.pokemonacademy.Entity;
/**
 * The UserQnsAns consists
 * of the attributes and getter setter
 * function for a UserQnsAns.
 *
 * @author  Maggie
 * @since   2019-11-01
 */
public class UserQnsAns {

    String userId;
    int qnsId;
    int choiceId;
    boolean isRight;
    boolean isSelected;

    public UserQnsAns(){

    }

    public UserQnsAns(String userId, int qnsId, int choiceId, boolean isRight, boolean isSelected) {
        this.userId = userId;
        this.qnsId = qnsId;
        this.choiceId = choiceId;
        this.isRight = isRight;
        this.isSelected = isSelected;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getQnsId() {
        return qnsId;
    }

    public void setQnsId(int qnsId) {
        this.qnsId = qnsId;
    }

    public int getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(int choiceId) {
        this.choiceId = choiceId;
    }

    public boolean getIsRight() {
        return isRight;
    }

    public void setIsRight(boolean isRight) {
        this.isRight = isRight;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
