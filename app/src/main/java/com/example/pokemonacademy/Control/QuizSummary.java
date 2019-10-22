package com.example.pokemonacademy.Control;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.pokemonacademy.Entity.Choice;
import com.example.pokemonacademy.Entity.Question;
import com.example.pokemonacademy.Entity.UserQnsAns;
import com.example.pokemonacademy.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class QuizSummary extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference databaseReferenceUser = FirebaseDatabase.getInstance().getReference("USER");
    private DatabaseReference databaseReferenceQuestion = FirebaseDatabase.getInstance().getReference("QUESTION");
    private DatabaseReference databaseReferenceQnsChoice = FirebaseDatabase.getInstance().getReference("QUESTION_CHOICES");
    private DatabaseReference databaseReferenceUserQnsAns = FirebaseDatabase.getInstance().getReference("USER_QUESTION_ANS");

    // Adding to database.
//    UserQnsAns addUserQnsAns = new UserQnsAns(userId, qnsId, choiceId, isRight);
//    databaseReferenceUserQnsAns.child(userIdString).child(qnsIdString).child(choiceIdString).setValue(addUserQnsAns);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_summary);

        Intent intent = getIntent();

        String worldName = intent.getStringExtra("worldName");
        String miniQuizNum = intent.getStringExtra("miniQuizNum");
        int worldID = intent.getIntExtra("worldID", -1);
        int[] timeTaken = intent.getIntArrayExtra("timeTaken");
        ArrayList<Question> questionAnswered = intent.getExtras().getParcelableArrayList("questionAnswered");
        ArrayList<Choice> choiceChosen = intent.getExtras().getParcelableArrayList("choiceChosen");

        for (int i=0;i<10; i++){
            Log.d("time","Time Taken " + timeTaken[i]);
            Log.d("question","Question: " + questionAnswered.get(i).question);
            Log.d("choice","Selected Choice: " + choiceChosen.get(i).choice);
        }

    }
}
