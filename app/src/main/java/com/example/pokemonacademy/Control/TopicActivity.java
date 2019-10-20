package com.example.pokemonacademy.Control;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Random;

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

        ImageView imageView = (ImageView) findViewById(R.id.trainerView);
        imageView.setImageResource(getRandomImage());
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
                        Intent Layer = new Intent(TopicActivity.this, MiniQuizLandingPage.class);
                        Layer.putExtra("worldName", ((TextView) findViewById(R.id.topicpage)).getText());
                        Layer.putExtra("worldID", worldID);
                        ImageView imgView = (ImageView)findViewById(R.id.trainerView);
                        startActivity(Layer);
                    }
                });
            }
        }
    }

    private int getRandomImage() {
        TypedArray imgs = getResources().obtainTypedArray(R.array.trainer_imgs);
        // or set you ImageView's resource to the id
        int id = imgs.getResourceId(new Random().nextInt(imgs.length()), -1); //-1 is default if nothing is found (we don't care)
        imgs.recycle();
        return id;
    }
}
