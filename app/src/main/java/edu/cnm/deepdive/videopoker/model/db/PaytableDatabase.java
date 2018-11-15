package edu.cnm.deepdive.videopoker.model.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import edu.cnm.deepdive.videopoker.R;
import edu.cnm.deepdive.videopoker.controller.SplashActivity;
import edu.cnm.deepdive.videopoker.model.dao.PaytableDao;
import edu.cnm.deepdive.videopoker.model.dao.PokerHandDao;
import edu.cnm.deepdive.videopoker.model.entity.Paytable;
import edu.cnm.deepdive.videopoker.model.entity.PokerHand;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

@Database(
  entities = {Paytable.class, PokerHand.class},
  version = 1,
    exportSchema = true
)
public abstract class PaytableDatabase extends RoomDatabase {

  private static final String DB_NAME = "PaytableDatabase";
  private static final int INDEX_GAME_NAME = 0;
  private static final int INDEX_HAND_NAME = 1;
  private static final int INDEX_RULE_SEQUENCE = 2;
  private static final int INDEX_BET_ONE_VALUE = 3;
  private static final int INDEX_OVERLOADED_PARAM = 4;

  private static PaytableDatabase instance = null;
  
  public abstract PokerHandDao getPokerHandDao();

  public abstract PaytableDao getPaytableDao();

  public static PaytableDatabase getInstance(Context context) {
    if (instance == null) {
      instance = Room.databaseBuilder(context.getApplicationContext(), PaytableDatabase.class, DB_NAME)
          .addCallback(new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
              super.onCreate(db);
              new SetupTask(context).execute();
            }

            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
              super.onOpen(db);
              ((SplashActivity) context).ready();
            }
          })
          .build();

    }
    return instance;
  }

  private static class SetupTask extends AsyncTask<Void, Void, Void> {

    private Context context;

    private SetupTask(Context context) {
      this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
      try {
        PaytableDatabase db = PaytableDatabase.getInstance(context.getApplicationContext());
        PokerHandDao pokerHandDao = db.getPokerHandDao();

        InputStream paytablesInputStream = context.getResources().openRawResource(R.raw.paytables);
        InputStream gamesInputStream = context.getResources().openRawResource(R.raw.games);
        CSVParser paytablesCsvParser =
            new CSVParser(new InputStreamReader(paytablesInputStream), CSVFormat.DEFAULT);
        CSVParser gamesCsvParser =
            new CSVParser(new InputStreamReader(gamesInputStream), CSVFormat.DEFAULT);
        //TODO add and handle headers

        for (CSVRecord gameRecord : gamesCsvParser.getRecords()) {
          if (gameRecord.getRecordNumber() > 1) {
            Paytable paytable = new Paytable();
            paytable.setName(gameRecord.get(INDEX_GAME_NAME));
            db.getPaytableDao().insert(paytable);
          }
        }

        for (CSVRecord paytableRecord : paytablesCsvParser.getRecords()) {
          if (paytableRecord.getRecordNumber() > 1) {
            PokerHand newHand = new PokerHand();
            Paytable paytable = db.getPaytableDao().select(paytableRecord.get(INDEX_GAME_NAME));
            newHand.setPaytableId(paytable.getId());

            newHand.setName(paytableRecord.get(INDEX_HAND_NAME));
            newHand.setRuleSequence(paytableRecord.get(INDEX_RULE_SEQUENCE));
            newHand.setBetOneValue(Integer.parseInt(paytableRecord.get(INDEX_BET_ONE_VALUE)));

            if (paytableRecord.size() == INDEX_OVERLOADED_PARAM + 1) {
              newHand.setBetFiveValue(Integer.parseInt(paytableRecord.get(INDEX_OVERLOADED_PARAM)));
            } else {
              newHand.setBetFiveValue(newHand.getBetOneValue() * 5);
            }

            // showInTable flag off if the hand is useless (0 value) or already named in the game
            if (pokerHandDao.selectPokerHandNamesByBetOneFromPaytable(paytable.getId())
                .contains(newHand.getName())) {
              newHand.setShowInTable(false);
            } else {
              newHand.setShowInTable(newHand.getBetOneValue() > 0);
            }

            pokerHandDao.insert(newHand);

          }
        }
      } catch (IOException e) {
        //TODO handle or don't
      }
      return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
      ((SplashActivity) context).ready();
    }
  }
}
