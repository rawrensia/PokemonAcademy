package com.example.pokemonacademy.Control;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.pokemonacademy.Entity.QuestionChoice;
import com.example.pokemonacademy.Entity.Question;
import com.example.pokemonacademy.Entity.QuizzesCompleted;
import com.example.pokemonacademy.Entity.UserQnsAns;
import com.example.pokemonacademy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class QuizSummary extends AppCompatActivity {

    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    public String userID;
    private DatabaseReference databaseReferenceUser = FirebaseDatabase.getInstance().getReference("USER");
    private DatabaseReference databaseReferenceQuestion = FirebaseDatabase.getInstance().getReference("QUESTION");
    private DatabaseReference databaseReferenceQnsChoice = FirebaseDatabase.getInstance().getReference("QUESTION_CHOICES");
    private DatabaseReference databaseReferenceUserQnsAns = FirebaseDatabase.getInstance().getReference("USER_QUESTION_ANS");
    private DatabaseReference databaseReferenceUserCompletedQns = FirebaseDatabase.getInstance().getReference("USER_COMPLETED_QNS");
    private DatabaseReference databaseReferenceQuizCompleted = FirebaseDatabase.getInstance().getReference("QUIZZES_COMPLETED");

    TableLayout quizSummaryTableLayout;
    TableRow tableRow1, tableRow2;
    Button b1,b2,b3,b4,b5;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        String worldName = intent.getStringExtra("worldName");
        String miniQuizName = intent.getStringExtra("miniQuizName");
        int worldID = intent.getIntExtra("worldID", -1);
        int[] timeTaken = intent.getIntArrayExtra("timeTaken");
        int miniQuizID = intent.getIntExtra("miniQuizId",-1);
        ArrayList<Question> questionAnswered = intent.getExtras().getParcelableArrayList("questionAnswered");
        ArrayList<QuestionChoice> choiceChosen = intent.getExtras().getParcelableArrayList("choiceChosen");
        ArrayList<QuestionChoice> rightChoice = intent.getExtras().getParcelableArrayList("rightChoice");


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        userID = currentUser.getUid();

        // For pushing to db QUIZZES_COMPLETED
        QuizzesCompleted quizzesCompleted = new QuizzesCompleted();
        quizzesCompleted.setCompleted(true);
        quizzesCompleted.setMiniQuizId(miniQuizID);
        quizzesCompleted.setWorldId(worldID);
        quizzesCompleted.setUserId(userID);
        int tt=0;
        for (int i=0; i<timeTaken.length; i++){
            tt = timeTaken[i]-(-tt);
        }
        quizzesCompleted.setTimeTaken(tt);
        int s=0;
        for (int i=0; i< questionAnswered.size();i++){
            if (choiceChosen.get(i).isCorrect()){
                s = s-(-1);
            }
        }
        quizzesCompleted.setScore(s);
        databaseReferenceQuizCompleted.child(userID).child("World"+worldID).child("Quiz"+miniQuizID).setValue(quizzesCompleted);

        setContentView(R.layout.activity_quiz_summary);
        scrollView = (ScrollView)findViewById(R.id.quizsummaryscrollview);
        quizSummaryTableLayout = (TableLayout)findViewById(R.id.quizsummarytablelayout);

        final int shade1 = Color.parseColor("#F0BE2C");
        final int shade2 = Color.parseColor("#F4D372");

        int[] tvquestion = new int[questionAnswered.size()];
        int[] tvanswer = new int[questionAnswered.size()];
        int[] tvtime = new int[questionAnswered.size()];
        int[] tvcorrect = new int[questionAnswered.size()];

        tvquestion[0] = R.id.question1;
        tvquestion[1] = R.id.question2;
        tvquestion[2] = R.id.question3;
        tvquestion[3] = R.id.question4;
        tvquestion[4] = R.id.question5;
//        tvquestion[5] = R.id.question6;
//        tvquestion[6] = R.id.question7;
//        tvquestion[7] = R.id.question8;
//        tvquestion[8] = R.id.question9;
//        tvquestion[9] = R.id.question10;

        tvanswer[0] = R.id.answer1;
        tvanswer[1] = R.id.answer2;
        tvanswer[2] = R.id.answer3;
        tvanswer[3] = R.id.answer4;
        tvanswer[4] = R.id.answer5;
//        tvanswer[5] = R.id.answer6;
//        tvanswer[6] = R.id.answer7;
//        tvanswer[7] = R.id.answer8;
//        tvanswer[8] = R.id.answer9;
//        tvanswer[9] = R.id.answer10;

        tvtime[0] = R.id.time1;
        tvtime[1] = R.id.time2;
        tvtime[2] = R.id.time3;
        tvtime[3] = R.id.time4;
        tvtime[4] = R.id.time5;
//        tvtime[5] = R.id.time6;
//        tvtime[6] = R.id.time7;
//        tvtime[7] = R.id.time8;
//        tvtime[8] = R.id.time9;
//        tvtime[9] = R.id.time10;

        tvcorrect[0] = R.id.correct1;
        tvcorrect[1] = R.id.correct2;
        tvcorrect[2] = R.id.correct3;
        tvcorrect[3] = R.id.correct4;
        tvcorrect[4] = R.id.correct5;
//        tvcorrect[5] = R.id.correct6;
//        tvcorrect[6] = R.id.correct7;
//        tvcorrect[7] = R.id.correct8;
//        tvcorrect[8] = R.id.correct9;
//        tvcorrect[9] = R.id.correct10;

        TextView quizHeader = (TextView)findViewById(R.id.quiz_type_header);
        quizHeader.setText(miniQuizName);

        for (int i=0; i<questionAnswered.size(); i++){
            TextView question = (TextView)findViewById(tvquestion[i]);
            TextView answer = (TextView)findViewById(tvanswer[i]);
            TextView time = (TextView)findViewById(tvtime[i]);
            TextView correct = (TextView)findViewById(tvcorrect[i]);

            Question q = questionAnswered.get(i);
            String qs = q.question;
            try{
                qs = qs.substring(0,10) + "...";
            } catch (Exception e){
                Log.d("exception","qs.substring");
            }
            question.setText(qs);

            String rc = rightChoice.get(i).choice;
            try{
                rc = rc.substring(0,10) + "...";
            } catch (Exception e){
                Log.d("exception","rc.substring");
            }
            answer.setText(rc);
            time.setText(""+timeTaken[i]);
            if (choiceChosen.get(i).isCorrect()){
                correct.setText("Correct" );
            } else {
                correct.setText("Wrong" );
            }

            //TODO
            // set on click for Correct
            // go to activity which shows the full question, selected answer and the correct answer.

        }




//        TextView q1 = (TextView)findViewById(R.id.question1);
//        TextView q2 = (TextView)findViewById(R.id.question2);
//        TextView q3 = (TextView)findViewById(R.id.question3);
//        TextView q4 = (TextView)findViewById(R.id.question4);
//        TextView q5 = (TextView)findViewById(R.id.question5);
//        TextView q6 = (TextView)findViewById(R.id.question6);
//        TextView q7 = (TextView)findViewById(R.id.question7);
//        TextView q8 = (TextView)findViewById(R.id.question8);
//        TextView q9 = (TextView)findViewById(R.id.question9);
//        TextView q10 = (TextView)findViewById(R.id.question10);
//
//        TextView a1 = (TextView)findViewById(R.id.answer1);
//        TextView a2 = (TextView)findViewById(R.id.answer2);
//        TextView a3 = (TextView)findViewById(R.id.answer3);
//        TextView a4 = (TextView)findViewById(R.id.answer4);
//        TextView a5 = (TextView)findViewById(R.id.answer5);
//        TextView a6 = (TextView)findViewById(R.id.answer6);
//        TextView a7 = (TextView)findViewById(R.id.answer7);
//        TextView a8 = (TextView)findViewById(R.id.answer8);
//        TextView a9 = (TextView)findViewById(R.id.answer9);
//        TextView a10 = (TextView)findViewById(R.id.answer10);
//
//        TextView t1 = (TextView)findViewById(R.id.time1);
//        TextView t2 = (TextView)findViewById(R.id.time2);
//        TextView t3 = (TextView)findViewById(R.id.time3);
//        TextView t4 = (TextView)findViewById(R.id.time4);
//        TextView t5 = (TextView)findViewById(R.id.time5);
//        TextView t6 = (TextView)findViewById(R.id.time6);
//        TextView t7 = (TextView)findViewById(R.id.time7);
//        TextView t8 = (TextView)findViewById(R.id.time8);
//        TextView t9 = (TextView)findViewById(R.id.time9);
//        TextView t10 = (TextView)findViewById(R.id.time10);
//
//        TextView c1 = (TextView)findViewById(R.id.correct1);
//        TextView c2 = (TextView)findViewById(R.id.correct2);
//        TextView c3 = (TextView)findViewById(R.id.correct3);
//        TextView c4 = (TextView)findViewById(R.id.correct4);
//        TextView c5 = (TextView)findViewById(R.id.correct5);
//        TextView c6 = (TextView)findViewById(R.id.correct6);
//        TextView c7 = (TextView)findViewById(R.id.correct7);
//        TextView c8 = (TextView)findViewById(R.id.correct8);
//        TextView c9 = (TextView)findViewById(R.id.correct9);
//        TextView c10 = (TextView)findViewById(R.id.correct10);

//        for (int i = 0; i<questionAnswered.size(); i++){
//            final TextView question = new TextView(this);
//            final TextView answer = new TextView(this);
//            final TextView time = new TextView(this);
//            final Button correctBtn = new Button(this);
//            final Question q = questionAnswered.get(i);
//            question.setText("Q"+ i+1 + ") " + q.question);
//            for (int j=0; j<3; j++){
//                final Choice c = q.choiceOptions.get(j);
//                if (c.rightChoice){
//                    answer.setText(c.choice);
//                    break;
//                }
//            }
//            time.setText(timeTaken[i]);
//            if (choiceChosen.get(i).rightChoice){
//                correctBtn.setText("Correct" );
//            } else {
//                correctBtn.setText("Wrong" );
//            }
//
//            final TableRow tableRow = new TableRow(this);
//            question.setWidth(400);
//            question.setMaxHeight(200);
//            correctBtn.setHeight(200);
//            tableRow.addView(question);
//            tableRow.addView(correctBtn);
//
//            quizSummaryTableLayout.addView(tableRow);
//        }

//        tableRow1 = new TableRow(this);
//        tableRow2 = new TableRow(this);
//
//        b1 = new Button(this);
//        b2 = new Button(this);
//        b3 = new Button(this);
//        b4 = new Button(this);
//        b5 = new Button(this);
//
//        b1.setText("Column 1");
//        b2.setText("Column 2");
//        b3.setText("Column 3");
//        b4.setText("Column 4");
//        b5.setText("Column 5");
//
//        tableRow1.addView(b1);
//        tableRow1.addView(b2);
//        tableRow1.addView(b3);
//        tableRow1.addView(b4);
//        tableRow1.addView(b5);
//
//        quizSummaryTableLayout.addView(tableRow1);
//        quizSummaryTableLayout.addView(tableRow2);

//        scrollView.addView(b1);



        for (int i=0;i<5; i++){
            Log.d("time","Time Taken " + timeTaken[i]);
            Log.d("question","Question: " + questionAnswered.get(i).question);
            Log.d("choice","Selected Choice: " + choiceChosen.get(i).choice);
        }

        Button nextBtn = (Button)findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent intent = getIntent();
                String worldName = intent.getStringExtra("worldName");
                int worldID = intent.getIntExtra("worldID", -1);

                Intent Layer = new Intent(QuizSummary.this, MiniQuizLandingPage.class);
                Layer.putExtra("worldName", worldName);
                Layer.putExtra("worldID", worldID);
                startActivity(Layer);
            }
        });
    }
}
