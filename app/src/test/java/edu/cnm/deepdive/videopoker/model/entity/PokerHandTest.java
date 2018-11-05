package edu.cnm.deepdive.videopoker.model.entity;

import static org.junit.Assert.*;

import android.annotation.SuppressLint;
import edu.cnm.deepdive.videopoker.model.Card;
import edu.cnm.deepdive.videopoker.model.PlayerHand;
import edu.cnm.deepdive.videopoker.model.Rank;
import edu.cnm.deepdive.videopoker.model.Suit;
import java.util.DuplicateFormatFlagsException;
import org.junit.Test;

@SuppressWarnings("Duplicates")
public class PokerHandTest {

  PokerHand hand = new PokerHand();
  PlayerHand crap = new PlayerHand(
      new Card(Rank.ACE, Suit.SPADES),
      new Card(Rank.TWO, Suit.SPADES),
      new Card(Rank.SIX, Suit.HEARTS),
      new Card(Rank.JACK, Suit.SPADES),
      new Card(Rank.TWO, Suit.DIAMONDS));
  PlayerHand jacks = new PlayerHand(
      new Card(Rank.ACE, Suit.SPADES),
      new Card(Rank.JACK, Suit.SPADES),
      new Card(Rank.SIX, Suit.HEARTS),
      new Card(Rank.QUEEN, Suit.SPADES),
      new Card(Rank.JACK, Suit.DIAMONDS));
  PlayerHand aces = new PlayerHand(
      new Card(Rank.ACE, Suit.SPADES),
      new Card(Rank.ACE, Suit.DIAMONDS),
      new Card(Rank.SIX, Suit.HEARTS),
      new Card(Rank.FOUR, Suit.SPADES),
      new Card(Rank.JACK, Suit.DIAMONDS));
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
  PlayerHand straightFlush = new PlayerHand(
      new Card(Rank.THREE, Suit.DIAMONDS),
      new Card(Rank.FOUR, Suit.DIAMONDS),
      new Card(Rank.SEVEN, Suit.DIAMONDS),
      new Card(Rank.SIX, Suit.DIAMONDS),
      new Card(Rank.FIVE, Suit.DIAMONDS));
  PlayerHand royalFlush = new PlayerHand(
      new Card(Rank.KING, Suit.DIAMONDS),
      new Card(Rank.JACK, Suit.DIAMONDS),
      new Card(Rank.QUEEN, Suit.DIAMONDS),
      new Card(Rank.TEN, Suit.DIAMONDS),
      new Card(Rank.ACE, Suit.DIAMONDS));
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

  //Longest pattern sequence must be first, otherwise two of a three of a kind will be removed!
  String royalFlushSequence = "A=,T=,J=,Q=,K=";
  String straightFlushSequence = "**,+=,+=,+=,+=";
  String fourOfAKindSequence = "**,=*,=*,=*";
  String fullHouseSequence = "**,=*,=*;**,=*";
  String flushSequence = "**,*=,*=,*=,*=";
  String straightSequenceAceHigh = "A*,T*,J*,Q*,K*";
  String straightSequence = "**,+*,+*,+*,+*";
  String threeOfAKindSequence = "**,=*,=*";
  String twoPairSequence = "**,=*;**,=*";
  String jacksOrBetterSequence = "F*,=*";

  @Test
  public void testJacksOrBetter() {
    assertTrue(hand.parseRuleSequence(jacksOrBetterSequence, jacks));
    assertTrue(hand.parseRuleSequence(jacksOrBetterSequence, aces));
    assertFalse(hand.parseRuleSequence(jacksOrBetterSequence, crap));
  }

  @Test
  public void testFlush() {
    assertTrue(hand.parseRuleSequence(flushSequence, flush));
    assertFalse(hand.parseRuleSequence(flushSequence, crap));
  }

  @Test
  public void testRoyalFlush() {
    assertTrue(hand.parseRuleSequence(royalFlushSequence, royalFlush));
    assertFalse(hand.parseRuleSequence(royalFlushSequence, straightFlush));
    assertFalse(hand.parseRuleSequence(royalFlushSequence, flush));
    assertFalse(hand.parseRuleSequence(royalFlushSequence, straight));
    assertFalse(hand.parseRuleSequence(royalFlushSequence, crap));
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
    assertFalse(hand.parseRuleSequence(fourOfAKindSequence, crap));
    assertFalse(hand.parseRuleSequence(fourOfAKindSequence, twoPair1));
    assertFalse(hand.parseRuleSequence(fourOfAKindSequence, twoPair2));
    assertFalse(hand.parseRuleSequence(fourOfAKindSequence, threeOfAKindLow));
    assertFalse(hand.parseRuleSequence(fourOfAKindSequence, threeOfAKindHigh));
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
    assertTrue(hand.parseRuleSequence(straightSequenceAceHigh, aceHighStraight));
    assertTrue(hand.parseRuleSequence(straightSequence, aceLowStraight));
    assertFalse(hand.parseRuleSequence(straightSequenceAceHigh, crap));
    assertFalse(hand.parseRuleSequence(straightSequence, crap));
  }

  @Test
  public void testFullHouse() {
    assertTrue(hand.parseRuleSequence(fullHouseSequence, fullHouse));
    assertTrue(hand.parseRuleSequence(fullHouseSequence, fullHouse2));
    assertFalse(hand.parseRuleSequence(fullHouseSequence, crap));
    assertFalse(hand.parseRuleSequence(fullHouseSequence, twoPair1));
    assertFalse(hand.parseRuleSequence(fullHouseSequence, twoPair2));
    assertFalse(hand.parseRuleSequence(fullHouseSequence, threeOfAKindLow));
    assertFalse(hand.parseRuleSequence(fullHouseSequence, threeOfAKindHigh));
  }

  @Test
  public void testStraightFlush() {
    assertTrue(hand.parseRuleSequence(straightFlushSequence, straightFlush));
    assertFalse(hand.parseRuleSequence(straightFlushSequence, straight));
    assertFalse(hand.parseRuleSequence(straightFlushSequence, aceLowStraight));
    assertFalse(hand.parseRuleSequence(straightFlushSequence, flush));
    assertFalse(hand.parseRuleSequence(straightFlushSequence, crap));
  }
}