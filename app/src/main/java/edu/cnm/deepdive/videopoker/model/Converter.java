package edu.cnm.deepdive.videopoker.model;

import java.util.Collections;
import java.util.List;

public class Converter {

  public static final int FACE_CARD_LOWEST_VALUE = 10;

  /**
     * This will check a PlayerHand against a ruleSet and return TRUE if the hand matches the rules
     * provided.
     */
    public boolean parseRuleSequence(String ruleSequence, List<Card> hand) {
      //initialize hand index outside of loop so that multiple patterns do not throw it off
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
        for (int handIndex = 0; handIndex <= hand.size() - patternElements.length; ++handIndex) {
          pattern:
          for (int patternIndex = 0; true; ++patternIndex) {
            //TODO clean up
            //check if the card in the hand matches the first pattern element
            //if the pattern element does not match, break out of the loop
            //otherwise, continue checking if the next card from the first matches the next pattern element
            //switch case for ranks
            switch (patternElements[patternIndex].charAt(0)) {
              case '*':
                break;
              case '+':
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
              //Face card
              case 'F':
                if (!(hand.get(handIndex+patternIndex).getRank().getValue() > FACE_CARD_LOWEST_VALUE ||
                    hand.get(handIndex+patternIndex).getRank().getSymbol().equals("A"))) {
                  break pattern;
                }
                break;
              default:
                //Assume character is a literal in rank. If it isn't, too bad.
                if (!(hand.get(handIndex+patternIndex).getRank().getSymbol().equals(
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
//            //handIndex does not update when the hand is broken so it increments here for the length of the sequence.
//            handIndex += patternElements.length;

              //WHEN THERE IS A SUCCESS REMOVE ALL MATCHING CARDS FROM THE HAND
              for (int i = handIndex; i < handIndex+patternElements.length; ++i){
                hand.remove(handIndex);
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
