package com.example.pokemonacademy.Control;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pokemonacademy.R;

import androidx.appcompat.app.AppCompatActivity;

public class ChooseCharacterActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_character);

        LinearLayout mainGrid = findViewById(R.id.characterGrid);

        //Set Event
        setSingleEvent(mainGrid);
    }

    @Override
    protected void onStart()    {
        super.onStart();

        if(!firstTimeUserCheck())   {
            Intent Layer = new Intent(ChooseCharacterActivity.this, WorldActivity.class);
            startActivity(Layer);
        }
    }

    private void setSingleEvent(final LinearLayout mainGrid) {
        //Loop all child item of Main Grid
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            final ImageView imageView = (ImageView) mainGrid.getChildAt(i);
            final int imageNumber = i;
            imageView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    imageView.setColorFilter(Color.argb(50, 0, 0, 0));
                    for(int j=0; j<4; j++)
                        if(imageNumber != j)
                            ((ImageView) mainGrid.getChildAt(j)).setColorFilter(Color.argb(0, 0, 0, 0));
                }
            });
        }

        findViewById(R.id.profile_button_next)
                .setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        updateDB();
                        Intent Layer = new Intent(ChooseCharacterActivity.this, WorldActivity.class);
                        startActivity(Layer);
                        finish();
                    }
                });
    }

    private void updateDB() {
        // update user profile based on id
        String txtName = ((TextView) findViewById(R.id.profile_name)).getText().toString();
        String txtCourseIndex = ((TextView) findViewById(R.id.course_index)).getText().toString();
    }

    private boolean firstTimeUserCheck()  {
        return true;
    }
}
