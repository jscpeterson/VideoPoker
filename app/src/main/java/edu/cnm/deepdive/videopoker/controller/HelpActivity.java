package edu.cnm.deepdive.videopoker.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import edu.cnm.deepdive.videopoker.R;

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
