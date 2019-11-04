/**
 * The QuizSummary displays the activity_quiz_summary.xml
 * If the user attempted a custom quiz or mini quiz, it displays a list of
 * question which was presented to the user,
 * the user's selected answer and the user has gotten the question correct
 * The user would be able to select the question to view the full question
 * and the correct answer to the question
 *
 * If the user attemmpted a final quiz, the user would be presented with his grade
 * obtained for the quiz.
 *
 * @author  Lawrann
 * @version 1.0
 * @since   2019-10-07
 */

package com.example.pokemonacademy.Control;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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

import org.w3c.dom.Text;

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
            if (choiceChosen.get(i).getRightChoice()){ s = s-(-1);}
        }
        quizzesCompleted.setScore(s);
        if (miniQuizID!=-1){
            databaseReferenceQuizCompleted.child(userID).child("World"+worldID).child("Quiz"+miniQuizID).setValue(quizzesCompleted);
        } else {
            databaseReferenceQuizCompleted.child(userID).child(worldName).child(miniQuizName).setValue(quizzesCompleted);
        }


        // For pushing to db USER_COMPLETED_QNS
        for (int i=0; i<questionAnswered.size();i++){
            final UserCompletedQns userCompletedQns = new UserCompletedQns();
            userCompletedQns.setCompleted(true);
            userCompletedQns.setQnsId(questionAnswered.get(i).getQuestionId());
            userCompletedQns.setUserId(userID);
            if (miniQuizID!=-1){
                databaseReferenceUserCompletedQns.child(userID).child("World"+worldID).child("Quiz"+miniQuizID).child("Question"+userCompletedQns.getQnsId()).setValue(userCompletedQns);
            } else {
                databaseReferenceUserCompletedQns.child(userID).child(worldName).child(miniQuizName).child("Question"+userCompletedQns.getQnsId()).setValue(userCompletedQns);
            }
        }

        // For pushing to db USER_QUESTION_ANS
        for (int i=0; i<questionAnswered.size();i++){
            for (int j=0; j<3; j++){
                final UserQnsAns userQnsAns = new UserQnsAns();
//                userQnsAns.setChoiceId(questionAnswered.get(i).getQuestionChoice().get(j).getChoiceId());
                userQnsAns.setChoiceId(j);
                Log.i("choiceid","Choice"+userQnsAns.getChoiceId());
                userQnsAns.setIsRight(questionAnswered.get(i).getQuestionChoice().get(j).getRightChoice());
                if (choiceChosen.get(i).getChoiceId() == userQnsAns.getChoiceId()){
                    userQnsAns.setIsSelected(true);
                } else {
                    userQnsAns.setIsSelected(false);
                }
                userQnsAns.setQnsId(questionAnswered.get(i).getQuestionId());
                userQnsAns.setUserId(userID);
                if (miniQuizID!=-1){
                    databaseReferenceUserQnsAns.child(userID).child("World"+worldID).child("Quiz"+miniQuizID).child("Question"+userQnsAns.getQnsId()).child("Choice"+userQnsAns.getChoiceId()).setValue(userQnsAns);
                } else {
                    databaseReferenceUserQnsAns.child(userID).child(worldName).child(miniQuizName).child("Question"+userQnsAns.getQnsId()).child("Choice"+userQnsAns.getChoiceId()).setValue(userQnsAns);
                }
            }
        }

        setContentView(R.layout.activity_quiz_summary);
        scrollView = (ScrollView)findViewById(R.id.quizsummaryscrollview);
        quizSummaryTableLayout = (TableLayout)findViewById(R.id.quizsummarytablelayout);

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

        if (miniQuizID!=2){
            for (int i=0; i<questionAnswered.size(); i++){
                final int selected = i;
                TextView question = (TextView)findViewById(tvquestion[i]);
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
                if (choiceChosen.get(i).getRightChoice()){
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
            }

            TextView finalQuizTV = (TextView)findViewById(R.id.finalQuizTV);
            finalQuizTV.setVisibility(View.INVISIBLE);
            TextView finalQuizScoreTV = (TextView)findViewById(R.id.finalQuizScoreTv);
            finalQuizScoreTV.setVisibility(View.INVISIBLE);

        } else if (miniQuizID == 2) {
            TableLayout quizSummaryTableLayout = (TableLayout)findViewById(R.id.quizsummarytablelayout);
            quizSummaryTableLayout.setVisibility(View.INVISIBLE);
            TextView finalQuizTV = (TextView)findViewById(R.id.finalQuizTV);
            finalQuizTV.setVisibility(View.VISIBLE);
            TextView finalQuizScoreTV = (TextView)findViewById(R.id.finalQuizScoreTv);
            finalQuizScoreTV.setVisibility(View.VISIBLE);
            s=0;
            for (int j=0; j< questionAnswered.size();j++){
                if (choiceChosen.get(j).getRightChoice()){ s = s-(-1);}
            }
            int percentage = (s*100/10);
            if (s==10) {
                finalQuizTV.setText("Congratulations!\n\nFor " + worldName + "");
                finalQuizScoreTV.setText("You scored " + percentage+"%\nGrade: A+");
            } else if (s==9) {
                finalQuizTV.setText("Congratulations!\n\nFor " + worldName + "");
                finalQuizScoreTV.setText("You scored " + percentage+"%\nGrade: A");
            } else if (s==8) {
                finalQuizTV.setText("Good Try!\n\nFor " + worldName + "");
                finalQuizScoreTV.setText("You scored " + percentage+"%\nGrade: A-");
            } else if (s==7) {
                finalQuizTV.setText("Good Try!\n\nFor " + worldName + "");
                finalQuizScoreTV.setText("You scored " + percentage+"%\nGrade: B+");
            } else if (s==6) {
                finalQuizTV.setText("Good Try!\n\nFor " + worldName + "");
                finalQuizScoreTV.setText("You scored " + percentage+"%\nGrade: B");
            } else if (s==5) {
                finalQuizTV.setText("Try harder next time!\n\nFor " + worldName + "");
                finalQuizScoreTV.setText("You scored " + percentage+"%\nGrade: B-");
            } else if (s==4) {
                finalQuizTV.setText("Try harder next time!\n\nFor " + worldName + "");
                finalQuizScoreTV.setText("You scored " + percentage+"%\nGrade: C+");
            } else if (s==3) {
                finalQuizTV.setText("Please read up on the lecture notes before attempting!\n\nFor " + worldName + " World");
                finalQuizScoreTV.setText("You scored " + percentage+"%\nGrade: C");
            } else if (s==2) {
                finalQuizTV.setText("Please read up on the lecture notes before attempting!\n\nFor " + worldName + " World");
                finalQuizScoreTV.setText("You scored " + percentage+"%\nGrade: C-");
            } else if (s==1) {
                finalQuizTV.setText("Please read up on the lecture notes before attempting!\n\nFor " + worldName + " World");
                finalQuizScoreTV.setText("You scored " + percentage+"%\nGrade: D");
            } else if (s==0) {
                finalQuizTV.setText("Please read up on the lecture notes before attempting!\n\nFor " + worldName + " World");
                finalQuizScoreTV.setText("You scored " + percentage+"%\nGrade: F");
            }
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

                if (miniQuizID!=-1) {
                    Intent Layer = new Intent(QuizSummary.this, QuizLandingPage.class);
                    Layer.putExtra("worldName", worldName);
                    Layer.putExtra("worldID", worldID);
                    startActivity(Layer);
                    finish();
                } else {
                    Intent Layer = new Intent(QuizSummary.this, CustomQuizActivity.class);
                    startActivity(Layer);
                    finish();
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_mini_quiz_lp, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tocontent:
                Intent Layer = getIntent();
                String worldName = Layer.getStringExtra("worldName");
                int worldID = Layer.getIntExtra("worldID", -1);
                Intent intent = new Intent(getApplicationContext(), ContentActivity.class);
                intent.putExtra("worldName", worldName);
                intent.putExtra("worldID", worldID);
                startActivity(intent);
                return true;
            case R.id.toworld:
                Layer = new Intent(QuizSummary.this, WorldActivity.class);
                startActivity(Layer);
                return true;
            case R.id.tologout:
                mAuth.signOut();
                Layer = new Intent(QuizSummary.this, MainActivity.class);
                Toast.makeText(QuizSummary.this, "Successfully logged out.",
                        Toast.LENGTH_SHORT).show();
                startActivity(Layer);
                finish();
                return true;
            case R.id.toleaderboard:
                Layer = new Intent(QuizSummary.this, Leaderboard.class);
                startActivity(Layer);
                Toast.makeText(QuizSummary.this, "Welcome to the leaderboard!",
                        Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
