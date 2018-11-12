package edu.cnm.deepdive.videopoker.model;

import java.util.Collections;
import java.util.Stack;

public class Converter {

  private static final int FACE_CARD_LOWEST_VALUE = 10;
  private static final String ACE_SYMBOL = "A";

  /**
     * This will check a PlayerHand against a ruleSet and return TRUE if the hand matches the rules
     * provided.
     */
    public boolean parseRuleSequence(String ruleSequence, Stack<Card> playerHand) {
      //make working copy of playerhand, otherwise the converter eats cards
      PlayerHand hand = (PlayerHand) playerHand.clone();
      boolean patternMatched = false;
      //split sequence by semicolons to get rule patterns
      String[] patterns = ruleSequence.split(";");
      //iterate over each pattern
      for (String pattern : patterns) {
        patternMatched = false;
        //split sequence by commas to get pattern elements
        String[] patternElements = pattern.split(",");
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
                //Assume character is a literal in rank. If it isn't, too bad.
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
        if (!patternMatched) return false;
      }
      //if the method completes without encountering a failure, the hand matches the rule sequence
      return patternMatched;
    }

  }
