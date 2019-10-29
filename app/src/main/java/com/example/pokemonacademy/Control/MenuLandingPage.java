package com.example.pokemonacademy.Control;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.pokemonacademy.R;

import androidx.appcompat.app.AppCompatActivity;

public class MenuLandingPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_landing_page);

        LinearLayout linearLayout = findViewById(R.id.menulandingpage);
        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();
    }
}
