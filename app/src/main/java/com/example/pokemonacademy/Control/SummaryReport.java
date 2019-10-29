package com.example.pokemonacademy.Control;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.pokemonacademy.R;

public class SummaryReport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_report);

        CardView indivReport = findViewById(R.id.Indiv_Report);
        CardView allReport = findViewById(R.id.All_Report);

        indivReport.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent Layer = new Intent(SummaryReport.this, IndividualReportList.class);
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

}
