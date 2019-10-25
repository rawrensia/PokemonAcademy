package com.example.pokemonacademy.Control;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pokemonacademy.Entity.QuestionChoice;
import com.example.pokemonacademy.Entity.Question;
import com.example.pokemonacademy.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.TimeUnit;


import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

public class MiniQuiz extends AppCompatActivity {

    // Get user's pokemon character (drawable)
    // Get user's health

    private int selectedChoice = 0;
    int counter = 0;

    // Hardcode
    private int userpokemonhp = 100;
    private int enemypokemonhp = 100;
    private boolean endBattleFlag = false;
    private int num_of_question = 10;
    private int currentQuestionId;
    private int correctChoiceId;
    private int currentQuestionIndex;
    private int correctChoiceIndex;
    private ArrayList<Question> questionList = initialize();

    // Variables for summary quiz
    private Question questionAssigned;
    private ArrayList<Question> questionAnswered = new ArrayList<Question>(); // for storing questions answered
    private ArrayList<QuestionChoice> choiceChosen = new ArrayList<QuestionChoice>(); // for storing the choice which is chosen by the student
    private ArrayList<QuestionChoice> rightChoice = new ArrayList<QuestionChoice>(); // for storing the right choice
    private int timeTaken[] = new int[num_of_question];
    private long startTime, endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Starting
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_quiz);

        // Get intent
        Intent intent = getIntent();
        String worldName = intent.getStringExtra("worldName");
        String miniQuizNum = intent.getStringExtra("miniQuizNum");
        int worldID = intent.getIntExtra("worldID", -1);

        // initialize background & pokemons
        TextView tv = (TextView)findViewById(R.id.miniquiztitle);
        TextView userpokemonstatus = (TextView)findViewById(R.id.userpokemonstatus);
        TextView enemypokemonstatus = (TextView)findViewById(R.id.enemypokemonstatus);
        RelativeLayout questionlayout = (RelativeLayout)findViewById(R.id.questionlayout);
        ConstraintLayout battlelayout = (ConstraintLayout)findViewById(R.id.battlelayout);
        ConstraintLayout answerlayout = (ConstraintLayout)findViewById(R.id.answerlayout);
        ImageView enemypokemon = (ImageView)findViewById(R.id.enemypokemon);
        ImageView userpokemon = (ImageView)findViewById(R.id.userpokemon);

        tv.setText(miniQuizNum);
        userpokemonstatus.setText("");
        enemypokemonstatus.setText("");
        questionlayout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.questionbackground, null));
        battlelayout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.pokemonstandingbackground, null));
        answerlayout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.answerbackground, null));
        enemypokemon.setImageResource(getRandomEnemyImage());

        // initialize question, answers and buttons
        final int greenColor = Color.parseColor("#33FF93");
        final int yellowColor = Color.parseColor("#D5E3A1");
        final int redColor = Color.parseColor("#E73B3B");
        final int transparent = Color.parseColor("#00FFFFFF");

        final TextView question = (TextView)findViewById(R.id.questiontext);
        final TextView questionTv = (TextView)findViewById(R.id.questiontext);
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

        // Set question & answer options
        questionAssigned = displayNextQuestion(questionList);

        attackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // For mini quiz summary
                questionAnswered.add(questionAssigned);
                choiceChosen.add(questionAssigned.choiceOptions.get(selectedChoice-1));

                if (questionAssigned.choiceOptions.get(0).rightChoice){
                    rightChoice.add(questionAssigned.choiceOptions.get(0));
                } else if (questionAssigned.choiceOptions.get(1).rightChoice){
                    rightChoice.add(questionAssigned.choiceOptions.get(1));
                } else {
                    rightChoice.add(questionAssigned.choiceOptions.get(2));
                }

                getTimeTaken();

                // initialize
                ProgressBar enemyhealthbar = (ProgressBar)findViewById(R.id.enemypokemonhealth);
                ProgressBar userpokemonhealthbar = (ProgressBar)findViewById(R.id.userpokemonhealth);
                TextView questionTv = (TextView)findViewById(R.id.questiontext);
                TextView answeroption1 = (TextView)findViewById(R.id.answeroption1);
                TextView answeroption2 = (TextView)findViewById(R.id.answeroption2);
                TextView answeroption3 = (TextView)findViewById(R.id.answeroption3);
                Button choice1btn = (Button)findViewById(R.id.choice1btn);
                Button choice2btn = (Button)findViewById(R.id.choice2btn);
                Button choice3btn = (Button)findViewById(R.id.choice3btn);
                Button attackbtn = (Button)findViewById(R.id.attackbtn);
                ImageView enemypokemon = (ImageView)findViewById(R.id.enemypokemon);
                ImageView userpokemon = (ImageView)findViewById(R.id.userpokemon);
                TextView userpokemonstatus = (TextView)findViewById(R.id.userpokemonstatus);
                TextView enemypokemonstatus = (TextView)findViewById(R.id.enemypokemonstatus);

                if (selectedChoice==correctChoiceIndex){
                    int dmg = enemypokemonhp/num_of_question;
                    enemypokemonhp = enemypokemonhp - dmg;
                    enemyhealthbar.setProgress(enemypokemonhp);
                    userpokemonstatus.setText("Correct answer");
                    enemypokemonstatus.setText("Took "+ dmg +" damage");
                    damageAnimate(enemypokemon);
                    if (enemypokemonhp <= 0){endBattleFlag = true;}
                } else {
                    userpokemonhp = userpokemonhp - 10;
                    userpokemonhealthbar.setProgress(userpokemonhp);
                    userpokemonstatus.setText("Took 10 damage");
                    enemypokemonstatus.setText("Wrong answer");
                    damageAnimate(userpokemon);
                    if (userpokemonhp <= 0){endBattleFlag = true;}
                }
                num_of_question = num_of_question - 1;
                // Save question, answer, time, right/wrong to db


                //TODO
                // Add Different end states
                // 1) User died, show screen user's pokemon died. (FailQuizActivity)
                //      - reset users pokemon health to full (update db)
                //      - bring back to miniquizlandingpage
                // 2) User won, show quiz summary (QuizSummaryActivity)
                //      - update db the questions user completed, selected choice, time taken
                //      - update db user's pokemon remaining health
                if (endBattleFlag || num_of_question<=0){

                    Intent intent = getIntent();
                    String worldName = intent.getStringExtra("worldName");
                    int worldID = intent.getIntExtra("worldID", -1);
                    TextView miniQuizTv = (TextView)findViewById(R.id.miniquiztitle);

                    Intent Layer = new Intent(MiniQuiz.this, QuizSummary.class);

                    Bundle bundle1 = new Bundle();
                    bundle1.putParcelableArrayList("questionAnswered", questionAnswered);
                    Layer.putExtras(bundle1);

                    Bundle bundle2 = new Bundle();
                    bundle2.putParcelableArrayList("choiceChosen", choiceChosen);
                    Layer.putExtras(bundle2);

                    Bundle bundle3 = new Bundle();
                    bundle3.putParcelableArrayList("rightChoice", rightChoice);
                    Layer.putExtras(bundle3);

                    Layer.putExtra("timeTaken", timeTaken);
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
                questionAssigned = displayNextQuestion(questionList);
            }
        });
    }

    public Question displayNextQuestion(ArrayList<Question> questionList){
        startTime = System.currentTimeMillis();
        TextView questionTv = (TextView)findViewById(R.id.questiontext);
        TextView answeroption1 = (TextView)findViewById(R.id.answeroption1);
        TextView answeroption2 = (TextView)findViewById(R.id.answeroption2);
        TextView answeroption3 = (TextView)findViewById(R.id.answeroption3);

        Collections.shuffle(questionList);
        for (int i=0; i<questionList.size(); i++){
            Question q = questionList.get(i);
            if (q.attempted == false){
                questionTv.setText(questionList.get(i).question);
                currentQuestionIndex = i;
                answeroption1.setText("1) "+q.choiceOptions.get(0).choice);
                answeroption2.setText("2) "+q.choiceOptions.get(1).choice);
                answeroption3.setText("3) "+q.choiceOptions.get(2).choice);
                if (q.choiceOptions.get(0).isCorrect()){correctChoiceIndex = 1;}
                if (q.choiceOptions.get(1).isCorrect()){correctChoiceIndex = 2;}
                if (q.choiceOptions.get(2).isCorrect()){correctChoiceIndex = 3;}
                q.attempted = true;
                return q;
            }
        }
        return null;
    }

    public void getTimeTaken(){
        endTime = System.currentTimeMillis() - startTime;
        if(10-num_of_question>0){
            timeTaken[10-num_of_question] = (int)(long)(endTime/1000);
        }
        startTime = System.currentTimeMillis();
    }

    public void damageAnimate(ImageView v){
        Random random = new Random();
        float x = v.getX();
        float y = v.getY();
        final int randomInteger = random.nextInt(5);
        final boolean randomBool = random.nextBoolean();
        if (randomBool){
            v.setX(x+randomInteger);
            v.setY(y+randomInteger);
        } else {
            v.setX(x-randomInteger);
            v.setY(y-randomInteger);
        }
        delay(300);
    }

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

    public ArrayList<Question> initialize(){
        // Hardcode questions
        ArrayList<Question> questionList = new ArrayList<Question>();
        String[] allQns = {"What are we supposed to do when planning a project?",
                "Functional requirements ...",
                "What should not be done when doing requirement elicitation?",
                "Which of the following is not an activity of requirement development?",
                "Requirements analyst plays an important role in planning. Which one of the following are  some essential skills which  a good requirement analyst possesses?",
                "Which one of  the following are requirement elicitation techniques",
                "Use case diagram must have…...",
                "Which one of the following is  not a benefit of a dialog map?",
                "What are the quality characteristics of language used in SRS?",
                "“Must comply with FDA standard 138-B” belongs to which requirement classification?",
                "Which of the following statements is false?",
                "Which of the following statements about using marketing surveys is true?",
                "“Watch users do their jobs” ......",
                "SRS does not contain",
                "YET TO BE FILLED"
        };
        for (int i=0; i<allQns.length; i++){
            Question q = Question.addQuestion(allQns[i], "Planning", 1, "MINI");
            questionList.add(q);
        }
        // Hardcode choices & assign them to respective qn
        ArrayList<QuestionChoice> choiceList = new ArrayList<QuestionChoice>();
        String[][] allChoices = {
                {"Define the problem scope", "Conduct Acceptance test", "Monitor installation", " "},
                {"State system behavior under certain conditions", "Describe how well the system must function", "Describe the properties(criteria, quality attributes)the system must have", " "},
                {"Identifying possible software stakeholders", "Classifying the voices of the customer", "Testing the software system using black-box testing", " "},
                {"Elicitation of stakeholder requirements", "Implementation of stakeholder requirements", "Specification of stakeholder requirements", " "},
                {"Marketing skills and analytical skills", "Testing skills and listening skills", "Interviewing skills  and writing skills", " "},
                {"Task analysis and software architecture design", "Examine documents and interview stakeholders", "Software maintenance and prototyping ", " "},
                {"Actor and use case", "Boundary and association", "1 & 2", " "},
                {"Find missing or incorrect requirements early", "Model possible statuses of an object in the system", "Define user back-out and cancellation routes", " "},
                {"Correct, feasible and ambiguous", "Necessary, inconsistent and traceable", "Complete, modifiable and verifiable", " "},
                {"Business requirement", "Business rule", "Constraint", "Solution idea"},
                {"SRS is a complete description of the external behaviour of a system.", "Requirement analysis decomposes high-level requirements into details.",
                        "External interface requirement describes connections between software developer and outside world.",
                        "State-transition diagram models the discrete states a system can be in."},
                {"When doing marketing surveys, we can ask any type of questions.", "Using marketing surveys is not good for quantitative data.",
                        "Using marketing surveys is good for generating ideas.", "Using marketing surveys is good for statistical data."},
                {"looks at what information the user has", "applies methods like use cases or scenarios", "helps desk problem reports ", "focuses on system features"},
                {"Purpose of the system and  assumptions", "Scope of the system and functional requirements", "Test cases and marketing surveys", "Data dictionary and non-functional requirements"},
                {"YET TO BE FILLED", "YET TO BE FILLED", "YET TO BE FILLED", "YET TO BE FILLED"}
        };
        int[] rightChoiceOption = {1, 1, 3, 2, 3, 2, 3, 2, 3, 2, 3, 4, 1, 3, 1};
        // Creating the choiceList
        for (int i=0; i<allChoices.length; i++){
            ArrayList<QuestionChoice> choiceoption = new ArrayList<QuestionChoice>();
            for (int j=0; j<3; j++){
                if (j == rightChoiceOption[i]-1){ // minus 1 cuz its 0-4??
                    QuestionChoice c = QuestionChoice.addChoice(i+1,allChoices[i][j],true);
                    choiceList.add(c);
                    choiceoption.add(c);
                } else {
                    QuestionChoice c = QuestionChoice.addChoice(i + 1, allChoices[i][j], false);
                    choiceList.add(c);
                    choiceoption.add(c);
                }
            }
            questionList.get(i).setChoiceOptions(choiceoption); // Assign choice options to respective question
        }

        return questionList;
    }

}
