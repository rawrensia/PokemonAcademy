package com.example.pokemonacademy.Control;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.pokemonacademy.Entity.User;
import com.example.pokemonacademy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;

public class PlayCustomQuizActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_quiz);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("USER");

    }

//    private void userPageSelection() {
//        DatabaseReference reference = mDatabase.child(mAuth.getCurrentUser().getUid());
//
//        ValueEventListener userListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // Get Post object and use the values to update the UI
//                Intent Layer;
//                User user = dataSnapshot.getValue(User.class);
//
//                if (user.getUserType().equalsIgnoreCase("T")) {
//                    Layer = new Intent(LoginActivity.this, SummaryReport.class);
//                    startActivity(Layer);
//                }
//                if (user.getFirstTime().equalsIgnoreCase("false")) {
//                    Layer = new Intent(LoginActivity.this, WorldActivity.class);
//                    startActivity(Layer);
//                } else {
//                    Layer = new Intent(LoginActivity.this, ChooseCharacterActivity.class);
//                }
//                startActivity(Layer);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Getting Post failed, log a message
//                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
//            }
//        };
//        reference.addListenerForSingleValueEvent(userListener);
//    }
}
