package edu.cnm.deepdive.videopoker.model;

import java.util.Collections;
import java.util.Random;
import java.util.Stack;

/**
 * This class represents a deck of card objects. It is assumed to be a standard 52 card deck and
 * generates 52 cards from rank Ace to King of suits Clubs, Diamonds, Hearts, Spades upon creation.
 * The RNG to be used for shuffling is passed into the constructor.
 */
public class Deck extends Stack<Card> {

  /**
   * The random number generated to be used in shuffling.
   */
  private Random rng;

  /**
   * On creation of a Deck object it is automatically populated with 52 Card objects of a standard
   * 52 card deck of rank Ace through King, suit Clubs Diamonds Hearts Spades.
   *
   * @param rng the Random object to be used for shuffling.
   */
  public Deck(Random rng) {
    this.rng = rng;
    generateStandardDeck();
  }

  private void generateStandardDeck() {
    for (Suit suit : Suit.values()) {
      for (Rank rank : Rank.values()) {
        Card card = new Card(rank, suit);
        this.push(card);
      }
    }
  }

  /**
   * Shuffles the deck according to the Random object held in this class.
   */
  public void shuffle() {
    Collections.shuffle(this, rng);
  }

  /**
   * Creates a new hand by dealing numberOfCards from the start of the deck.
   *
   * @param numberOfCards the size of the hand to be dealt.
   * @return a new hand of numberOfCards length.
   */
  public PlayerHand deal(int numberOfCards) {
    PlayerHand playerHand = new PlayerHand();
    for (int i = 0; i < numberOfCards; ++i) {
      playerHand.push(this.remove(0));
    }
    return playerHand;
  }

  /**
   * Using an existing playerHand, adds cards from the start of the deck to end the playerHand while
   * taking cards from start of the playerHand and returning them to end of the deck.
   *
   * @param playerHand an existing playerHand of cards.
   */
  public void dealAndReplace(PlayerHand playerHand) {
    for (int i = 0; i < playerHand.size(); ++i) {
      playerHand.push(this.remove(0));
      this.add(playerHand.remove(0));
    }
  }

  /**
   * @return the size of this deck.
   */
  int getSize() {
    return this.size();
  }

}
