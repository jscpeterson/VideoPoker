package edu.cnm.deepdive.videopoker.controller;

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

  private static final int INDEX_GAME_NAME = 0;
  private static final int INDEX_HAND_NAME = 1;
  private static final int INDEX_RULE_SEQUENCE = 2;
  private static final int INDEX_BET_ONE_VALUE = 3;
  private static final int INDEX_OVERLOADED_PARAM = 4;


  private Button playButton;

  public SplashActivity() throws IOException {
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //TODO allow options for additional games with separate paytables
    //TODO create dialog to putExtra for purse and credit value

    setContentView(R.layout.activity_splash);
    playButton = findViewById(R.id.splash_play_button);
    playButton.setOnClickListener((v) -> {
      Intent intent = new Intent(this, GameActivity.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
      new SetupTask().execute();

      // TODO Get these values from an alertDialog
      intent.putExtra(PURSE_KEY, 100);
      intent.putExtra(CREDIT_VALUE_KEY, 0.50);

      startActivity(intent);
    });
  }


  private class SetupTask extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... voids) {
      try {
        //TODO Only build paytable once if not already built? Move this to callback?
        PaytableDatabase db = PaytableDatabase.getInstance(getApplicationContext());
        PokerHandDao pokerHandDao = db.getPokerHandDao();

        InputStream paytablesInputStream = getResources().openRawResource(R.raw.paytables);
        InputStream gamesInputStream = getResources().openRawResource(R.raw.games);
        CSVParser paytablesCsvParser =
            new CSVParser(new InputStreamReader(paytablesInputStream), CSVFormat.DEFAULT);
        CSVParser gamesCsvParser =
            new CSVParser(new InputStreamReader(gamesInputStream), CSVFormat.DEFAULT);

        for (CSVRecord gameRecord : gamesCsvParser.getRecords()) {
          Paytable paytable = new Paytable();
          paytable.setName(gameRecord.get(INDEX_GAME_NAME));
          db.getPaytableDao().insert(paytable);
          System.out.println("");
          System.out.println(paytable.getId());
          System.out.println(paytable.getName());
        }

        for (CSVRecord paytableRecord : paytablesCsvParser.getRecords()) {
          PokerHand newHand = new PokerHand();
          Paytable paytable = db.getPaytableDao().select(paytableRecord.get(INDEX_GAME_NAME));
          newHand.setPaytableId(paytable.getId());

          newHand.setName(paytableRecord.get(INDEX_HAND_NAME));
          newHand.setRuleSequence(paytableRecord.get(INDEX_RULE_SEQUENCE));
          newHand.setBetOneValue(Integer.parseInt(paytableRecord.get(INDEX_BET_ONE_VALUE)));

          //TODO handle overloaded params
          newHand.setBetFiveValue(newHand.getBetOneValue() * 5);
          newHand.setShowInTable(newHand.getBetOneValue() > 0);

          pokerHandDao.insert(newHand);
        }

      } catch (IOException e) {
        //TODO handle or don't
      }
      return null;
    }

  }

}
