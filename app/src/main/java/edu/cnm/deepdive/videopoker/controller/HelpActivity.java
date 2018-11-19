package edu.cnm.deepdive.videopoker.controller;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import edu.cnm.deepdive.videopoker.R;

public class HelpActivity extends AppCompatActivity {

  TextView helpText;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState,
      @Nullable PersistableBundle persistentState) {
    super.onCreate(savedInstanceState, persistentState);
    setContentView(R.layout.activity_help);
    helpText.findViewById(R.id.help_text);
    helpText.setText("Strange things are afoot at the circle K");
  }

}
