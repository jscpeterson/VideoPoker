package edu.cnm.deepdive.videopoker.controller;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import edu.cnm.deepdive.videopoker.GameApplication;
import edu.cnm.deepdive.videopoker.R;
import edu.cnm.deepdive.videopoker.model.db.PaytableDatabase;
import java.io.IOException;


public class SplashActivity extends AppCompatActivity {

  //CONSTANTS
  private static final String PURSE_KEY = "purse";
  private static final String CREDIT_VALUE_KEY = "creditValue";
  private static final String PAYTABLE_ID_KEY = "paytableId";
  private static final String PAYTABLE_NAME_KEY = "paytableNameKey";

  private int gameId = 1;
  private GameApplication instance = GameApplication.getInstance();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    new Thread(() -> {
      PaytableDatabase.getInstance(this).getPaytableDao().select();
    }).start();
    setContentView(R.layout.activity_splash);
    Button playButton = findViewById(R.id.splash_play_button);
    playButton.setOnClickListener((v) -> {
      Intent intent = new Intent(this, GameActivity.class);
      // TODO Get these values from an alertDialog
      intent.putExtra(PURSE_KEY, 100);
      intent.putExtra(CREDIT_VALUE_KEY, 0.50);
      // TODO Make multiple game buttons
      intent.putExtra(PAYTABLE_ID_KEY, instance.getLocalDb().get(gameId).getId());
      intent.putExtra(PAYTABLE_NAME_KEY, instance.getLocalDb().get(gameId).getName());
      startActivity(intent);
    });
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
