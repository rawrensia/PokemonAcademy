/**
 * The SummaryReportActivity display the activity_summary_report.xml
 * It allows the user to select the individual report or overall report
 *
 * @author  Maggie
 * @version 1.0
 * @since   2019-10-07
 */

package com.example.pokemonacademy.Control;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.pokemonacademy.R;

public class SummaryReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_report);

        CardView indivReport = findViewById(R.id.Indiv_Report);
        CardView allReport = findViewById(R.id.All_Report);

        indivReport.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent Layer = new Intent(SummaryReportActivity.this, IndividualReportListActivity.class);
                startActivity(Layer);
            }
        });

        allReport.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent Layer = new Intent(SummaryReportActivity.this, OverallSummaryReportActivity.class);
                startActivity(Layer);
            }
        });
    }

}
