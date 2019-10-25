package com.example.pokemonacademy.Control;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.pokemonacademy.R;

public class Leaderboard extends AppCompatActivity {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        /*LinearLayout linearLayout = findViewById(R.id.leaderboardlayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();*/

        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter();
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        /*LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_fall_down);
        recyclerView.setLayoutAnimation(animationController);*/


        /*Button returnBtn = (Button)findViewById(R.id.returnBtn);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent Layer = new Intent(Leaderboard.this, WorldActivity.class);
                startActivity(Layer);
            }
        });*/
    }

    //to rerun the animation after changing the data

    private void runLayoutAnimation(final RecyclerView recyclerView){
        final Context context = recyclerView.getContext();
        final LayoutAnimationController layoutAnimationController =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);
        recyclerView.setLayoutAnimation(layoutAnimationController);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
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
                Intent Layer = new Intent(Leaderboard.this, WorldActivity.class);
                startActivity(Layer);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
