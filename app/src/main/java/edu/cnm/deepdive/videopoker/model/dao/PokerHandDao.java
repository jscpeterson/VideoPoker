package edu.cnm.deepdive.videopoker.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import edu.cnm.deepdive.videopoker.model.entity.PokerHand;
import java.util.List;

@Dao
public interface PokerHandDao {

  @Insert(onConflict = OnConflictStrategy.FAIL)
  long insert(PokerHand pokerhand);

  @Query("SELECT * FROM PokerHand ORDER BY betOneValue DESC")
  List<PokerHand> selectPokerHandsByBetOne();


}
