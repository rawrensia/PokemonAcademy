package com.example.pokemonacademy.Control;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import com.example.pokemonacademy.R;

import org.w3c.dom.Text;

public class IndividualSummaryReport extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_report);

        // Get intent
        Intent intent = getIntent();
        int userId = intent.getIntExtra("worldName", -1);
        //TODO get user stuff from db

        ImageView usericon = (ImageView) findViewById(R.id.usericon);
        TextView report = (TextView) findViewById(R.id.individualReport);
        TextView planningScore = (TextView) findViewById(R.id.planningScore);
        TextView designScore = (TextView) findViewById(R.id.designScore);
        TextView implementationScore = (TextView) findViewById(R.id.implementationScore);
        TextView analysisScore = (TextView) findViewById(R.id.analysisScore);
        TextView maintenanceScore = (TextView) findViewById(R.id.maintenanceScore);
        TextView testingScore = (TextView) findViewById(R.id.testingScore);

        //TODO get user's character image
        //usericon.setImageResource();

        //TODO fill in scores with data from databse
        //from User_Completed_Quiz, if completed get score for each quiz for user, else print not completed

        report.setText("Boham's Report");
        planningScore.setText("Mini Quiz 1: 50 Mini Quiz 2: 30 Final Quiz : 50");
        designScore.setText("Mini Quiz 1: 30 Mini Quiz 2: 40 Final Quiz : 70");
        implementationScore.setText("Mini Quiz 1: 40 Mini Quiz 2: 40 Final Quiz : 10");
        analysisScore.setText("Mini Quiz 1: 10 Mini Quiz 2: 70 Final Quiz : 30");
        maintenanceScore.setText("Mini Quiz 1: 70 Mini Quiz 2: 40 Final Quiz : 64");
        testingScore.setText("Mini Quiz 1: 80 Mini Quiz 2: 98 Final Quiz : 37");
    }
}
