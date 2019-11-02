package com.example.pokemonacademy.Control;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pokemonacademy.Entity.Question;
import com.example.pokemonacademy.Entity.QuestionChoice;
import com.example.pokemonacademy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CreateCustomQuizActivity extends AppCompatActivity {

    private static final int RANDOM_STRING_LENGTH = 8;
    private int questionIndex = 1;

    private FirebaseAuth mAuth;
    private DatabaseReference questionDatabase;
    private DatabaseReference questionChoiceDatabase;
    final ArrayList<Question> questions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_custom_quiz);

        mAuth = FirebaseAuth.getInstance();
        questionDatabase = FirebaseDatabase.getInstance().getReference("QUESTION");
        questionChoiceDatabase = FirebaseDatabase.getInstance().getReference("QUESTION_CHOICES");
        createEmptyQuestionObject();

        updateUI();
        setSingleEvent();
    }

    private void createEmptyQuestionObject()    {
        for(int i=0; i<5; i++)  {
            Question q = new Question();
            ArrayList<QuestionChoice> qcArray = new ArrayList<>();

            q.setWorldId(mAuth.getCurrentUser().getUid());
            q.setAttempted(false);
            q.setDifficultyLevel(-1);
            q.setQuestionId(i+1);

            for(int j=0; j<3; j++)  {
                qcArray.add(new QuestionChoice());
            }
            q.setQuestionChoice(qcArray);
            questions.add(q);
        }
    }

    private void setSingleEvent()   {
        Button createQuizBtn = findViewById(R.id.createQuizBtn);
        ImageView leftBtn = findViewById(R.id.customQuizLeftBtn);
        ImageView rightBtn = findViewById(R.id.customQuizRightBtn);

        leftBtn
            .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveCurrentQuestion();
                    questionIndex--;
                    updateUI();
                }
            });

        rightBtn
            .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveCurrentQuestion();
                    questionIndex++;
                    updateUI();
                }
            });

        createQuizBtn
            .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(checkEmptyQuestion())  {
                        Toast.makeText(CreateCustomQuizActivity.this, "Please do not leave any questions or choices empty.", Toast.LENGTH_LONG).show();
                    } else {
                        String randomCode = GenerateRandomString.generateRandomString(RANDOM_STRING_LENGTH);

                        shuffleChoices();

                        for(int i=0; i<questions.size(); i++)   {
                            ArrayList<QuestionChoice> qc = questions.get(i).getQuestionChoice();
                            questions.get(i).setQuizId(randomCode);
                            questions.get(i).setQuestionChoice(null);
                            questionDatabase.child(mAuth.getCurrentUser().getUid()).child(randomCode).child("Question"+questions.get(i).getQuestionId()).setValue(questions.get(i));

                            for(int j=0; j<qc.size(); j++)  {
                                questionChoiceDatabase.child(mAuth.getCurrentUser().getUid()).child(randomCode).child("Question"+questions.get(i).getQuestionId()).child("Choice"+(j+1)).setValue(qc.get(j));
                            }
                        }
                        questions.clear();
                        createEmptyQuestionObject();
                        updateUI();

                        Toast.makeText(CreateCustomQuizActivity.this, "Successfully created custom quiz.", Toast.LENGTH_LONG).show();
                        Intent Layer = new Intent(CreateCustomQuizActivity.this, CustomQuizInfoActivity.class);
                        startActivity(Layer);
                        finish();
                    }
                }
            });
    }

    private void updateUI()   {
        TextView questionTV = findViewById(R.id.addQuestionTV);
        TextView correctChoiceTV = findViewById(R.id.correctChoiceTV);
        TextView choice1TV = findViewById(R.id.addChoice1TV);
        TextView choice2TV = findViewById(R.id.addChoice2TV);

        ImageView leftBtn = findViewById(R.id.customQuizLeftBtn);
        ImageView rightBtn = findViewById(R.id.customQuizRightBtn);

        // UPDATES THE VISIBILITY OF THE BUTTONS
        if(questionIndex == 1)
            leftBtn.setVisibility(View.INVISIBLE);
        else if(questionIndex == 5)
            rightBtn.setVisibility(View.INVISIBLE);
        else {
            leftBtn.setVisibility(View.VISIBLE);
            rightBtn.setVisibility(View.VISIBLE);
        }

        // UPDATES THE QUESTIONS
        Question q = questions.get(questionIndex-1);
        ArrayList<QuestionChoice> qcArray = q.getQuestionChoice();

        questionTV.setText(q.getQuestion());
        correctChoiceTV.setText(qcArray.get(0).getChoice());
        choice1TV.setText(qcArray.get(1).getChoice());
        choice2TV.setText(qcArray.get(2).getChoice());
    }

    private void saveCurrentQuestion()  {
        TextView questionTV = findViewById(R.id.addQuestionTV);
        TextView correctChoiceTV = findViewById(R.id.correctChoiceTV);
        TextView choice1TV = findViewById(R.id.addChoice1TV);
        TextView choice2TV = findViewById(R.id.addChoice2TV);

        ArrayList<QuestionChoice> qcArray = new ArrayList<>();

        for(int i=0; i<3; i++)  {
            QuestionChoice qc = new QuestionChoice();
            qc.setQnsId(questionIndex);

            if(i==0)    {
                qc.setChoice(correctChoiceTV.getText().toString());
                qc.setRightChoice(true);
            } else if(i==1) {
                qc.setChoice(choice1TV.getText().toString());
                qc.setRightChoice(false);
            } else {
                qc.setChoice(choice2TV.getText().toString());
                qc.setRightChoice(false);
            }

            qcArray.add(qc);
        }
        questions.get(questionIndex-1).setQuestionChoice(qcArray);
        questions.get(questionIndex-1).setQuestion(questionTV.getText().toString());
    }

    private boolean checkEmptyQuestion()    {
        for(int i=0; i<questions.size()-1; i++)   {
            if(questions.get(i).getQuestion() == null || questions.get(i).getQuestion().isEmpty())
                return true;

            ArrayList<QuestionChoice> qc = questions.get(i).getQuestionChoice();
            for(int j=0; j<qc.size(); j++)  {
                if(qc.get(j).getChoice() == null || qc.get(j).getChoice().isEmpty())
                    return true;
            }
        }
        if(questionIndex == 5 && questions.get(4).getQuestion() == null)    {
            saveCurrentQuestion();
            if(questions.get(4).getQuestion() == null || questions.get(4).getQuestion().isEmpty())
                return true;
        }
        return false;
    }

    private void shuffleChoices()   {
        for(int i=0; i<questions.size(); i++) {
            ArrayList<QuestionChoice> qc = questions.get(i).getQuestionChoice();
            qc = Shuffle.shuffleList(qc);
            questions.get(i).setQuestionChoice(qc);
        }
    }

    private void closeKeyboard()    {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_logout_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tomenulp:
                Intent Layer = new Intent(CreateCustomQuizActivity.this, MenuLandingPage.class);
                startActivity(Layer);
                return true;
            case R.id.tologout:
                mAuth.signOut();
                Layer = new Intent(CreateCustomQuizActivity.this, MainActivity.class);
                Toast.makeText(CreateCustomQuizActivity.this, "Successfully logged out.",
                        Toast.LENGTH_LONG).show();
                startActivity(Layer);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
