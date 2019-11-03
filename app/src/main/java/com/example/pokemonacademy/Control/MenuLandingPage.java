package com.example.pokemonacademy.Control;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.pokemonacademy.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MenuLandingPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_landing_page);

        setAnimation();
        setSingleEvent();
    }

    private void setSingleEvent()   {
        CardView worldTrainingBtn = findViewById(R.id.worldTrainingBtn);
        CardView customModeBtn = findViewById(R.id.customModeBtn);

        worldTrainingBtn
            .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent Layer = new Intent(MenuLandingPage.this, WorldActivity.class);
                    startActivity(Layer);
                    finish();
                }
            });

        customModeBtn
            .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent Layer = new Intent(MenuLandingPage.this, CustomQuizActivity.class);
                    startActivity(Layer);
                }
            });
    }

    private void setAnimation() {
        LinearLayout linearLayout = findViewById(R.id.menulandingpage);
        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();
    }
}
