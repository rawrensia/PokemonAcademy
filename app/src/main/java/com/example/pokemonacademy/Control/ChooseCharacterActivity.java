package com.example.pokemonacademy.Control;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

import androidx.appcompat.app.AppCompatActivity;

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

    private User updateDB(User user) {

        FirebaseUser currentUser = mAuth.getCurrentUser();
        user.setId(currentUser.getUid());

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
                            mainGrid.getChildAt(j).setSelected(false);
                        }
                }
            });
        }

        findViewById(R.id.profile_button_next)
                .setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        int char_id = -1;
                        String txtCourseIndex = ((TextView) findViewById(R.id.course_index)).getText().toString();
                        String txtName = ((TextView) findViewById(R.id.profile_name)).getText().toString();

                        for(int i=0; i<mainGrid.getChildCount(); i++)   {
                            if(mainGrid.getChildAt(i).isSelected())
                                char_id = i;
                        }

                        // VALIDATION
                        if (txtName.isEmpty() || txtCourseIndex.isEmpty())
                            Toast.makeText(ChooseCharacterActivity.this, "Unable to proceed, please enter all information.", Toast.LENGTH_LONG).show();
                        else {
                            if (char_id == -1) {
                                Toast.makeText(ChooseCharacterActivity.this, "Please select a character.", Toast.LENGTH_LONG).show();
                            } else if (!txtCourseIndex.matches("[0-9]*")) {
                                Toast.makeText(ChooseCharacterActivity.this, "Invalid Course Index", Toast.LENGTH_LONG).show();
                            } else {
                                // PASSED VALIDATION
                                User user = new User();
                                user.setName(txtName);
                                user.setUserType("S");
                                user.setCourseIndex(Integer.parseInt(txtCourseIndex));
                                user.setCharId(char_id);
                                user.setFirstTime("False");

                                user = updateDB(user);
                                Intent Layer = new Intent(ChooseCharacterActivity.this, WorldActivity.class);
                                Layer.putExtra("object", user);
                                startActivity(Layer);
                                finish();
                            }
                        }
                    }
                });
    }

}
