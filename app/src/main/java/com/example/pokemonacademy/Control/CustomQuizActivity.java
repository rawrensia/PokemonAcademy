package com.example.pokemonacademy.Control;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pokemonacademy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
/**
 * The CustomQuizActivity displays the activity_custom_quiz.xml
 * It allows the user to input custom quiz ids try custom quizzes created
 * by other users, Or the user can create their own custom quiz
 * by selecting the create custom quiz button, Or the user can view the custom quizzes
 * he has created.
 *
 * @author  Benjamin Lim
 * @since   2019-11-01
 */
public class CustomQuizActivity extends AppCompatActivity {

    private DatabaseReference questiondDB;
    private ArrayList<String> quizCodes = new ArrayList<>();
    private ArrayList<String> worldCodes = new ArrayList<>();
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_quiz);

        LinearLayout linearLayout = findViewById(R.id.custom_quiz_layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        questiondDB = FirebaseDatabase.getInstance().getReference("QUESTION");
        mAuth = FirebaseAuth.getInstance();

        customQuizDatabaseQuery();

        setSingleEvent();
    }

    private void setSingleEvent()   {
        Button createBtn = findViewById(R.id.createCustomQuizBtn);
        Button customQuizInfoBtn = findViewById(R.id.customQuizInfoBtn);
        Button playBtn = findViewById(R.id.playCustomQuizBtn);

        final TextView playTV = findViewById(R.id.enterCustomQuizIdTV);

        createBtn
            .setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     Intent Layer = new Intent(CustomQuizActivity.this, CreateCustomQuizActivity.class);
                     startActivity(Layer);
                 }
            }
        );

        customQuizInfoBtn
            .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent Layer = new Intent(CustomQuizActivity.this, CustomQuizInfoActivity.class);
                    startActivity(Layer);
                }
            });

        playBtn
            .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!playTV.getText().toString().isEmpty())  {
                        for(int i=0; i<quizCodes.size(); i++){
                            String s = quizCodes.get(i);
                            if(s.equals(playTV.getText().toString())) {
                                Intent Layer = new Intent(CustomQuizActivity.this, Quiz.class);
                                Layer.putExtra("worldID", -1);
                                Layer.putExtra("worldName", worldCodes.get(i));
                                Layer.putExtra("customworldID", worldCodes.get(i));
                                Layer.putExtra("miniQuizName", playTV.getText().toString());
                                Layer.putExtra("miniQuizID",-1);
                                startActivity(Layer);
                                break;
                            }
                        }
                    } else {
                        Toast.makeText(CustomQuizActivity.this, "Please enter a correct code.", Toast.LENGTH_LONG).show();
                    }
                }
            });
    }

    private void customQuizDatabaseQuery()  {
        DatabaseReference reference = questiondDB;

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    if(!data.getRef().getParent().getKey().matches("World[0-5]")){
                        worldCodes.add(data.getRef().getParent().getKey());
                        quizCodes.add(data.getKey());
                        Log.i("CustomQuizActivityParen", ""+data.getRef().getParent().getKey());
                        Log.i("CustomQuizActivity", ""+data.getKey());
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    worldCodes.remove(data.getRef().getParent().getKey());
                    quizCodes.remove(data.getKey());
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_logout_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent Layer;
        switch (item.getItemId()) {
            case R.id.tomenulp:
                Layer = new Intent(CustomQuizActivity.this, MenuLandingPage.class);
                startActivity(Layer);
                return true;
            case R.id.tologout:
                mAuth.signOut();
                Layer = new Intent(CustomQuizActivity.this, MainActivity.class);
                Toast.makeText(CustomQuizActivity.this, "Successfully logged out.",
                        Toast.LENGTH_LONG).show();
                startActivity(Layer);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
