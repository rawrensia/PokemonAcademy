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

public class CustomQuizActivity extends AppCompatActivity {

    private DatabaseReference questiondDB;
    private ArrayList<String> quizCodes = new ArrayList<>();

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
                        for(String s : quizCodes)
                            if(s.equals(playTV.getText().toString())) {
                                Intent Layer = new Intent(CustomQuizActivity.this, PlayCustomQuizActivity.class);
                                Layer.putExtra("playCQID", playTV.getText().toString());
                                startActivity(Layer);
                                break;
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
                for(DataSnapshot data : dataSnapshot.getChildren())
                    if(!data.getKey().matches("World[0-5]"))
                        quizCodes.add(data.getKey());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren())
                    quizCodes.remove(data.getKey());
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
