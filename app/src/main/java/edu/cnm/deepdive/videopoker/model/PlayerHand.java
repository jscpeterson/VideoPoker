package edu.cnm.deepdive.videopoker.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class PlayerHand extends Stack<Card> {

  private final String BUST = "\u2639";

  public String getBustString() {
    return BUST;
  }

  public PlayerHand(Card... cards) {
    for (Card card : cards) {
      this.add(card);
    }
  }

}
