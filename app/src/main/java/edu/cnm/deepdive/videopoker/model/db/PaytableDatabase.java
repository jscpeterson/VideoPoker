package edu.cnm.deepdive.videopoker.model.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import edu.cnm.deepdive.videopoker.model.dao.PokerHandDao;
import edu.cnm.deepdive.videopoker.model.entity.Paytable;
import edu.cnm.deepdive.videopoker.model.entity.PokerHand;

@Database(
  entities = {Paytable.class, PokerHand.class},
  version = 1,
    exportSchema = true
)
public abstract class PaytableDatabase extends RoomDatabase {

  private static final String DB_NAME = "PaytableDatabase";

  private static PaytableDatabase instance = null;
  
  public abstract PokerHandDao getPokerHandDao();

  public static PaytableDatabase getInstance(Context context) {
    if (instance == null) {
      instance = Room.databaseBuilder(context.getApplicationContext(), PaytableDatabase.class, DB_NAME)
          .build();
    }
    return instance;
  }

}
