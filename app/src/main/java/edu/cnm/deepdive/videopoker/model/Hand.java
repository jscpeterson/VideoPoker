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

public class Hand extends Stack<Card> {

  // TODO bring in unit tests
  // TODO optimize, especially Jacks or Better method
  // TODO separate paytable data from Hand
  // TODO implement "best hand" field ready with an accessor

  private final String ROYAL_FLUSH = "Royal Flush";
  private boolean royalFlush;
  private final String STRAIGHT_FLUSH = "Straight Flush";
  private boolean straightFlush;
  private final String STRAIGHT = "Straight";
  private boolean straight;
  private final String FLUSH = "Flush";
  private boolean flush;
  private final String FULL_HOUSE = "Full House";
  private boolean fullhouse;
  private final String FOUR_OF_A_KIND = "Four of a Kind";
  private boolean fourofakind;
  private final String THREE_OF_A_KIND = "Three of a Kind";
  private boolean threeofakind;
  private final String TWO_PAIR = "Two Pair";
  private boolean twopair;
  private final String JACKS_OR_BETTER = "Jacks or Better";
  private boolean jacksorbetter;
  private Map<String, Integer> betOnePayTable = new Hashtable<>();
  private Map<String, Integer> betFivePayTable = new Hashtable<>();
  private Map<Rank, Integer> rankMap = new Hashtable<>();

  void makePayTable() {
    betOnePayTable.put(ROYAL_FLUSH, 800);
    betOnePayTable.put(STRAIGHT_FLUSH, 50);
    betOnePayTable.put(FOUR_OF_A_KIND, 25);
    betOnePayTable.put(FULL_HOUSE, 9);
    betOnePayTable.put(FLUSH, 6);
    betOnePayTable.put(STRAIGHT, 4);
    betOnePayTable.put(THREE_OF_A_KIND, 3);
    betOnePayTable.put(TWO_PAIR, 2);
    betOnePayTable.put(JACKS_OR_BETTER, 1);
    betOnePayTable.put("BUST", 0);
  }

  public int getHandScore(int bet) {
    evaluateHand();
    makePayTable();
    return (bet*betOnePayTable.get(returnBestHand()));
  }

  void evaluateHand() {
    rankMap = mapLikeCards();
    flush = checkForFlush();
    straight = checkForStraight();
    if (flush && straight) {
      if (checkForRoyalFlush())
        royalFlush = true;
      straightFlush = true;
    }
    if (checkForFullHouse()) {
      fullhouse = true;
    }
    else if (checkForFourOfAKind()) {
      fourofakind = true;
      threeofakind = true;
    }
    else if (checkForThreeOfAKind()) {
      threeofakind = true;
    }
    twopair = checkForTwoPair();
    jacksorbetter = checkForJacksOrBetter();
  }

  public String returnBestHand(){
    if (royalFlush) return ROYAL_FLUSH;
    if (straightFlush) return STRAIGHT_FLUSH;
    if (fourofakind) return FOUR_OF_A_KIND;
    if (fullhouse) return FULL_HOUSE;
    if (flush) return FLUSH;
    if (straight) return STRAIGHT;
    if (threeofakind) return THREE_OF_A_KIND;
    if (twopair) return TWO_PAIR;
    if (jacksorbetter) return JACKS_OR_BETTER;
    return "BUST";
  }

  boolean checkForRoyalFlush() {
    Set<Rank> rankSet = rankMap.keySet();
    List<Rank> royalFlushValues = Arrays.asList(Rank.KING, Rank.QUEEN, Rank.ACE, Rank.JACK, Rank.TEN);
    return (rankSet.containsAll(royalFlushValues) && checkForFlush());
  }

  boolean checkForStraightFlush() {
    return (checkForStraight() && checkForFlush());
  }

  boolean checkForStraight() {
    if (rankMap.values().size() < 5) {
      return false;
    }
    List<Rank> rankList = new ArrayList<>();
    for (Card card : this) {
      rankList.add(card.getRank());
    }
    Collections.sort(rankList);

    return (rankList.get(0).getValue() + 4 == rankList.get(4).getValue());
  }

  boolean checkForFlush() {
    Card cardToCheckSuit = this.get(0);
    for (Card card : this) {
      if (!card.getSuit().equals(cardToCheckSuit.getSuit())) return false;
    }
    return true;
  }

  boolean checkForFullHouse() {
    Collection<Integer> rankValues = rankMap.values();
    return (rankValues.contains(2) && rankValues.contains(3));
  }

  boolean checkForFourOfAKind() {
    return rankMap.containsValue(4);
  }

  boolean checkForThreeOfAKind() {
    return rankMap.containsValue(3);
  }

  boolean checkForTwoPair() {
    Collection<Integer> rankValues = rankMap.values();
    int pairsFound = 0;
    for (Integer value : rankValues) {
      if (value == 2) pairsFound++;
    }
    return pairsFound == 2;
  }

  boolean checkForJacksOrBetter() {
    //iterate over deck
    for (Card card : this) {
      //check if card is Jack or Better
      if (card.getRank().getValue() >= 11 || card.getRank().getValue() == 1) {
        //if Jack or Better, iterate over rest of deck
        for (Card comparisonCard : this) {
          //if one rank matches original card, return true
          //also check to make sure card is not the original card itself
          if (!card.equals(comparisonCard) && card.getRank().equals(comparisonCard.getRank())) return true;
        }
      }
    }
    //if original loop reaches end of deck, return false
    return false;
  }

  Map mapLikeCards() {
    rankMap.clear();
    for (Card card : this) {
      if (!rankMap.containsKey(card.getRank())) {
        rankMap.put(card.getRank(), 1);
      }
      else {
        int rankVal = rankMap.get(card.getRank());
        rankMap.put(card.getRank(), rankVal + 1);
      }
    }
//    System.out.println(rankMap);
    return rankMap;
  }

}
