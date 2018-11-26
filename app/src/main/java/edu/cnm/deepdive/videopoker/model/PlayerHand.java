package edu.cnm.deepdive.videopoker.model;

import edu.cnm.deepdive.videopoker.model.entity.PokerHand;
import java.util.Stack;

/**
 * This class represents the hand actually being played in a game of poker, as opposed to the poker
 * hand type with corresponding rules and worth. It exists to store the best hand available and can
 * be created with a constructor accepting a set of Cards.
 */
public class PlayerHand extends Stack<Card> {

  /**
   * The best winning PokerHand this card has been evaluated as a match to.
   */
  private PokerHand bestHand;

  /**
   * @param cards a set of Card objects to be stored in a hand.
   */
  public PlayerHand(Card... cards) {
    for (Card card : cards) {
      this.add(card);
    }
  }

  /**
   * Assumes that this has already been evaluated externally.
   *
   * @return the last PokerHand object that has been recorded as the best winning hand possible with
   * the given cards in this set.
   */
  public PokerHand getBestHand() {
    return bestHand;
  }

  /**
   * Stores a PokerHand parameter as the best possible hand for this card.
   *
   * @param bestHand a PokerHand object with associated rules and worth.
   */
  public void setBestHand(PokerHand bestHand) {
    this.bestHand = bestHand;
  }

}
