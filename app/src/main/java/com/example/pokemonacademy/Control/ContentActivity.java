package com.example.pokemonacademy.Control;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.text.Html;

import com.example.pokemonacademy.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_material);
        Intent intent = getIntent();
        String worldName = intent.getStringExtra("worldName");

        TextView tv = findViewById(R.id.content_textView);
        tv.setText(worldName);




        int worldID = intent.getIntExtra("worldID", -1);
        String content = String.valueOf(worldID);
        switch(worldID) {
            case WorldActivity.WORLD_PLANNING_ID:
                // get planning content
                content = getString(R.string.planning_content);
                break;
            case WorldActivity.WORLD_ANALYSIS_ID:
                // get analysis content
                break;
            case WorldActivity.WORLD_DESIGN_ID:
                break;
            case WorldActivity.WORLD_IMPLEMENTATION_ID:
                break;
            case WorldActivity.WORLD_TESTING_ID:
                break;
            case WorldActivity.WORLD_MAINTENANCE_ID:
                break;

            default:
                break;
        }

        TextView content_tv = findViewById(R.id.contentBlock);
        content_tv.setText(Html.fromHtml(content));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_mini_quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tominiquiz:
                Intent Layer = getIntent();
                String worldName = Layer.getStringExtra("worldName");
                int worldID = Layer.getIntExtra("worldID", -1);
                Intent intent = new Intent(getApplicationContext(), MiniQuizLandingPage.class);
                intent.putExtra("worldName", worldName);
                intent.putExtra("worldID", worldID);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
