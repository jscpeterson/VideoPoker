package edu.cnm.deepdive.videopoker.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;
import edu.cnm.deepdive.videopoker.model.Card;

@Entity
public class PokerHand {

  public static final int DEFAULT_VALUE = 0;
  public static final int MAX_BET = 5;

  @NonNull
  @ColumnInfo
  //the big toledo
  private String rules;

  @NonNull
  @ColumnInfo
  private String name;

  @NonNull
  @ColumnInfo
  private int betOneValue;

  @NonNull
  @ColumnInfo
  private int betFiveValue;

  public class PokerHandRuleConverter {
    /*
    Card:: = suit, rank
    Suit::= wildcard | suit literal | carry
    Suit literal ::= C|D|H|S
    Wildcard ::= *
    Rank ::= wildcard | offset | [op]rank literal
    Offset ::= -n | 0 | n
    op::= = | >= | <= | > | <
    Rank.literal::= 2|3|4|5|6|7|8|9|T|J|Q|K|A
    */

    //Cards sort first by rank then suit

    //Rules string format
    //For cards a two char string represents a char - A♠ is Ace of Spades
    //For the rules set each two char string represents a matching rule
    // - a card would be represented by two matching rules for a suit matching rule and a rank matching rule

    //char 0 being the first card, char 1 being the following card to compare against char 0

    //A flush for example might be
    //**,=*;**,=*;**,=*;**,=*;**,=*;
    //RANK (first char set) is irrelevant, all wildcard matches
    //SUIT requires a rule on each so that the first item matches the next item

    boolean checkHandAgainstRules(String rules, Card[] hand) {
       return false;
    }

  }

}
