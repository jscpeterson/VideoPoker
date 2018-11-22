package edu.cnm.deepdive.videopoker.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import edu.cnm.deepdive.videopoker.model.entity.Paytable;
import edu.cnm.deepdive.videopoker.model.entity.PokerHand;
import java.util.List;

@Dao
public interface PaytableDao {

  @Insert(onConflict = OnConflictStrategy.FAIL)
  long insert(Paytable paytable);

  @Query("SELECT * FROM Paytable WHERE paytable_id = :paytableId LIMIT 1")
  Paytable select(long paytableId);

  @Query("SELECT * FROM Paytable WHERE name = :name LIMIT 1")
  Paytable select(String name);

  @Query("SELECT * FROM Paytable ORDER BY paytable_id")
  List<Paytable> select();
}
