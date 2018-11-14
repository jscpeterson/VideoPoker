package edu.cnm.deepdive.videopoker.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import edu.cnm.deepdive.videopoker.model.entity.Paytable;
import edu.cnm.deepdive.videopoker.model.entity.PokerHand;

@Dao
public interface PaytableDao {

  @Insert(onConflict = OnConflictStrategy.FAIL)
  long insert(Paytable paytable);

}
