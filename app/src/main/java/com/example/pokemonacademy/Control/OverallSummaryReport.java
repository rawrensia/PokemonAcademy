package com.example.pokemonacademy.Control;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import com.example.pokemonacademy.R;

public class OverallSummaryReport extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overall_summary_report);

        TextView planningAvg = (TextView) findViewById(R.id.planningAvg);
        TextView designAvg = (TextView) findViewById(R.id.designAvg);
        TextView implementationAvg = (TextView) findViewById(R.id.implementationAvg);
        TextView analysisAvg = (TextView) findViewById(R.id.analysisAvg);
        TextView maintenanceAvg = (TextView) findViewById(R.id.maintenanceAvg);
        TextView testingAvg = (TextView) findViewById(R.id.testingAvg);

        //TODO fill in scores with data from database
        //from User_Completed_Quiz, get scores from all user completed quiz and calculate average


        planningAvg.setText("Mini Quiz 1: 50 Mini Quiz 2: 30 Final Quiz : 50");
        designAvg.setText("Mini Quiz 1: 30 Mini Quiz 2: 40 Final Quiz : 70");
        implementationAvg.setText("Mini Quiz 1: 40 Mini Quiz 2: 40 Final Quiz : 10");
        analysisAvg.setText("Mini Quiz 1: 10 Mini Quiz 2: 70 Final Quiz : 30");
        maintenanceAvg.setText("Mini Quiz 1: 70 Mini Quiz 2: 40 Final Quiz : 64");
        testingAvg.setText("Mini Quiz 1: 80 Mini Quiz 2: 98 Final Quiz : 37");
    }
}
