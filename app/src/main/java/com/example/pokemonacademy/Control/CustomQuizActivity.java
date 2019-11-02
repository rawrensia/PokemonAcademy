package com.example.pokemonacademy.Control;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pokemonacademy.Entity.User;
import com.example.pokemonacademy.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class CustomQuizActivity extends AppCompatActivity {

    private ArrayList<String> customWorldIdList;
    private ArrayList<String> customQuizIdList;
    private DatabaseReference questionDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_quiz);

        questionDB = FirebaseDatabase.getInstance().getReference("QUESTION");

        LinearLayout linearLayout = findViewById(R.id.custom_quiz_layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        questionDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                customWorldIdList = new ArrayList<String>();
                customQuizIdList = new ArrayList<String>();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    String w = ds.getKey();
                    customWorldIdList.add(w);
                }
                for (int i=0; i<customWorldIdList.size(); i++){
                    for (DataSnapshot ds : dataSnapshot.child(customWorldIdList.get(i)).getChildren()){
                        String q = ds.getKey();
                        if (!q.substring(0,4).equals("Quiz")){
                            customQuizIdList.add(q);
                            Log.i("customQuizId",""+q);
                        }
                    }
                }
                setSingleEvent();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void setSingleEvent()   {
        Button createBtn = findViewById(R.id.createCustomQuizBtn);
        Button playBtn = findViewById(R.id.playCustomQuizBtn);
        Button viewBtn = findViewById(R.id.customQuizInfoBtn);
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

        viewBtn
            .setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent Layer = new Intent(CustomQuizActivity.this, CustomQuizInfoActivity.class);
                                        startActivity(Layer);
                                    }
                                }
            );

        playBtn
            .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(playTV.getText().toString().isEmpty())  {
                        Intent Layer = new Intent(CustomQuizActivity.this, CustomQuizInfoActivity.class);
//                        Layer.putExtra("playCQID", playTV.getText().toString());
                        startActivity(Layer);
                    } else {
                        String customQuizId = playTV.getText().toString();
                        if (!checkDbCustomQuizId(customQuizId)) {
                            Toast.makeText(CustomQuizActivity.this, "Please enter a correct code.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(CustomQuizActivity.this, "TRUE", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
    }

    private boolean checkDbCustomQuizId(final String customQuizId){
        for (int i=0; i<customQuizIdList.size(); i++){
            if (customQuizId.equals(customQuizIdList.get(i))){
                return true;
            }
        }
        return false;
    }
}
