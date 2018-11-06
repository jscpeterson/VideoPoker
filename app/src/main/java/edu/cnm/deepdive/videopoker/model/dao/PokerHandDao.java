package edu.cnm.deepdive.videopoker.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import edu.cnm.deepdive.videopoker.model.entity.PokerHand;
import java.util.List;

@Dao
public interface PokerHandDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  String insert(PokerHand pokerhand);

  @Query("SELECT * FROM PokerHand ORDER BY betOneValue")
  List<PokerHand> selectPokerHandsByBetOne();

  @Query("SELECT * FROM PokerHand ORDER BY betFiveValue")
  List<PokerHand> selectPokerHandsByBetFive();

}