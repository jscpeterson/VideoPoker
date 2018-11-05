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
  // - a card would be represented by two matching rules for a suit matching rule and a rank matching rule

  //RANK (first char set) is irrelevant, all wildcard matches
  //SUIT requires a rule on each so that the first item matches the next item

  //A hand is represented by a string of values,

  String flushSequence = "**,*=,*=,*=,*=";

  /**
   * This will check a PlayerHand against a ruleSet and return TRUE if the hand matches the rules
   * provided.
   */
  public boolean parseRuleSequence(String ruleSequence, List<Card> hand) {
    //initialize hand index outside of loop so that multiple patterns do not throw it off
    int handIndex = 0;
    boolean patternMatched = false;
    //split delimiter by semicolons to get rule patterns
    String[] patterns = ruleSequence.split(";");
    //iterate over each pattern
    for (String pattern : patterns) {
      patternMatched = false;
      //split delimiter by commas to get pattern elements
      String[] patternElements = pattern.split(",");
      //sort hand by rank
      Collections.sort(hand);
      //for each pattern, iterate over the hand for the length of the pattern
      //a pattern of 2 should iterate only up to the fourth card to prevent an exception
      hand:
      for (handIndex = handIndex; handIndex <= hand.size() - patternElements.length; ++handIndex) {
        pattern:
        for (int patternIndex = 0; handIndex <= patternElements.length; ++patternIndex) {
          //check if the card in the hand matches the first pattern element
          //if the pattern element does not match, break out of the loop
          //otherwise, continue checking if the next card from the first matches the next pattern element
          //switch case for ranks
          switch (patternElements[patternIndex].charAt(0)) {
            case '*':
              break;
            case '+':
              System.out.println(hand.get(handIndex).getRank().getValue() + patternIndex);
              System.out.println(hand.get(handIndex + patternIndex).getRank().getValue());
              if (!(hand.get(handIndex).getRank().getValue() + patternIndex == hand
                  .get(handIndex + patternIndex).getRank().getValue())) {
                break pattern;
              }
              break;
            case '=':
              if (!(hand.get(handIndex).getRank() == hand.get(handIndex + patternIndex)
                  .getRank())) {
                break pattern;
              }
              break;
            default:
              break pattern;
          }
          //switch case for suits - limited flexibility as suits are unsorted
          switch (patternElements[patternIndex].charAt(1)) {
            case '*':
              break;
            case '=':
              if (!(hand.get(handIndex).getSuit() == hand.get(handIndex + patternIndex)
                  .getSuit())) {
                break pattern;
              }
              break;
            default:
              break pattern;
          }
          //if pattern reaches end of loop without a failure indicate a success and break the greater loop
          if (patternIndex + 1 == patternElements.length) {
            patternMatched = true;
            //handIndex does not update when the hand is broken so it increments here.
            ++handIndex;
            //increments twice to not check the card in a completed pattern again
            ++handIndex;
            break hand;
          }
        }
      }
      //if the hand loop completes without finding a match, return a failure
//      if (!patternMatched) return false;
    }
    //if the method completes without encountering a failure, the hand matches the rule sequence
    return patternMatched;
  }

  public class PokerHandRuleConverter {


  }

}
