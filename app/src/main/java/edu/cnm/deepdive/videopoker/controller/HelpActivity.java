package edu.cnm.deepdive.videopoker.controller;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import edu.cnm.deepdive.videopoker.R;

public class HelpActivity extends AppCompatActivity {

  TextView helpText;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState,
      @Nullable PersistableBundle persistentState) {
    super.onCreate(savedInstanceState, persistentState);
    helpText.findViewById(R.id.help_text);
    this.setTitle("How to play Video Poker");
    helpText.setText("Strange things are afoot at the circle K");
    setContentView(R.layout.activity_help);
    ActionBar actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(false);
  }

}
