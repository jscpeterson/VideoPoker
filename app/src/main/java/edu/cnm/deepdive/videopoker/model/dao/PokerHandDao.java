package edu.cnm.deepdive.videopoker.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import edu.cnm.deepdive.videopoker.model.entity.PokerHand;
import java.util.List;

/**
 * Data access object for the PokerHand entity.
 */
@Dao
public interface PokerHandDao {

  /**
   * Insert a single hand into the database.
   */
  @Insert(onConflict = OnConflictStrategy.FAIL)
  long insert(PokerHand pokerhand);

  /**
   * This query updates the bet one value of a hand in a specific paytable.
   */
  @Query("UPDATE PokerHand SET betOneValue = :newValue WHERE name = :handName AND paytable_id = :paytableId")
  long updateBetOneValue(long paytableId, String handName, int newValue);

  /**
   * This query updates the bet five value of a hand in a specific paytable.
   */
  @Query("UPDATE PokerHand SET betFiveValue = :newValue WHERE name = :handName AND paytable_id = :paytableId")
  long updateBetFiveValue(long paytableId, String handName, int newValue);

  /**
   * This query returns a list of PokerHands for a specific paytable in descending order by the
   * betOneValue. It is intended for evaluations of a hand - the first hand encountered has the
   * highest value.
   */
  @Query("SELECT * FROM PokerHand WHERE paytable_id IS :paytableId ORDER BY betOneValue DESC")
  List<PokerHand> selectPokerHandsByBetOneFromPaytable(long paytableId);

  /**
   * This query returns a list of hand names for a specific paytable in descending order by the
   * betOneValue. It is intended for evaluations of a hand - the first hand encountered has the
   * highest value.
   */
  @Query("SELECT name FROM PokerHand WHERE paytable_id IS :paytableId ORDER BY betOneValue DESC")
  List<String> selectPokerHandNamesByBetOneFromPaytable(long paytableId);

}
