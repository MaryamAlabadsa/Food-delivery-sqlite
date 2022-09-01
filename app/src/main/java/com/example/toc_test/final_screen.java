package com.example.toc_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class final_screen extends AppCompatActivity {
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_screen);
        ImageView img = findViewById(R.id.logo);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                run_activity();
            }
        };
        handler.postDelayed(runnable, (long) (3 * 1000));
    }
  //  final MediaPlayer mediaPlayer= android.media.MediaPlayer.create(this,R.raw.sound);

    public void run_activity() {
        Intent intent = new Intent(this, view_item_main.class);
        startActivity(intent);
        finish();
     //   mediaPlayer.start();
    }
}