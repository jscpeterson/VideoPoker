package edu.cnm.deepdive.videopoker.controller;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import edu.cnm.deepdive.videopoker.R;
import edu.cnm.deepdive.videopoker.model.dao.PokerHandDao;
import edu.cnm.deepdive.videopoker.model.db.PaytableDatabase;
import edu.cnm.deepdive.videopoker.model.entity.Paytable;
import edu.cnm.deepdive.videopoker.model.entity.PokerHand;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class SplashActivity extends AppCompatActivity {

  private static final String PURSE_KEY = "purse";
  private static final String CREDIT_VALUE_KEY = "creditValue";
  private static final String PAYTABLE_ID_KEY = "paytableId";
  private static final String PAYTABLE_NAME_KEY = "paytableNameKey";

  private Paytable paytable;


  private Button playButton;

  public SplashActivity() throws IOException {
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    // TODO Add an option to reset paytable defaults
    super.onCreate(savedInstanceState);
    new Thread(() -> {
      PaytableDatabase.getInstance(this).getPaytableDao().select();
    }).start();


    setContentView(R.layout.activity_splash);
    playButton = findViewById(R.id.splash_play_button);
    playButton.setOnClickListener((v) -> {
      Intent intent = new Intent(this, GameActivity.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
      // TODO Get these values from an alertDialog
      intent.putExtra(PURSE_KEY, 100);
      intent.putExtra(CREDIT_VALUE_KEY, 0.50);

      // TODO Make multiple game buttons
      intent.putExtra(PAYTABLE_ID_KEY, paytable.getId());
      intent.putExtra(PAYTABLE_NAME_KEY, paytable.getName());

      startActivity(intent);
    });
  }

  public void ready() {
    new SetupTask(this).execute();
  }

  private static class SetupTask extends AsyncTask<Void, Void, Paytable> {

    private Paytable paytable;
    private Context context;

    public String getName() {
      return name;
    }

    private String name;

    private SetupTask(Context context) {
      this.context = context;
    }

    @Override
    protected Paytable doInBackground(Void... voids) {
      //do something with the database to initialize it
      paytable = PaytableDatabase.getInstance(context.getApplicationContext())
          .getPaytableDao().select(1);
      if (paytable != null) {
        return paytable;
      }
      return null;
   }

    @Override
    protected void onPostExecute(Paytable paytable) {
      if (paytable != null) {
        ((SplashActivity) context).paytable = paytable;
      }
    }
  }

}
