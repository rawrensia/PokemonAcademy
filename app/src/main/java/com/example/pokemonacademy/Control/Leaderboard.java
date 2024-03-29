package com.example.pokemonacademy.Control;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.pokemonacademy.Entity.QuestionChoice;
import com.example.pokemonacademy.Entity.QuizzesCompleted;
import com.example.pokemonacademy.Entity.User;
import com.example.pokemonacademy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * The Leaderboard activity displays the activity_leaderboard.xml
 * It generates the ranking and scores of all students within the database
 * and displays them in a recycle view
 *
 * @author  Lawrann
 * @version 1.0
 * @since   2019-11-01
 */

public class Leaderboard extends AppCompatActivity {

    RecyclerView recyclerView;
    private DatabaseReference quizzesCompletedDb;
    private DatabaseReference userDb;
    private ArrayList<User> userList = new ArrayList<User>();
    private ArrayList<String> userGradesList = new ArrayList<String>();
    private ArrayList<Integer> totalScoreList = new ArrayList<Integer>();
    private ArrayList<Integer> userTimeList = new ArrayList<Integer>();
    private LinearLayoutManager manager;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        quizzesCompletedDb = FirebaseDatabase.getInstance().getReference("QUIZZES_COMPLETED");
        userDb = FirebaseDatabase.getInstance().getReference("USER");
        manager = new LinearLayoutManager(this);
        mContext = this;

        // Get all the users.
        userDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    final User u = ds.getValue(User.class);
                    Log.i("user", ""+u.getName() + " " + u.getId());
                    if (u.getUserType().equals("S")){
                        userList.add(u);
                    }
                    quizzesCompletedDb.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (int i=0; i<userList.size(); i++){
                                String grades = "";
                                int totalScore = 0;
                                int totalTime = 0;
                                QuizzesCompleted quizWorld0 = new QuizzesCompleted();
                                QuizzesCompleted quizWorld1 = new QuizzesCompleted();
                                QuizzesCompleted quizWorld2 = new QuizzesCompleted();
                                QuizzesCompleted quizWorld3 = new QuizzesCompleted();
                                QuizzesCompleted quizWorld4 = new QuizzesCompleted();
                                QuizzesCompleted quizWorld5 = new QuizzesCompleted();
                                quizWorld0 = dataSnapshot.child(userList.get(i).getId()).child("World0").child("Quiz2").getValue(QuizzesCompleted.class);
                                quizWorld1 = dataSnapshot.child(userList.get(i).getId()).child("World1").child("Quiz2").getValue(QuizzesCompleted.class);
                                quizWorld2 = dataSnapshot.child(userList.get(i).getId()).child("World2").child("Quiz2").getValue(QuizzesCompleted.class);
                                quizWorld3 = dataSnapshot.child(userList.get(i).getId()).child("World3").child("Quiz2").getValue(QuizzesCompleted.class);
                                quizWorld4 = dataSnapshot.child(userList.get(i).getId()).child("World4").child("Quiz2").getValue(QuizzesCompleted.class);
                                quizWorld5 = dataSnapshot.child(userList.get(i).getId()).child("World5").child("Quiz2").getValue(QuizzesCompleted.class);
                                int sqw0,sqw1,sqw2,sqw3,sqw4,sqw5, tt0,tt1,tt2,tt3,tt4,tt5;
                                sqw0 = -1;
                                sqw1 = -1;
                                sqw2 = -1;
                                sqw3 = -1;
                                sqw4 = -1;
                                sqw5 = -1;
                                tt0 = 0;
                                tt1 = 0;
                                tt2 = 0;
                                tt3 = 0;
                                tt4 = 0;
                                tt5 = 0;
                                if (quizWorld0!=null){
                                    sqw0 = quizWorld0.getScore();
                                    tt0 = quizWorld0.getTimeTaken();
                                }
                                if (quizWorld1!=null){
                                    sqw1 = quizWorld1.getScore();
                                    tt1 = quizWorld1.getTimeTaken();
                                }
                                if (quizWorld2!=null){
                                    sqw2 = quizWorld2.getScore();
                                    tt2 = quizWorld2.getTimeTaken();
                                }
                                if (quizWorld3!=null){
                                    sqw3 = quizWorld3.getScore();
                                    tt3 = quizWorld3.getTimeTaken();
                                }
                                if (quizWorld4!=null){
                                    sqw4 = quizWorld4.getScore();
                                    tt4 = quizWorld4.getTimeTaken();
                                }
                                if (quizWorld5!=null){
                                    sqw5 = quizWorld5.getScore();
                                    tt5 = quizWorld5.getTimeTaken();
                                }
                                grades = getGrade(sqw0) + "," +
                                        getGrade(sqw1) + "," +
                                        getGrade(sqw2) + "," +
                                        getGrade(sqw3) + "," +
                                        getGrade(sqw4) + "," +
                                        getGrade(sqw5);
                                totalScore = sqw0 + sqw1 + sqw2 + sqw3 + sqw4 + sqw5;
                                totalTime = tt0 + tt1 + tt2 + tt3 + tt4 + tt5;

                                userTimeList.add(totalTime);
                                totalScoreList.add(totalScore);
                                userGradesList.add(grades);
                            }
                            sortUser();
                            recyclerView = findViewById(R.id.recycler_view);
                            RecyclerViewAdapter adapter = new RecyclerViewAdapter(userTimeList,userGradesList,userList, mContext);
                            recyclerView.setLayoutManager(manager);
                            recyclerView.setAdapter(adapter);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void sortUser(){
        for (int i=0; i<userList.size(); i++){
            for (int j=i+1; j<userList.size(); j++){
                if (totalScoreList.get(i) < totalScoreList.get(j)){
                    Collections.swap(totalScoreList, i, j);
                    Collections.swap(userTimeList, i, j);
                    Collections.swap(userGradesList, i, j);
                    Collections.swap(userList, i, j);
                }
                if (totalScoreList.get(i) == totalScoreList.get(j)){
                    if (userTimeList.get(i) > userTimeList.get(j)){
                        Collections.swap(totalScoreList, i, j);
                        Collections.swap(userTimeList, i, j);
                        Collections.swap(userGradesList, i, j);
                        Collections.swap(userList, i, j);
                    }
                }
            }
        }
        for (int i=0; i<userList.size(); i++){
            try {
                Log.i("Leaderboard", userList.get(i).getName()+" score "+ totalScoreList.get(i) +
                        " grades " + userGradesList.get(i) + " time taken " + userTimeList.get(i));
            } catch (NullPointerException n) {
                Log.i("Nullpointerexception", "" + n.getMessage());
                continue;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_back_world, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toworld:
                Intent Layer = new Intent(Leaderboard.this, WorldActivity.class);
                startActivity(Layer);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public String getGrade(int score) {
        switch (score){
            case 10: return "A+";
            case 9: return "A ";
            case 8: return "A-";
            case 7: return "B+";
            case 6: return "B ";
            case 5: return "B-";
            case 4: return "C+";
            case 3: return "C ";
            case 2: return "C-";
            case 1: return "D ";
            case 0: return "F ";
        }
        return "X ";
    }
}
