package com.example.pokemonacademy.Control;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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

/**
 * The IndividualSummaryReportActivity displays the activity_individual_summary_report.xml
 * It allows the user to view the summary report which consists of the mini quiz score and
 * final quiz scores for all the respective SDLC topics.
 *
 * @author  Maggie
 * @since   2019-11-01
 */
public class IndividualSummaryReportActivity extends AppCompatActivity {

    private DatabaseReference mDatabaseUser;
    private DatabaseReference mDatabaseResult;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private String uType;

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

        mAuth = FirebaseAuth.getInstance();
        mDatabaseUser = FirebaseDatabase.getInstance().getReference("USER");
        mDatabaseResult = FirebaseDatabase.getInstance().getReference("QUIZZES_COMPLETED");

        generateHeader(userId);

        DatabaseReference reference = mDatabaseUser.child(mAuth.getCurrentUser().getUid());
        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Intent Layer;
                User user = dataSnapshot.getValue(User.class);
                if (user.getUserType().equals("T")) {
                    uType = "T";
                } else if (user.getUserType().equals("S")) {
                    uType = "S";
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        reference.addListenerForSingleValueEvent(userListener);

//        mDatabaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                questionList = getQuestions2(dataSnapshot, worldID, miniQuizID);
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });


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
                uType = user.getUserType();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_logout_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tomenulp:
                if (uType.equals("S")){
                    Intent Layer = new Intent(IndividualSummaryReportActivity.this, MenuLandingPage.class);
                    startActivity(Layer);
                }else if (uType.equals("T")){
                    Intent Layer = new Intent(IndividualSummaryReportActivity.this, TeacherMenuLandingPage.class);
                    startActivity(Layer);
                }
                return true;
            case R.id.tologout:
                mAuth.signOut();
                Intent Layer = new Intent(IndividualSummaryReportActivity.this, MainActivity.class);
                Toast.makeText(IndividualSummaryReportActivity.this, "Successfully logged out.",
                        Toast.LENGTH_LONG).show();
                startActivity(Layer);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
