package com.example.pokemonacademy.Control;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.example.pokemonacademy.Entity.Question;
import com.example.pokemonacademy.Entity.QuestionChoice;
import com.example.pokemonacademy.Entity.QuizzesCompleted;
import com.example.pokemonacademy.Entity.User;
import com.example.pokemonacademy.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button btnStart;

    private DatabaseReference databaseReferenceQuestion;
    private DatabaseReference databaseReferenceQnsChoice;
    private DatabaseReference databaseReferenceQuizzesCompleted;
    private DatabaseReference databaseReferenceUser;
    private ArrayList<User> userList = new ArrayList<User>();

    private Animation frombottom, fromtop;
    private ImageView pokemonLogo;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        btnStart = findViewById(R.id.button_start);
        pokemonLogo = findViewById(R.id.pokemonlogo);

        fromtop = AnimationUtils.loadAnimation(this, R.anim.fromtop);
        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);

        btnStart.setAnimation(frombottom);
        pokemonLogo.setAnimation(fromtop);

        btnStart.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View view) {
                //tables in the database
                databaseReferenceQuestion = FirebaseDatabase.getInstance().getReference("QUESTION");
                databaseReferenceQnsChoice = FirebaseDatabase.getInstance().getReference("QUESTION_CHOICES");
                databaseReferenceQuizzesCompleted = FirebaseDatabase.getInstance().getReference("QUIZZES_COMPLETED");
                databaseReferenceUser = FirebaseDatabase.getInstance().getReference("USER");

//                populateQuestionTable();
//                populateQuestionChoicesTable();
//                populateQuizzesCompleted();

                Animation animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.fadeout);
                btnStart.startAnimation(animation);

                Intent Layer = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(Layer);
            }
        });
    }

    public void populateQuizzesCompleted(){
        databaseReferenceUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Random rand = new Random();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    final User u = ds.getValue(User.class);
                    Log.i("user", ""+u.getName() + " " + u.getId());
                    userList.add(u);
                }
                for (int i=0; i<userList.size(); i++){
                    for (int worldNum=0; worldNum<6; worldNum++){
                        for (int quizNum=0; quizNum<3; quizNum++){
                            QuizzesCompleted qc = new QuizzesCompleted();
                            qc.setCompleted(false);
                            qc.setUserId(userList.get(i).getId());
                            qc.setWorldId(worldNum);
                            qc.setMiniQuizId(quizNum);
//                            qc.setScore(-1);
//                            qc.setTimeTaken(-1);
                            qc.setScore(rand.nextInt(11));
                            qc.setTimeTaken(rand.nextInt(100));
                            databaseReferenceQuizzesCompleted.child(userList.get(i).getId()).child("World"+worldNum).child("Quiz"+quizNum).setValue(qc);
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    public void populateQuestionTable(){
        AssetManager mngr = getAssets();
        String s;
        try{
            InputStream is = mngr.open("question.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            s = reader.readLine();
            String[] data;
            while(s!=null){
                Log.i(TAG, s);
                data = s.split("\\|");
//                Log.i(TAG, data[1]); // String: WorldId
//                Log.i(TAG, data[2]); // String: QuizId
//                Log.i(TAG, data[3]); // String: QuestionId
//                Log.i(TAG, data[4]); // boolean? attempted
//                Log.i(TAG, data[5]); // int: difficultyLevel
//                Log.i(TAG, data[6]); // int: questionId
//                Log.i(TAG, data[7]); // String: quizId
//                Log.i(TAG, data[8]); // String: worldId
//                Log.i(TAG, data[9]); // String: question
                final Question question = new Question();
                question.setAttempted(false);
                question.setDifficultyLevel(Integer.parseInt(data[5]));
                question.setQuestionId(Integer.parseInt(data[6]));
                question.setQuizId(data[7]);
                question.setWorldId(data[8]);
                question.setQuestion(data[9]);
                databaseReferenceQuestion.child(data[1]).child(data[2]).child(data[3]).setValue(question);
                s = reader.readLine();
            }
        }catch (Exception e){
            Log.i(TAG, ""+e);
        }
    }

    public void populateQuestionChoicesTable(){
        AssetManager mngr = getAssets();
        String s;
        try{
            InputStream is = mngr.open("question_choice.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            s = reader.readLine();
            String[] data;
            while(s!=null){
                Log.i(TAG, s);
                data = s.split("\\|");
//                Log.i(TAG, data[1]); // String: WorldId
//                Log.i(TAG, data[2]); // String: QuizId
//                Log.i(TAG, data[3]); // String: QuestionId
//                Log.i(TAG, data[4]); // String: ChoiceId
//                Log.i(TAG, data[5]); // String: choice
//                Log.i(TAG, data[6]); // int: choiceId
//                Log.i(TAG, data[7]); // boolean: correct
//                Log.i(TAG, data[8]); // int: qnsId
//                Log.i(TAG, data[9]); // boolean: rightChoice
                final QuestionChoice qc = new QuestionChoice();
                qc.setChoice(data[5]);
                qc.setChoiceId(Integer.parseInt(data[6]));
                qc.setCorrect(Boolean.valueOf(data[7]));
                qc.setQnsId(Integer.parseInt(data[8]));
                qc.setRightChoice(Boolean.valueOf(data[9]));
                databaseReferenceQnsChoice.child(data[1]).child(data[2]).child(data[3]).child(data[4]).setValue(qc);
                s = reader.readLine();
            }
        }catch (Exception e){
            Log.i(TAG,""+e);
        }
    }
}
