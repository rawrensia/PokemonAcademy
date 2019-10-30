package com.example.pokemonacademy.Control;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.pokemonacademy.Entity.QuestionChoice;
import com.example.pokemonacademy.Entity.Question;
import com.example.pokemonacademy.Entity.QuizzesCompleted;
import com.example.pokemonacademy.Entity.UserCompletedQns;
import com.example.pokemonacademy.Entity.UserQnsAns;
import com.example.pokemonacademy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    String worldName;
    int worldID;
    int[] timeTaken;
    String miniQuizName;
    int miniQuizID;
    ArrayList<Question> questionAnswered;
    ArrayList<QuestionChoice> choiceChosen;
    ArrayList<QuestionChoice> rightChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        worldName = intent.getStringExtra("worldName");
        miniQuizName = intent.getStringExtra("miniQuizName");
        worldID = intent.getIntExtra("worldID", -1);
        timeTaken = intent.getIntArrayExtra("timeTaken");
        miniQuizID = intent.getIntExtra("miniQuizId",-1);
        questionAnswered = intent.getExtras().getParcelableArrayList("questionAnswered");
        choiceChosen = intent.getExtras().getParcelableArrayList("choiceChosen");
        rightChoice = intent.getExtras().getParcelableArrayList("rightChoice");


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
        for (int i=0; i<timeTaken.length; i++){tt = timeTaken[i]-(-tt);}
        quizzesCompleted.setTimeTaken(tt);
        int s=0;
        for (int i=0; i< questionAnswered.size();i++){
            if (choiceChosen.get(i).isCorrect()){ s = s-(-1);}
        }
        quizzesCompleted.setScore(s);
        databaseReferenceQuizCompleted.child(userID).child("World"+worldID).child("Quiz"+miniQuizID).setValue(quizzesCompleted);

        // For pushing to db USER_COMPLETED_QNS
        for (int i=0; i<questionAnswered.size();i++){
            final UserCompletedQns userCompletedQns = new UserCompletedQns();
            userCompletedQns.setCompleted(true);
            userCompletedQns.setQnsId(questionAnswered.get(i).getQuestionId());
            userCompletedQns.setUserId(userID);
            databaseReferenceUserCompletedQns.child(userID).child("Question"+userCompletedQns.getQnsId()).setValue(userCompletedQns);
        }

        // For pushing to db USER_QUESTION_ANS
        for (int i=0; i<questionAnswered.size();i++){
            for (int j=0; j<3; j++){
                final UserQnsAns userQnsAns = new UserQnsAns();
                userQnsAns.setChoiceId(questionAnswered.get(i).getQuestionChoice().get(j).getChoiceId());
                userQnsAns.setIsRight(questionAnswered.get(i).getQuestionChoice().get(j).getRightChoice());
                if (choiceChosen.get(i).getChoiceId() == userQnsAns.getChoiceId()){
                    userQnsAns.setIsSelected(true);
                } else {
                    userQnsAns.setIsSelected(false);
                }
                userQnsAns.setQnsId(questionAnswered.get(i).getQuestionId());
                userQnsAns.setUserId(userID);
                databaseReferenceUserQnsAns.child(userID).child("Question"+userQnsAns.getQnsId()).child("Choice"+userQnsAns.getChoiceId()).setValue(userQnsAns);
            }

        }

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

        tvanswer[0] = R.id.answer1;
        tvanswer[1] = R.id.answer2;
        tvanswer[2] = R.id.answer3;
        tvanswer[3] = R.id.answer4;
        tvanswer[4] = R.id.answer5;

        tvtime[0] = R.id.time1;
        tvtime[1] = R.id.time2;
        tvtime[2] = R.id.time3;
        tvtime[3] = R.id.time4;
        tvtime[4] = R.id.time5;

        tvcorrect[0] = R.id.correct1;
        tvcorrect[1] = R.id.correct2;
        tvcorrect[2] = R.id.correct3;
        tvcorrect[3] = R.id.correct4;
        tvcorrect[4] = R.id.correct5;

        TextView quizHeader = (TextView)findViewById(R.id.quiz_type_header);
        quizHeader.setText(miniQuizName);

        for (int i=0; i<questionAnswered.size(); i++){
            final int selected = i;
            final TextView question = (TextView)findViewById(tvquestion[i]);
            TextView answer = (TextView)findViewById(tvanswer[i]);
            TextView time = (TextView)findViewById(tvtime[i]);
            TextView correct = (TextView)findViewById(tvcorrect[i]);

            // display question
            Question q = questionAnswered.get(i);
            String qs = q.question;
            try{
                qs = qs.substring(0,10) + "...";
            } catch (Exception e){
                Log.d("exception","qs.substring");
            }
            question.setText(qs);

//            String rc = rightChoice.get(i).choice;
//            try{
//                rc = rc.substring(0,10) + "...";
//            } catch (Exception e){
//                Log.d("exception","rc.substring");
//            }
//            answer.setText(rc);

            // display chosen choice
            String cc = choiceChosen.get(i).getChoice();
            try{
                cc = cc.substring(0,10) + "...";
            } catch (Exception e){
                Log.d("exception","rc.substring");
            }
            answer.setText(cc);

            // display timetaken
            time.setText(""+timeTaken[i]);

            // display correct
            if (choiceChosen.get(i).isCorrect()){
                correct.setText("Correct" );
            } else {
                correct.setText("Wrong" );
            }

            correct.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view){
                    if (miniQuizID != 2) {
                        Intent Layer = new Intent(QuizSummary.this, QuestionReview.class);

                        Layer.putExtra("questionAnswered", questionAnswered.get(selected).question);
                        Layer.putExtra("choiceChosen", choiceChosen.get(selected).choice);
                        Layer.putExtra("rightChoice", rightChoice.get(selected).choice);

                        startActivity(Layer);
                    }
                }
            });

            //TODO
            // set on click for Correct
            // go to activity which shows the full question, selected answer and the correct answer.

        }

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

                Intent Layer = new Intent(QuizSummary.this, QuizLandingPage.class);
                Layer.putExtra("worldName", worldName);
                Layer.putExtra("worldID", worldID);
                startActivity(Layer);
            }
        });
    }
}
