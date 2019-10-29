package com.example.pokemonacademy.Control;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.example.pokemonacademy.Entity.Question;
import com.example.pokemonacademy.Entity.QuestionChoice;
import com.example.pokemonacademy.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button btnStart;

    private DatabaseReference databaseReferenceQuestion;
    private DatabaseReference databaseReferenceQnsChoice;

    private Animation frombottom, fromtop;
    private ImageView pokemonLogo;

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

                populateQuestionTable();
                populateQuestionChoicesTable();

                Animation animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.fadeout);
                btnStart.startAnimation(animation);

                Intent Layer = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(Layer);
            }
        });
    }


    public void populateQuestionTable(){
        String qns;
        int worldId;
        int diffLevel;
        int quizId;

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

        //"Planning" world
        for (int i=0; i<allQns.length; i++){
            String qnsIdString = "Question"+ (i+1); //qnsId

            qns = allQns[i]; //actual question

            worldId = 0; //"Planning" world
            String worldIdString = "World"+worldId;

            if (i<=8){
                if (i%2 == 0){
                    quizId = 0; //mini quiz 1

                }
                else{
                    quizId = 1; //mini quiz 2
                }
            }
            else{
                quizId = 2; //final quiz
            }
            diffLevel = (i+1) % 4;
            String quizIdString = "Quiz"+quizId; //quizId

            Question addQuestion = new Question(worldId, quizId, (i+1), diffLevel, qns);
            databaseReferenceQuestion.child(worldIdString).child(quizIdString).child(qnsIdString).setValue(addQuestion);
        }

    }

    public void populateQuestionChoicesTable(){
        int choiceId;
        int qnsId;
        boolean isRightChoice;
        String choice; //choices available

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
                {"When doing marketing surveys, we can ask any type of questions.", "Using marketing surveys is not good for quantitative data.", "Using marketing surveys is good for statistical data.",
                        "Using marketing surveys is good for generating ideas."},
                {"looks at what information the user has", "applies methods like use cases or scenarios", "helps desk problem reports ", "focuses on system features"},
                {"Purpose of the system and  assumptions", "Scope of the system and functional requirements", "Test cases and marketing surveys", "Data dictionary and non-functional requirements"},
                {"YET TO BE FILLED", "YET TO BE FILLED", "YET TO BE FILLED", "YET TO BE FILLED"}
        };
        int[] rightChoiceOption = {1, 1, 3, 2, 3, 2, 3, 2, 3, 2, 3, 3, 1, 3, 1};

        int quizId;
        for (int i=0; i<allChoices.length; i++){
            for (int j=0; j<3; j++){
                qnsId = i + 1;
                String qnsIdString = "Question"+qnsId;
                choiceId = j + 1;
                String choiceIdString = "Choice"+choiceId;
                if (choiceId == rightChoiceOption[i]){
                    isRightChoice = true;
                }
                else{
                    isRightChoice = false;
                }

                if (i<=8){
                    if (i%2 == 0){
                        quizId = 0; //mini quiz 1

                    }
                    else{
                        quizId = 1; //mini quiz 2
                    }
                }
                else{
                    quizId = 2; //final quiz
                }

                String quizIdString = "Quiz"+quizId; //quizId

                choice = allChoices[i][j];
                QuestionChoice addQnsChoice = new QuestionChoice(qnsId, choiceId, choice,isRightChoice);
                databaseReferenceQnsChoice.child("World0").child(quizIdString).child(qnsIdString).child(choiceIdString).setValue(addQnsChoice);
            }
        }
    }
}
