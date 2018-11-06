package edu.cnm.deepdive.videopoker.model.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import edu.cnm.deepdive.videopoker.model.dao.PokerHandDao;
import edu.cnm.deepdive.videopoker.model.entity.PokerHand;

@Database(
  entities = {PokerHand.class},
  version = 1,
    exportSchema = true
)
public abstract class Paytable extends RoomDatabase {

  private static final String DB_NAME="paytable";

  private static Paytable instance = null;

  Context context;

  z

  public abstract PokerHandDao getPokerHandDao();

  public static Paytable getInstance(Context context) {
    if (instance == null) {
      instance = Room.databaseBuilder(context.getApplicationContext(), Paytable.class, DB_NAME)
          .build();
    }
    return instance;
  }

  protected Void doInBackground(Void... voids) {
    Paytable paytable = Paytable.getInstance(context);
    PokerHandDao dao = paytable.getPokerHandDao();
    defaultPayouts(dao);
    return null;
  }

  String royalFlushSequence = "A=,T=,J=,Q=,K=";
  String straightFlushSequence = "**,+=,+=,+=,+=";
  String fourOfAKindSequence = "**,=*,=*,=*";
  String fullHouseSequence = "**,=*,=*;**,=*";
  String flushSequence = "**,*=,*=,*=,*=";
  String straightSequenceAceHigh = "A*,T*,J*,Q*,K*";
  String straightSequence = "**,+*,+*,+*,+*";
  String threeOfAKindSequence = "**,=*,=*";
  String twoPairSequence = "**,=*;**,=*";
  String jacksOrBetterSequence = "F*,=*";

  private void defaultPayouts(PokerHandDao dao) {
    dao.insert(new PokerHand("Royal Flush", royalFlushSequence, 250, 4000));
    dao.insert(new PokerHand("Straight Flush", straightFlushSequence, 50));
    dao.insert(new PokerHand("Four of a Kind", fourOfAKindSequence, 25));
    dao.insert(new PokerHand("Full House", fullHouseSequence, 9));
    dao.insert(new PokerHand("Flush", flushSequence, 6));
    dao.insert(new PokerHand("Straight", straightSequenceAceHigh, 4, false));
    dao.insert(new PokerHand("Straight", straightSequence, 4));
    dao.insert(new PokerHand("Three of a Kind", threeOfAKindSequence, 3));
    dao.insert(new PokerHand("Two Pair", twoPairSequence, 2));
    dao.insert(new PokerHand("Jacks or Better", jacksOrBetterSequence, 1));
  }

}
