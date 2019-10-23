package com.example.pokemonacademy.Control;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.pokemonacademy.R;

public class IndividualReportList extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_report_list);

        LinearLayout studentList = findViewById(R.id.studentList);

        for (int i = 0; i<studentList.getChildCount()-1;i++){
            final CardView student = (CardView)studentList.getChildAt(i);

            student.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view){
                    Intent Layer = new Intent(IndividualReportList.this, IndividualSummaryReport.class);
                    //TODO somehow extract student's id if possible
                    Layer.putExtra("userId", 1); //Hardcoded
                    startActivity(Layer);
                }
            });
        }
    }
}