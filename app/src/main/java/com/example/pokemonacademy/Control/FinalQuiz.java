package com.example.pokemonacademy.Control;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.pokemonacademy.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;
import java.util.TimerTask;

public class FinalQuiz extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference databaseReferenceUser = FirebaseDatabase.getInstance().getReference("USER");
    private DatabaseReference databaseReferenceQuestion = FirebaseDatabase.getInstance().getReference("QUESTION");
    private DatabaseReference databaseReferenceQnsChoice = FirebaseDatabase.getInstance().getReference("QUESTION_CHOICES");
    private DatabaseReference databaseReferenceUserQnsAns = FirebaseDatabase.getInstance().getReference("USER_QUESTION_ANS");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_quiz);

        // For timer
//        int timeTaken = 0;
//        final Timer startTime = new Timer();
//        final ProgressBar timeProgressBar = (ProgressBar)findViewById(R.id.userpokemonhealth);
//        TimerTask tt = new TimerTask(){
//            @Override
//            public void run (){
//                timeTaken++;
//                timeProgressBar.setProgress(timeTaken);
//            }
//        };
//        startTime.schedule(tt,0,1000);
    }
}
