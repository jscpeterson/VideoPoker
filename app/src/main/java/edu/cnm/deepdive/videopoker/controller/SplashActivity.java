package edu.cnm.deepdive.videopoker.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import edu.cnm.deepdive.videopoker.GameApplication;
import edu.cnm.deepdive.videopoker.R;
import edu.cnm.deepdive.videopoker.model.db.PaytableDatabase;

public class SplashActivity extends AppCompatActivity {

  //CONSTANTS
  private static final String PURSE_KEY = "purse";
  private static final String CREDIT_VALUE_KEY = "creditValue";

  //FIELDS
  private GameSelectDialog gameSelectDialog;
  private EditText purseEdit;
  private EditText creditValueEdit;

  @SuppressLint("SetTextI18n")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    new Thread(() -> PaytableDatabase.getInstance(this).getPaytableDao().select()).start();
    setContentView(R.layout.activity_splash);
    Button playButton = findViewById(R.id.splash_play_button);
    purseEdit = findViewById(R.id.splash_purse_edit);
    creditValueEdit = findViewById(R.id.splash_credit_edit);
    purseEdit.setText(Integer.toString(50));
    creditValueEdit.setText(
        Double.toString(0.25)); //TODO change decimal formatting (right now you can do $5.325)
    playButton.setOnClickListener((v) -> {
      gameSelectDialog = new GameSelectDialog();
      Bundle arguments = new Bundle();
      int purseInput = Integer.parseInt(purseEdit.getText().toString());
      double creditValueInput = Double.parseDouble(creditValueEdit.getText().toString());
      arguments.putInt(PURSE_KEY, purseInput);
      arguments.putDouble(CREDIT_VALUE_KEY, creditValueInput);
      gameSelectDialog.setArguments(arguments);
      gameSelectDialog.show(getSupportFragmentManager(), "dialog");
    });
  }

  @Override
  protected void onPause() {
    super.onPause();
    if (gameSelectDialog != null) {
      gameSelectDialog.dismiss();
    }
  }

  public void ready() {
    new SetupTask(this).execute();
  }

  private static class SetupTask extends AsyncTask<Void, Void, Void> {

    GameApplication instance = GameApplication.getInstance();
    private Context context;

    private SetupTask(Context context) {
      this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
      //do something with the database to initialize it
      instance.setLocalDb(PaytableDatabase.getInstance(context.getApplicationContext())
          .getPaytableDao().select());
      return null;
    }
  }
}
