package edu.cnm.deepdive.videopoker.controller;

import android.app.FragmentManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import edu.cnm.deepdive.videopoker.GameApplication;
import edu.cnm.deepdive.videopoker.R;
import edu.cnm.deepdive.videopoker.model.db.PaytableDatabase;

public class SplashActivity extends AppCompatActivity {

  GameSelectDialog gameSelectDialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    new Thread(() -> {
      PaytableDatabase.getInstance(this).getPaytableDao().select();
    }).start();
    setContentView(R.layout.activity_splash);
    Button playButton = findViewById(R.id.splash_play_button);
    playButton.setOnClickListener((v) -> {
      gameSelectDialog = new GameSelectDialog();
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
