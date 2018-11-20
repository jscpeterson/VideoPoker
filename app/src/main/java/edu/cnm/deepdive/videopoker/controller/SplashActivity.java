package edu.cnm.deepdive.videopoker.controller;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import edu.cnm.deepdive.videopoker.R;
import edu.cnm.deepdive.videopoker.model.dao.PokerHandDao;
import edu.cnm.deepdive.videopoker.model.db.PaytableDatabase;
import edu.cnm.deepdive.videopoker.model.entity.Paytable;
import edu.cnm.deepdive.videopoker.model.entity.PokerHand;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class SplashActivity extends AppCompatActivity {

  private static final String PURSE_KEY = "purse";
  private static final String CREDIT_VALUE_KEY = "creditValue";
  private static final String PAYTABLE_ID_KEY = "paytableId";
  private static final String PAYTABLE_NAME_KEY = "paytableNameKey";

  private List<Paytable> db;
  private Button playButton;
  private ProgressBar progressSpinner;
  private TextView splashTitle;
  private ImageView splashImage;

  public SplashActivity() throws IOException {
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    // TODO Add an option to reset paytable defaults
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);
    setupLayout();
    new Thread(() -> {
      PaytableDatabase.getInstance(this).getPaytableDao().select();
    }).start();
  }

  public void ready() {
    new SetupTask(this).execute();
  }

  private void setupLayout() {
    progressSpinner = findViewById(R.id.progress_spinner);
    splashImage = findViewById(R.id.splash_image);
    splashTitle = findViewById(R.id.splash_title);
    playButton = findViewById(R.id.splash_play_button);
  }

  private static class SetupTask extends AsyncTask<Void, Void, Void> {

    private List<Paytable> db;
    private Context context;

    @Override
    protected void onPreExecute() {
      ((SplashActivity) context).progressSpinner.setVisibility(View.VISIBLE);
    }

    private SetupTask(Context context) {
      this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
      //do something with the database to initialize it
      db = PaytableDatabase.getInstance(context.getApplicationContext())
          .getPaytableDao().select();
      if (db != null) {
        for (Paytable item : db) {
          System.out.println(item);
        }
      }
      return null;
   }

    @Override
    protected void onPostExecute(Void aVoid) {
      super.onPostExecute(aVoid);
        SplashActivity activityContext = (SplashActivity) context;
        if (db != null) {
          activityContext.db = db;
        }
        activityContext.progressSpinner.setVisibility(View.INVISIBLE);
        activityContext.splashTitle.setVisibility(View.VISIBLE);
        activityContext.splashImage.setVisibility(View.VISIBLE);
        activityContext.playButton.setVisibility(View.VISIBLE);
        activityContext.playButton.setOnClickListener((v) -> {
        Intent intent = new Intent(activityContext, GameActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // TODO Get these values from an alertDialog
        intent.putExtra(PURSE_KEY, 100);
        intent.putExtra(CREDIT_VALUE_KEY, 0.50);
        // TODO Make multiple game buttons
        intent.putExtra(PAYTABLE_ID_KEY, db.get(4).getId());
        intent.putExtra(PAYTABLE_NAME_KEY, db.get(4).getId());
        activityContext.startActivity(intent);
      });
    }
  }
}
