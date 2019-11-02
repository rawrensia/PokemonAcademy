package com.example.pokemonacademy.Control;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pokemonacademy.Entity.QuestionChoice;
import com.example.pokemonacademy.Entity.Question;
import com.example.pokemonacademy.Entity.QuizzesCompleted;
import com.example.pokemonacademy.Entity.User;
import com.example.pokemonacademy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.TimeUnit;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

public class Quiz extends AppCompatActivity {

    ImageView userpokemon;

    private int selectedChoice = 0;
    int counter = 0;
    private int userpokemonhp = 100;
    private int enemypokemonhp = 100;
    private boolean endBattleFlag = false;
    private int total_num_of_question = 5;
    private int current_num_of_question = 5;
    private int currentQuestionId;
    private int correctChoiceId;
    private int currentQuestionIndex;
    private int correctChoiceIndex;

    private FirebaseAuth mAuth;
    private DatabaseReference userDb;
    private DatabaseReference questionDb;
    private DatabaseReference choiceDb;
    public String userID;
    public int worldID;
    public String worldName;
    public int miniQuizID;
    public String miniQuizName;
    public String customWorldID;
    private ArrayList<Question> questionList;

    // Variables for summary quiz
    private Question questionAssigned;
    private ArrayList<Question> questionAnswered = new ArrayList<Question>(); // for storing questions answered
    private ArrayList<QuestionChoice> choiceChosen = new ArrayList<QuestionChoice>(); // for storing the choice which is chosen by the student
    private ArrayList<QuestionChoice> rightChoice = new ArrayList<QuestionChoice>(); // for storing the right choice
    private int timeTaken[] = new int[current_num_of_question];
    private long startTime, endTime;
    private boolean choiceClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Starting
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Initialize dbs
        mAuth = FirebaseAuth.getInstance();
        userDb = FirebaseDatabase.getInstance().getReference("USER");
        questionDb = FirebaseDatabase.getInstance().getReference("QUESTION");
        choiceDb = FirebaseDatabase.getInstance().getReference("QUESTION_CHOICES");
        FirebaseUser currentUser = mAuth.getCurrentUser();
        userID = currentUser.getUid();

        // Get intent
        Intent intent = getIntent();
        worldID = intent.getIntExtra("worldID", -1);
        worldName = intent.getStringExtra("worldName");
        miniQuizID = intent.getIntExtra("miniQuizID",-1);
        miniQuizName = intent.getStringExtra("miniQuizName");

        if (miniQuizID == 0){
            questionList = intent.getExtras().getParcelableArrayList("questionList0");
            gamePlay();
        } else if (miniQuizID == 1) {
            questionList = intent.getExtras().getParcelableArrayList("questionList1");
            gamePlay();
        } else if (miniQuizID == 2){
            questionList = intent.getExtras().getParcelableArrayList("questionList2");
            total_num_of_question = 10;
            current_num_of_question = 10;
            gamePlay();
        } else {
            customWorldID = intent.getStringExtra("customworldID");
            questionDb.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    questionList = getQuestions(dataSnapshot, worldName, miniQuizName);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
            choiceDb.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    assignQuestionChoice(dataSnapshot.child(worldName).child(miniQuizName),questionList);
                    gamePlay();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
    }

    public void gamePlay(){
        // initialize background & pokemons
        TextView tv = (TextView)findViewById(R.id.miniquiztitle);
        TextView userpokemonstatus = (TextView)findViewById(R.id.userpokemonstatus);
        TextView enemypokemonstatus = (TextView)findViewById(R.id.enemypokemonstatus);
        RelativeLayout questionlayout = (RelativeLayout)findViewById(R.id.questionlayout);
        ConstraintLayout battlelayout = (ConstraintLayout)findViewById(R.id.battlelayout);
        ConstraintLayout answerlayout = (ConstraintLayout)findViewById(R.id.answerlayout);
        ImageView enemypokemon = (ImageView)findViewById(R.id.enemypokemon);
        userpokemon = (ImageView)findViewById(R.id.userpokemon);

        tv.setText(miniQuizName);
        userpokemonstatus.setText("");
        enemypokemonstatus.setText("");
        questionlayout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.questionbackground, null));
        battlelayout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.pokemonstandingbackground, null));
        answerlayout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.answerbackground, null));
        enemypokemon.setImageResource(getRandomEnemyImage());
        userDb.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot = dataSnapshot.child(userID);
                User u = new User();
                u = dataSnapshot.getValue(User.class);
                if (u.getCharId() == 0) {
                    userpokemon.setImageDrawable(getResources().getDrawable(R.drawable.userpokemonpikachu));
                } else if (u.getCharId() == 1) {
                    userpokemon.setImageDrawable(getResources().getDrawable(R.drawable.userpokemonpikachu));
                } else if (u.getCharId() == 2) {
                    userpokemon.setImageDrawable(getResources().getDrawable(R.drawable.userpokemonpikachu));
                } else if (u.getCharId() == 3) {
                    userpokemon.setImageDrawable(getResources().getDrawable(R.drawable.userpokemonpikachu));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // initialize question, answers and buttons
        final int greenColor = Color.parseColor("#22B395");
        final int yellowColor = Color.parseColor("#ffbf00");
        final int redColor = Color.parseColor("#741B3F");
        final int transparent = Color.parseColor("#00FFFFFF");
        final int blueColor = Color.parseColor("#193862");

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

        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(120); //You can manage the blinking time with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        attackbtn.startAnimation(anim);

        choice1btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choice1btn.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button1, null));
                choice1btn.setTextColor(blueColor);
                choice2btn.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button2, null));
                choice2btn.setTextColor(yellowColor);
                choice3btn.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button2, null));
                choice3btn.setTextColor(yellowColor);

                answeroption1.setBackgroundColor(greenColor);
                answeroption2.setBackgroundColor(transparent);
                answeroption3.setBackgroundColor(transparent);

                attackbtn.setVisibility(View.VISIBLE);
                selectedChoice = 1;
                choiceClicked = true;
            }
        });
        choice2btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choice2btn.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button1, null));
                choice2btn.setTextColor(blueColor);
                choice1btn.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button2, null));
                choice1btn.setTextColor(yellowColor);
                choice3btn.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button2, null));
                choice3btn.setTextColor(yellowColor);

                answeroption2.setBackgroundColor(greenColor);
                answeroption1.setBackgroundColor(transparent);
                answeroption3.setBackgroundColor(transparent);

                attackbtn.setVisibility(View.VISIBLE);
                selectedChoice = 2;
                choiceClicked = true;
            }
        });
        choice3btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choice3btn.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button1, null));
                choice3btn.setTextColor(blueColor);
                choice1btn.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button2, null));
                choice1btn.setTextColor(yellowColor);
                choice2btn.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button2, null));
                choice2btn.setTextColor(yellowColor);

                answeroption3.setBackgroundColor(greenColor);
                answeroption2.setBackgroundColor(transparent);
                answeroption1.setBackgroundColor(transparent);

                attackbtn.setVisibility(View.VISIBLE);
                selectedChoice = 3;
                choiceClicked = true;
            }
        });

        // initialize health bar for enemy and user pokemons
        ProgressBar enemyhealthbar = (ProgressBar)findViewById(R.id.enemypokemonhealth);
        ProgressBar userpokemonhealthbar = (ProgressBar)findViewById(R.id.userpokemonhealth);
        enemyhealthbar.setProgress(enemypokemonhp);
        userpokemonhealthbar.setProgress(userpokemonhp);

        // Set question & answer options
        Log.i("questionlist",""+questionList.size());
        questionAssigned = displayNextQuestion(questionList, getDifficultyLevel(userpokemonhp));

        attackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!choiceClicked)
                    Toast.makeText(Quiz.this, "Please select an answer.", Toast.LENGTH_LONG).show();
                else {

                    // For mini quiz summary
                    questionAnswered.add(questionAssigned);
                    choiceChosen.add(questionAssigned.getQuestionChoice().get(selectedChoice-1));

                    if (questionAssigned.getQuestionChoice().get(0).getRightChoice()){
                        rightChoice.add(questionAssigned.getQuestionChoice().get(0));
                    } else if (questionAssigned.getQuestionChoice().get(1).getRightChoice()){
                        rightChoice.add(questionAssigned.getQuestionChoice().get(1));
                    } else {
                        rightChoice.add(questionAssigned.getQuestionChoice().get(2));
                    }

                    getTimeTaken();

                    // initialize
                    ProgressBar enemyhealthbar = (ProgressBar) findViewById(R.id.enemypokemonhealth);
                    ProgressBar userpokemonhealthbar = (ProgressBar) findViewById(R.id.userpokemonhealth);
                    TextView questionTv = (TextView) findViewById(R.id.questiontext);
                    TextView answeroption1 = (TextView) findViewById(R.id.answeroption1);
                    TextView answeroption2 = (TextView) findViewById(R.id.answeroption2);
                    TextView answeroption3 = (TextView) findViewById(R.id.answeroption3);
                    Button choice1btn = (Button) findViewById(R.id.choice1btn);
                    Button choice2btn = (Button) findViewById(R.id.choice2btn);
                    Button choice3btn = (Button) findViewById(R.id.choice3btn);
                    Button attackbtn = (Button) findViewById(R.id.attackbtn);
                    ImageView enemypokemon = (ImageView) findViewById(R.id.enemypokemon);
                    ImageView userpokemon = (ImageView) findViewById(R.id.userpokemon);
                    TextView userpokemonstatus = (TextView) findViewById(R.id.userpokemonstatus);
                    TextView enemypokemonstatus = (TextView) findViewById(R.id.enemypokemonstatus);

                    if (selectedChoice == correctChoiceIndex) {
                        int dmg = enemypokemonhp / current_num_of_question;
                        enemypokemonhp = enemypokemonhp - dmg;
                        enemyhealthbar.setProgress(enemypokemonhp);
                        userpokemonstatus.setText("Correct answer");
                        enemypokemonstatus.setText("Took " + dmg + " damage");
                        damageAnimate(enemypokemon);
                        if (enemypokemonhp <= 0) {
                            endBattleFlag = true;
                        }
                    } else {
                        int dmg = userpokemonhp / current_num_of_question;
                        userpokemonhp = userpokemonhp - dmg;
                        userpokemonhealthbar.setProgress(userpokemonhp);
                        userpokemonstatus.setText("Took " + dmg + " damage");
                        enemypokemonstatus.setText("Wrong answer");
                        damageAnimate(userpokemon);
                        if (userpokemonhp <= 0) {
                            endBattleFlag = true;
                        }
                    }
                    current_num_of_question = current_num_of_question - 1;
                    choiceClicked = false;

                    // End condition
                    if (endBattleFlag || current_num_of_question <= 0) {
                        Intent intent = getIntent();
//                        String worldName = intent.getStringExtra("worldName");
//                        int worldID = intent.getIntExtra("worldID", -1);
                        TextView miniQuizTv = (TextView) findViewById(R.id.miniquiztitle);

                        Intent Layer = new Intent(Quiz.this, QuizSummary.class);

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
                        Layer.putExtra("miniQuizId", miniQuizID);
                        Layer.putExtra("worldName", worldName);
                        Layer.putExtra("worldID", worldID);
                        if (miniQuizID!=-1) {
                            Layer.putExtra("worldName", worldName);
                            Layer.putExtra("miniQuizName", miniQuizTv.getText().toString());
                        } else {
                            Layer.putExtra("worldName", customWorldID);
                            Layer.putExtra("miniQuizName", miniQuizName);
                        }
                        startActivity(Layer);
                    }

                    // Reset button states
                    selectedChoice = 0;
                    answeroption1.setBackgroundColor(transparent);
                    answeroption2.setBackgroundColor(transparent);
                    answeroption3.setBackgroundColor(transparent);
                    choice1btn.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button2, null));
                    choice2btn.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button2, null));
                    choice3btn.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.button2, null));
                    choice1btn.setTextColor(yellowColor);
                    choice2btn.setTextColor(yellowColor);
                    choice3btn.setTextColor(yellowColor);
                    attackbtn.setVisibility(View.INVISIBLE);

                    // Display next question
                    if (current_num_of_question !=0){
                        questionAssigned = displayNextQuestion(questionList, getDifficultyLevel(userpokemonhp));
                    }
                }
            }
        });
    }

    public Question displayNextQuestion(ArrayList<Question> questionList, int difficultyLevel){
        startTime = System.currentTimeMillis();
        TextView questionTv = (TextView)findViewById(R.id.questiontext);
        TextView answeroption1 = (TextView)findViewById(R.id.answeroption1);
        TextView answeroption2 = (TextView)findViewById(R.id.answeroption2);
        TextView answeroption3 = (TextView)findViewById(R.id.answeroption3);

        Collections.shuffle(questionList);
        for (int i=0; i<questionList.size(); i++){
            Question q = questionList.get(i);
            Log.d("questionlist","question " + q.getQuestion());
            Log.d("questionlist","question " + q.getAttempted());
//            if (q.attemped == false && (q.getDifficultyLevel() == difficultyLevel || q.getDifficultyLevel == -1)){
            if (q.attempted == false){
                questionTv.setText(questionList.get(i).question);
                currentQuestionIndex = i;
                answeroption1.setText("1) "+q.getQuestionChoice().get(0).getChoice());
                answeroption2.setText("2) "+q.getQuestionChoice().get(1).getChoice());
                answeroption3.setText("3) "+q.getQuestionChoice().get(2).getChoice());
                if (q.getQuestionChoice().get(0).getRightChoice()){correctChoiceIndex = 1;}
                if (q.getQuestionChoice().get(1).getRightChoice()){correctChoiceIndex = 2;}
                if (q.getQuestionChoice().get(2).getRightChoice()){correctChoiceIndex = 3;}
                q.setAttempted(true);
                return q;
            }
        }
        Question q = new Question();
        return q;
    }

    public int getDifficultyLevel(int userpokemonhp){
        int difficultyLevel;
        if (userpokemonhp < 30) {
            difficultyLevel = 1;
        } else if (userpokemonhp < 70){
            difficultyLevel = 2;
        } else {
            difficultyLevel = 3;
        }
        return difficultyLevel;
    }

    public void getTimeTaken(){
        endTime = System.currentTimeMillis() - startTime;
        if(total_num_of_question- current_num_of_question >0){
            timeTaken[total_num_of_question- current_num_of_question] = (int)(long)(endTime/1000);
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
