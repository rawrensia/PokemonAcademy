package com.example.pokemonacademy.Control;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pokemonacademy.R;

import androidx.appcompat.app.AppCompatActivity;

public class CustomQuizActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_quiz);

        LinearLayout linearLayout = findViewById(R.id.custom_quiz_layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        setSingleEvent();
    }

    private void setSingleEvent()   {
        Button createBtn = findViewById(R.id.createCustomQuizBtn);
        Button playBtn = findViewById(R.id.playCustomQuizBtn);
        final TextView playTV = findViewById(R.id.playCustomQuizTV);

        createBtn
            .setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     Intent Layer = new Intent(CustomQuizActivity.this, CreateCustomQuizActivity.class);
                     startActivity(Layer);
                 }
            }
        );

        playBtn
            .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!playTV.getText().toString().isEmpty())  {
                        Intent Layer = new Intent(CustomQuizActivity.this, PlayCustomQuizActivity.class);
                        Layer.putExtra("playCQID", playTV.getText().toString());
                        startActivity(Layer);
                    } else {
                        Toast.makeText(CustomQuizActivity.this, "Please enter a correct code.", Toast.LENGTH_LONG).show();
                    }
                }
            });

    }
}
