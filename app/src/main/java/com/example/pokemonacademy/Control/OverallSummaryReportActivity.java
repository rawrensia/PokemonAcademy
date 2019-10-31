package com.example.pokemonacademy.Control;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.pokemonacademy.Entity.QuizzesCompleted;
import com.example.pokemonacademy.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

public class OverallSummaryReportActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overall_summary_report);

        mDatabase = FirebaseDatabase.getInstance().getReference("QUIZZES_COMPLETED");

        GridLayout mainGrid = findViewById(R.id.overallGrid);
        setSingleEvent(mainGrid);
    }

    private void setSingleEvent(GridLayout mainGrid){
        //Loop all child item of Main Grid
        for (int i = 0; i < mainGrid.getChildCount();i++)
        {
            final CardView cardView = (CardView)mainGrid.getChildAt(i);
            final int counter = i;

            loadQuizScores(counter, cardView);

            cardView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view){
                    Intent Layer = new Intent(OverallSummaryReportActivity.this, FinalQuizPerformanceReportActivity.class);
                    TextView tv = (TextView)((LinearLayout)cardView.getChildAt(0)).getChildAt(0);
                    Layer.putExtra("worldName", tv.getText());
                    Layer.putExtra("worldID", counter);
                    startActivity(Layer);
                }
            });
        }
    }

    private void loadQuizScores(final int worldId, final CardView cardView) {
        DatabaseReference reference = mDatabase;

        final DecimalFormat df2 = new DecimalFormat("#.##");

        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int mq1_sum = 0;
                int mq2_sum = 0;
                int fq_sum = 0;
                int mq1_count = 0;
                int mq2_count = 0;
                int fq_count = 0;

                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    DataSnapshot ds = snapshot.child("World" + worldId);

                    for (DataSnapshot quizzes : ds.getChildren()) {
                        QuizzesCompleted quizzesCompleted = quizzes.getValue(QuizzesCompleted.class);
                        if (quizzesCompleted.getWorldId() == worldId) {
                            if (quizzesCompleted.getCompleted()) {
                                int score = quizzesCompleted.getScore();
                                switch (quizzesCompleted.getMiniQuizId()) {
                                    case 0:
                                        mq1_sum += score;
                                        mq1_count++;
                                        break;
                                    case 1:
                                        mq2_sum += score;
                                        mq2_count++;
                                        break;
                                    case 2:
                                        fq_sum += score;
                                        fq_count++;
                                        break;
                                }
                            }
                        }
                    }
                }

                double mq1_avg = ((double)mq1_sum) / mq1_count;
                double mq2_avg = ((double)mq2_sum) / mq2_count;
                double fq_avg = ((double)fq_sum) / fq_count;

                TextView tv = (TextView)((LinearLayout)cardView.getChildAt(0)).getChildAt(1);
                tv.setText("Mini Quiz 1: " + df2.format(mq1_avg) + " Mini Quiz 2: " + df2.format(mq2_avg) + " Final Quiz : " + df2.format(fq_avg));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("OverallSummary", "loadPost:onCancelled", databaseError.toException());
            }
        };
        reference.addListenerForSingleValueEvent(userListener);
    }
}
