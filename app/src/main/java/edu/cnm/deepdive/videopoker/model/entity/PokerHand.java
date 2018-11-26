package edu.cnm.deepdive.videopoker.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;
import android.support.annotation.NonNull;
import edu.cnm.deepdive.videopoker.model.Card;
import edu.cnm.deepdive.videopoker.model.PlayerHand;
import edu.cnm.deepdive.videopoker.model.Rank;
import edu.cnm.deepdive.videopoker.model.Suit;
import edu.cnm.deepdive.videopoker.model.dao.PokerHandDao;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * This is a database entity associated with a winning hand for a game of poker. It contains a name,
 * the ID of the paytable it is associated with, the rule sequence (see Converter class for more
 * details), the value of a one credit bet, and optionally the distinct value of a five (max) credit
 * bet. It also contains a "showInValue" flag to determine if it should appear in displays to the
 * user - this should be false for zero value (bust) hands or for duplicate hands.
 */
@Entity(
    foreignKeys = {
        @ForeignKey(
            entity = Paytable.class,
            parentColumns = "paytable_id",
            childColumns = "paytable_id"
        )
    }
)
public class PokerHand {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "poker_hand_id")
  private long id;
  @ColumnInfo(name = "paytable_id", index = true)
  private long paytableId;
  private String name;
  private String ruleSequence;
  private int betOneValue;
  private int betFiveValue;
  private boolean showInTable = true;

  /**
   * Empty constructor for the persistence library.
   */
  public PokerHand() {

  }

  /**
   * @return the ID of the hand.
   */
  public long getId() {
    return id;
  }

  /**
   * @param id a numeric value to assign to the ID of the hand.
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * @return the name of the hand.
   */
  public String getName() {
    return name;
  }

  /**
   * @param name a String to change the name of the hand to.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the String rule sequence for the hand to be used by the converter.
   */
  public String getRuleSequence() {
    return ruleSequence;
  }

  /**
   * @param ruleSequence a String rule sequence for the hand to be used by the converter.
   */
  public void setRuleSequence(String ruleSequence) {
    this.ruleSequence = ruleSequence;
  }

  /**
   * @return the return value on a single bet for the hand.
   */
  public int getBetOneValue() {
    return betOneValue;
  }

  /**
   * @param betOneValue a value to assign to the return value on a single bet for the hand.
   */
  public void setBetOneValue(int betOneValue) {
    this.betOneValue = betOneValue;
  }

  /**
   * @return the potentially unique bonus value for the return on the maximum bet for the hand.
   */
  public int getBetFiveValue() {
    return betFiveValue;
  }

  /**
   * @param betFiveValue a potentially unique bonus value for the return on the maximum bet for the
   * hand.
   */
  public void setBetFiveValue(int betFiveValue) {
    this.betFiveValue = betFiveValue;
  }

  /**
   * @return the status of the "showInTable" flag.
   */
  public boolean isShowInTable() {
    return showInTable;
  }

  /**
   * @param showInTable a boolean value to assign to the "showInTable" flag.
   */
  public void setShowInTable(boolean showInTable) {
    this.showInTable = showInTable;
  }

  /**
   * @return the ID of the paytable this hand is associated with.
   */
  public long getPaytableId() {
    return paytableId;
  }

  /**
   * @param paytableId an ID associated with a paytable to assign this hand to.
   */
  public void setPaytableId(long paytableId) {
    this.paytableId = paytableId;
  }

}
