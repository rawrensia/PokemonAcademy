package com.example.pokemonacademy.Control;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import com.example.pokemonacademy.Entity.User;
import com.example.pokemonacademy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * The IndividualReportListActivity displays the activity_individual_report_list.xml
 * This activity displays the list of students within the course and allows the user to
 * select a student to view their individual report.
 *
 * @author  Maggie
 * @since   2019-11-01
 */
public class IndividualReportListActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private static final String TAG = "StudentList";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        mAuth = FirebaseAuth.getInstance();
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

        CardView cv = new CardView(getApplicationContext());

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                Math.round(30 * getResources().getDisplayMetrics().density));

        int margin = Math.round(7 * getResources().getDisplayMetrics().density);
        lp.setMargins(margin, margin, margin, margin);
        cv.setLayoutParams(lp);

        cv.setCardBackgroundColor(getResources().getColor(R.color.yellow));
        cv.setRadius(Math.round(8 * getResources().getDisplayMetrics().density));
        cv.setCardElevation(Math.round(6 * getResources().getDisplayMetrics().density));

        TextView tv = new TextView(getApplicationContext());
        CardView.LayoutParams tvParams = new CardView.LayoutParams(
                CardView.LayoutParams.WRAP_CONTENT,
                CardView.LayoutParams.MATCH_PARENT);
        tvParams.setMargins(Math.round(8 * getResources().getDisplayMetrics().density), 0, 0, 0);
        tv.setLayoutParams(tvParams);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(getResources().getColor(R.color.darkBlue));
        tv.setTypeface(ResourcesCompat.getFont(IndividualReportListActivity.this, R.font.quenda));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_logout_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent Layer;
        switch (item.getItemId()) {
            case R.id.tomenulp:
                Layer = new Intent(IndividualReportListActivity.this, TeacherMenuLandingPage.class);
                startActivity(Layer);
                return true;
            case R.id.tologout:
                mAuth.signOut();
                Layer = new Intent(IndividualReportListActivity.this, MainActivity.class);
                Toast.makeText(IndividualReportListActivity.this, "Successfully logged out.",
                        Toast.LENGTH_LONG).show();
                startActivity(Layer);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}