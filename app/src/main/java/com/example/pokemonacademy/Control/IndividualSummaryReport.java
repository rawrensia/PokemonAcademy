package com.example.pokemonacademy.Control;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import com.example.pokemonacademy.R;

import org.w3c.dom.Text;

public class IndividualSummaryReport extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_summary_report);

        LinearLayout linearLayout = findViewById(R.id.indiv_summary);
        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        // Get intent
        Intent intent = getIntent();
        int userId = intent.getIntExtra("worldName", -1);
        //TODO get user stuff from db

        ImageView usericon = (ImageView) findViewById(R.id.usericon);
        TextView report = (TextView) findViewById(R.id.individualReport);
        TextView planning_mq1 = (TextView) findViewById(R.id.planning_mq1);
        TextView planning_mq2 = (TextView) findViewById(R.id.planning_mq2);
        TextView planning_fq = (TextView) findViewById(R.id.planning_fq);
        TextView design_mq1 = (TextView) findViewById(R.id.design_mq1);
        TextView design_mq2 = (TextView) findViewById(R.id.design_mq2);
        TextView design_fq = (TextView) findViewById(R.id.design_fq);
        TextView implementation_mq1 = (TextView) findViewById(R.id.implementation_mq1);
        TextView implementation_mq2 = (TextView) findViewById(R.id.implementation_mq2);
        TextView implementation_fq = (TextView) findViewById(R.id.implementation_fq);
        TextView analysis_mq1 = (TextView) findViewById(R.id.analysis_mq1);
        TextView analysis_mq2 = (TextView) findViewById(R.id.analysis_mq2);
        TextView analysis_fq = (TextView) findViewById(R.id.analysis_fq);
        TextView maintenance_mq1 = (TextView) findViewById(R.id.maintenance_mq1);
        TextView maintenance_mq2 = (TextView) findViewById(R.id.maintenance_mq2);
        TextView maintenance_fq = (TextView) findViewById(R.id.maintenance_fq);
        TextView testing_mq1 = (TextView) findViewById(R.id.testing_mq1);
        TextView testing_mq2 = (TextView) findViewById(R.id.testing_mq2);
        TextView testing_fq = (TextView) findViewById(R.id.testing_fq);

        //TODO get user's character image
        //usericon.setImageResource();

        //TODO fill in scores with data from databse
        //from User_Completed_Quiz, if completed get score for each quiz for user, else print not completed

        report.setText("Boham's Report");
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
                Intent Layer = new Intent(IndividualSummaryReport.this, WorldActivity.class);
                startActivity(Layer);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
