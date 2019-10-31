package com.example.pokemonacademy.Control;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.pokemonacademy.Entity.QuizzesCompleted;
import com.example.pokemonacademy.Entity.User;
import com.example.pokemonacademy.R;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class IndividualSummaryReportActivity extends AppCompatActivity {

    private DatabaseReference mDatabaseUser;
    private DatabaseReference mDatabaseResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_summary_report);

        LinearLayout linearLayout = findViewById(R.id.indiv_summary);
        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        // Get intent
        Intent intent = getIntent();
        String userId = intent.getStringExtra("userId");

        mDatabaseUser = FirebaseDatabase.getInstance().getReference("USER");
        mDatabaseResult = FirebaseDatabase.getInstance().getReference("QUIZZES_COMPLETED");

        generateHeader(userId);

//        ImageView usericon = (ImageView) findViewById(R.id.usericon);
//        TextView report = (TextView) findViewById(R.id.individualReport);
//        TextView planningScore = (TextView) findViewById(R.id.planningScore);
//        TextView designScore = (TextView) findViewById(R.id.designScore);
//        TextView implementationScore = (TextView) findViewById(R.id.implementationScore);
//        TextView analysisScore = (TextView) findViewById(R.id.analysisScore);
//        TextView maintenanceScore = (TextView) findViewById(R.id.maintenanceScore);
//        TextView testingScore = (TextView) findViewById(R.id.testingScore);

        GridLayout mainGrid = findViewById(R.id.indivReportGrid);

        for (int i = 0; i < mainGrid.getChildCount();i++)
        {
            final CardView cardView = (CardView)mainGrid.getChildAt(i);
            final int counter = i;

            generateReport(userId, counter, cardView);
        }
        //TODO fill in scores with data from databse
        //from User_Completed_Quiz, if completed get score for each quiz for user, else print not completed

//        report.setText("Boham's Report");
//        planningScore.setText("Mini Quiz 1: 50 Mini Quiz 2: 30 Final Quiz : 50");
//        designScore.setText("Mini Quiz 1: 30 Mini Quiz 2: 40 Final Quiz : 70");
//        implementationScore.setText("Mini Quiz 1: 40 Mini Quiz 2: 40 Final Quiz : 10");
//        analysisScore.setText("Mini Quiz 1: 10 Mini Quiz 2: 70 Final Quiz : 30");
//        maintenanceScore.setText("Mini Quiz 1: 70 Mini Quiz 2: 40 Final Quiz : 64");
//        testingScore.setText("Mini Quiz 1: 80 Mini Quiz 2: 98 Final Quiz : 37");
    }

    private void generateHeader(String id){
        DatabaseReference reference = mDatabaseUser.child(id);

        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                String name = user.getName();
                int charId = user.getCharId();

                ImageView usericon = (ImageView) findViewById(R.id.usericon);
                TextView report = (TextView) findViewById(R.id.individualReport);

                usericon.setImageResource(R.drawable.gamelogo); //default
                switch (charId){
                    case 0:
                        usericon.setImageResource(R.drawable.char1);
                        break;
                    case 1:
                        usericon.setImageResource(R.drawable.char2);
                        break;
                    case 2:
                        usericon.setImageResource(R.drawable.char3);
                        break;
                    case 3:
                        usericon.setImageResource(R.drawable.char5);
                }

                report.setText(name + "'s Report");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        reference.addListenerForSingleValueEvent(userListener);
    }

    private void generateReport(final String userId, final int worldId, final CardView cardView){
        DatabaseReference reference = mDatabaseResult.child(userId).child("World" + worldId);

        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String mq1 = "-";
                String mq2 = "-";
                String fq = "-";

                for (DataSnapshot quizzes : dataSnapshot.getChildren()) {
                    QuizzesCompleted quizzesCompleted = quizzes.getValue(QuizzesCompleted.class);
                    if (quizzesCompleted.getCompleted()) {
                        int score = quizzesCompleted.getScore();
                        switch (quizzesCompleted.getMiniQuizId()) {
                            case 0:
                                mq1 = String.valueOf(score);
                                break;
                            case 1:
                                mq2 = String.valueOf(score);
                                break;
                            case 2:
                                fq = String.valueOf(score);
                                break;
                        }
                    }
                }

                LinearLayout indi_summary = (LinearLayout)cardView.getChildAt(0);

                for(int i=1; i<indi_summary.getChildCount(); i++) {
                    String score = "";
                    switch(i) {
                        case 1:
                            score = mq1;
                            break;
                        case 2:
                            score = mq2;
                            break;
                        case 3:
                            score = fq;
                            break;
                    }
                    TextView tv = (TextView) indi_summary.getChildAt(i);
                    tv.setText(tv.getText().toString() + score);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("OverallSummary", "loadPost:onCancelled", databaseError.toException());
            }
        };
        reference.addListenerForSingleValueEvent(userListener);
    }
}
