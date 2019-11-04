package com.example.pokemonacademy.Entity;
/**
 * The UserCompletedQns consists
 * of the attributes and getter setter
 * function for a UserCompletedQns.
 *
 * @author  Maggie
 * @since   2019-11-01
 */
public class UserCompletedQns {
    String userId;
    int qnsId;
    boolean completed;

    public UserCompletedQns(){

    }

    public UserCompletedQns(String userId, int qnsId, boolean completed) {
        this.userId = userId;
        this.qnsId = qnsId;
        this.completed = completed;
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

    public boolean getCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
