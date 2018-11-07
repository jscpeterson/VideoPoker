package edu.cnm.deepdive.videopoker.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;
import android.support.annotation.NonNull;
import edu.cnm.deepdive.videopoker.model.Card;
import edu.cnm.deepdive.videopoker.model.PlayerHand;
import edu.cnm.deepdive.videopoker.model.Rank;
import edu.cnm.deepdive.videopoker.model.Suit;
import edu.cnm.deepdive.videopoker.model.dao.PokerHandDao;
import edu.cnm.deepdive.videopoker.model.db.Paytable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

@Entity
public class PokerHand {

  public static final int DEFAULT_VALUE = 0;
  public static final int MAX_BET = 5;

  @PrimaryKey(autoGenerate = true)
  private long id;
  private String name;
  private String ruleSequence;
  private int betOneValue;
  private int betFiveValue;
  private boolean showInTable = true;

  public PokerHand(String name, String ruleSequence, int betOneValue) {
    this.name = name;
    this.ruleSequence = ruleSequence;
    this.betOneValue = betOneValue;
    this.betFiveValue = betOneValue*MAX_BET;
  }

  @Ignore
  public PokerHand(String name, String ruleSequence, int betOneValue, int betFiveValue) {
    this.name = name;
    this.ruleSequence = ruleSequence;
    this.betOneValue = betOneValue;
    this.betFiveValue = betFiveValue;
  }

//  public PokerHand(String name, String ruleSequence, int betOneValue, boolean showInTable) {
//    this.name = name;
//    this.ruleSequence = ruleSequence;
//    this.betOneValue = betOneValue;
//    this.betFiveValue = betOneValue*MAX_BET;
//    this.showInTable = showInTable;
//  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getRuleSequence() {
    return ruleSequence;
  }

  public void setRuleSequence(String ruleSequence) {
    this.ruleSequence = ruleSequence;
  }

  public int getBetOneValue() {
    return betOneValue;
  }

  public void setBetOneValue(int betOneValue) {
    this.betOneValue = betOneValue;
  }

  public int getBetFiveValue() {
    return betFiveValue;
  }

  public void setBetFiveValue(int betFiveValue) {
    this.betFiveValue = betFiveValue;
  }

  public boolean isShowInTable() {
    return showInTable;
  }

  public void setShowInTable(boolean showInTable) {
    this.showInTable = showInTable;
  }

}
