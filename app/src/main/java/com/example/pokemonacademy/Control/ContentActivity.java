package com.example.pokemonacademy.Control;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.text.Html;

import com.example.pokemonacademy.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_material);
        Intent intent = getIntent();
        String worldName = intent.getStringExtra("worldName");

        TextView tv = findViewById(R.id.content_textView);
        tv.setText(worldName);

        String planning = "<h5> <font color=\"#784C27\">Requirements:</h5>"
                + "<p>They are specifications of what should be implemented, " +
                "descriptions of how a system should behave, or of a system property/attribute.</p>"
                + "<h5> <font color=\"#784C27\"> Requirement Engineering</h5><p>It is about identifying " +
                "the purpose of a software system and the contexts in which it will be used. " +
                "It refers to the activity of requirement development, which includes elicitation, " +
                "specification, analysis and management of stakeholder requirements.</p>"
                + "<br>There are two Requirement Elicitation techniques:<br></br></br>"
                + "<br><b>Technique 1:</b><br></br></br>"
                + "<br>&#8226; Examine Documents: reuse existing or related documents, read standards or government regulations.\n</br>"
                + "<br>&#8226; Interview stakeholders: conduct focus groups with customers to gather idea and needs.\n</br>"
                + "<br>&#8226; Use questionnaires or marketing surveys for quantitative data.<br></br></br>"
                + "<br><b>Technique 2:</b><br></br></br>"
                + "<br>&#8226; Watch users do their job.</br>"
                + "<br>&#8226; Prototyping: create interface to help customers better understand the system.\n</br>"
                + "<br>&#8226; Task analysis: make use cases/ scenarios.<br></br></br>"
                + "<h5> <font color=\"#784C27\"> Requirement Analysis</h5>"
                + "<p>Decompose high-level requirements into details and identify priorities."
                + "<br><b>Requirement Speicification Approaches:</b><br></br></br>"
                + "<br>&#8226; <b>Textual:</b> vision and scope document, use case document, SRS.</br>"
                + "<br>&#8226; <b>Graphical:</b> structured analysis models (data flow diagram, entity-relationship diagram, dialog map), OO analysis models.</br>"
                + "<br>&#8226; <b>Formal:</b> formal specification languages.</br>"
                + "<p><b>SRS</b> is a set of precisely-stated properties and constraints " +
                "that a software system must satisfy, and a complete description of the external " +
                "behaviour of a system. The objectives of SRS are to achieve agreement regarding the " +
                "requirements among developers, customers and other stakeholders, as well as provide basis for design and system testing.</p>";


        int worldID = intent.getIntExtra("worldID", -1);
        String content = String.valueOf(worldID);
        switch(worldID) {
            case WorldActivity.WORLD_PLANNING_ID:
                // get planning content
                content = getString(R.string.planning_content);
                break;
            case WorldActivity.WORLD_ANALYSIS_ID:
                // get analysis content
                break;
            case WorldActivity.WORLD_DESIGN_ID:
                break;
            case WorldActivity.WORLD_IMPLEMENTATION_ID:
                break;
            case WorldActivity.WORLD_TESTING_ID:
                break;
            case WorldActivity.WORLD_MAINTENANCE_ID:
                break;

            default:
                break;
        }

        TextView content_tv = findViewById(R.id.contentBlock);
        content_tv.setText(Html.fromHtml(planning));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_mini_quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tominiquiz:
                Intent intent = new Intent(getApplicationContext(), MiniQuiz.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
