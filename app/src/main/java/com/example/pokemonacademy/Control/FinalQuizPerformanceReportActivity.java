/**
 * The FinalQuizPerformanceReportActivity displays the activity_final_quiz_performance.xml
 * It allows the teacher to see an overview of the perforamnce on a final quiz.
 *
 * @author  Maggie
 * @version 1.0
 * @since   2019-10-07
 */

package com.example.pokemonacademy.Control;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.pokemonacademy.Entity.Question;
import com.example.pokemonacademy.Entity.QuestionChoice;
import com.example.pokemonacademy.Entity.QuizzesCompleted;
import com.example.pokemonacademy.Entity.UserQnsAns;
import com.example.pokemonacademy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class FinalQuizPerformanceReportActivity extends AppCompatActivity {
    private DatabaseReference mDatabaseQns;
    private DatabaseReference mDatabaseQnsChoice;
    private DatabaseReference mDatabaseQuiz;
    private DatabaseReference mDatabaseAns;

    private static final String TAG = "FinalQuizReport";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_quiz_performance);

        Intent intent = getIntent();
        String worldName = intent.getStringExtra("worldName");
        int worldId = intent.getIntExtra("worldID", -1);

        mDatabaseQns = FirebaseDatabase.getInstance().getReference("QUESTION");
        mDatabaseQnsChoice = FirebaseDatabase.getInstance().getReference("QUESTION_CHOICES");
        mDatabaseQuiz = FirebaseDatabase.getInstance().getReference("QUIZZES_COMPLETED");
        mDatabaseAns = FirebaseDatabase.getInstance().getReference("USER_QUESTION_ANS");

        TextView title = findViewById(R.id.worldFinalQuizReport);
        title.setText("Final Quiz for " + worldName);
        TextView avgTime = findViewById(R.id.averageTime);
        calculateAvgTime(worldId, avgTime);

        LinearLayout layout = findViewById(R.id.finalQuizReportLayout);

        generateReport(worldId, layout);
    }

    private void calculateAvgTime(final int worldId, final TextView tv){
        DatabaseReference reference = mDatabaseQuiz;

        final DecimalFormat df2 = new DecimalFormat("#.##");

        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int sumTime = 0;
                int count = 0;

                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DataSnapshot ds = snapshot.child("World" + worldId).child("Quiz2");
                    QuizzesCompleted quiz = ds.getValue(QuizzesCompleted.class);
                    if(quiz.getCompleted()) {
                        sumTime += quiz.getTimeTaken();
                        count++;
                    }
                }

                if(count>0) {
                    double averageTimeTaken = ((double) sumTime) / count;
                    int mins = (int) (averageTimeTaken / 60);
                    double secs = averageTimeTaken % 60;
                    tv.setText("Average Time Taken: " + mins + " minutes " + df2.format(secs) + " seconds");
                }
                else{
                    tv.setText("No attempts on this quiz yet");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        reference.addListenerForSingleValueEvent(userListener);
    }

    private void generateReport(final int worldId, final LinearLayout layout){
        DatabaseReference reference = mDatabaseQns.child("World" + worldId).child("Quiz2");

        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot question : dataSnapshot.getChildren()){
                    Question q = question.getValue(Question.class);
                    int qId = q.getQuestionId();
                    Log.i(TAG, "onDataChange: generateReport: " + qId);

                    CardView cv = new CardView(FinalQuizPerformanceReportActivity.this);
                    LinearLayout block = new LinearLayout(FinalQuizPerformanceReportActivity.this);
                    block.setOrientation(LinearLayout.VERTICAL);

                    TextView questionText = new TextView(FinalQuizPerformanceReportActivity.this);
                    TextView choice1 = new TextView(FinalQuizPerformanceReportActivity.this);
                    TextView choice2 = new TextView(FinalQuizPerformanceReportActivity.this);
                    TextView choice3 = new TextView(FinalQuizPerformanceReportActivity.this);
                    TextView correctAttemptText = new TextView(FinalQuizPerformanceReportActivity.this);

                    block.addView(questionText, 0);
                    block.addView(choice1, 1);
                    block.addView(choice2, 2);
                    block.addView(choice3, 3);
                    block.addView(correctAttemptText, 4);
                    cv.addView(block);
                    layout.addView(cv);

                    addQuestion(worldId, qId, questionText);
                    addChoices(worldId, qId, block);
                    addCorrectAttempts(worldId, qId, correctAttemptText);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        reference.addListenerForSingleValueEvent(userListener);
    }

    private void addQuestion(final int worldId, final int qnsId, final TextView tv){
        DatabaseReference reference = mDatabaseQns.child("World" + worldId).child("Quiz2").child("Question"+qnsId);

        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i(TAG, "addQuestion: " + qnsId);
                Question q = dataSnapshot.getValue(Question.class);
                tv.setText(q.getQuestion());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        reference.addListenerForSingleValueEvent(userListener);
    }

    private void addChoices(final int worldId, final int qnsId, final LinearLayout block){
        DatabaseReference reference = mDatabaseQnsChoice.child("World" + worldId).child("Quiz2").child("Question" + qnsId);

        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i(TAG, "addChoices: " + qnsId);
                for(DataSnapshot choice : dataSnapshot.getChildren()){
                    QuestionChoice c = choice.getValue(QuestionChoice.class);
                    TextView tv = (TextView) block.getChildAt(c.getChoiceId());
                    tv.setText("-" + c.getChoice());
                    if(c.getRightChoice()){
                        tv.setTextColor(Color.RED);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        reference.addListenerForSingleValueEvent(userListener);
    }

    private void addCorrectAttempts(final int worldId, final int qnsId, final TextView tv){
        DatabaseReference reference = mDatabaseAns;

        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i(TAG, "addCorrectAattempts: " + qnsId);
                int count = 0;
                int correctUsers = 0;

                for(DataSnapshot user : dataSnapshot.getChildren()) {
                    DataSnapshot question = user.child("World"+ worldId).child("Quiz2").child("Question" + qnsId);
                    for(DataSnapshot choice : question.getChildren()) {
                        UserQnsAns ans = choice.getValue(UserQnsAns.class);
                        if (ans.getIsSelected()) {
                            count++;
                            if(ans.getIsRight()){
                                correctUsers++;
                                break;
                            }
                        }
                    }
                }

                if(count > 0) {
                    int percentage = (correctUsers * 100) / count;
                    tv.setText("Correct Attempts: " + percentage + "%\n");
                } else{tv.setText("No attempts on this question yet\n");}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        reference.addListenerForSingleValueEvent(userListener);
    }


//    private void addText(String text, Boolean correctChoice){
//        LinearLayout layout = findViewById(R.id.finalQuizReportLayout);
//        TextView tv = new TextView(this);
//        tv.setText(text);
//        if (correctChoice) {
//            tv.setTextColor(Color.RED);
//        }
//        layout.addView(tv);
//
//
//    }
}
