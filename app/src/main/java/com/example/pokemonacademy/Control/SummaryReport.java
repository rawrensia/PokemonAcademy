package com.example.pokemonacademy.Control;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.pokemonacademy.R;

public class SummaryReport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_report);

        LinearLayout linearLayout = findViewById(R.id.summary_layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        Intent intent = getIntent();
        final int user_id = intent.getIntExtra("userId", -1);
        Log.i("HERE","user_id "+user_id);
        // TODO get user from db and parse details to get user_type
        // Hardcode user to teacher
        char user_type = 'T';

        if(user_type == 'T'){
            CardView indivReport = (CardView)findViewById(R.id.Indiv_Report);
            CardView allReport = (CardView)findViewById(R.id.All_Report);

            indivReport.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view){
                    Intent Layer = new Intent(SummaryReport.this, IndividualSummaryReport.class);
                    startActivity(Layer);
                }
            });

            allReport.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view){
                    Intent Layer = new Intent(SummaryReport.this, OverallSummaryReport.class);
                    startActivity(Layer);
                }
            });

        }

        else{
            Intent Layer = new Intent(SummaryReport.this, IndividualSummaryReport.class);
            Layer.putExtra("userId", user_id);
            startActivity(Layer);
        }
    }
}
