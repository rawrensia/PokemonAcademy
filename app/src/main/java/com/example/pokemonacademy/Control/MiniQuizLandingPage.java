package com.example.pokemonacademy.Control;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.icu.lang.UCharacter;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.GridLayout;

import com.example.pokemonacademy.R;

import java.util.Random;

public class MiniQuizLandingPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String worldName = intent.getStringExtra("worldName");
        int worldID = intent.getIntExtra("worldID", -1);

        Drawable dynamicBackground, finalQuizNpc;
        int textColor;

        switch (worldName) {
            case ("Planning"):
                dynamicBackground = ResourcesCompat.getDrawable(getResources(), R.drawable.quiz_landing_page_m5, null);
                finalQuizNpc = ResourcesCompat.getDrawable(getResources(), R.drawable.elite1, null);
                textColor = Color.parseColor("#ebe850");
                break;
            case ("Analysis"):
                dynamicBackground = ResourcesCompat.getDrawable(getResources(), R.drawable.quiz_landing_page_m6, null);
                finalQuizNpc = ResourcesCompat.getDrawable(getResources(), R.drawable.elite2, null);
                textColor = Color.parseColor("#272a40");
                break;
            case ("Design"):
                dynamicBackground = ResourcesCompat.getDrawable(getResources(), R.drawable.quiz_landing_page_m4, null);
                finalQuizNpc = ResourcesCompat.getDrawable(getResources(), R.drawable.elite3, null);
                textColor = Color.parseColor("#941b27");
                break;
            case ("Implementation"):
                dynamicBackground = ResourcesCompat.getDrawable(getResources(), R.drawable.quiz_landing_page_m3, null);
                finalQuizNpc = ResourcesCompat.getDrawable(getResources(), R.drawable.elite4, null);
                textColor = Color.parseColor("#1b9451");
                break;
            case ("Testing & Integration"):
                dynamicBackground = ResourcesCompat.getDrawable(getResources(), R.drawable.quiz_landing_page_m1, null);
                finalQuizNpc = ResourcesCompat.getDrawable(getResources(), R.drawable.elite5, null);
                textColor = Color.parseColor("#0d0982");
                break;
            case ("Maintenance"):
                dynamicBackground = ResourcesCompat.getDrawable(getResources(), R.drawable.quiz_landing_page_m2, null);
                finalQuizNpc = ResourcesCompat.getDrawable(getResources(), R.drawable.elite6, null);
                textColor = Color.parseColor("#cbb8f2");
                break;
            default: // use for custom
                dynamicBackground = ResourcesCompat.getDrawable(getResources(), R.drawable.quiz_landing_page_m6, null);
                finalQuizNpc = ResourcesCompat.getDrawable(getResources(), R.drawable.elite6, null);
                textColor = Color.parseColor("#825e09");
                break;
        }

//        switch (worldID) {
//            case (WorldActivity.WORLD_PLANNING_ID):
//                dynamicBackground = ResourcesCompat.getDrawable(getResources(), R.drawable.quiz_landing_page_m1, null);
//                break;
//            case (WorldActivity.WORLD_ANALYSIS_ID):
//                dynamicBackground = ResourcesCompat.getDrawable(getResources(), R.drawable.quiz_landing_page_m2, null);
//                break;
//            case (WorldActivity.WORLD_DESIGN_ID):
//                dynamicBackground = ResourcesCompat.getDrawable(getResources(), R.drawable.quiz_landing_page_m3, null);
//                break;
//            case (WorldActivity.WORLD_IMPLEMENTATION_ID):
//                dynamicBackground = ResourcesCompat.getDrawable(getResources(), R.drawable.quiz_landing_page_m4, null);
//                break;
//            case (WorldActivity.WORLD_TESTING_ID):
//                dynamicBackground = ResourcesCompat.getDrawable(getResources(), R.drawable.quiz_landing_page_m5, null);
//                break;
//            case (WorldActivity.WORLD_MAINTENANCE_ID):
//                dynamicBackground = ResourcesCompat.getDrawable(getResources(), R.drawable.quiz_landing_page_m6, null);
//                break;
//            default: // use for custom
//                dynamicBackground = ResourcesCompat.getDrawable(getResources(), R.drawable.quiz_landing_page_m6, null);
//                break;
//        }

        setContentView(R.layout.activity_mini_quiz_landing_page);
        LinearLayout background = (LinearLayout)findViewById(R.id.miniquizlinearlayout);
        background.setBackground(dynamicBackground);
        TextView miniquizheading = (TextView)findViewById(R.id.miniquizlandingheading);
        miniquizheading.setText(worldName);
        miniquizheading.setTextColor(textColor);

        GridLayout npcGrid = findViewById(R.id.npcGrid);
        // Set final quiz
        final CardView finalQuiz = (CardView)npcGrid.getChildAt(npcGrid.getChildCount()-1);
        ((ImageView)((LinearLayout)finalQuiz.getChildAt(0)).getChildAt(0)).setImageDrawable(finalQuizNpc);
        finalQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Layer = new Intent(MiniQuizLandingPage.this, FinalQuiz.class);
                TextView tv = (TextView)((LinearLayout)finalQuiz.getChildAt(0)).getChildAt(1);
                Layer.putExtra("worldName", tv.getText().toString());
                Layer.putExtra("worldID", finalQuiz.getId());
                startActivity(Layer);
            }
        });
        // Set mini quiz
        for (int i = 0; i<npcGrid.getChildCount()-1;i++){
            final CardView miniQuiz = (CardView)npcGrid.getChildAt(i);
            ((ImageView)((LinearLayout)miniQuiz.getChildAt(0)).getChildAt(0)).setImageResource(getRandomNpcImage());

            miniQuiz.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view){
                    Intent Layer = new Intent(MiniQuizLandingPage.this, MiniQuiz.class);
                    TextView tv = (TextView)((LinearLayout)miniQuiz.getChildAt(0)).getChildAt(1);
                    Layer.putExtra("worldName", tv.getText().toString());
                    Layer.putExtra("worldID", miniQuiz.getId());
                    startActivity(Layer);
                }
            });
        }
    }

    private int getRandomNpcImage() {
        TypedArray imgs = getResources().obtainTypedArray(R.array.npc_imgs);
        // or set you ImageView's resource to the id
        int id = imgs.getResourceId(new Random().nextInt(imgs.length()), -1); //-1 is default if nothing is found (we don't care)
        imgs.recycle();
        return id;
    }

    // Get from db which quiz mini quiz has completed by the user

}
