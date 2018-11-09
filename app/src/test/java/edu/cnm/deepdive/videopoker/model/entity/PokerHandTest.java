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

  private Converter converter = new Converter();
  private PlayerHand crap = new PlayerHand(
      new Card(Rank.ACE, Suit.SPADES),
      new Card(Rank.TWO, Suit.SPADES),
      new Card(Rank.SIX, Suit.HEARTS),
      new Card(Rank.JACK, Suit.SPADES),
      new Card(Rank.TWO, Suit.DIAMONDS));
  private PlayerHand jacks = new PlayerHand(
      new Card(Rank.ACE, Suit.SPADES),
      new Card(Rank.JACK, Suit.SPADES),
      new Card(Rank.SIX, Suit.HEARTS),
      new Card(Rank.QUEEN, Suit.SPADES),
      new Card(Rank.JACK, Suit.DIAMONDS));
  private PlayerHand aces = new PlayerHand(
      new Card(Rank.ACE, Suit.SPADES),
      new Card(Rank.ACE, Suit.DIAMONDS),
      new Card(Rank.SIX, Suit.HEARTS),
      new Card(Rank.FOUR, Suit.SPADES),
      new Card(Rank.JACK, Suit.DIAMONDS));
  private PlayerHand flush = new PlayerHand(
      new Card(Rank.ACE, Suit.SPADES),
      new Card(Rank.TWO, Suit.SPADES),
      new Card(Rank.ACE, Suit.SPADES),
      new Card(Rank.FOUR, Suit.SPADES),
      new Card(Rank.ACE, Suit.SPADES));
  private PlayerHand threeOfAKindHigh = new PlayerHand(
      new Card(Rank.KING, Suit.SPADES),
      new Card(Rank.TWO, Suit.SPADES),
      new Card(Rank.KING, Suit.HEARTS),
      new Card(Rank.FOUR, Suit.SPADES),
      new Card(Rank.KING, Suit.DIAMONDS));
  private PlayerHand threeOfAKindLow = new PlayerHand(
      new Card(Rank.TWO, Suit.SPADES),
      new Card(Rank.TWO, Suit.HEARTS),
      new Card(Rank.KING, Suit.HEARTS),
      new Card(Rank.FOUR, Suit.SPADES),
      new Card(Rank.TWO, Suit.DIAMONDS));
  private PlayerHand fourOfAKindLow = new PlayerHand(
      new Card(Rank.TWO, Suit.SPADES),
      new Card(Rank.TWO, Suit.CLUBS),
      new Card(Rank.TWO, Suit.HEARTS),
      new Card(Rank.FOUR, Suit.SPADES),
      new Card(Rank.TWO, Suit.DIAMONDS));
  private PlayerHand fourOfAKindHigh = new PlayerHand(
      new Card(Rank.KING, Suit.SPADES),
      new Card(Rank.KING, Suit.CLUBS),
      new Card(Rank.KING, Suit.HEARTS),
      new Card(Rank.FOUR, Suit.SPADES),
      new Card(Rank.KING, Suit.DIAMONDS));
  private PlayerHand twoPair1 = new PlayerHand(
      new Card(Rank.SIX, Suit.SPADES),
      new Card(Rank.SIX, Suit.CLUBS),
      new Card(Rank.FOUR, Suit.HEARTS),
      new Card(Rank.FOUR, Suit.SPADES),
      new Card(Rank.QUEEN, Suit.DIAMONDS));
  private PlayerHand twoPair2 = new PlayerHand(
      new Card(Rank.KING, Suit.SPADES),
      new Card(Rank.KING, Suit.CLUBS),
      new Card(Rank.FOUR, Suit.HEARTS),
      new Card(Rank.FOUR, Suit.SPADES),
      new Card(Rank.QUEEN, Suit.DIAMONDS));
  private PlayerHand straight = new PlayerHand(
      new Card(Rank.THREE, Suit.DIAMONDS),
      new Card(Rank.FOUR, Suit.DIAMONDS),
      new Card(Rank.SEVEN, Suit.HEARTS),
      new Card(Rank.SIX, Suit.CLUBS),
      new Card(Rank.FIVE, Suit.DIAMONDS));
  private PlayerHand aceLowStraight = new PlayerHand(
      new Card(Rank.ACE, Suit.HEARTS),
      new Card(Rank.TWO, Suit.DIAMONDS),
      new Card(Rank.THREE, Suit.CLUBS),
      new Card(Rank.FOUR, Suit.SPADES),
      new Card(Rank.FIVE, Suit.SPADES));
  private PlayerHand aceHighStraight = new PlayerHand(
      new Card(Rank.ACE, Suit.HEARTS),
      new Card(Rank.KING, Suit.DIAMONDS),
      new Card(Rank.QUEEN, Suit.CLUBS),
      new Card(Rank.JACK, Suit.SPADES),
      new Card(Rank.TEN, Suit.SPADES));
  private PlayerHand straightFlush = new PlayerHand(
      new Card(Rank.THREE, Suit.DIAMONDS),
      new Card(Rank.FOUR, Suit.DIAMONDS),
      new Card(Rank.SEVEN, Suit.DIAMONDS),
      new Card(Rank.SIX, Suit.DIAMONDS),
      new Card(Rank.FIVE, Suit.DIAMONDS));
  private PlayerHand royalFlush = new PlayerHand(
      new Card(Rank.KING, Suit.DIAMONDS),
      new Card(Rank.JACK, Suit.DIAMONDS),
      new Card(Rank.QUEEN, Suit.DIAMONDS),
      new Card(Rank.TEN, Suit.DIAMONDS),
      new Card(Rank.ACE, Suit.DIAMONDS));
  private PlayerHand fullHouse = new PlayerHand(
      new Card(Rank.TWO, Suit.SPADES),
      new Card(Rank.TWO, Suit.CLUBS),
      new Card(Rank.THREE, Suit.HEARTS),
      new Card(Rank.THREE, Suit.SPADES),
      new Card(Rank.THREE, Suit.DIAMONDS));
  private PlayerHand fullHouse2 = new PlayerHand(
      new Card(Rank.TWO, Suit.SPADES),
      new Card(Rank.TWO, Suit.CLUBS),
      new Card(Rank.TWO, Suit.HEARTS),
      new Card(Rank.THREE, Suit.SPADES),
      new Card(Rank.THREE, Suit.DIAMONDS));
  private PlayerHand fourAces = new PlayerHand(
      new Card(Rank.ACE, Suit.SPADES),
      new Card(Rank.ACE, Suit.DIAMONDS),
      new Card(Rank.ACE, Suit.CLUBS),
      new Card(Rank.JACK, Suit.CLUBS),
      new Card(Rank.ACE, Suit.HEARTS));
  private PlayerHand fourThrees = new PlayerHand(
      new Card(Rank.THREE, Suit.SPADES),
      new Card(Rank.THREE, Suit.DIAMONDS),
      new Card(Rank.THREE, Suit.CLUBS),
      new Card(Rank.TWO, Suit.CLUBS),
      new Card(Rank.THREE, Suit.HEARTS));
  private PlayerHand fourFours = new PlayerHand(
      new Card(Rank.FIVE, Suit.CLUBS),
      new Card(Rank.FOUR, Suit.SPADES),
      new Card(Rank.FOUR, Suit.DIAMONDS),
      new Card(Rank.FOUR, Suit.HEARTS),
      new Card(Rank.FOUR, Suit.CLUBS));


  //Longest pattern sequence must be first, otherwise two of a three of a kind will be removed!
  private String royalFlushSequence = "A=,T=,J=,Q=,K=";
  private String straightFlushSequence = "**,+=,+=,+=,+=";
  private String fourOfAKindSequence = "**,=*,=*,=*";
  private String fullHouseSequence = "**,=*,=*;**,=*";
  private String flushSequence = "**,*=,*=,*=,*=";
  private String straightSequenceAceHigh = "A*,T*,J*,Q*,K*";
  private String straightSequence = "**,+*,+*,+*,+*";
  private String threeOfAKindSequence = "**,=*,=*";
  private String twoPairSequence = "**,=*;**,=*";
  private String jacksOrBetterSequence = "F*,=*";
  //TODO test additional hand sequences
  private String fourTwosSequence = "2*,=*,=*,=*";
  private String fourThreesSequence = "3*,=*,=*,=*";
  private String fourFoursSequence = "4*,=*,=*,=*";

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
  public void testFourTwos() {
    assertTrue(converter.parseRuleSequence(fourTwosSequence, fourOfAKindLow));
    assertFalse(converter.parseRuleSequence(fourTwosSequence, fourOfAKindHigh));
    assertFalse(converter.parseRuleSequence(fourTwosSequence, crap));
    assertFalse(converter.parseRuleSequence(fourTwosSequence, twoPair1));
    assertFalse(converter.parseRuleSequence(fourTwosSequence, twoPair2));
    assertFalse(converter.parseRuleSequence(fourTwosSequence, threeOfAKindLow));
    assertFalse(converter.parseRuleSequence(fourTwosSequence, threeOfAKindHigh));
  }

  @Test
  public void testFourThrees() {
    assertTrue(converter.parseRuleSequence(fourThreesSequence, fourThrees));
    assertFalse(converter.parseRuleSequence(fourThreesSequence, fourOfAKindHigh));
    assertFalse(converter.parseRuleSequence(fourThreesSequence, crap));
    assertFalse(converter.parseRuleSequence(fourThreesSequence, twoPair1));
    assertFalse(converter.parseRuleSequence(fourThreesSequence, twoPair2));
    assertFalse(converter.parseRuleSequence(fourThreesSequence, threeOfAKindLow));
    assertFalse(converter.parseRuleSequence(fourThreesSequence, threeOfAKindHigh));
  }

  @Test
  public void testFourFours() {
    assertTrue(converter.parseRuleSequence(fourFoursSequence, fourFours));
    assertFalse(converter.parseRuleSequence(fourFoursSequence, fourOfAKindHigh));
    assertFalse(converter.parseRuleSequence(fourFoursSequence, crap));
    assertFalse(converter.parseRuleSequence(fourFoursSequence, twoPair1));
    assertFalse(converter.parseRuleSequence(fourFoursSequence, twoPair2));
    assertFalse(converter.parseRuleSequence(fourFoursSequence, threeOfAKindLow));
    assertFalse(converter.parseRuleSequence(fourFoursSequence, threeOfAKindHigh));
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