package edu.cnm.deepdive.videopoker.controller;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import edu.cnm.deepdive.videopoker.R;
import edu.cnm.deepdive.videopoker.model.dao.PokerHandDao;
import edu.cnm.deepdive.videopoker.model.db.Paytable;
import edu.cnm.deepdive.videopoker.model.entity.PokerHand;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class SplashActivity extends AppCompatActivity {

  private static final String PURSE_KEY = "purse";
  private static final String CREDIT_VALUE_KEY = "creditValue";
  private static final String GAME_NAME_KEY = "gameName";
  private Button playButton;

  public SplashActivity() throws IOException {
  }

  @Override
  protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    //TODO allow options for additional games with separate paytables
    //TODO create dialog to putExtra for purse and credit value

    setContentView(R.layout.activity_splash);
    playButton = findViewById(R.id.splash_play_button);
    playButton.setOnClickListener((v) -> {
      Intent intent = new Intent(this, GameActivity.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
      // TODO Get this name from a csv file header
      String gameName = "Jacks or Better";
      intent.putExtra(GAME_NAME_KEY, gameName);
      new SetupTask().execute(R.raw.jacksorbetter);

      // TODO Get these values from an alertDialog
      intent.putExtra(PURSE_KEY, 100);
      intent.putExtra(CREDIT_VALUE_KEY, 0.50);

      startActivity(intent);
    });
  }


  private class SetupTask extends AsyncTask<Integer, Void, Void> {

    @Override
    protected Void doInBackground(Integer... gameCsvFileIds) {
      try {
        //TODO Only build paytable once if not already built? Move this to callback?
        //TODO Get gameName from a csv file header
        Paytable db = Paytable.getInstance(SplashActivity.this, "Jacks or Better");
        PokerHandDao dao = db.getPokerHandDao();
        InputStream csvInputStream = getResources().openRawResource(gameCsvFileIds[0]);
        CSVParser csvParser = null;
        csvParser = new CSVParser(new InputStreamReader(csvInputStream), CSVFormat.DEFAULT);
        for (CSVRecord record : csvParser.getRecords()) {
          //check record size if constructors need to be overloaded
          //no flexibility for both a max bet value and a showInTable change but none needed for now
          if (record.size() > 3) {
            //constructor overloaded with "false" boolean value for showInTable
            //no flexibility for a "true" but none needed for now
            if (record.get(3).equals("false")) {
              dao.insert(new PokerHand(record.getRecordNumber(),
                  record.get(0), record.get(1), Integer.parseInt(record.get(2)), false));
            }
            else {
              //constructor overloaded with integer value for max bet amount
              dao.insert(new PokerHand(record.getRecordNumber(),
                  record.get(0), record.get(1), Integer.parseInt(record.get(2)),
                  Integer.parseInt(record.get(3))));
            }
          }
          else {
            //constructor not overloaded, max bet and showInTable set to defaults
            dao.insert(new PokerHand(record.getRecordNumber(),
                record.get(0), record.get(1), Integer.parseInt(record.get(2))));
          }
        }
      }catch (IOException e) {
        //TODO handle or don't
      }
      return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

    }
  }

}
