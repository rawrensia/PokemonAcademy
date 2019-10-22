package com.example.pokemonacademy.Control;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.pokemonacademy.R;

public class SummaryReport extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        final int user_id = intent.getIntExtra("worldName", -1);
        // TODO get user from db and parse details to get user_type
        // Hardcode user to teacher
        char user_type = 'T';

        if(user_type == 'T'){
            setContentView(R.layout.activity_summary_report);
            CardView indivReport = (CardView)findViewById(R.id.Indiv_Report);
            CardView allReport = (CardView)findViewById(R.id.All_Report);

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

        else{
            Intent Layer = new Intent(SummaryReport.this, IndividualSummaryReport.class);
            Layer.putExtra("userId", user_id);
            startActivity(Layer);
        }
    }
}
