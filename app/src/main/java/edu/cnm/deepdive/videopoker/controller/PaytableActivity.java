package edu.cnm.deepdive.videopoker.controller;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import edu.cnm.deepdive.videopoker.R;

public class PaytableActivity extends AppCompatActivity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_paytable);
    this.setTitle("PAYTABLE");
    ActionBar actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(false);
  }

  @Override
  protected void onPause() {
    super.onPause();
    finish();
  }
}
