package com.example.pokemonacademy.Control;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GameplayManager {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference questionDb = FirebaseDatabase.getInstance().getReference("QUESTION");
    DatabaseReference userDb = FirebaseDatabase.getInstance().getReference("USER");
    FirebaseUser currentUser = mAuth.getCurrentUser();
    String userID = currentUser.getUid();

}
