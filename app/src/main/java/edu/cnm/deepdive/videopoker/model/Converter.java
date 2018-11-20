package edu.cnm.deepdive.videopoker.model;

import java.util.Collections;
import java.util.Stack;

public class Converter {

  private static final int FACE_CARD_LOWEST_VALUE = 10;
  private static final String ACE_SYMBOL = "A";
  private static final String SEQUENCE_DELIMITER = ";";
  private static final String PATTERN_DELIMITER = ",";

  /**
   * This method checks if provided hand matches a provided sequence of patterns.
   * @param ruleSequence A string to represent the rules of a poker hand. It should be a sequence
   * of patterns separated by a semicolon. If multiple patterns are provided the hand will only
   * return true if it matches all of the patterns. Within each sequence, pattern rules are
   * separated by commas. Each rule contains two characters, the first character representing the
   * rank of the hand and the second character representing the suit. Hands are sorted by rank
   * upon evaluation, so there is limited flexibility with suit rules. Currently only a flush is
   * supported. The rules for ranks are as follows:
   * <ul>
   *   <li>
   *     * - wildcard, any pattern matches.
   *   </li>
   *   <li>
   *     = - pattern matches if this element matches the previous element in the pattern.
   *   </li>
   *   <li>
   *     + - pattern rank is one higher than the previous element in the pattern.
   *   </li>
   *   <li>
   *     F - Face card
   *   </li>
   *   <li>
   *     Any other character is assumed to be a literal. <br>
   *     <strong>Warning!</strong> If it is not a literal that corresponds
   *     to a rank ("A, 2, 3, 4, 5, 6, 7, 8, 9, T, J, Q, K") it will likely throw an exception.
   *   </li>
   * </ul>
   * @param playerHand
   * @return
   */
  public boolean parseRuleSequence(String ruleSequence, Stack<Card> playerHand) {
    //make working copy of playerhand, otherwise the converter eats cards
    PlayerHand hand = (PlayerHand) playerHand.clone();
    boolean patternMatched = false;
    //split sequence by semicolons to get rule patterns
    String[] patterns = ruleSequence.split(SEQUENCE_DELIMITER);
    //iterate over each pattern
    for (String pattern : patterns) {
      patternMatched = false;
      //split sequence by commas to get pattern elements
      String[] patternElements = pattern.split(PATTERN_DELIMITER);
      //sort hand by rank
      Collections.sort(hand);
      //for each pattern, iterate over the hand for the length of the pattern
      //a pattern of 2 should iterate only up to the fourth card to prevent an exception
      hand:
      for (int handIndex = 0; handIndex <= hand.size() - patternElements.length; ++handIndex) {
        pattern:
        for (int patternIndex = 0; true; ++patternIndex) {
          //check if the card in the hand matches the first pattern element
          //if the pattern element does not match, break out of the loop
          //otherwise, continue checking if the next card from the first matches the next pattern element
          //switch case for ranks
          Rank rankAtHandIndex = hand.get(handIndex).getRank();
          Suit suitAtHandIndex = hand.get(handIndex).getSuit();
          Rank rankAtPointInPattern = hand.get(handIndex + patternIndex)
              .getRank();
          Suit suitAtPointInPattern = hand.get(handIndex + patternIndex).getSuit();
          switch (patternElements[patternIndex].charAt(0)) {
            case '*':
              break;
            case '+':
              if (!(rankAtHandIndex.getValue() + patternIndex == rankAtPointInPattern.getValue())) {
                break pattern;
              }
              break;
            case '=':
              if (!(rankAtHandIndex == rankAtPointInPattern)) {
                break pattern;
              }
              break;
            //Face card
            //TODO change from face card to greater than operator
            case 'F':
              if (!(rankAtPointInPattern.getValue() > FACE_CARD_LOWEST_VALUE ||
                  rankAtPointInPattern.getSymbol().equals(ACE_SYMBOL))) {
                break pattern;
              }
              break;
            default:
              //Assume character is a literal in rank.
              //TODO add OR operator
              if (!(rankAtPointInPattern.getSymbol().equals(
                  String.valueOf(patternElements[patternIndex].charAt(0))))) {
                break pattern;
              }
              break;
          }
          //switch case for suits - limited flexibility as suits are unsorted
          switch (patternElements[patternIndex].charAt(1)) {
            case '*':
              break;
            case '=':
              if (!(suitAtHandIndex == suitAtPointInPattern)) {
                break pattern;
              }
              break;
            default:
              break pattern;
          }
          //if pattern reaches end of loop without a failure indicate a successful match
          if (patternIndex + 1 == patternElements.length) {
            patternMatched = true;
            //remove matched card from the hand
            if (handIndex + patternElements.length > handIndex) {
              hand.subList(handIndex, handIndex + patternElements.length).clear();
            }
            break hand;
          }
        }
      }
      //if the hand loop completes without finding a match, return a failure
      if (!patternMatched) {
        return false;
      }
    }
    //if the method completes without encountering a failure, the hand matches the rule sequence
    return patternMatched;
  }
}