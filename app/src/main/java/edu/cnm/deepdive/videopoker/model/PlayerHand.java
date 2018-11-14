package edu.cnm.deepdive.videopoker.model;

import edu.cnm.deepdive.videopoker.model.entity.PokerHand;
import java.util.Stack;

public class PlayerHand extends Stack<Card> {

  PokerHand bestHand;

  public PlayerHand(Card... cards) {
    for (Card card : cards) {
      this.add(card);
    }
  }

  public PokerHand getBestHand() {
    return bestHand;
  }

  public void setBestHand(PokerHand bestHand) {
    this.bestHand = bestHand;
  }

}
