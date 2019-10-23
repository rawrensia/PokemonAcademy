package com.example.pokemonacademy.Control;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pokemonacademy.R;
import com.google.firebase.auth.FirebaseAuth;

public class WorldActivity extends AppCompatActivity {
    public final static int WORLD_PLANNING_ID = 0;
    public final static int WORLD_ANALYSIS_ID = 1;
    public final static int WORLD_DESIGN_ID = 2;
    public final static int WORLD_IMPLEMENTATION_ID = 3;
    public final static int WORLD_TESTING_ID = 4;
    public final static int WORLD_MAINTENANCE_ID = 5;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world_selection);

        mAuth = FirebaseAuth.getInstance();

        GridLayout mainGrid = findViewById(R.id.worldGrid);
        //Set Event
        setSingleEvent(mainGrid);

        Button summaryReportBtn = (Button)findViewById(R.id.summaryReportBtn);
        summaryReportBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent Layer = new Intent(WorldActivity.this, SummaryReport.class);
                startActivity(Layer);
            }
        });

        Button leaderboardBtn = (Button)findViewById(R.id.leaderboardBtn);
        leaderboardBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent Layer = new Intent(WorldActivity.this, Leaderboard.class);
                startActivity(Layer);
            }
        });
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_world_logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tologout:
                mAuth.signOut();
                Intent Layer = new Intent(WorldActivity.this, MainActivity.class);
                Toast.makeText(WorldActivity.this, "Successfully logged out.",
                        Toast.LENGTH_SHORT).show();
                startActivity(Layer);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
