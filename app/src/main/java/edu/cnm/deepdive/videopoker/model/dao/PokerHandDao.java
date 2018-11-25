package edu.cnm.deepdive.videopoker.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import edu.cnm.deepdive.videopoker.model.entity.PokerHand;
import java.util.List;

@Dao
public interface PokerHandDao {

  @Insert(onConflict = OnConflictStrategy.FAIL)
  long insert(PokerHand pokerhand);

  @Query("UPDATE PokerHand SET betOneValue = :newValue WHERE name = :handName AND paytable_id = :paytableId")
  long updateBetOneValue(long paytableId, String handName, int newValue);

  @Query("SELECT * FROM PokerHand WHERE paytable_id IS :paytableId ORDER BY betOneValue DESC")
  List<PokerHand> selectPokerHandsByBetOneFromPaytable(long paytableId);

  @Query("SELECT name FROM PokerHand WHERE paytable_id IS :paytableId ORDER BY betOneValue DESC")
  List<String> selectPokerHandNamesByBetOneFromPaytable(long paytableId);

}
