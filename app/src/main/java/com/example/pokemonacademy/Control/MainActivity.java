package com.example.pokemonacademy.Control;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pokemonacademy.R;

public class MainActivity extends AppCompatActivity {
    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = (Button)findViewById(R.id.button_start);

        btnStart.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View view) {
                Intent Layer = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(Layer);
            }
        });

    }

}
