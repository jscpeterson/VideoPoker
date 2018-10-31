package edu.cnm.deepdive.videopoker.model.entity;

import static org.junit.Assert.*;

import edu.cnm.deepdive.videopoker.model.Card;
import edu.cnm.deepdive.videopoker.model.PlayerHand;
import edu.cnm.deepdive.videopoker.model.Rank;
import edu.cnm.deepdive.videopoker.model.Suit;
import edu.cnm.deepdive.videopoker.model.entity.PokerHand.PokerHandRuleConverter;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PokerHandTest {

  PokerHand hand = new PokerHand();
  PlayerHand crap = new PlayerHand(
      new Card(Rank.ACE, Suit.SPADES),
      new Card(Rank.TWO, Suit.SPADES),
      new Card(Rank.SIX, Suit.HEARTS),
      new Card(Rank.FOUR, Suit.SPADES),
      new Card(Rank.KING, Suit.DIAMONDS));
  PlayerHand flush = new PlayerHand(
      new Card(Rank.ACE, Suit.SPADES),
      new Card(Rank.TWO, Suit.SPADES),
      new Card(Rank.ACE, Suit.SPADES),
      new Card(Rank.FOUR, Suit.SPADES),
      new Card(Rank.ACE, Suit.SPADES));
  String flushRules = "**,=*;**,=*;**,=*;**,=*;**,=*";

  @Test
  public void testFlush() {
    assertTrue(hand.checkHandAgainstRules(flushRules, flush));
    assertFalse(hand.checkHandAgainstRules(flushRules, crap));
  }
}