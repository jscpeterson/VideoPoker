package edu.cnm.deepdive.videopoker.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;
import edu.cnm.deepdive.videopoker.model.Card;
import edu.cnm.deepdive.videopoker.model.PlayerHand;
import edu.cnm.deepdive.videopoker.model.Rank;
import edu.cnm.deepdive.videopoker.model.Suit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

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

  PlayerHand flush = new PlayerHand();

  String exampleRules = "**,=*;**,=*;**,=*;**,=*;**,=*";

  /**
   * This will check a PlayerHand against a ruleSet and return TRUE if the hand matches the rules
   * provided.
   * @param rules
   * @param hand
   * @return
   */
  public boolean checkHandAgainstRules(String rules, List<Card> hand) {
    List<Rank> rankList = new ArrayList<>();
    List<Suit> suitList = new ArrayList<>();
    if (hand.size() == 1) return true;
    for (Card card : hand) {
      rankList.add(card.getRank());
      suitList.add(card.getSuit());
    }
    Collections.sort(rankList);
    Collections.sort(suitList);
    for (String cardRules : rules.split(";")) {
      String rankRules = cardRules.split(",")[0];
      String suitRules = cardRules.split(",")[1];
      switch (rankRules.charAt(0)) {
        case '=':
        case '*':
        default:
          break;
      }
      switch (suitRules.charAt(0)) {
        case '=':
          System.out.println(hand);
          return hand.get(0).getSuit().equals(hand.get(1).getSuit()) &&
              checkHandAgainstRules(rules.substring(6), (List<Card>) hand.subList(1, hand.size()));
        case '*':
          return true;
        default:
          break;
      }
    }
    return false;

  }

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

    //A hand is represented by a string of values,



  }

}
