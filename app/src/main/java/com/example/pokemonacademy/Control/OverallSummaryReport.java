package com.example.pokemonacademy.Control;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

        TextView planningAvg_mq1 = (TextView) findViewById(R.id.planning_avg_mq1);
        TextView planningAvg_mq2 = (TextView) findViewById(R.id.planning_avg_mq2);
        TextView planningAvg_fq = (TextView) findViewById(R.id.planning_avg_fq);
        TextView designAvg_mq1 = (TextView) findViewById(R.id.design_avg_mq1);
        TextView designAvg_mq2 = (TextView) findViewById(R.id.design_avg_mq2);
        TextView designAvg_fq = (TextView) findViewById(R.id.design_avg_fq);
        TextView implementationAvg_mq1 = (TextView) findViewById(R.id.implementation_avg_mq1);
        TextView implementationAvg_mq2 = (TextView) findViewById(R.id.implementation_avg_mq2);
        TextView implementationAvg_fq = (TextView) findViewById(R.id.implementation_avg_fq);
        TextView analysisAvg_mq1 = (TextView) findViewById(R.id.analysis_avg_mq1);
        TextView analysisAvg_mq2 = (TextView) findViewById(R.id.analysis_avg_mq2);
        TextView analysisAvg_fq = (TextView) findViewById(R.id.analysis_avg_fq);
        TextView maintenanceAvg_mq1 = (TextView) findViewById(R.id.maintenance_avg_mq1);
        TextView maintenanceAvg_mq2 = (TextView) findViewById(R.id.maintenance_avg_mq2);
        TextView maintenanceAvg_fq = (TextView) findViewById(R.id.maintenance_avg_fq);
        TextView testingAvg_mq1 = (TextView) findViewById(R.id.testing_avg_mq1);
        TextView testingAvg_mq2 = (TextView) findViewById(R.id.testing_avg_mq2);
        TextView testingAvg_fq = (TextView) findViewById(R.id.testing_avg_fq);

        //TODO fill in scores with data from database
        //from User_Completed_Quiz, get scores from all user completed quiz and calculate average


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_back_world, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toworld:
                Intent Layer = new Intent(OverallSummaryReport.this, WorldActivity.class);
                startActivity(Layer);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
