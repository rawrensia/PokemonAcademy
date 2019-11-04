package com.example.pokemonacademy.Control;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pokemonacademy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
/**
 * The CustomQuizInfoActivity displays the activity_custom_quiz_info.xml
 * It allows the user to view the past 5 custom quizzes he/she has created
 * The user is able to copy the custom quizzes id he has created or share
 * it on social media platform Twitter.
 *
 * @author  Benjamin Lim
 * @since   2019-11-01
 */
public class CustomQuizInfoActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference questiondDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_quiz_info);

        mAuth = FirebaseAuth.getInstance();
        questiondDB = FirebaseDatabase.getInstance().getReference("QUESTION");

        getRecentCustomQuizCode();
        setSingleEvent();
    }

    private void getRecentCustomQuizCode()  {
        Query reference = questiondDB.child(mAuth.getCurrentUser().getUid()).limitToFirst(5);
        final ArrayList<String> randomCodes = new ArrayList<>();

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                randomCodes.add(0, dataSnapshot.getKey());
                updateUI(randomCodes);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                randomCodes.remove(dataSnapshot.getKey());
                updateUI(randomCodes);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updateUI(ArrayList<String> randomCodes)    {
        LinearLayout LL = findViewById(R.id.customQuizGrid);
        ConstraintLayout cL = (ConstraintLayout)LL.getChildAt(0);

        for(int i=1; i<cL.getChildCount()-1; i++) {
            ConstraintLayout innerCL = (ConstraintLayout)((LinearLayout)cL.getChildAt(i)).getChildAt(0);

            TextView tv = (TextView) innerCL.getChildAt(0);
            Button shareBtn = (Button) innerCL.getChildAt(1);
            try {
                tv.setText(randomCodes.get(i - 1));
                shareBtn.setContentDescription(randomCodes.get(i - 1));
            } catch (Exception e)   {
                tv.setText("");
                shareBtn.setContentDescription("");
            }
        }
    }

    private void setSingleEvent()   {
        LinearLayout LL = findViewById(R.id.customQuizGrid);
        ConstraintLayout cL = (ConstraintLayout)LL.getChildAt(0);

        Button customQuizBackBtn = findViewById(R.id.customQuizInfoBackBtn);

        for(int i=1; i<cL.getChildCount()-1; i++) {
            ConstraintLayout innerCL = (ConstraintLayout)((LinearLayout)cL.getChildAt(i)).getChildAt(0);

            final TextView tv = (TextView)innerCL.getChildAt(0);
            Button copyBtn = (Button)innerCL.getChildAt(2);
            Button shareBtn = (Button)innerCL.getChildAt(1);

            // DO SOMETHING WITH BUTTONS
            copyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    copyToClipboard(tv.getText().toString());
                    Toast.makeText(CustomQuizInfoActivity.this, tv.getText().toString() + " copied to clipboard.",
                            Toast.LENGTH_LONG).show();
                }
            });

            shareBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String tweetText = "I have created a Custom Quiz on Pokemon Academy! Come and try it using this code: " + tv.getText().toString();
                    String tweetUrl = "https://twitter.com/intent/tweet?text=" + tweetText;
                    Uri uri = Uri.parse(tweetUrl);
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }
            });
        }

        customQuizBackBtn
            .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent Layer = new Intent(CustomQuizInfoActivity.this, CustomQuizActivity.class);
                    startActivity(Layer);
                    finish();
                }
            });
    }

    private void copyToClipboard(String text)  {
        String label = "Custom Quiz Code";
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(label, text);
        clipboard.setPrimaryClip(clip);
    }
}
