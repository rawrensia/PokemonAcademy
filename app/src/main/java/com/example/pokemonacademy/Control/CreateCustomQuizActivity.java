package com.example.pokemonacademy.Control;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pokemonacademy.Entity.Question;
import com.example.pokemonacademy.Entity.QuestionChoice;
import com.example.pokemonacademy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class CreateCustomQuizActivity extends AppCompatActivity {

    private static final int RANDOM_STRING_LENGTH = 8;

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

        setSingleEvent();
    }

    private void setSingleEvent()   {
        final Button addQuestionBtn = findViewById(R.id.addQuestionBtn);
        Button questionListBtn = findViewById(R.id.createQuizBtn);

        final TextView addQuestionTV = findViewById(R.id.addQuestionTV);
        final TextView addChoice1TV = findViewById(R.id.addChoice1TV);
        final TextView addChoice2TV = findViewById(R.id.addChoice2TV);
        final TextView choiceNumberTV = findViewById(R.id.correctChoiceTV);

        final ScrollView createCustomQuizSV = findViewById(R.id.createCustomQuizSV);

        addQuestionBtn
            .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(addQuestionTV.getText().toString().isEmpty() ||
                        addChoice1TV.getText().toString().isEmpty() ||
                        addChoice2TV.getText().toString().isEmpty() ||
                        choiceNumberTV.getText().toString().isEmpty())    {

                        Toast.makeText(CreateCustomQuizActivity.this, "Please leave no blanks.", Toast.LENGTH_LONG).show();
                    } else {
                        Question q = new Question();
                        ArrayList<QuestionChoice> choices = new ArrayList<>();
                        String choice;

                        Integer[] shuffleChoices = Shuffle.shuffleList(new Integer[]{1,2,3});

                        for (int i = 0; i < 3; i++) {
                            QuestionChoice qc = new QuestionChoice();
                            qc.setQnsId(questions.size() + 1);
                            qc.setChoiceId(i+1);

                            if(shuffleChoices[i] == 1) {
                                choice = choiceNumberTV.getText().toString();
                                qc.setRightChoice(true);
                            }
                            else if(shuffleChoices[i] == 2) {
                                choice = addChoice1TV.getText().toString();
                                qc.setRightChoice(false);
                            }
                            else {
                                choice = addChoice2TV.getText().toString();
                                qc.setRightChoice(false);
                            }

                            qc.setChoice(choice);
                            qc.setCorrect(false);
                            choices.add(qc);
                        }

                        q.setQuestionChoice(choices);
                        q.setQuestion(addQuestionTV.getText().toString());
                        q.setQuestionId(questions.size() + 1);
                        q.setDifficultyLevel(-1);
                        q.setAttempted(false);
                        q.setWorldId(mAuth.getCurrentUser().getUid());

                        // reset textviews
                        addQuestionTV.setText("");
                        addChoice1TV.setText("");
                        addChoice2TV.setText("");
                        choiceNumberTV.setText("");

                        questions.add(q);
                    }
                    closeKeyboard();
                    updateTotalQuestionsUI();
                }
            });

        questionListBtn
            .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(questions.size() <= 0) {
                        Toast.makeText(CreateCustomQuizActivity.this, "Please add at least one question before submitting.", Toast.LENGTH_LONG).show();
                    } else {
                        String randomCode = GenerateRandomString.generateRandomString(RANDOM_STRING_LENGTH);

                        for(int i=0; i<questions.size(); i++)   {
                            questions.get(i).setQuizId(randomCode);
                            questionDatabase.child(mAuth.getCurrentUser().getUid()).child(randomCode).child("Question"+questions.get(i).getQuestionId()).setValue(questions.get(i));
                            ArrayList<QuestionChoice> qc = questions.get(i).getQuestionChoice();
                            for(int j=0; j<qc.size(); j++)
                                questionChoiceDatabase.child(mAuth.getCurrentUser().getUid()).child(randomCode).child("Question"+questions.get(i).getQuestionId()).child("Choice"+(j+1)).setValue(qc.get(j));
                        }

                        questions.clear();
                        updateTotalQuestionsUI();
                        Toast.makeText(CreateCustomQuizActivity.this, "Successfully created custom quiz.", Toast.LENGTH_LONG).show();
                        Intent Layer = new Intent(CreateCustomQuizActivity.this, CustomQuizInfoActivity.class);
                        startActivity(Layer);
                    }
                    updateTotalQuestionsUI();
//                    if(questions.size() <= 0)   {
//                        Toast.makeText(CreateCustomQuizActivity.this, "Please add at least one question before submitting.", Toast.LENGTH_LONG).show();
//                    } else {
//                        Intent Layer = new Intent(CreateCustomQuizActivity.this, CustomQuizQuestionListActivity.class);
//                        Bundle bd = new Bundle();
//                        bd.putParcelableArrayList("questionList", questions);
//                        Layer.putExtras(bd);
//                        startActivity(Layer);
//                    }
                }
            });
    }

    private void updateTotalQuestionsUI()   {
        final TextView totalQuestions = findViewById(R.id.totalQuestionTV);
        totalQuestions.setText("Total questions: " + questions.size());
    }

    private void closeKeyboard()    {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
