package edu.cnm.deepdive.videopoker.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;
import edu.cnm.deepdive.videopoker.model.Card;
import edu.cnm.deepdive.videopoker.model.PlayerHand;
import edu.cnm.deepdive.videopoker.model.Rank;
import edu.cnm.deepdive.videopoker.model.Suit;
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


  private String name;
  private String ruleSequence;
  private int betOneValue;
  private int betFiveValue;
  private boolean showInTable = true;




}
