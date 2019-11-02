package com.example.pokemonacademy.Control;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pokemonacademy.Entity.Question;
import com.example.pokemonacademy.Entity.QuestionChoice;
import com.example.pokemonacademy.Entity.User;
import com.example.pokemonacademy.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class CustomQuizActivity extends AppCompatActivity {

    private ArrayList<String> customWorldIdList; // store unique worldId
    private ArrayList<String> customQuizIdList; // store unique quizId

    private ArrayList<String> customQuizToWorldList; // store the matching index quizid to worldid
    public ArrayList<Question> customQuestionList; // List of question within the custom quiz

    private DatabaseReference questionDB;
    private DatabaseReference questionchoiceDB;

    private int index;
    private String customQuizId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_quiz);

        questionDB = FirebaseDatabase.getInstance().getReference("QUESTION");
        questionchoiceDB = FirebaseDatabase.getInstance().getReference("QUESTION_CHOICES");

        LinearLayout linearLayout = findViewById(R.id.custom_quiz_layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        questionDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                customWorldIdList = new ArrayList<String>();
                customQuizIdList = new ArrayList<String>();
                customQuizToWorldList = new ArrayList<String>();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    String w = ds.getKey();
                    customWorldIdList.add(w);
                }
                for (int i=0; i<customWorldIdList.size(); i++){
                    if (!customWorldIdList.get(i).substring(0,5).equals("World")){
                        for (DataSnapshot ds : dataSnapshot.child(customWorldIdList.get(i)).getChildren()){
                            String q = ds.getKey();
                            if (!q.substring(0,4).equals("Quiz")){
                                customQuizIdList.add(q);
                                customQuizToWorldList.add(customWorldIdList.get(i));
                            }
                        }
                    }
                }
                setSingleEvent();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void setSingleEvent()   {
        Button createBtn = findViewById(R.id.createCustomQuizBtn);
        Button playBtn = findViewById(R.id.playCustomQuizBtn);
        Button viewBtn = findViewById(R.id.customQuizInfoBtn);
        final TextView playTV = findViewById(R.id.enterCustomQuizIdTV);

        createBtn
            .setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     Intent Layer = new Intent(CustomQuizActivity.this, CreateCustomQuizActivity.class);
                     startActivity(Layer);
                 }
            }
        );

        viewBtn
            .setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent Layer = new Intent(CustomQuizActivity.this, CustomQuizInfoActivity.class);
                                        startActivity(Layer);
                                    }
                                }
            );

        playBtn
            .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(playTV.getText().toString().isEmpty())  {
                        Intent Layer = new Intent(CustomQuizActivity.this, CustomQuizInfoActivity.class);
//                        Layer.putExtra("playCQID", playTV.getText().toString());
                        startActivity(Layer);
                    } else {
                        customQuizId = playTV.getText().toString();
                        if (!checkDbCustomQuizId(customQuizId)) {
                            Toast.makeText(CustomQuizActivity.this, "Please enter a correct code.", Toast.LENGTH_LONG).show();
                        } else {
                            questionDB.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    customQuestionList = getQuestions(dataSnapshot,customQuizToWorldList.get(index), customQuizId);
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                }
                            });

                            questionchoiceDB.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    assignQuestionChoice(dataSnapshot.child(customQuizToWorldList.get(index)).child(customQuizId),customQuestionList);
                                    Intent Layer = new Intent(CustomQuizActivity.this, Quiz.class);
                                    Layer.putExtra("customworldID", customQuizToWorldList.get(index));
                                    Layer.putExtra("worldID",-1); // default for custom quiz set worldID to -1
                                    Layer.putExtra("worldName",customQuizId);
                                    Layer.putExtra("miniQuizID",-1 ); // custom quiz can default as -1
                                    Layer.putExtra("miniQuizName",customQuizId);

                                    Bundle bundle = new Bundle();
                                    bundle.putParcelableArrayList("customQuestionList", customQuestionList);
                                    Layer.putExtras(bundle);

                                    startActivity(Layer);
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                }
                            });
                        }
                    }
                }
            });
    }

    private boolean checkDbCustomQuizId(final String customQuizId){
        for (int i=0; i<customQuizIdList.size(); i++){
            if (customQuizId.equals(customQuizIdList.get(i))){
                index = i; // this is to allow reference to the worldid.
                return true;
            }
        }
        return false;
    }

    public ArrayList<Question> getQuestions(DataSnapshot dataSnapshot, String worldID, String quizId){
        dataSnapshot = dataSnapshot.child(worldID).child(quizId); // Question->World->Quiz-> Q1,Q2,Q3
        ArrayList<Question> questionList = new ArrayList<Question>();

        for(DataSnapshot ds: dataSnapshot.getChildren()){
            final Question question = new Question();
            question.setAttempted(false);
            question.setDifficultyLevel(ds.getValue(Question.class).getDifficultyLevel());
            question.setQuestion(ds.getValue(Question.class).getQuestion());
            question.setQuestionId(ds.getValue(Question.class).getQuestionId());
            question.setQuizId(ds.getValue(Question.class).getQuizId());
            Log.i("QuestionDB","===== LOOP ======");
            Log.i("QuestionDB","question: " + question.getQuestion());
            Log.i("QuestionDB","attempted: " + question.getAttempted());
            Log.i("QuestionDB","difficulty level: " + question.getDifficultyLevel());
            Log.i("QuestionDB","questionid: " + question.getQuestionId());
            Log.i("QuestionDB","quizid: " + question.getQuizId());
            questionList.add(question);
        }
        return questionList;

    }

    public void assignQuestionChoice(DataSnapshot dataSnapshot, ArrayList<Question> questionList){
        int questionId;
        DataSnapshot datasnap;
        // iterate through the question list and assign the ArrayList<QuestionChoice>
        for (int i = 0; i<questionList.size(); i++){
            final ArrayList<QuestionChoice> questionChoiceList = new ArrayList<QuestionChoice>();
            questionId = questionList.get(i).getQuestionId();
            Log.i("questionChoice","questionid" + questionId);
            datasnap = dataSnapshot.child("Question"+questionId);
            // Get choice 1,2,3 into choiceList
            for (DataSnapshot ds : datasnap.getChildren()){
                final QuestionChoice questionChoice = ds.getValue(QuestionChoice.class);
                Log.i("questionChoice","==== QC LOOP ====");
                Log.i("questionChoice","choice: " + questionChoice.getChoice());
                Log.i("questionChoice","choiceid: " + questionChoice.getChoiceId());
                Log.i("questionChoice","qnid: " + questionChoice.getQnsId());
                Log.i("questionChoice","rightchoice: " + questionChoice.getRightChoice());
                questionChoiceList.add(questionChoice);
            }
            // Assign choicelist to the question.
            questionList.get(i).setQuestionChoice(questionChoiceList);
        }
    }
}
