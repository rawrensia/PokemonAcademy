package com.example.pokemonacademy.Control;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pokemonacademy.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ChooseCharacterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_character);

        GridLayout mainGrid = findViewById(R.id.characterGrid);
        //Set Event
        setSingleEvent(mainGrid);
    }

    private void setSingleEvent(GridLayout mainGrid) {
        //Loop all child item of Main Grid
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            final CardView cardView = (CardView) mainGrid.getChildAt(i);

            cardView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent Layer = new Intent(ChooseCharacterActivity.this, WorldActivity.class);
                    TextView tv = (TextView) ((LinearLayout) cardView.getChildAt(0)).getChildAt(1);
                    startActivity(Layer);
                }
            });
        }
    }

}
