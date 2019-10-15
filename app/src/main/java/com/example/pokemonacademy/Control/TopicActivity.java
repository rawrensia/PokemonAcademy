package com.example.pokemonacademy.Control;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.pokemonacademy.R;

public class TopicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        Intent intent = getIntent();
        String worldName = intent.getStringExtra("worldName");
        int worldID = intent.getIntExtra("worldID", -1);

        TextView tv = findViewById(R.id.topicpage);
        tv.setText(worldName);

        GridLayout mainGrid = findViewById(R.id.topicGrid);
        setSingleEvent(mainGrid, worldID);
    }

    private void setSingleEvent(GridLayout mainGrid, final int worldID){
        //Loop all child item of Main Grid
        for (int i = 0; i<mainGrid.getChildCount();i++)
        {
            final CardView cardView = (CardView)mainGrid.getChildAt(i);

            if(i == 0) {
                cardView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        Intent Layer = new Intent(TopicActivity.this, ContentActivity.class);
                        Layer.putExtra("worldName", ((TextView) findViewById(R.id.topicpage)).getText());
                        Layer.putExtra("worldID", worldID);
                        startActivity(Layer);
                    }
                });
            } else {
                cardView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        Intent Layer = new Intent(TopicActivity.this, MiniQuiz.class);
                        startActivity(Layer);
                    }
                });
            }
        }

        ImageButton worldBackBtn = findViewById(R.id.topicBackBtn);
        worldBackBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)  {
                finish();
            }
        });
    }
}
