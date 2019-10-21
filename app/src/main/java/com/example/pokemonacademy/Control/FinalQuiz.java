package com.example.pokemonacademy.Control;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.pokemonacademy.R;
import java.util.Timer;
import java.util.TimerTask;

public class FinalQuiz extends AppCompatActivity {

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
