package com.example.pokemonacademy.Control;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;

import com.example.pokemonacademy.R;
import com.example.pokemonacademy.UserInterface.Topic;

public class World extends AppCompatActivity {

    GridLayout mainGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world_selection);

        mainGrid = (GridLayout)findViewById(R.id.mainGrid);
        //Set Event
        setSingleEvent(mainGrid);
    }

    private void setSingleEvent(GridLayout mainGrid){
        //Loop all child item of Main Grid
        for (int i = 0; i<mainGrid.getChildCount();i++)
        {
            CardView cardView = (CardView)mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view){
                    if (finalI == 0)
                    {
                        Intent Layer = new Intent(World.this, Topic.class);
                        startActivity(Layer);
                    }
                }
            });
        }
    }

}
