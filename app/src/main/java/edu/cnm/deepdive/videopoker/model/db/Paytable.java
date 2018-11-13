package edu.cnm.deepdive.videopoker.model.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import edu.cnm.deepdive.videopoker.model.dao.PokerHandDao;
import edu.cnm.deepdive.videopoker.model.entity.PokerHand;

@Database(
  entities = {PokerHand.class},
  version = 1,
    exportSchema = true
)
public abstract class Paytable extends RoomDatabase {

  private static final String DB_NAME="ffdfffdf";

  String gameName;

  private static Paytable instance = null;

  Context context;

  public abstract PokerHandDao getPokerHandDao();

  public static Paytable getInstance(Context context, String gameName) {
    if (instance == null) {
      instance = Room.databaseBuilder(context.getApplicationContext(), Paytable.class, gameName)
          .build();
    }
    return instance;
  }

}
