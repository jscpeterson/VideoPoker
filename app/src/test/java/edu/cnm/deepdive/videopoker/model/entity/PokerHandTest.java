package edu.cnm.deepdive.videopoker.model.entity;

import static org.junit.Assert.*;

import android.annotation.SuppressLint;
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
  PlayerHand threeOfAKindHigh = new PlayerHand(
      new Card(Rank.KING, Suit.SPADES),
      new Card(Rank.TWO, Suit.SPADES),
      new Card(Rank.KING, Suit.HEARTS),
      new Card(Rank.FOUR, Suit.SPADES),
      new Card(Rank.KING, Suit.DIAMONDS));
  PlayerHand threeOfAKindLow = new PlayerHand(
      new Card(Rank.TWO, Suit.SPADES),
      new Card(Rank.TWO, Suit.HEARTS),
      new Card(Rank.KING, Suit.HEARTS),
      new Card(Rank.FOUR, Suit.SPADES),
      new Card(Rank.TWO, Suit.DIAMONDS));
  PlayerHand fourOfAKindLow = new PlayerHand(
      new Card(Rank.TWO, Suit.SPADES),
      new Card(Rank.TWO, Suit.CLUBS),
      new Card(Rank.TWO, Suit.HEARTS),
      new Card(Rank.FOUR, Suit.SPADES),
      new Card(Rank.TWO, Suit.DIAMONDS));
  PlayerHand fourOfAKindHigh = new PlayerHand(
      new Card(Rank.KING, Suit.SPADES),
      new Card(Rank.KING, Suit.CLUBS),
      new Card(Rank.KING, Suit.HEARTS),
      new Card(Rank.FOUR, Suit.SPADES),
      new Card(Rank.KING, Suit.DIAMONDS));
  PlayerHand twoPair1 = new PlayerHand(
      new Card(Rank.SIX, Suit.SPADES),
      new Card(Rank.SIX, Suit.CLUBS),
      new Card(Rank.FOUR, Suit.HEARTS),
      new Card(Rank.FOUR, Suit.SPADES),
      new Card(Rank.QUEEN, Suit.DIAMONDS));
  PlayerHand twoPair2 = new PlayerHand(
      new Card(Rank.KING, Suit.SPADES),
      new Card(Rank.KING, Suit.CLUBS),
      new Card(Rank.FOUR, Suit.HEARTS),
      new Card(Rank.FOUR, Suit.SPADES),
      new Card(Rank.QUEEN, Suit.DIAMONDS));
  PlayerHand straight = new PlayerHand(
      new Card(Rank.THREE, Suit.DIAMONDS),
      new Card(Rank.FOUR, Suit.DIAMONDS),
      new Card(Rank.SEVEN, Suit.HEARTS),
      new Card(Rank.SIX, Suit.CLUBS),
      new Card(Rank.FIVE, Suit.DIAMONDS));
  PlayerHand aceLowStraight = new PlayerHand(
      new Card(Rank.ACE, Suit.HEARTS),
      new Card(Rank.TWO, Suit.DIAMONDS),
      new Card(Rank.THREE, Suit.CLUBS),
      new Card(Rank.FOUR, Suit.SPADES),
      new Card(Rank.FIVE, Suit.SPADES));
  PlayerHand aceHighStraight = new PlayerHand(
      new Card(Rank.ACE, Suit.HEARTS),
      new Card(Rank.KING, Suit.DIAMONDS),
      new Card(Rank.QUEEN, Suit.CLUBS),
      new Card(Rank.JACK, Suit.SPADES),
      new Card(Rank.TEN, Suit.SPADES));

  String flushSequence = "**,*=,*=,*=,*=";
  String threeOfAKindSequence = "**,=*,=*";
  String fourOfAKindSequence = "**,=*,=*,=*";
  String twoPairSequence = "**,=*;**,=*";
  String straightSequence = "**,+*,+*,+*,+*";

  @Test
  public void testFlush() {
    assertTrue(hand.parseRuleSequence(flushSequence, flush));
    assertFalse(hand.parseRuleSequence(flushSequence, crap));
  }

  @Test
  public void testThreeOfAKind() {
    assertTrue(hand.parseRuleSequence(threeOfAKindSequence, threeOfAKindLow));
    assertTrue(hand.parseRuleSequence(threeOfAKindSequence, threeOfAKindHigh));
    assertFalse(hand.parseRuleSequence(threeOfAKindSequence, crap));
  }

  @Test
  public void testFourOfAKind() {
    assertTrue(hand.parseRuleSequence(fourOfAKindSequence, fourOfAKindLow));
    assertTrue(hand.parseRuleSequence(fourOfAKindSequence, fourOfAKindHigh));
    assertFalse(hand.parseRuleSequence(fourOfAKindSequence, threeOfAKindLow));
    assertFalse(hand.parseRuleSequence(fourOfAKindSequence, twoPair1));
    assertFalse(hand.parseRuleSequence(fourOfAKindSequence, twoPair2));
    assertFalse(hand.parseRuleSequence(fourOfAKindSequence, crap));
  }

  @Test
  public void testTwoPair() {
    assertTrue(hand.parseRuleSequence(twoPairSequence, twoPair1));
    assertTrue(hand.parseRuleSequence(twoPairSequence, twoPair2));
    assertFalse(hand.parseRuleSequence(twoPairSequence, threeOfAKindLow));
    assertFalse(hand.parseRuleSequence(twoPairSequence, threeOfAKindHigh));
    assertFalse(hand.parseRuleSequence(twoPairSequence, crap));
  }

  @Test
  public void testStraight() {
    assertTrue(hand.parseRuleSequence(straightSequence, straight));
    assertTrue(hand.parseRuleSequence(straightSequence, aceHighStraight));
    //TODO Fix ace low straight
//    assertTrue(hand.parseRuleSequence(straightSequence, aceLowStraight));
    assertFalse(hand.parseRuleSequence(straightSequence, crap));
  }

  //Highest card value must be first, otherwise two of a three of a kind will be removed!
  String fullHouseSequence = "**,=*,=*;**,=*";
//  String fullHouseSequence = "**,=*;**,=*,=*";

  PlayerHand fullHouse = new PlayerHand(
      new Card(Rank.TWO, Suit.SPADES),
      new Card(Rank.TWO, Suit.CLUBS),
      new Card(Rank.THREE, Suit.HEARTS),
      new Card(Rank.THREE, Suit.SPADES),
      new Card(Rank.THREE, Suit.DIAMONDS));
  PlayerHand fullHouse2 = new PlayerHand(
      new Card(Rank.TWO, Suit.SPADES),
      new Card(Rank.TWO, Suit.CLUBS),
      new Card(Rank.TWO, Suit.HEARTS),
      new Card(Rank.THREE, Suit.SPADES),
      new Card(Rank.THREE, Suit.DIAMONDS));

  @Test
  public void testFullHouse() {
    assertTrue(hand.parseRuleSequence(fullHouseSequence, fullHouse2));
    assertTrue(hand.parseRuleSequence(fullHouseSequence, fullHouse));
    assertFalse(hand.parseRuleSequence(fullHouseSequence, crap));
    assertFalse(hand.parseRuleSequence(fullHouseSequence, twoPair1));
    assertFalse(hand.parseRuleSequence(fullHouseSequence, twoPair2));
    assertFalse(hand.parseRuleSequence(fullHouseSequence, threeOfAKindLow));
    assertFalse(hand.parseRuleSequence(fullHouseSequence, threeOfAKindHigh));
  }
}