/**
 * The QuestionReview displays the activity_question_review.xml
 * The user is able to view the full question, his selected choice and the correct choice.
 *
 * @author  Lawrann
 * @version 1.0
 * @since   2019-10-07
 */

package com.example.pokemonacademy.Control;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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

        ConstraintLayout constLayout = findViewById(R.id.question_review_layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

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
