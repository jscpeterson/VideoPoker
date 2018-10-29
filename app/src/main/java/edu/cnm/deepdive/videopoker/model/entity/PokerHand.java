package edu.cnm.deepdive.videopoker.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

@Entity
public enum PokerHand {
  ROYAL_FLUSH("Royal Flush", 250, 4000),
  STRAIGHT_FLUSH("Straight Flush", 50),
  FOUR_ACES("Four Aces"),
  FOUR_OF_A_KIND("Four of a Kind", 25),
  FULL_HOUSE("Full House", 9),
  FLUSH("Flush", 5),
  STRAIGHT("Straight", 4),
  THREE_OF_A_KIND("Three of a Kind", 3),
  TWO_PAIR("Two Pair", 2),
  JACKS_OR_BETTER("Jacks or Better", 1),
  BUST("\u2639");

  public static final int DEFAULT_VALUE = 0;
  public static final int MAX_BET = 5;

  PokerHand(String name, int betOneValue, int betFiveValue) {
    this.name = name;
    this.betOneValue = betOneValue;
    this.betFiveValue = betFiveValue;
  }

  PokerHand(String name, int betOneValue) {
    this.name = name;
    this.betOneValue = betOneValue;
    this.betFiveValue = betOneValue * MAX_BET;
  }

  PokerHand(String name) {
    this.name = name;
    this.betOneValue = DEFAULT_VALUE;
    this.betFiveValue = DEFAULT_VALUE;
  }

  @NonNull
  @ColumnInfo
  private String name;

  @NonNull
  @ColumnInfo
  private int betOneValue;

  @NonNull
  @ColumnInfo
  private int betFiveValue;

}
