package com.example.pokemonacademy.Control;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.pokemonacademy.R;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class TeacherMenuLandingPage extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_landing_page_teacher);

        mAuth = FirebaseAuth.getInstance();

        setAnimation();
        setSingleEvent();
    }

    private void setSingleEvent()   {
        CardView summaryBtn = findViewById(R.id.summaryBtn);
        CardView customModeBtn = findViewById(R.id.teacher_customModeBtn);

        summaryBtn
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent Layer = new Intent(TeacherMenuLandingPage.this, SummaryReportActivity.class);
                        startActivity(Layer);
                    }
                });

        customModeBtn
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent Layer = new Intent(TeacherMenuLandingPage.this, CustomQuizActivity.class);
                        startActivity(Layer);
                    }
                });
    }

    private void setAnimation() {
        LinearLayout linearLayout = findViewById(R.id.teacher_menulandingpage);
        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_world, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.toleaderboard:
                Intent Layer = new Intent(TeacherMenuLandingPage.this, Leaderboard.class);
                startActivity(Layer);
                Toast.makeText(TeacherMenuLandingPage.this, "Welcome to the leaderboard!",
                        Toast.LENGTH_SHORT).show();
                return true;
            case R.id.tologout:
                mAuth.signOut();
                Layer = new Intent(TeacherMenuLandingPage.this, MainActivity.class);
                Toast.makeText(TeacherMenuLandingPage.this, "Successfully logged out.",
                        Toast.LENGTH_LONG).show();
                startActivity(Layer);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
