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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.GridLayout;
import android.widget.Toast;

import com.example.pokemonacademy.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Random;

public class MiniQuizLandingPage extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String worldName = intent.getStringExtra("worldName");
        int worldID = intent.getIntExtra("worldID", -1);
        Log.d("myTag", worldName);

        Drawable dynamicBackground, finalQuizNpc;
        int textColor;

        switch (worldName) {
            case ("PLANNING"):
                dynamicBackground = ResourcesCompat.getDrawable(getResources(), R.drawable.quiz_landing_page_m5, null);
                finalQuizNpc = ResourcesCompat.getDrawable(getResources(), R.drawable.elite1, null);
                textColor = Color.parseColor("#ebe850");
                break;
            case ("ANALYSIS"):
                dynamicBackground = ResourcesCompat.getDrawable(getResources(), R.drawable.quiz_landing_page_m6, null);
                finalQuizNpc = ResourcesCompat.getDrawable(getResources(), R.drawable.elite2, null);
                textColor = Color.parseColor("#C7BCE6");
                break;
            case ("DESIGN"):
                dynamicBackground = ResourcesCompat.getDrawable(getResources(), R.drawable.quiz_landing_page_m4, null);
                finalQuizNpc = ResourcesCompat.getDrawable(getResources(), R.drawable.elite3, null);
                textColor = Color.parseColor("#ffbf00");
                break;
            case ("IMPLEMENTATION"):
                dynamicBackground = ResourcesCompat.getDrawable(getResources(), R.drawable.quiz_landing_page_m3, null);
                finalQuizNpc = ResourcesCompat.getDrawable(getResources(), R.drawable.elite4, null);
                textColor = Color.parseColor("#1b9451");
                break;
            case ("TESTING & INTEGRATION"):
                dynamicBackground = ResourcesCompat.getDrawable(getResources(), R.drawable.quiz_landing_page_m1, null);
                finalQuizNpc = ResourcesCompat.getDrawable(getResources(), R.drawable.elite5, null);
                textColor = Color.parseColor("#0d0982");
                break;
            case ("MAINTENANCE"):
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
                Intent intent = getIntent();
                String worldName = intent.getStringExtra("worldName");
                int worldID = intent.getIntExtra("worldID", -1);

                //TODO
                // Final quiz
                Intent Layer = new Intent(MiniQuizLandingPage.this, MiniQuiz.class);
                TextView tv = (TextView)((LinearLayout)finalQuiz.getChildAt(0)).getChildAt(1);
                Layer.putExtra("finalQuizNum", tv.getText().toString());
                Layer.putExtra("worldName", worldName);
                Layer.putExtra("worldID", worldID);
                startActivity(Layer);
            }
        });
        // Set mini quiz
        for (int i = 0; i<npcGrid.getChildCount()-1;i++){
            final CardView miniQuiz = (CardView)npcGrid.getChildAt(i);
            ((ImageView)((LinearLayout)miniQuiz.getChildAt(0)).getChildAt(0)).setImageResource(getRandomNpcImage());

            miniQuiz.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view){
                    Intent intent = getIntent();
                    String worldName = intent.getStringExtra("worldName");
                    int worldID = intent.getIntExtra("worldID", -1);

                    Intent Layer = new Intent(MiniQuizLandingPage.this, MiniQuiz.class);
                    TextView tv = (TextView)((LinearLayout)miniQuiz.getChildAt(0)).getChildAt(1);
                    Layer.putExtra("miniQuizNum", tv.getText().toString());
                    Layer.putExtra("worldName", worldName);
                    Layer.putExtra("worldID", worldID);
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

    // TODO
    // Get from db which mini quiz has completed by the user
    // Mini quiz can be done in any order
    // Condition to check all miniquiz is completed before allowing access to Final quiz.

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_mini_quiz_lp, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tocontent:
                Intent Layer = getIntent();
                String worldName = Layer.getStringExtra("worldName");
                int worldID = Layer.getIntExtra("worldID", -1);
                Intent intent = new Intent(getApplicationContext(), ContentActivity.class);
                intent.putExtra("worldName", worldName);
                intent.putExtra("worldID", worldID);
                startActivity(intent);
                return true;
            case R.id.toworld:
                Layer = new Intent(MiniQuizLandingPage.this, WorldActivity.class);
                startActivity(Layer);
                return true;
            case R.id.tologout:
                mAuth.signOut();
                Layer = new Intent(MiniQuizLandingPage.this, MainActivity.class);
                Toast.makeText(MiniQuizLandingPage.this, "Successfully logged out.",
                        Toast.LENGTH_SHORT).show();
                startActivity(Layer);
                finish();
                return true;
            case R.id.toleaderboard:
                Layer = new Intent(MiniQuizLandingPage.this, Leaderboard.class);
                startActivity(Layer);
                Toast.makeText(MiniQuizLandingPage.this, "Welcome to the leaderboard!",
                        Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
