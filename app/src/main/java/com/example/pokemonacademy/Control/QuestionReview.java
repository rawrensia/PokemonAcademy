package com.example.pokemonacademy.Control;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.pokemonacademy.R;

import org.w3c.dom.Text;

public class QuestionReview extends AppCompatActivity {

    String q;
    String sc;
    String cc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_review);

        Intent intent = getIntent();

        q = intent.getStringExtra("questionAnswered");
        sc = intent.getStringExtra("choiceChosen");
        cc = intent.getStringExtra("rightChoice");

        TextView question = (TextView)findViewById(R.id.question);
        TextView selectedChoice = (TextView)findViewById(R.id.selectedChoice);
        TextView correctChoice = (TextView)findViewById(R.id.correctChoice);

        question.setText("Question: " + q);
        selectedChoice.setText("Selected Choice: " + sc);
        correctChoice.setText("Correct Choice: " + cc);
    }
}
