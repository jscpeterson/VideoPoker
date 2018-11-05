package edu.cnm.deepdive.videopoker.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import edu.cnm.deepdive.videopoker.R;
import edu.cnm.deepdive.videopoker.model.Game;
import edu.cnm.deepdive.videopoker.model.db.Paytable;

public class SplashActivity extends AppCompatActivity {

  Button playButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Paytable table1 = Paytable.getInstance(SplashActivity.this);


    setContentView(R.layout.activity_splash);
    playButton = findViewById(R.id.splash_play_button);
    playButton.setOnClickListener((v) -> {
      Intent intent = new Intent(SplashActivity.this, GameActivity.class);
      startActivity(intent);
    });
  }
}
