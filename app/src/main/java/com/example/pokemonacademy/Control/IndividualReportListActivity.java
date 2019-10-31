package com.example.pokemonacademy.Control;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.pokemonacademy.Entity.User;
import com.example.pokemonacademy.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class IndividualReportListActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;

    private static final String TAG = "StudentList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_report_list);

        mDatabase = FirebaseDatabase.getInstance().getReference("USER");

        generateStudentList();
    }

    private void generateStudentList(){
        DatabaseReference reference = mDatabase;

        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    if(user.getUserType().equalsIgnoreCase("S")){
                        addStudent(user.getId(), user.getName());
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        reference.addListenerForSingleValueEvent(userListener);
    }

    private void addStudent(final String sId, final String name){
        LinearLayout studentList = findViewById(R.id.studentList);

        CardView cv = new CardView(IndividualReportListActivity.this);
        cv.setCardBackgroundColor(Color.LTGRAY);

        LinearLayout.LayoutParams cardViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        cv.setLayoutParams(cardViewParams);

        ViewGroup.MarginLayoutParams cardViewMarginParams = (ViewGroup.MarginLayoutParams) cv.getLayoutParams();
        cardViewMarginParams.setMargins(20, 10, 20, 10);
        cv.requestLayout();

        TextView tv = new TextView(IndividualReportListActivity.this);
        tv.setPadding(8,2,8,2);
        tv.setText(name);

        cv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent Layer = new Intent(IndividualReportListActivity.this, IndividualSummaryReportActivity.class);
                Layer.putExtra("userId", sId);
                startActivity(Layer);
            }
        });

        cv.addView(tv);
        studentList.addView(cv);
    }
}