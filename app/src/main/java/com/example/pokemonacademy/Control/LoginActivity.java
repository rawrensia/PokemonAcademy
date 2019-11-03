package com.example.pokemonacademy.Control;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pokemonacademy.Entity.User;
import com.example.pokemonacademy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText txtEmail;
    EditText txtPassword;
    Button btnLogin;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private static final String TAG = "ChooseCharacterActivity";
    private Animation from_down;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtEmail = findViewById(R.id.email);
        txtPassword = findViewById(R.id.password);
        txtPassword.setTransformationMethod(new AsteriskPassword());
        btnLogin = findViewById(R.id.button_login);

        from_down = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        btnLogin.setAnimation(from_down);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("USER");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();
                if (email.isEmpty() || password.isEmpty())
                    Toast.makeText(LoginActivity.this, "Failed to log in, please enter the correct login information.", Toast.LENGTH_LONG).show();
                else
                    authenticate(email, password);
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Log.i("whathappen", " "+currentUser);
        checkUser(currentUser);
    }

    private void checkUser(FirebaseUser currentUser)    {
        if(currentUser != null) {
            userPageSelection();
        }
    }

    private void userPageSelection() {
        DatabaseReference reference = mDatabase.child(mAuth.getCurrentUser().getUid());
        Log.i("whathappen", " "+reference);

        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Intent Layer;
                User user = dataSnapshot.getValue(User.class);

                if (user.getUserType().equalsIgnoreCase("T")) {
                    Layer = new Intent(LoginActivity.this, TeacherMenuLandingPage.class);
                } else if (user.getFirstTime().equalsIgnoreCase("False")) {
                    Layer = new Intent(LoginActivity.this, MenuLandingPage.class);
                } else {
                    Layer = new Intent(LoginActivity.this, ChooseCharacterActivity.class);
                }
                startActivity(Layer);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        reference.addListenerForSingleValueEvent(userListener);
    }

    private void authenticate(String email, String password)  {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            checkUser(user);
                            Toast.makeText(LoginActivity.this, "Login successful!",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed. Invalid username or password. Please try again",
                                    Toast.LENGTH_LONG).show();
                            checkUser(null);
                        }
                    }
                });
    }
}
