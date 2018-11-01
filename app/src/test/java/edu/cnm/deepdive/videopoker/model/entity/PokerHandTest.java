package edu.cnm.deepdive.videopoker.model.entity;

import static org.junit.Assert.*;

import edu.cnm.deepdive.videopoker.model.Card;
import edu.cnm.deepdive.videopoker.model.PlayerHand;
import edu.cnm.deepdive.videopoker.model.Rank;
import edu.cnm.deepdive.videopoker.model.Suit;
import org.junit.Test;

public class PokerHandTest {

  PokerHand hand = new PokerHand();
  PlayerHand crap = new PlayerHand(
      new Card(Rank.ACE, Suit.SPADES),
      new Card(Rank.TWO, Suit.SPADES),
      new Card(Rank.SIX, Suit.HEARTS),
      new Card(Rank.FOUR, Suit.SPADES),
      new Card(Rank.TWO, Suit.DIAMONDS));
  PlayerHand flush = new PlayerHand(
      new Card(Rank.ACE, Suit.SPADES),
      new Card(Rank.TWO, Suit.SPADES),
      new Card(Rank.ACE, Suit.SPADES),
      new Card(Rank.FOUR, Suit.SPADES),
      new Card(Rank.ACE, Suit.SPADES));
  PlayerHand threeOfAKind = new PlayerHand(
      new Card(Rank.ACE, Suit.SPADES),
      new Card(Rank.TWO, Suit.SPADES),
      new Card(Rank.ACE, Suit.HEARTS),
      new Card(Rank.FOUR, Suit.SPADES),
      new Card(Rank.ACE, Suit.DIAMONDS));
  PlayerHand fourOfAKind = new PlayerHand(
      new Card(Rank.ACE, Suit.SPADES),
      new Card(Rank.ACE, Suit.CLUBS),
      new Card(Rank.ACE, Suit.HEARTS),
      new Card(Rank.FOUR, Suit.SPADES),
      new Card(Rank.ACE, Suit.DIAMONDS));
      PlayerHand twoPair = new PlayerHand(
      new Card(Rank.ACE, Suit.SPADES),
      new Card(Rank.ACE, Suit.CLUBS),
      new Card(Rank.FOUR, Suit.HEARTS),
      new Card(Rank.FOUR, Suit.SPADES),
      new Card(Rank.KING, Suit.DIAMONDS));
      PlayerHand fullHouse = new PlayerHand(
      new Card(Rank.ACE, Suit.SPADES),
      new Card(Rank.ACE, Suit.CLUBS),
      new Card(Rank.TWO, Suit.HEARTS),
      new Card(Rank.TWO, Suit.SPADES),
      new Card(Rank.TWO, Suit.DIAMONDS));
  PlayerHand fullHouse2 = new PlayerHand(
      new Card(Rank.ACE, Suit.SPADES),
      new Card(Rank.ACE, Suit.CLUBS),
      new Card(Rank.ACE, Suit.HEARTS),
      new Card(Rank.TWO, Suit.SPADES),
      new Card(Rank.TWO, Suit.DIAMONDS));
  PlayerHand straight = new PlayerHand(
      new Card(Rank.THREE, Suit.DIAMONDS),
      new Card(Rank.FOUR, Suit.DIAMONDS),
      new Card(Rank.SEVEN, Suit.HEARTS),
      new Card(Rank.SIX, Suit.CLUBS),
      new Card(Rank.FIVE, Suit.DIAMONDS));

  String flushSequence = "**,*=,*=,*=,*=";
  String threeOfAKindSequence = "**,=*,=*";
  String fourOfAKindSequence = "**,=*,=*,=*";
  String twoPairSequence = "**,=*;**,=*";
  String straightSequence = "**,+*,+*,+*,+*";
//  String fullHouseSequence = "**,=*,=*;**,=*";
  String fullHouseSequence = "**,=*;**,=*,=*";



  @Test
  public void testFlush() {
    assertTrue(hand.parseRuleSequence(flushSequence, flush));
    assertFalse(hand.parseRuleSequence(flushSequence, crap));

    assertTrue(hand.parseRuleSequence(threeOfAKindSequence, threeOfAKind));
    assertFalse(hand.parseRuleSequence(threeOfAKindSequence, crap));

    assertTrue(hand.parseRuleSequence(fourOfAKindSequence, fourOfAKind));
    assertFalse(hand.parseRuleSequence(fourOfAKindSequence, threeOfAKind));
    assertFalse(hand.parseRuleSequence(fourOfAKindSequence, crap));

    assertTrue(hand.parseRuleSequence(twoPairSequence, twoPair));
    assertFalse(hand.parseRuleSequence(twoPairSequence, crap));

    assertTrue(hand.parseRuleSequence(straightSequence, straight));
    assertFalse(hand.parseRuleSequence(straightSequence, crap));

    assertTrue(hand.parseRuleSequence(fullHouseSequence, fullHouse));
    assertTrue(hand.parseRuleSequence(fullHouseSequence, fullHouse2));
    assertFalse(hand.parseRuleSequence(fullHouseSequence, crap));
    assertFalse(hand.parseRuleSequence(fullHouseSequence, twoPair));
    assertFalse(hand.parseRuleSequence(fullHouseSequence, threeOfAKind));
  }


}