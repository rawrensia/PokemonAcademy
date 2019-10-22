package com.example.pokemonacademy.Control;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pokemonacademy.R;

public class WorldActivity extends AppCompatActivity {
    public final static int WORLD_PLANNING_ID = 0;
    public final static int WORLD_ANALYSIS_ID = 1;
    public final static int WORLD_DESIGN_ID = 2;
    public final static int WORLD_IMPLEMENTATION_ID = 3;
    public final static int WORLD_TESTING_ID = 4;
    public final static int WORLD_MAINTENANCE_ID = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world_selection);


        GridLayout mainGrid = findViewById(R.id.worldGrid);
        //Set Event
        setSingleEvent(mainGrid);
    }

    private void setSingleEvent(GridLayout mainGrid){
        //Loop all child item of Main Grid
        for (int i = 0; i<mainGrid.getChildCount();i++)
        {
            final CardView cardView = (CardView)mainGrid.getChildAt(i);
            final int counter = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view){
                    Intent Layer = new Intent(WorldActivity.this, TopicActivity.class);
                    TextView tv = (TextView)((LinearLayout)cardView.getChildAt(0)).getChildAt(1);
                    Layer.putExtra("worldName", tv.getText().toString());
                    Layer.putExtra("worldID", counter);
                    startActivity(Layer);
                }
            });
        }

    }
}
