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

import com.example.pokemonacademy.Entity.Question;
import com.example.pokemonacademy.R;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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
    private int enemypokemonhp = 100;
    private boolean endBattleFlag = false;
    private int num_of_question = 10;
    public ArrayList<Question> questionAnswered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_quiz);

        // Get intent
        Intent intent = getIntent();
        String worldName = intent.getStringExtra("worldName");
        String miniQuizNum = intent.getStringExtra("miniQuizNum");
        int worldID = intent.getIntExtra("worldID", -1);

        // initialize background & pokemons
        TextView tv = (TextView)findViewById(R.id.miniquiztitle);
        RelativeLayout questionlayout = (RelativeLayout)findViewById(R.id.questionlayout);
        ConstraintLayout battlelayout = (ConstraintLayout)findViewById(R.id.battlelayout);
        ConstraintLayout answerlayout = (ConstraintLayout)findViewById(R.id.answerlayout);
        ImageView enemypokemon = (ImageView)findViewById(R.id.enemypokemon);
        ImageView userpokemon = (ImageView)findViewById(R.id.userpokemon);

        tv.setText(miniQuizNum);
        questionlayout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.questionbackground, null));
        battlelayout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.pokemonstandingbackground, null));
        answerlayout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.answerbackground, null));
        enemypokemon.setImageResource(getRandomEnemyImage());
        //userpokemon.setImageResource(); // Set this once db is up and we can check what is the user's pokemon


        // initialize question, answers and buttons
        final int greenColor = Color.parseColor("#33FF93");
        final int yellowColor = Color.parseColor("#D5E3A1");
        final int redColor = Color.parseColor("#E73B3B");
        final int transparent = Color.parseColor("#00FFFFFF");

        final TextView question = (TextView)findViewById(R.id.questiontext);
        final TextView answeroption1 = (TextView)findViewById(R.id.answeroption1);
        final TextView answeroption2 = (TextView)findViewById(R.id.answeroption2);
        final TextView answeroption3 = (TextView)findViewById(R.id.answeroption3);

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
                answeroption2.setBackgroundColor(transparent);
                answeroption3.setBackgroundColor(transparent);

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
                answeroption1.setBackgroundColor(transparent);
                answeroption3.setBackgroundColor(transparent);

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
                answeroption2.setBackgroundColor(transparent);
                answeroption1.setBackgroundColor(transparent);

                attackbtn.setVisibility(View.VISIBLE);
                selectedChoice = 3;
            }
        });

        // initialize health bar for enemy and user pokemons
        ProgressBar enemyhealthbar = (ProgressBar)findViewById(R.id.enemypokemonhealth);
        ProgressBar userpokemonhealthbar = (ProgressBar)findViewById(R.id.userpokemonhealth);
        enemyhealthbar.setProgress(enemypokemonhp);
        userpokemonhealthbar.setProgress(userpokemonhp);

        // Get list of questions for 3 difficulty levels from db

        // Set Question
        TextView questionTv = (TextView)findViewById(R.id.questiontext);

        // Set question & answer options
        question.setText("Option 3 is the correct option");
        answeroption1.setText("1) Option 1");
        answeroption2.setText("2) Option 2");
        answeroption3.setText("3) Option 3");

        attackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressBar enemyhealthbar = (ProgressBar)findViewById(R.id.enemypokemonhealth);
                ProgressBar userpokemonhealthbar = (ProgressBar)findViewById(R.id.userpokemonhealth);
                TextView question = (TextView)findViewById(R.id.questiontext);
                TextView answeroption1 = (TextView)findViewById(R.id.answeroption1);
                TextView answeroption2 = (TextView)findViewById(R.id.answeroption2);
                TextView answeroption3 = (TextView)findViewById(R.id.answeroption3);
                Button choice1btn = (Button)findViewById(R.id.choice1btn);
                Button choice2btn = (Button)findViewById(R.id.choice2btn);
                Button choice3btn = (Button)findViewById(R.id.choice3btn);
                Button attackbtn = (Button)findViewById(R.id.attackbtn);
                ImageView enemypokemon = (ImageView)findViewById(R.id.enemypokemon);
                ImageView userpokemon = (ImageView)findViewById(R.id.userpokemon);

                // Check if selectedChoice is correct.  if (choiceOptions[selectedChoice].isRight())
                if (selectedChoice==3){
                    int dmg = enemypokemonhp/num_of_question;
                    enemypokemonhp = enemypokemonhp - dmg;
                    enemyhealthbar.setProgress(enemypokemonhp);
                    float x = enemypokemon.getX();
                    float y = enemypokemon.getY();
                    enemypokemon.setX(x-5);
                    enemypokemon.setY(y-5);
                    delay(300);

                    if (enemypokemonhp <= 0){endBattleFlag = true;}
                } else {
                    userpokemonhp = userpokemonhp - 10;
                    userpokemonhealthbar.setProgress(userpokemonhp);
                    float x = userpokemon.getX();
                    float y = userpokemon.getY();
                    userpokemon.setX(x-5);
                    userpokemon.setY(y-5);
                    delay(300);
                    if (userpokemonhp <= 0){endBattleFlag = true;}
                }
                num_of_question = num_of_question - 1;
                // Save question, answer, time, right/wrong to db


                if (endBattleFlag){
                    Intent intent = getIntent();
                    String worldName = intent.getStringExtra("worldName");
                    int worldID = intent.getIntExtra("worldID", -1);
                    TextView miniQuizTv = (TextView)findViewById(R.id.miniquiztitle);

                    Intent Layer = new Intent(MiniQuiz.this, MiniQuizLandingPage.class);
                    Layer.putExtra("miniQuizNum", miniQuizTv.getText().toString());
                    Layer.putExtra("worldName", worldName);
                    Layer.putExtra("worldID", worldID);
                    startActivity(Layer);
                }

                // Reset button states
                selectedChoice = 0;
                answeroption1.setBackgroundColor(transparent);
                answeroption2.setBackgroundColor(transparent);
                answeroption3.setBackgroundColor(transparent);
                choice1btn.setBackgroundColor(yellowColor);
                choice2btn.setBackgroundColor(yellowColor);
                choice3btn.setBackgroundColor(yellowColor);
                attackbtn.setVisibility(View.INVISIBLE);

                // Display next question
                // Condition to check for difficulty level
                question.setText("On to the next question");
                answeroption1.setText("1) Option 1");
                answeroption2.setText("2) Option 2");
                answeroption3.setText("3) Option 3");
            }
        });
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

    public void delay(int ms){
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (Exception e){
            Log.i("Interrupted", "Interrupted");
        }
    }

}
