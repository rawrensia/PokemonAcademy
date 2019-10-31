package com.example.pokemonacademy.Control;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pokemonacademy.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


public class TopicActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        mAuth = FirebaseAuth.getInstance();

        LinearLayout linearLayout = findViewById(R.id.topiclayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        Intent intent = getIntent();
        String worldName = intent.getStringExtra("worldName");
        int worldID = intent.getIntExtra("worldID", -1);

        TextView tv = findViewById(R.id.topicpage);
        tv.setText(worldName);

        GridLayout mainGrid = findViewById(R.id.topicGrid);
        setSingleEvent(mainGrid, worldID);

        ImageView imageView = findViewById(R.id.trainerView);
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
                        Intent Layer = new Intent(TopicActivity.this, QuizLandingPage.class);
                        Layer.putExtra("worldName", ((TextView) findViewById(R.id.topicpage)).getText());
                        Layer.putExtra("worldID", worldID);
                        ImageView imgView = findViewById(R.id.trainerView);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_world, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.toleaderboard:
                Intent Layer = new Intent(TopicActivity.this, Leaderboard.class);
                startActivity(Layer);
                Toast.makeText(TopicActivity.this, "Welcome to the leaderboard!",
                        Toast.LENGTH_SHORT).show();
                return true;
            case R.id.tologout:
                mAuth.signOut();
                Layer = new Intent(TopicActivity.this, MainActivity.class);
                Toast.makeText(TopicActivity.this, "Successfully logged out.",
                        Toast.LENGTH_LONG).show();
                startActivity(Layer);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
