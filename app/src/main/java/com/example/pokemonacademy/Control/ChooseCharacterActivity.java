package com.example.pokemonacademy.Control;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pokemonacademy.Entity.User;
import com.example.pokemonacademy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.lang.invoke.ConstantCallSite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class ChooseCharacterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_character);

        mDatabase = FirebaseDatabase.getInstance().getReference("USER");
        mAuth = FirebaseAuth.getInstance();

        LinearLayout mainGrid = findViewById(R.id.characterGrid);

        //Set Event
        setSingleEvent(mainGrid);
    }

    private User updateDB(LinearLayout mainGrid) {
        int char_id = -1;
        FirebaseUser currentUser = mAuth.getCurrentUser();
        // update user profile based on id
        String txtName = ((TextView) findViewById(R.id.profile_name)).getText().toString();
        int txtCourseIndex = Integer.parseInt(((TextView) findViewById(R.id.course_index)).getText().toString());

        for(int i=0; i<mainGrid.getChildCount(); i++)   {
            if(((ImageView)mainGrid.getChildAt(i)).isSelected())
                char_id = i;
        }
//
        User user = new User(currentUser.getUid(), txtName, "S", txtCourseIndex, char_id, "False");

        mDatabase.child(currentUser.getUid()).setValue(user);

        return user;
    }

    private void setSingleEvent(final LinearLayout mainGrid) {
        //Loop all child item of Main Grid
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            final ImageView imageView = (ImageView) mainGrid.getChildAt(i);
            final int imageNumber = i;
            imageView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    imageView.setColorFilter(Color.argb(50, 0, 0, 0));
                    imageView.setSelected(true);
                    for(int j=0; j<4; j++)
                        if(imageNumber != j) {
                            ((ImageView) mainGrid.getChildAt(j)).setColorFilter(Color.argb(0, 0, 0, 0));
                            ((ImageView) mainGrid.getChildAt(j)).setSelected(false);
                        }
                }
            });
        }

        findViewById(R.id.profile_button_next)
                .setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        int txtCourseIndex = -1;
                        String txtName = ((TextView) findViewById(R.id.profile_name)).getText().toString();
                        txtCourseIndex = Integer.parseInt(((TextView) findViewById(R.id.course_index)).getText().toString());
                        if (txtName.isEmpty() || txtCourseIndex == -1)
                            Toast.makeText(ChooseCharacterActivity.this, "Unable to proceed, please enter all information.", Toast.LENGTH_LONG).show();
                        else {
                            User user = updateDB(mainGrid);
                            Intent Layer = new Intent(ChooseCharacterActivity.this, WorldActivity.class);
                            Layer.putExtra("object", user);
                            startActivity(Layer);
                            finish();
                        }
                    }
                });
    }

}
