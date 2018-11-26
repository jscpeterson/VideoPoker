package edu.cnm.deepdive.videopoker.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import edu.cnm.deepdive.videopoker.model.entity.Paytable;
import edu.cnm.deepdive.videopoker.model.entity.PokerHand;
import java.util.List;

/**
 * Data access object for the paytable.
 */
@Dao
public interface PaytableDao {

  /**
   * Insert a single paytable.
   */
  @Insert(onConflict = OnConflictStrategy.FAIL)
  long insert(Paytable paytable);

  /**
   * Retrieve the data from a paytable that matches the name.
   */
  @Query("SELECT * FROM Paytable WHERE name = :name LIMIT 1")
  Paytable select(String name);

  /**
   * Retrieve a list of paytables from the database according to the ID.
   */
  @Query("SELECT * FROM Paytable ORDER BY paytable_id")
  List<Paytable> select();
}
