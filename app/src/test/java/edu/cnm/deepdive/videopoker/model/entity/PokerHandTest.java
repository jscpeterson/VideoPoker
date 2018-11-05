package edu.cnm.deepdive.videopoker.model.entity;

import static org.junit.Assert.*;

import edu.cnm.deepdive.videopoker.model.Card;
import edu.cnm.deepdive.videopoker.model.Converter;
import edu.cnm.deepdive.videopoker.model.PlayerHand;
import edu.cnm.deepdive.videopoker.model.Rank;
import edu.cnm.deepdive.videopoker.model.Suit;
import org.junit.Test;

@SuppressWarnings("Duplicates")
public class PokerHandTest {

  Converter converter = new Converter();
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
    assertTrue(converter.parseRuleSequence(jacksOrBetterSequence, jacks));
    assertTrue(converter.parseRuleSequence(jacksOrBetterSequence, aces));
    assertFalse(converter.parseRuleSequence(jacksOrBetterSequence, crap));
  }

  @Test
  public void testFlush() {
    assertTrue(converter.parseRuleSequence(flushSequence, flush));
    assertFalse(converter.parseRuleSequence(flushSequence, crap));
  }

  @Test
  public void testRoyalFlush() {
    assertTrue(converter.parseRuleSequence(royalFlushSequence, royalFlush));
    assertFalse(converter.parseRuleSequence(royalFlushSequence, straightFlush));
    assertFalse(converter.parseRuleSequence(royalFlushSequence, flush));
    assertFalse(converter.parseRuleSequence(royalFlushSequence, straight));
    assertFalse(converter.parseRuleSequence(royalFlushSequence, crap));
  }

  @Test
  public void testThreeOfAKind() {
    assertTrue(converter.parseRuleSequence(threeOfAKindSequence, threeOfAKindLow));
    assertTrue(converter.parseRuleSequence(threeOfAKindSequence, threeOfAKindHigh));
    assertFalse(converter.parseRuleSequence(threeOfAKindSequence, crap));
  }

  @Test
  public void testFourOfAKind() {
    assertTrue(converter.parseRuleSequence(fourOfAKindSequence, fourOfAKindLow));
    assertTrue(converter.parseRuleSequence(fourOfAKindSequence, fourOfAKindHigh));
    assertFalse(converter.parseRuleSequence(fourOfAKindSequence, crap));
    assertFalse(converter.parseRuleSequence(fourOfAKindSequence, twoPair1));
    assertFalse(converter.parseRuleSequence(fourOfAKindSequence, twoPair2));
    assertFalse(converter.parseRuleSequence(fourOfAKindSequence, threeOfAKindLow));
    assertFalse(converter.parseRuleSequence(fourOfAKindSequence, threeOfAKindHigh));
  }

  @Test
  public void testTwoPair() {
    assertTrue(converter.parseRuleSequence(twoPairSequence, twoPair1));
    assertTrue(converter.parseRuleSequence(twoPairSequence, twoPair2));
    assertFalse(converter.parseRuleSequence(twoPairSequence, threeOfAKindLow));
    assertFalse(converter.parseRuleSequence(twoPairSequence, threeOfAKindHigh));
    assertFalse(converter.parseRuleSequence(twoPairSequence, crap));
  }

  @Test
  public void testStraight() {
    assertTrue(converter.parseRuleSequence(straightSequence, straight));
    assertTrue(converter.parseRuleSequence(straightSequenceAceHigh, aceHighStraight));
    assertTrue(converter.parseRuleSequence(straightSequence, aceLowStraight));
    assertFalse(converter.parseRuleSequence(straightSequenceAceHigh, crap));
    assertFalse(converter.parseRuleSequence(straightSequence, crap));
  }

  @Test
  public void testFullHouse() {
    assertTrue(converter.parseRuleSequence(fullHouseSequence, fullHouse));
    assertTrue(converter.parseRuleSequence(fullHouseSequence, fullHouse2));
    assertFalse(converter.parseRuleSequence(fullHouseSequence, crap));
    assertFalse(converter.parseRuleSequence(fullHouseSequence, twoPair1));
    assertFalse(converter.parseRuleSequence(fullHouseSequence, twoPair2));
    assertFalse(converter.parseRuleSequence(fullHouseSequence, threeOfAKindLow));
    assertFalse(converter.parseRuleSequence(fullHouseSequence, threeOfAKindHigh));
  }

  @Test
  public void testStraightFlush() {
    assertTrue(converter.parseRuleSequence(straightFlushSequence, straightFlush));
    assertFalse(converter.parseRuleSequence(straightFlushSequence, straight));
    assertFalse(converter.parseRuleSequence(straightFlushSequence, aceLowStraight));
    assertFalse(converter.parseRuleSequence(straightFlushSequence, flush));
    assertFalse(converter.parseRuleSequence(straightFlushSequence, crap));
  }
}