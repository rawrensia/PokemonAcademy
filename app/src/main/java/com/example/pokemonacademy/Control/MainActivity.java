package com.example.pokemonacademy.Control;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pokemonacademy.Entity.Question;
import com.example.pokemonacademy.Entity.QuestionChoice;
import com.example.pokemonacademy.Entity.Student;
import com.example.pokemonacademy.Entity.UserQnsAns;
import com.example.pokemonacademy.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    Button btnStart;

    private FirebaseDatabase database;
    private DatabaseReference databaseReferenceUser;
    private DatabaseReference databaseReferenceQuestion;
    private DatabaseReference databaseReferenceQnsChoice;
    private DatabaseReference databaseReferenceUserQnsAns;
    private static final int NUM_STUDENTS = 10;
    private static final int NUM_QUESTIONS = 15;
    private static final int NUM_CHOICES = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = (Button)findViewById(R.id.button_start);

        btnStart.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View view) {
                //tables in the database
                databaseReferenceUser = FirebaseDatabase.getInstance().getReference("USER");
                databaseReferenceQuestion = FirebaseDatabase.getInstance().getReference("QUESTION");
                databaseReferenceQnsChoice = FirebaseDatabase.getInstance().getReference("QUESTION_CHOICES");
                databaseReferenceUserQnsAns = FirebaseDatabase.getInstance().getReference("USER_QUESTION_ANS");

                populateStudentTable();
                populateQuestionTable();
                populateQuestionChoicesTable();
                populateUserQuestionAnsTable();


                Intent Layer = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(Layer);
            }
        });

    }

    public void populateStudentTable(){
        int studentId;
        String studentName;
        String studentEmail;
        String studentPw;
        String userType;
        int studentCourseInd;

        String[] studentNames = {"Spike", "Tom", "Jerry", "Mike", "Michael", "John", "Johnson", "Johnny", "Mary", "Marilyn"};
        String[] studentEmails = {"spike@xyz.com", "Tom@xyz.com", "Jerry@xyz.com", "Mike@xyz.com", "Michael@xyz.com",
                "John@xyz.com", "Johnson@xyz.com", "Johnny@xyz.com", "Mary@xyz.com", "Marilyn@xyz.com"};
        String[] studentPasswords = {"Spike123", "123Tom", "Jerry123", "123Mike", "Michael123", "123John", "Johnson123", "123Johnny", "Mary123", "123Marilyn"};
        String[] userTypes = {"S", "S", "S", "S", "S", "S", "S", "S", "S", "T"};
        int[] courseIndex = {10272, 10273, 10274, 10275};

        for (int i=0; i<NUM_STUDENTS; i++){
            studentId = i + 1;
            String studIdString = "Student"+studentId;
            studentName = studentNames[i];
            studentEmail = studentEmails[i];
            studentPw = studentPasswords[i];
            userType = userTypes[i];
            studentCourseInd = courseIndex[i%4];
            Student addStudent = new Student(studentId, studentName, studentEmail, studentPw, userType, studentCourseInd);

            //String id = databaseReferenceUser.push().getKey();
            databaseReferenceUser.child(studIdString).setValue(addStudent);
        }
    }

    public void populateQuestionTable(){
        int qnsId;
        String qns;
        String world;
        String topic;
        String quizType;
        int diffLevel;

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
        String[] allTopics;
        int[] allDiffLevels;

        //"Planning" world
        for (int i=0; i<NUM_QUESTIONS; i++){
            qnsId = i + 1;
            String qnsIdString = "Question"+qnsId;
            qns = allQns[i];
            world = "Planning";
            topic = "Topic A"; //TEMPORARY VARIABLE
            if (i<=8){
                quizType = "MINI";
            }
            else{
                quizType = "FINAL";
            }
            diffLevel = (i+1) % 4;

            Question addQuestion = new Question(qnsId, qns, world, topic, quizType, diffLevel);
            databaseReferenceQuestion.child(qnsIdString).setValue(addQuestion);
        }

    }

    public void populateQuestionChoicesTable(){
        int choiceId;
        int qnsId;
        int isRightChoice;
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
                {"When doing marketing surveys, we can ask any type of questions.", "Using marketing surveys is not good for quantitative data.",
                        "Using marketing surveys is good for generating ideas.", "Using marketing surveys is good for statistical data."},
                {"looks at what information the user has", "applies methods like use cases or scenarios", "helps desk problem reports ", "focuses on system features"},
                {"Purpose of the system and  assumptions", "Scope of the system and functional requirements", "Test cases and marketing surveys", "Data dictionary and non-functional requirements"},
                {"YET TO BE FILLED", "YET TO BE FILLED", "YET TO BE FILLED", "YET TO BE FILLED"}
        };
        int[] rightChoiceOption = {1, 1, 3, 2, 3, 2, 3, 2, 3, 2, 3, 4, 1, 3, 1};

        for (int i=0; i<NUM_QUESTIONS; i++){
            for (int j=0; j<NUM_CHOICES; j++){
                qnsId = i + 1;
                String qnsIdString = "Question"+qnsId;
                choiceId = j + 1;
                String choiceIdString = "Choice"+choiceId;
                if (choiceId == rightChoiceOption[i]){
                    isRightChoice = 1;
                }
                else{
                    isRightChoice = 0;
                }
                choice = allChoices[i][j];
                QuestionChoice addQnsChoice = new QuestionChoice(choiceId, qnsId, isRightChoice, choice);
                databaseReferenceQnsChoice.child(qnsIdString).child(choiceIdString).setValue(addQnsChoice);
            }
        }
    }

    public void populateUserQuestionAnsTable(){
        int userId;
        int qnsId;
        int choiceId;
        int isRight;

        for (int i=0; i<NUM_STUDENTS; i++){
            for (int j=0; j<NUM_QUESTIONS; j++){
                for (int k=0; k<NUM_CHOICES; k++){
                    userId = i + 1;
                    qnsId = j + 1;
                    choiceId = k + 1;

                    String choiceIdString = "Choice"+choiceId;
                    String qnsIdString = "Question"+qnsId;
                    String userIdString = "User"+userId;

                    isRight = 1; //TEMPORARY VARIABLE
                    UserQnsAns addUserQnsAns = new UserQnsAns(userId, qnsId, choiceId, isRight);
                    databaseReferenceUserQnsAns.child(userIdString).child(qnsIdString).child(choiceIdString).setValue(addUserQnsAns);
                }
            }
        }
    }



}
