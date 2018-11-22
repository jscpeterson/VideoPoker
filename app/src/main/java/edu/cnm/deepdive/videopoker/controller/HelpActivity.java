package edu.cnm.deepdive.videopoker.controller;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import edu.cnm.deepdive.videopoker.R;

public class HelpActivity extends AppCompatActivity {

  TextView helpText;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_help);
    helpText = findViewById(R.id.help_text);
    helpText.setText("Help text here....");
    this.setTitle("How to play Video Poker");
  }

  @Override
  protected void onPause() {
    super.onPause();
    finish();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      onBackPressed();
      return true;
    }
    return(super.onOptionsItemSelected(item));
  }
}
