package com.example.pokemonacademy.Control;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.example.pokemonacademy.Entity.Question;
import com.example.pokemonacademy.Entity.QuestionChoice;
import com.example.pokemonacademy.Entity.QuizzesCompleted;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.GridLayout;
import android.widget.Toast;

import com.example.pokemonacademy.R;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class QuizLandingPage extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference userDb;
    private DatabaseReference questionDb;
    private DatabaseReference choiceDb;
    private DatabaseReference quizzesCompletedDb;
    public FirebaseUser currentUser;
    public String userID;
    public String worldName;
    public int worldID;
    public ArrayList<Question> questionList1;
    public ArrayList<Question> questionList2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        userDb = FirebaseDatabase.getInstance().getReference("USER");
        quizzesCompletedDb = FirebaseDatabase.getInstance().getReference("QUIZZES_COMPLETED");
        currentUser = mAuth.getCurrentUser();
        userID = currentUser.getUid();

        Intent intent = getIntent();
        worldName = intent.getStringExtra("worldName");
        worldID = intent.getIntExtra("worldID", -1);

        Log.i("User","user "+currentUser.getUid());
        Log.i("World","world "+worldID);

        quizzesCompletedDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
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

                setContentView(R.layout.activity_quiz_landing_page);
                LinearLayout background = (LinearLayout)findViewById(R.id.miniquizlinearlayout);
                background.setBackground(dynamicBackground);
                TextView miniquizheading = (TextView)findViewById(R.id.miniquizlandingheading);
                miniquizheading.setText(worldName);
                miniquizheading.setTextColor(textColor);

                GridLayout npcGrid = findViewById(R.id.npcGrid);

                // Set mini quiz
                for (int i = 0; i<npcGrid.getChildCount();i++){
                    final CardView miniQuiz = (CardView)npcGrid.getChildAt(i);
                    ((ImageView)((LinearLayout)miniQuiz.getChildAt(0)).getChildAt(0)).setImageResource(getRandomNpcImage());

                    miniQuiz.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view){
                            Intent intent = getIntent();
                            String worldName = intent.getStringExtra("worldName");
                            int worldID = intent.getIntExtra("worldID", -1);

                            Intent Layer = new Intent(QuizLandingPage.this, Quiz.class);
                            TextView tv = (TextView)((LinearLayout)miniQuiz.getChildAt(0)).getChildAt(1);
                            String miniQuizName = tv.getText().toString();
                            if (miniQuizName.equals("Mini Quiz 1")){
                                Layer.putExtra("miniQuizID", 0);
                            } else if (miniQuizName.equals("Mini Quiz 2")){
                                Layer.putExtra("miniQuizID", 1);
                            } else if (miniQuizName.equals("Final Quiz")){
                                Layer.putExtra("miniQuizID", 2);
                            } else {
                                Layer.putExtra("miniQuizID", -1);
                            }
                            Layer.putExtra("miniQuizName", miniQuizName);
                            Layer.putExtra("worldName", worldName);
                            Layer.putExtra("worldID", worldID);
                            startActivity(Layer);
                        }
                    });
                }

                // Set final quiz visible only after completing all quizzes.
                DataSnapshot dataSnap;
                QuizzesCompleted qc0 = new QuizzesCompleted();
                QuizzesCompleted qc1 = new QuizzesCompleted();
                qc0.setCompleted(false);
                qc1.setCompleted(false);
                dataSnap = dataSnapshot.child(currentUser.getUid()).child("World0").child("Quiz0");
                if (dataSnap.getValue(QuizzesCompleted.class)!=null) {qc0 = dataSnap.getValue(QuizzesCompleted.class);}
                dataSnap = dataSnapshot.child(currentUser.getUid()).child("World"+worldID).child("Quiz1");
                if (dataSnap.getValue(QuizzesCompleted.class)!=null) {qc1 = dataSnap.getValue(QuizzesCompleted.class);}

                CardView npcFinal = (CardView)findViewById(R.id.npcfinal);
                if (!(qc1.getCompleted() && qc0.getCompleted())){
                    npcFinal.setVisibility(View.INVISIBLE);
                } else {
                    npcFinal.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
                Layer = new Intent(QuizLandingPage.this, WorldActivity.class);
                startActivity(Layer);
                return true;
            case R.id.tologout:
                mAuth.signOut();
                Layer = new Intent(QuizLandingPage.this, MainActivity.class);
                Toast.makeText(QuizLandingPage.this, "Successfully logged out.",
                        Toast.LENGTH_SHORT).show();
                startActivity(Layer);
                finish();
                return true;
            case R.id.toleaderboard:
                Layer = new Intent(QuizLandingPage.this, Leaderboard.class);
                startActivity(Layer);
                Toast.makeText(QuizLandingPage.this, "Welcome to the leaderboard!",
                        Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
