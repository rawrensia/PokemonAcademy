package com.example.pokemonacademy.Control;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pokemonacademy.R;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

public class MiniQuiz extends AppCompatActivity {

    private int selectedChoice = 0;
    int counter = 0;
    // Get list of questions
    // Assign the questions their choices
    // Get user's pokemon
    // Get user's health

    // Hardcode
    private int userpokemonhp = 100;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_quiz);

        Intent intent = getIntent();
        String worldName = intent.getStringExtra("worldName");
        String miniQuizNum = intent.getStringExtra("miniQuizNum");
        int worldID = intent.getIntExtra("worldID", -1);

        // initialize background
        TextView tv = (TextView)findViewById(R.id.miniquiztitle);
        tv.setText(miniQuizNum);
        RelativeLayout questionlayout = (RelativeLayout)findViewById(R.id.questionlayout);
        questionlayout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.questionbackground, null));
        ConstraintLayout battlelayout = (ConstraintLayout)findViewById(R.id.battlelayout);
        battlelayout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.pokemonstandingbackground, null));
        ConstraintLayout answerlayout = (ConstraintLayout)findViewById(R.id.answerlayout);
        answerlayout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.answerbackground, null));
        ImageView enemypokemon = (ImageView)findViewById(R.id.enemypokemon);
        enemypokemon.setImageResource(getRandomEnemyImage());

        // initialize buttons
        final TextView answeroption1 = (TextView)findViewById(R.id.answeroption1);
        final TextView answeroption2 = (TextView)findViewById(R.id.answeroption2);
        final TextView answeroption3 = (TextView)findViewById(R.id.answeroption3);

        final int greenColor = Color.parseColor("#33FF93");
        final int yellowColor = Color.parseColor("#D5E3A1");
        final int redColor = Color.parseColor("#E73B3B");

        final Button choice1btn = (Button)findViewById(R.id.choice1btn);
        final Button choice2btn = (Button)findViewById(R.id.choice2btn);
        final Button choice3btn = (Button)findViewById(R.id.choice3btn);
        final Button attackbtn = (Button)findViewById(R.id.attackbtn);

        attackbtn.setBackgroundColor(redColor);
        attackbtn.setVisibility(View.INVISIBLE);

        choice1btn.setBackgroundColor(yellowColor);
        choice2btn.setBackgroundColor(yellowColor);
        choice3btn.setBackgroundColor(yellowColor);

        choice1btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choice1btn.setBackgroundColor(greenColor);
                choice2btn.setBackgroundColor(yellowColor);
                choice3btn.setBackgroundColor(yellowColor);

                answeroption1.setBackgroundColor(greenColor);
                answeroption2.setBackgroundColor(yellowColor);
                answeroption3.setBackgroundColor(yellowColor);

                attackbtn.setVisibility(View.VISIBLE);
                selectedChoice = 1;
            }
        });
        choice2btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choice2btn.setBackgroundColor(greenColor);
                choice1btn.setBackgroundColor(yellowColor);
                choice3btn.setBackgroundColor(yellowColor);

                answeroption2.setBackgroundColor(greenColor);
                answeroption1.setBackgroundColor(yellowColor);
                answeroption3.setBackgroundColor(yellowColor);

                attackbtn.setVisibility(View.VISIBLE);
                selectedChoice = 2;
            }
        });
        choice3btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choice3btn.setBackgroundColor(greenColor);
                choice2btn.setBackgroundColor(yellowColor);
                choice1btn.setBackgroundColor(yellowColor);

                answeroption3.setBackgroundColor(greenColor);
                answeroption2.setBackgroundColor(yellowColor);
                answeroption1.setBackgroundColor(yellowColor);

                attackbtn.setVisibility(View.VISIBLE);
                selectedChoice = 3;
            }
        });

        attackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // initialize health bar for enemy and user pokemons
        ProgressBar enemyhealthbar = (ProgressBar)findViewById(R.id.enemypokemonhealth);
        ProgressBar userpokemonhealthbar = (ProgressBar)findViewById(R.id.userpokemonhealth);
        userpokemonhealthbar.setProgress(userpokemonhp);


        // Get list of questions for 3 difficulty levels from db

        // Set Question
        TextView questionTv = (TextView)findViewById(R.id.questiontext);

        // Set answer options
        answeroption1.setText("1) Test1");
        answeroption2.setText("2) Test2");
        answeroption3.setText("3) Test3");
    }
    // Require from DB
    // User's selected pokemon
    // User's selected pokemon health
    // The mini quiz questions

    // TODO
    // When mini quiz is completed, update DB that user has completed the miniquiz of this world
    //

    private int getRandomEnemyImage() {
        TypedArray imgs = getResources().obtainTypedArray(R.array.pokemon_imgs);
        // or set you ImageView's resource to the id
        int id = imgs.getResourceId(new Random().nextInt(imgs.length()), -1); //-1 is default if nothing is found (we don't care)
        imgs.recycle();
        return id;
    }
}
