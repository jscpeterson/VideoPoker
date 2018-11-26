package edu.cnm.deepdive.videopoker.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import edu.cnm.deepdive.videopoker.R;

/**
 * This class simply displays information about how to play the game to the user. There is very
 * little interactivity, once the user is done reading they can press back on their menu bar or on
 * their device to return tot he game.
 */
public class HelpActivity extends AppCompatActivity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_help);
    this.setTitle(getString(R.string.help_title));
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
